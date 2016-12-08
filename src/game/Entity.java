package game;

import java.util.ArrayList;

import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector4f;

import engine.ColorRGBA8;
import engine.SpriteBatch;
import engine.UV;

public abstract class Entity {
	// Holds the health of the entity
	protected Health health;
	// Holds the x and y position of the entity (as floats) (in room
	// coordinates)
	protected Vector2f position;
	// Holds the velocity of the entity
	protected Vector2f velocity;
	// Holds the angle of motion that the entity follows
	protected float direction;
	// Holds the width and height of the entity (as integers)
	protected Vector2i dimensions;
	// Speed of the entity in pixels per second
	protected float speed;
	// Speed of the shots that the entity fires (if any) in pixels per second
	protected float shotSpeed;
	// The rate at which the entity shoots its shots (if any) in shots per
	// second
	protected float fireRate;
	// The damage the entity does by shots (and by contact in the case of
	// enemies)
	protected int damage;

	// The handle to the texture of the entity
	private int texture = 0;
	// The color to multiply the texture color by
	private ColorRGBA8 color = ColorRGBA8.WHITE;
	
	private final float ENTITY_RADIUS;
	private final float MINIMUM_TILE_DISTANCE;

	public Entity(int hp, float x, float y, int width, float speed, float shotSpeed, float fireRate,
			int damage, int texture, ColorRGBA8 color) {
		health = new Health(hp);
		position = new Vector2f(x, y);
		velocity = new Vector2f(0f, 0f);
		dimensions = new Vector2i(width);

		this.speed = speed;
		this.shotSpeed = shotSpeed;
		this.fireRate = fireRate;
		this.damage = damage;

		this.texture = texture;
		this.color = color;
		
		ENTITY_RADIUS = (float)width / 2f;
		MINIMUM_TILE_DISTANCE = ENTITY_RADIUS + Room.TILE_RADIUS;
	}

	// Default draw function for entities
	// their depth is their y position
	public void draw(SpriteBatch spb) {
		spb.addGlyph(new Vector4f(position.x, position.y, (float)dimensions.x, (float)dimensions.y),
				UV.DEFAULT_UV_RECT, color, texture, position.y);
	}
	
	public void move(float deltaTime, float idealFrameRate) {
		position = position.add(velocity.mul(deltaTime / idealFrameRate));
//		System.out.println(position.x + " " + position.y);
	}

	// Place holder for collision functions
	public boolean collideWithRoom(Room room) {
		ArrayList<Vector2f> collideTilePositions = new ArrayList<>(4);
		
		checkTilePosition(room, collideTilePositions, position.x + dimensions.x, position.y);
		checkTilePosition(room, collideTilePositions, position.x, position.y + dimensions.y);
		checkTilePosition(room, collideTilePositions, position.x + dimensions.x, position.y);
		checkTilePosition(room, collideTilePositions, position.x + dimensions.x, position.y + dimensions.y);
		
//		System.out.println(collideTilePositions.size());
		
		if (collideTilePositions.isEmpty())
			return false;
		
		/*for (Vector2f pos : collideTilePositions) {
			collideWithTile(pos);
		}*/
		
		collideTilePositions.clear();
		
		return true;
	}
	
	private void collideWithTile(Vector2f tilePos) {
		Vector2f centerPos = new Vector2f();
		centerPos = position.add((float) dimensions.x / 2f, (float) dimensions.y / 2f, centerPos);
		Vector2f distanceVector = new Vector2f();
		distanceVector = centerPos.sub(tilePos, distanceVector);
		
		float xDepth = MINIMUM_TILE_DISTANCE - Math.abs(distanceVector.x),
				yDepth = MINIMUM_TILE_DISTANCE - Math.abs(distanceVector.y);
		
		if (xDepth > 0f && yDepth > 0f) {
			if (Math.min(xDepth, 0f) < Math.min(yDepth, 0f)) {
				if (distanceVector.x < 0f) {
					position.x -= xDepth;
				} else {
					position.x += xDepth;
				}
			} else {
				if (distanceVector.y < 0f) {
					position.y -= yDepth;
				} else {
					position.y += yDepth;
				}
			}
		}
	}
	
	private void checkTilePosition(Room room, ArrayList<Vector2f> collideTilePositions, float x,
			float y) {
		Vector2i gridPos = new Vector2i((int) Math.floor(x / (Room.TILE_WIDTH * Room.TILE_SCALE)),
				(int) Math.floor(y / (Room.TILE_WIDTH * Room.TILE_SCALE)));
		
		if (gridPos.x < 0 || gridPos.x >= room.getRoomData().get(0).length() || gridPos.y < 0
				|| gridPos.y >= room.getRoomData().size()) {
			return;
		}
		
		char tile = room.getRoomData().get(gridPos.y).charAt(gridPos.x);
		Vector2f tilePos = new Vector2f(
				gridPos.x * Room.TILE_WIDTH * Room.TILE_SCALE - (Room.TILE_WIDTH * Room.TILE_SCALE) / 2f,
				gridPos.y * Room.TILE_WIDTH * Room.TILE_SCALE - (Room.TILE_WIDTH * Room.TILE_SCALE) / 2f);
		
		/*for (Vector2f p : collideTilePositions) {
			if (p.equals(tilePos)) {
				return;
			}
		}*/
		
		switch (tile) {
		case Room.TILE_SYMBOL_WALL:
		case Room.TILE_SYMBOL_BLOCK:
			collideTilePositions.add(tilePos);
			break;
		case Room.TILE_SYMBOL_DOOR:
			if (room.isCleared()) {
				if (gridPos.x == 0) {
					if (room.getLeftDoorState() != Room.DoorState.OPEN) {
						collideTilePositions.add(tilePos);
					}
				} else {
					if (room.getRightDoorState() != Room.DoorState.OPEN) {
						collideTilePositions.add(tilePos);
					}
				}
				
				if (gridPos.y == 0) {
					if (room.getDownDoorState() != Room.DoorState.OPEN) {
						collideTilePositions.add(tilePos);
					}
				} else {
					if (room.getUpDoorState() != Room.DoorState.OPEN) {
						collideTilePositions.add(tilePos);
					}
				}
			}
			break;
		case Room.TILE_SYMBOL_FLOOR:
			break;
		default:
			break;
		}
	}

	// getters
	final public int getMaxHealth() {
		return health.getMaxHealth();
	}

	final public int getCurrentHealth() {
		return health.getCurrentHealth();
	}

	final public Vector2f getPosition() {
		return position;
	}

	final public Vector2i getDimensions() {
		return dimensions;
	}

	final public float getSpeed() {
		return speed;
	}

	final public float getShotSpeed() {
		return shotSpeed;
	}

	final public float getFireRate() {
		return fireRate;
	}

	final public int damage() {
		return damage;
	}
}
