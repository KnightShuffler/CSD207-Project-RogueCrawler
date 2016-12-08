package game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

import org.joml.Vector2f;
import org.joml.Vector4f;

import engine.ColorRGBA8;
import engine.SpriteBatch;
import engine.Texture;
import engine.UV;

public class Room {

	final public static int ROOM_WIDTH = 20;
	final public static int ROOM_HEIGHT = 15;

	final public static int TILE_WIDTH = 16;

	final public static float TILE_SCALE = 4f;
	
	final public static float TILE_RADIUS = Room.TILE_WIDTH * Room.TILE_SCALE / 2f;

	final private static int TILE_SHEET_WIDTH = 128;
	final private static int TILE_SHEET_HEIGHT = 64;

	final private static int TILES_PER_ROW = TILE_SHEET_WIDTH / TILE_WIDTH;
	final private static int TILES_PER_COLUMN = TILE_SHEET_HEIGHT / TILE_WIDTH;

	final private static float TILE_UV_WIDTH = 1f / (float) TILES_PER_ROW;
	final private static float TILE_UV_HEIGHT = 1f / (float) TILES_PER_COLUMN;

	final private static int NUM_TILES = 32;
	
	final public static char TILE_SYMBOL_FLOOR = '*';
	final public static char TILE_SYMBOL_BLOCK = 'b';
	final public static char TILE_SYMBOL_WALL = 'w';
	final public static char TILE_SYMBOL_DOOR = 'd';

	final private static int TILE_INDEX_DOWN_LEFT_CORNER = 0;
	final private static int TILE_INDEX_DOWN_WALL = 1;
	final private static int TILE_INDEX_DOWN_RIGHT_CORNER = 2;
	final private static int TILE_INDEX_LEFT_DOOR_LOWER = 3;
	final private static int TILE_INDEX_RIGHT_DOOR_LOWER = 4;
	final private static int TILE_INDEX_BLOCK = 5;
	final private static int TILE_INDEX_UP_DOOR_LEFT_OPEN = 6;
	final private static int TILE_INDEX_UP_DOOR_RIGHT_OPEN = 7;

	final private static int TILE_INDEX_LEFT_WALL = 8;
	final private static int TILE_INDEX_FLOOR = 9;
	final private static int TILE_INDEX_RIGHT_WALL = 10;
	final private static int TILE_INDEX_LEFT_DOOR_MID_OPEN = 11;
	final private static int TILE_INDEX_RIGHT_DOOR_MID_OPEN = 12;
	final private static int TILE_INDEX_RIGHT_DOOR_MID_CLOSED = 13;
	final private static int TILE_INDEX_UP_DOOR_LEFT_CLOSED = 14;
	final private static int TILE_INDEX_UP_DOOR_RIGHT_CLOSED = 15;

	final private static int TILE_INDEX_UP_LEFT_CORNER = 16;
	final private static int TILE_INDEX_UP_WALL = 17;
	final private static int TILE_INDEX_UP_RIGHT_CORNER = 18;
	final private static int TILE_INDEX_LEFT_DOOR_UPPER = 19;
	final private static int TILE_INDEX_RIGHT_DOOR_UPPER = 20;
	final private static int TILE_INDEX_LEFT_DOOR_MID_CLOSED = 21;
	final private static int TILE_INDEX_DOWN_DOOR_LEFT_OPEN = 22;
	final private static int TILE_INDEX_DOWN_DOOR_RIGHT_OPEN = 23;

	final private static int TILE_INDEX_DOWN_DOOR_LEFT_CLOSED = 24;
	final private static int TILE_INDEX_DOWN_DOOR_RIGHT_CLOSED = 25;
	final private static int TILE_INDEX_UP_DOOR_LEFT_LOCKED = 26;
	final private static int TILE_INDEX_UP_DOOR_RIGHT_LOCKED = 27;
	final private static int TILE_INDEX_DOWN_DOOR_LEFT_LOCKED = 28;
	final private static int TILE_INDEX_DOWN_DOOR_RIGHT_LOCKED = 29;
	final private static int TILE_INDEX_RIGHT_DOOR_MID_LOCKED = 30;
	final private static int TILE_INDEX_LEFT_DOOR_MID_LOCKED = 31;

	private static Vector4f[] tileUVs = new Vector4f[NUM_TILES];

	final private static Texture tileSheetTexture = new Texture("./textures/tiles.png");

	private ArrayList<String> roomData;
	private int numEnemies;
	private Integer roomNo;
	private ArrayList<String> enemies;
	private ArrayList<Vector2f> enemyStartPositions;
	
	private boolean isCleared = false;
	
	public static enum DoorState {
		OPEN, OPENABLE, CLOSED, LOCKED, BOSS_LOCKED
	};
	
	public DoorState upDoorState, downDoorState, leftDoorState, rightDoorState;
	
	SpriteBatch roomSpriteBatch;

	public static void tileInit() {
		for (int i = 0; i < NUM_TILES; i++) {
			
//			System.out.print(i + ": ");
//			System.out.print((i % TILES_PER_ROW) + ", ");
//			System.out.println((i / TILES_PER_ROW));
			
			tileUVs[i] = new Vector4f((i % TILES_PER_ROW) * TILE_UV_WIDTH, (i / TILES_PER_ROW) * TILE_UV_HEIGHT,
					TILE_UV_WIDTH, TILE_UV_HEIGHT);
//			tileUVs[i] = UV.DEFAULT_UV_RECT;
		}
	}

	public Room(boolean clear, DoorState up, DoorState down, DoorState left, DoorState right) throws IOException {
		isCleared = clear;
		upDoorState = up;
		downDoorState = down;
		leftDoorState = left;
		rightDoorState = right;
		
		enemies = new ArrayList<>();
		enemyStartPositions = new ArrayList<>();
		
		roomData = new ArrayList<>(ROOM_HEIGHT);
		for (int i = 0; i < ROOM_HEIGHT; i++) {
			roomData.add(null);
		}

		Random rn = new Random();
		Integer roomNo = rn.nextInt(6) + 1;
		FileReader input = null;

		input = new FileReader("./rooms/Room" + roomNo.toString() + ".txt");
//		input = new FileReader("./rooms/Room6.txt");

		BufferedReader br = new BufferedReader(input);

		for (int i = 1; i <= ROOM_HEIGHT; i++) {
			roomData.set(ROOM_HEIGHT - i, br.readLine());
		}

//		for (String s : roomData) {
//			System.out.println(s);
//		}

		numEnemies = Integer.parseInt(br.readLine());
		if (numEnemies > 0) {
			String enemyData;
			while ((enemyData = br.readLine()) != null) {
				StringTokenizer stok = new StringTokenizer(enemyData);
				enemies.add(stok.nextToken());
				enemyStartPositions
						.add(new Vector2f(Integer.parseInt(stok.nextToken()), Integer.parseInt(stok.nextToken())));
			}
		}
		
		br.close();

		roomSpriteBatch = new SpriteBatch();
		createRoomBatch();
	}
	
	public ArrayList<String> getRoomData() {
		return roomData;
	}
	
	public boolean isCleared() {
		return isCleared;
	}
	
	public DoorState getUpDoorState() {
		return upDoorState;
	}
	
	public DoorState getDownDoorState() {
		return downDoorState;
	}
	
	public DoorState getLeftDoorState() {
		return leftDoorState;
	}
	
	public DoorState getRightDoorState() {
		return rightDoorState;
	}
	
	public void clearRoom(boolean hasKey, boolean hasBossKey) {
		isCleared = true;
		updateRoomBatch();
		if (hasKey) {
			if (upDoorState == DoorState.LOCKED) {
				upDoorState = DoorState.OPEN;
			}
			if (downDoorState == DoorState.LOCKED) {
				downDoorState = DoorState.OPEN;
			}
			if (leftDoorState == DoorState.LOCKED) {
				leftDoorState = DoorState.OPEN;
			}
			if (rightDoorState == DoorState.LOCKED) {
				rightDoorState = DoorState.OPEN;
			}
		}
		if (hasBossKey) {
			if (upDoorState == DoorState.BOSS_LOCKED) {
				upDoorState = DoorState.OPEN;
			}
			if (downDoorState == DoorState.BOSS_LOCKED) {
				downDoorState = DoorState.OPEN;
			}
			if (leftDoorState == DoorState.BOSS_LOCKED) {
				leftDoorState = DoorState.OPEN;
			}
			if (rightDoorState == DoorState.BOSS_LOCKED) {
				rightDoorState = DoorState.OPEN;
			}
		}
		
		if (upDoorState == DoorState.OPENABLE) {
			upDoorState = DoorState.OPEN;
		}
		
		if (downDoorState == DoorState.OPENABLE) {
			downDoorState = DoorState.OPEN;
		}
		
		if (leftDoorState == DoorState.OPENABLE) {
			leftDoorState = DoorState.OPEN;
		}
		
		if (rightDoorState == DoorState.OPENABLE) {
			rightDoorState = DoorState.OPEN;
		}
	}
	
	public void createRoomBatch() {
		roomSpriteBatch.begin(SpriteBatch.SORT_BY_TEXTURE);
		for (int y = 0; y < ROOM_HEIGHT; y++) {
//			System.out.println();
			for (int x = 0; x < ROOM_WIDTH; x++) {
				Vector4f destRect = new Vector4f(x * TILE_WIDTH * TILE_SCALE, y * TILE_WIDTH * TILE_SCALE,
						TILE_WIDTH * TILE_SCALE, TILE_WIDTH * TILE_SCALE);
				
				// Check if the tile is a corner
				if (x == 0 && y == 0) {
					roomSpriteBatch.addGlyph(destRect, tileUVs[TILE_INDEX_DOWN_LEFT_CORNER], ColorRGBA8.WHITE,
							tileSheetTexture.getTexture(), 1f);
//					System.out.println("DOWN LEFT Corner");
				}
				else if (x == 0 && y == ROOM_HEIGHT - 1) {
					roomSpriteBatch.addGlyph(destRect, tileUVs[TILE_INDEX_UP_LEFT_CORNER], ColorRGBA8.WHITE,
							tileSheetTexture.getTexture(), 1f);
//					System.out.println("UP LEFT Corner");
				}
				else if (x == ROOM_WIDTH - 1 && y == 0) {
					roomSpriteBatch.addGlyph(destRect, tileUVs[TILE_INDEX_DOWN_RIGHT_CORNER], ColorRGBA8.WHITE,
							tileSheetTexture.getTexture(), 1f);
//					System.out.println("DOWN RIGHT Corner");
				}
				else if (x == ROOM_WIDTH - 1 && y == ROOM_HEIGHT - 1) {
					roomSpriteBatch.addGlyph(destRect, tileUVs[TILE_INDEX_UP_RIGHT_CORNER], ColorRGBA8.WHITE,
							tileSheetTexture.getTexture(), 1f);
//					System.out.println("UP RIGHT Corner");
				}
				
				// Check if the tile is a door tile and if the room is cleared,
				// and can be opened set the door to be open
				
				// Left Door tiles
				else if (x == 0 && y == ROOM_HEIGHT / 2) {
					int tileIndex;
					if (leftDoorState == DoorState.LOCKED || leftDoorState == DoorState.BOSS_LOCKED) {
						tileIndex = TILE_INDEX_LEFT_DOOR_MID_LOCKED;
					} else if (leftDoorState == DoorState.OPEN && isCleared) {
						tileIndex = TILE_INDEX_LEFT_DOOR_MID_OPEN;
					} else {
						tileIndex = TILE_INDEX_LEFT_DOOR_MID_CLOSED;
					}
					
					roomSpriteBatch.addGlyph(destRect, tileUVs[tileIndex], ColorRGBA8.WHITE,
							tileSheetTexture.getTexture(), 1f);
					
//					System.out.println("LEFT DOOR MID");
				}
				else if (x == 0 && y == ROOM_HEIGHT / 2 - 1) {
					roomSpriteBatch.addGlyph(destRect, tileUVs[TILE_INDEX_LEFT_DOOR_LOWER], ColorRGBA8.WHITE,
							tileSheetTexture.getTexture(), 1f);
					
//					System.out.println("LEFT DOOR LOWER");
				}
				else if (x == 0 && y == ROOM_HEIGHT / 2 + 1) {
					roomSpriteBatch.addGlyph(destRect, tileUVs[TILE_INDEX_LEFT_DOOR_UPPER], ColorRGBA8.WHITE,
							tileSheetTexture.getTexture(), 1f);
					
//					System.out.println("LEFT DOOR UPPER");
				}
				
				// Right door tiles
				else if (x == ROOM_WIDTH - 1 && y == ROOM_HEIGHT / 2) {
					int tileIndex;
					if (rightDoorState == DoorState.LOCKED || rightDoorState == DoorState.BOSS_LOCKED) {
						tileIndex = TILE_INDEX_RIGHT_DOOR_MID_LOCKED;
					} else if (rightDoorState == DoorState.OPEN && isCleared) {
						tileIndex = TILE_INDEX_RIGHT_DOOR_MID_OPEN;
					} else {
						tileIndex = TILE_INDEX_RIGHT_DOOR_MID_CLOSED;
					}
					
					roomSpriteBatch.addGlyph(destRect, tileUVs[tileIndex], ColorRGBA8.WHITE,
							tileSheetTexture.getTexture(), 1f);
					
//					System.out.println("RIGHT DOOR MID");
				}
				else if (x == ROOM_WIDTH - 1 && y == ROOM_HEIGHT / 2 - 1) {
					roomSpriteBatch.addGlyph(destRect, tileUVs[TILE_INDEX_RIGHT_DOOR_LOWER], ColorRGBA8.WHITE,
							tileSheetTexture.getTexture(), 1f);
					
//					System.out.println("RIGHT DOOR LOWER");
				}
				else if (x == ROOM_WIDTH - 1 && y == ROOM_HEIGHT / 2 + 1) {
					roomSpriteBatch.addGlyph(destRect, tileUVs[TILE_INDEX_RIGHT_DOOR_UPPER], ColorRGBA8.WHITE,
							tileSheetTexture.getTexture(), 1f);
					
//					System.out.println("RIGHT DOOR UPPER");
				}
				
				// Down door
				else if (x == ROOM_WIDTH / 2 - 1 && y == 0) {
					int tileIndex;
					if (downDoorState == DoorState.LOCKED || downDoorState == DoorState.BOSS_LOCKED) {
						tileIndex = TILE_INDEX_DOWN_DOOR_LEFT_LOCKED;
					} else if (downDoorState == DoorState.OPEN && isCleared) {
						tileIndex = TILE_INDEX_DOWN_DOOR_LEFT_OPEN;
					} else {
						tileIndex = TILE_INDEX_DOWN_DOOR_LEFT_CLOSED;
					}
					
					roomSpriteBatch.addGlyph(destRect, tileUVs[tileIndex], ColorRGBA8.WHITE,
							tileSheetTexture.getTexture(), 1f);
					
//					System.out.println("DOWN DOOR LEFT");
				}
				else if (x == ROOM_WIDTH / 2 && y == 0) {
					int tileIndex;
					if (downDoorState == DoorState.LOCKED || downDoorState == DoorState.BOSS_LOCKED) {
						tileIndex = TILE_INDEX_DOWN_DOOR_RIGHT_LOCKED;
					} else if (downDoorState == DoorState.OPEN && isCleared) {
						tileIndex = TILE_INDEX_DOWN_DOOR_RIGHT_OPEN;
					} else {
						tileIndex = TILE_INDEX_DOWN_DOOR_RIGHT_CLOSED;
					}
					
					roomSpriteBatch.addGlyph(destRect, tileUVs[tileIndex], ColorRGBA8.WHITE,
							tileSheetTexture.getTexture(), 1f);
					
//					System.out.println("DOWN DOOR RIGHT");
				}
				
				// Up door
				else if (x == ROOM_WIDTH / 2 - 1 && y == ROOM_HEIGHT - 1) {
					int tileIndex;
					if (upDoorState == DoorState.LOCKED || upDoorState == DoorState.BOSS_LOCKED) {
						tileIndex = TILE_INDEX_UP_DOOR_LEFT_LOCKED;
					} else if (upDoorState == DoorState.OPEN && isCleared) {
						tileIndex = TILE_INDEX_UP_DOOR_LEFT_OPEN;
					} else {
						tileIndex = TILE_INDEX_UP_DOOR_LEFT_CLOSED;
					}
					
					roomSpriteBatch.addGlyph(destRect, tileUVs[tileIndex], ColorRGBA8.WHITE,
							tileSheetTexture.getTexture(), 1f);
					
//					System.out.println("UP DOOR LEFT");
				}
				else if (x == ROOM_WIDTH / 2 && y == ROOM_HEIGHT - 1) {
					int tileIndex;
					if (upDoorState == DoorState.LOCKED || upDoorState == DoorState.BOSS_LOCKED) {
						tileIndex = TILE_INDEX_UP_DOOR_RIGHT_LOCKED;
					} else if (upDoorState == DoorState.OPEN && isCleared) {
						tileIndex = TILE_INDEX_UP_DOOR_RIGHT_OPEN;
					} else {
						tileIndex = TILE_INDEX_UP_DOOR_RIGHT_CLOSED;
					}
					
					roomSpriteBatch.addGlyph(destRect, tileUVs[tileIndex], ColorRGBA8.WHITE,
							tileSheetTexture.getTexture(), 1f);
					
//					System.out.println("UP DOOR RIGHT");
				}
				
				// Otherwise it is a normal tile
				else {
					char currentTile = roomData.get(y).charAt(x);
					switch (currentTile) {
					// Floor tile
					case '*':
						roomSpriteBatch.addGlyph(destRect, tileUVs[TILE_INDEX_FLOOR], ColorRGBA8.WHITE,
								tileSheetTexture.getTexture(), 1f);
						
//						System.out.print("F");
						break;
					// Block tile
					case 'b':
						roomSpriteBatch.addGlyph(destRect, tileUVs[TILE_INDEX_BLOCK], ColorRGBA8.WHITE,
								tileSheetTexture.getTexture(), 1f);
//						System.out.print("B");
						break;
					// Wall tile
					case 'w':
						if (x == 0) {
							roomSpriteBatch.addGlyph(destRect, tileUVs[TILE_INDEX_LEFT_WALL], ColorRGBA8.WHITE,
									tileSheetTexture.getTexture(), 1f);
//							System.out.print("L");
						}
						else if (x == ROOM_WIDTH - 1) {
							roomSpriteBatch.addGlyph(destRect, tileUVs[TILE_INDEX_RIGHT_WALL], ColorRGBA8.WHITE,
									tileSheetTexture.getTexture(), 1f);
							
//							System.out.print("R");
						}
						else if (y == 0) {
							roomSpriteBatch.addGlyph(destRect, tileUVs[TILE_INDEX_DOWN_WALL], ColorRGBA8.WHITE,
									tileSheetTexture.getTexture(), 1f);
							
//							System.out.print("D");
						}
						else {
							roomSpriteBatch.addGlyph(destRect, tileUVs[TILE_INDEX_UP_WALL], ColorRGBA8.WHITE,
									tileSheetTexture.getTexture(), 1f);
							
//							System.out.print("U");
						}
						break;
					/*case 'd' :
						break;*/
					default :
						System.err.println("Illegal symbol '" + currentTile + "' in file Room" + roomNo.toString()
								+ ".txt in line " + y);
						throw new IllegalStateException("Illegal symbol in the room");
					}
				}
			}
		}
		roomSpriteBatch.end();
	}
	
	private void updateRoomBatch() {
		
	}

	public void render() {
		roomSpriteBatch.render();
	}

}
