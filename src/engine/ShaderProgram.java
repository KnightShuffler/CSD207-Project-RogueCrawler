package engine;

import static org.lwjgl.opengl.GL20.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

public class ShaderProgram {
	int programID;
	int vertexShaderID;
	int fragmentShaderID;

	public ShaderProgram(String vertexShaderPath, String fragmentShaderPath) {
		programID = glCreateProgram();

		vertexShaderID = compileShader(vertexShaderPath, GL_VERTEX_SHADER);
		fragmentShaderID = compileShader(fragmentShaderPath, GL_FRAGMENT_SHADER);

		glAttachShader(programID, vertexShaderID);
		glAttachShader(programID, fragmentShaderID);

		// Pre-Linking
		bindAttributes(0, "vertices");
		bindAttributes(1, "colors");
		bindAttributes(2, "textures");

		linkShaders();
	}

	protected void finalize() throws Throwable {
		glDeleteProgram(programID);
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

		// Load the shader
		glShaderSource(shader, fileContents.toString());

		// Compile the shader
		glCompileShader(shader);

		// Error checking in the shader
		if (glGetShaderi(shader, GL_COMPILE_STATUS) != 1) {
			System.err.println(shaderPath + "\nShader could not compile");
			System.err.println(glGetShaderInfoLog(shader));
			System.exit(1);
		}

		// return the shader id
		return shader;
	}

	public void bindAttributes(int index, String attribName) {
		glBindAttribLocation(programID, index, attribName);
	}

	private void linkShaders() {
		glLinkProgram(programID);

		// Error checking in the linking
		if (glGetProgrami(programID, GL_LINK_STATUS) != 1) {
			System.err.println("Program failed to link");
			System.err.println(glGetProgramInfoLog(programID));
			System.exit(1);
		}

		glValidateProgram(programID);
		if (glGetProgrami(programID, GL_VALIDATE_STATUS) != 1) {
			System.err.println("Program failed to validate");
			System.err.println(glGetProgramInfoLog(programID));
			System.exit(1);
		}

		glDetachShader(programID, vertexShaderID);
		glDetachShader(programID, fragmentShaderID);
		glDeleteShader(vertexShaderID);
		glDeleteShader(fragmentShaderID);

	}

	// setting an integer uniform
	public void setUniform(String name, int value) {
		int location = glGetUniformLocation(programID, name);
		if (location != -1) {
			glUniform1i(location, value);
		}
	}

	// setting a float uniform
	public void setUniform(String name, float value) {
		int location = glGetUniformLocation(programID, name);
		if (location != -1) {
			glUniform1f(location, value);
		}
	}

	// setting a Matrix4f
	public void setUniform(String name, Matrix4f value) {
		int location = glGetUniformLocation(programID, name);
		FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
		value.get(buffer);
		if (location != -1) {
			glUniformMatrix4fv(location, false, buffer);
		}
	}

	public void bind() {
		glUseProgram(programID);
	}

	public void unbind() {
		glUseProgram(0);
	}
}
