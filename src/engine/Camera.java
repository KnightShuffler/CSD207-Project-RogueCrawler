package engine;
import org.joml.*;

public class Camera {
	
		private Vector3f position;
		private Matrix4f projection;
		
		public Camera(int width, int height){
			position = new Vector3f(0,0,0);
			projection = new Matrix4f().setOrtho2D(-width/2, width/2, -height/2, height/2);
			}

		public void setPosition(Vector3f position){
			this.position=position;
		}
		
		public void addPosition(Vector3f position){
			this.position.add(position);
		}
		
		public Vector3f getPosition(){
			return position;
		}
		
		public Matrix4f projection(){
			Matrix4f target = new Matrix4f();
			Matrix4f pos = new Matrix4f().setTranslation(position);
			
			target=projection.mul(pos, target);
			return target;
		}
}
