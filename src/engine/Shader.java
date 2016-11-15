package engine;

import static org.lwjgl.opengl.GL20.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;


public class Shader {
	int m_programID;
	int m_vertexShaderID;
	int m_fragmentShaderID;
	
	private int m_attributeIndex = 0;
	
	public Shader(String vertexShaderPath, String fragmentShaderPath) {
		m_attributeIndex = 0;
		m_programID = glCreateProgram();
		
		m_vertexShaderID = compileShader(vertexShaderPath, GL_VERTEX_SHADER);
		m_fragmentShaderID = compileShader(fragmentShaderPath, GL_FRAGMENT_SHADER);
		
		linkShaders();
	}
	
	protected void finalize() throws Throwable {
		glDeleteProgram(m_programID);
		super.finalize();
	}
	
	private int compileShader(String shaderPath, int shaderType) {
		int shader = 0;
		try {
			shader = glCreateShader(shaderType);
			if (shader == 0) {
				throw new Exception("Could not create shader\n" + shaderPath);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		StringBuilder fileContents = new StringBuilder();
		BufferedReader br;
		
		try {
			br = new BufferedReader(new FileReader(new File(shaderPath)));
			
			String line;
			while ((line = br.readLine()) != null) {
				fileContents.append(line + "\n");
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Load the shader
		glShaderSource(shader, fileContents.toString());
		
		//Compile the shader
		glCompileShader(shader);
		
		//Error checking in the shader
		if (glGetShaderi(shader, GL_COMPILE_STATUS) != 1) {
			System.err.println(shaderPath + "\nShader could not compile");
			System.err.println(glGetShaderInfoLog(shader));
			System.exit(1);
		}
		
		//return the shader id
		return shader;
	}
	
	public void addAttributes(String attribName) {
		glBindAttribLocation(m_programID, m_attributeIndex++, attribName);
	}
	
	private void linkShaders() {
		glAttachShader(m_programID, m_vertexShaderID);
		glAttachShader(m_programID, m_fragmentShaderID);
		
		glLinkProgram(m_programID);
		
		//Error checking in the linking
		if (glGetProgrami(m_programID, GL_LINK_STATUS) != 1) {
			System.err.println("Program failed to link");
			System.err.println(glGetProgramInfoLog(m_programID));
			System.exit(1);
		}
		
		glValidateProgram(m_programID);
		if (glGetProgrami(m_programID, GL_VALIDATE_STATUS) != 1) {
			System.err.println("Program failed to validate");
			System.err.println(glGetProgramInfoLog(m_programID));
			System.exit(1);
		}
		
		glDetachShader(m_programID, m_vertexShaderID);
		glDetachShader(m_programID, m_fragmentShaderID);
		glDeleteShader(m_vertexShaderID);
		glDeleteShader(m_fragmentShaderID);
		
	}
	
	//setting an integer uniform
	public void setUniform(String name, int value) {
		int location = glGetUniformLocation(m_programID, name);
		if (location != -1) {
			glUniform1f(location, value);
		}
	}
	
	public void setUniform(String name, Matrix4f value) {
		int location = glGetUniformLocation(m_programID, name);
		if (location != -1) {
			FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
			value.get(buffer);
			glUniformMatrix4fv(location, false, buffer);
		}
	}
	
	public void use() {
		glUseProgram(m_programID);
		for (int i = 0; i < m_attributeIndex; i++) {
			glEnableVertexAttribArray(i);
		}
	}
	
	public void unuse() {
		glUseProgram(0);
		for (int i = 0; i < m_attributeIndex; i++) {
			glDisableVertexAttribArray(i);
		}
	}
}
