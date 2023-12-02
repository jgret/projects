/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package game.level;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import game.Game;
import game.entity.Door;
import game.entity.GameObject;
import game.entity.Player;
import game.entity.enemy.Enemy;
import game.graphics.Camera;
import game.graphics.Drawable;
import game.graphics.Image2d;
import game.graphics.Screen;
import game.graphics.TiledTileSet;
import game.graphics.TileSet;
import game.io.FileIO;
import game.io.Input;
import game.shape.Line;
import game.shape.Polygon2D;
import game.shape.Rectangle;
import game.shape.Vector2;

public class World implements Drawable {

	private Game game;
	private Input input;
	private ArrayList<Rectangle> collisionRectangles;
	private ArrayList<Polygon2D> collisionPolygons;
	private ArrayList<GameObject> actors;
	private ArrayList<GameObject> newActorQueue;
	private Image2d worldImageBuffer;
	private TiledTileSet tilesets;
	private Player player;
	private ArrayList<int[][]> layerList;
	private Monsterspawner monsterspawner;
	private int tilesize;
	private int width;
	private int enemies;
	private int height;
	private boolean showHitboxes;
	private boolean drawWorld;
	private boolean fillHitboxes;
	private Image2d background;
	private Rectangle bounds;
	private Vector2 spawnPoint;
	private double monsterSpawnCooldown;
	private double monsterSpawnCooldownValue = 5;
	private int maxEnemies = 5;

	public World(Game game) {
		this.game = game;
		this.collisionPolygons = new ArrayList<>();
		this.collisionRectangles = new ArrayList<>();
		this.actors = new ArrayList<>();
		this.newActorQueue = new ArrayList<>();
		this.input = game.getInput();
		this.showHitboxes = false;
		this.drawWorld = true;
		this.fillHitboxes = false;
		this.spawnPoint = new Vector2(2, 2);
	}

	public void load(String filename) {
		layerList = new ArrayList<int[][]>();
		this.background = FileIO.loadImage("img/waterfall.png");
		this.monsterspawner = new Monsterspawner(this, "world/" + filename + ".mls");

		try {
			// load world File into DOM
			InputStream in = FileIO.getResourceAsStream("world/" + filename + ".tmx");
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(in);
			doc.getDocumentElement().normalize();
			Element map = doc.getDocumentElement();

			tilesize = Integer.parseInt(map.getAttribute("tilewidth"));
			width = Integer.parseInt(map.getAttribute("width"));
			height = Integer.parseInt(map.getAttribute("height"));
			this.bounds = new Rectangle(0, 0, width, height);

			tilesets = new TiledTileSet();

			// read tilesets
			NodeList tilesets = doc.getElementsByTagName("tileset");
			for (int i = 0; i < tilesets.getLength(); i++) {
				Element tileset = (Element) tilesets.item(i);
				Element src = (Element) tileset.getElementsByTagName("image").item(0);
				String stringsrc = src.getAttribute("source");
				String[] parts = stringsrc.split("/");
				String fname = parts[parts.length - 1];
				System.out.println("Loading tileset: " + fname);
				int tilesettilesize = Integer.parseInt(tileset.getAttribute("tilewidth"));
				this.tilesets.addTileset(new TileSet("img/" + fname, tilesettilesize));
			}

			// read layers
			NodeList layers = doc.getElementsByTagName("layer");
			for (int i = 0; i < layers.getLength(); i++) {
				Element layer = (Element) layers.item(i);
				String name = layer.getAttribute("name");
				System.out.println("Loading layer: " + name);
				Element data = (Element) layer.getElementsByTagName("data").item(0);
				layerList.add(toIntArray(data.getTextContent().trim()));

			}

			System.out.println("Reading Objects");

			// read objectgroups
			NodeList objectgroups = doc.getElementsByTagName("objectgroup");
			for (int i = 0; i < objectgroups.getLength(); i++) {
				Element objgrp = (Element) objectgroups.item(i);
				NodeList objs = objgrp.getElementsByTagName("object");
				for (int j = 0; j < objs.getLength(); j++) {
					Element obj = (Element) objs.item(j);
					if (obj.hasChildNodes()) {
						if (obj.getElementsByTagName("polygon").getLength() > 0) {
							Element polygon = (Element) obj.getElementsByTagName("polygon").item(0);

							double offsetX = Double.parseDouble(obj.getAttribute("x"));
							double offsetY = Double.parseDouble(obj.getAttribute("y"));
							Vector2 offset = new Vector2(offsetX, offsetY);

							String spoints = polygon.getAttribute("points");
							String[] points = spoints.split(" ");
							Vector2[] verts = new Vector2[points.length];

							for (int k = 0; k < verts.length; k++) {
								String[] xy = points[k].split(",");
								verts[k] = new Vector2(Double.parseDouble(xy[0]), Double.parseDouble(xy[1]));
								verts[k] = verts[k].add(offset).mul(1.0 / (double) tilesize);
							}

							Polygon2D poly = new Polygon2D(verts);
							this.collisionPolygons.add(poly);

						} else if (obj.getElementsByTagName("point").getLength() > 0) {

							String type = obj.getAttribute("type");
							Vector2 p = new Vector2(Double.parseDouble(obj.getAttribute("x")),
									Double.parseDouble(obj.getAttribute("y"))).mul(1 / (double) tilesize);
							switch (type) {
								case ("spawnpoint"): {
									spawnPoint = p;
									break;
								}
								case ("mspawn"): {
									monsterspawner.addLocation(p);
									break;
								}
							}
						} else {
							if (obj.getElementsByTagName("properties").getLength() > 0) {
								Element special = (Element) obj.getElementsByTagName("property").item(0);
								String data = special.getAttribute("value");
								System.out.println(data);

								double rx = Double.parseDouble(obj.getAttribute("x"));
								double ry = Double.parseDouble(obj.getAttribute("y"));
								double rwidth = Double.parseDouble(obj.getAttribute("width"));
								double rheight = Double.parseDouble(obj.getAttribute("height"));

								rx = (rx / (double) tilesize);
								ry = (ry / (double) tilesize);
								rwidth = (rwidth / (double) tilesize);
								rheight = (rheight / (double) tilesize);

								Rectangle rect = new Rectangle(rx, ry, rwidth, rheight);
								TileSet doors = new TileSet("img/world_doors_32x32.png", 64, 96);
								Door door = new Door(this, rect, doors.get(0), data.split(":")[1]);
								this.spawn(door, rect.getPosition());

							}
						}

					} else {

						double rx = Double.parseDouble(obj.getAttribute("x"));
						double ry = Double.parseDouble(obj.getAttribute("y"));
						double rwidth = Double.parseDouble(obj.getAttribute("width"));
						double rheight = Double.parseDouble(obj.getAttribute("height"));

						rx = (rx / (double) tilesize);
						ry = (ry / (double) tilesize);
						rwidth = (rwidth / (double) tilesize);
						rheight = (rheight / (double) tilesize);

						Rectangle rect = new Rectangle(rx, ry, rwidth, rheight);
						this.collisionRectangles.add(rect);
					}
				}
			}

			createImageBuffer(Screen.TILESIZE);
			System.out.println("DONE");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void init() {

	}

	public void calcEnemies() {
		enemies = 0;
		for (GameObject g : actors) {
			if (g instanceof Enemy) {
				enemies++;
			}
		}
	}

	public void update(double elapsedTime) {
		this.input();
		monsterSpawnCooldown += elapsedTime;

		if (enemies < maxEnemies && monsterSpawnCooldown > monsterSpawnCooldownValue) {
			monsterspawner.spawnMonsters(1);
			monsterSpawnCooldown = 0;
		}

		if (newActorQueue.size() > 0) {
			for (GameObject actor : newActorQueue) {
				this.actors.add(actor);
			}
			calcEnemies();
			newActorQueue.clear();
		}

		// Update GameObjects
		for (GameObject actor : actors) {
			actor.update(elapsedTime);
			actor.applyFriction(elapsedTime);
			actor.applyGravity(elapsedTime);
			actor.move(elapsedTime);
		}

		// Collision detection
		for (GameObject actor : actors) {
			actor.setGrounded(false);
			boolean slopeCollision = false;
			for (GameObject actor2 : actors) {
				if (actor != actor2 && !actor.isRemove() && !actor2.isRemove()) {
					if (actor.intersects(actor2)) {

						if (actor.shouldCollide(actor2) && actor2.shouldCollide(actor)) {
							int predictedPosition = actor.predictPositionOf(actor2);

							if (predictedPosition == GameObject.POS_UP) {
								actor2.setY(actor.getTop() - actor2.getHeight());
								actor2.setGrounded(true);
							} else if (predictedPosition == GameObject.POS_LEFT) {
								actor2.setX(actor.getLeft() - actor2.getWidth());
								actor2.setVelX(actor.getVelX());
							} else if (predictedPosition == GameObject.POS_RIGHT) {
								actor2.setX(actor.getRight());
								actor2.setVelX(actor.getVelX());
							} else if (predictedPosition == GameObject.POS_DOWN) {
								actor.setY(actor2.getTop() - actor.getHeight());
								actor.setVelY(actor2.getVelY());
								actor.addPosition(actor2.getVel().mul(elapsedTime));
								actor.setGrounded(true);
							}
						}

						actor.onCollision(actor2);
						actor2.onCollision(actor);

					}
				}
			}
			if (actor.isStaticCollision()) {
				for (Polygon2D poly : collisionPolygons) {

					if (actor.isSlopeCollision()) {
						Vector2 slopePoint = actor.getSlopePoint();
						Vector2 topPoint = actor.getTopCollisionPoint();

						if (poly.contains(topPoint)) {

							actor.onStaticCollision(poly);
							while (poly.contains(topPoint)) {
								actor.setY(actor.getY() + (1.0 / (double) Screen.TILESIZE));
								topPoint = actor.getTopCollisionPoint();
							}

						} else {

							if (poly.getBounds().contains(slopePoint)) {
								slopeCollision = true;
								if (poly.contains(slopePoint)) {
									actor.onStaticCollision(poly);
									actor.setBoxCollision(false);
									actor.setGrounded(true);
									Line line = poly.getNearestSideToPoint(actor.getSlopePoint());
									Vector2 contact = line.getNormalContactPoint(actor.getSlopePoint());
									if (contact.isFinite()) {
										actor.setY(contact.getY() - actor.getHeight() + GameObject.SLOPE_POINT_OFFSET);
										actor.getVel().setY(0);
									}
								}
							}
						}
					}
				}

				for (Rectangle rect : collisionRectangles) {
					if (rect.intersects(actor)) {
						actor.onStaticCollision(rect);
						if (actor.isBoxCollision()) {
							int dir = actor.predictPositionOf(rect);
							if (dir == GameObject.POS_DOWN) {
								actor.setY(rect.getTop() - actor.getHeight());
								actor.getVel().y = 0;
								actor.setGrounded(true);
							} else if (dir == GameObject.POS_UP) {
								actor.setY(rect.getBot());
								actor.getVel().y = 0;
							} else if (dir == GameObject.POS_RIGHT) {
								actor.setX(rect.getLeft() - actor.getWidth());
								actor.getVel().x = 0;
							} else if (dir == GameObject.POS_LEFT) {
								actor.setX(rect.getRight());
								actor.getVel().x = 0;
							}
						}
					}
				}
			}
			if (!slopeCollision) {
				actor.setBoxCollision(true);
			}
		}
		garbageCollection();
	}

	public void input() {
		if (input.keyPressed(KeyEvent.VK_F6)) {
			fillHitboxes = !fillHitboxes;
			System.out.println("fillHitboxes: " + fillHitboxes);
		} else if (input.keyPressed(KeyEvent.VK_F7)) {
			drawWorld = !drawWorld;
			System.out.println("drawWorld: " + drawWorld);
		} else if (input.keyPressed(KeyEvent.VK_F5)) {
			showHitboxes = !showHitboxes;
			System.out.println("showHitboxes: " + showHitboxes);
		} else if (input.keyPressed(KeyEvent.VK_F2)) {
			monsterspawner.spawnMonsters(100);
			System.out.println("Spawn Monsters");
		} else if (input.keyPressed(KeyEvent.VK_F3)) {
			this.spawnQueue(game.getItems().get("game_jack_daniels"), spawnPoint);
			System.out.println("Spawn Monsters");
		}
	}

	public boolean checkCollision(Rectangle rectangle) {
		for (Rectangle rect : collisionRectangles) {
			if (rect.intersects(rectangle)) {
				return true;
			}
		}
		for (Polygon2D poly : collisionPolygons) {
			if (poly.intersects(rectangle)) {
				return true;
			}
		}
		for (GameObject g : actors) {
			if (g.intersects(rectangle)) {
				return true;
			}
		}
		return false;
	}

	public boolean checkCollision(Vector2 point) {
		for (Rectangle rect : collisionRectangles) {
			if (rect.contains(point)) {
				return true;
			}
		}
		for (Polygon2D poly : collisionPolygons) {
			if (poly.contains(point)) {
				return true;
			}
		}
		for (GameObject g : actors) {
			if (g.contains(point)) {
				return true;
			}
		}
		return false;
	}

	public boolean checkSlopeCollision(GameObject g) {
		for (Polygon2D poly : collisionPolygons) {
			if (poly.contains(g.getSlopePoint()) || poly.contains(g.getTopCollisionPoint())) {
				return true;
			}
		}
		return false;
	}

	public boolean checkRectangleCollision(GameObject g) {
		for (Rectangle rect : collisionRectangles) {
			if (rect.intersects(g)) {
				return true;
			}
		}
		return false;
	}

	public void garbageCollection() {
		ArrayList<GameObject> trash = new ArrayList<GameObject>();
		for (GameObject g : actors) {
			if (g.isRemove()) {
				trash.add(g);
			}

			if (outOfWorld(g)) {
				g.onOutOfWorld(this);
			}

		}
		for (GameObject g : trash) {
			actors.remove(g);
		}
	}

	public void draw(Graphics2D g2, Camera cam) {

		int scale = Screen.TILESIZE;
		background.draw(g2, 0, 0, cam.getWidth(), cam.getHeight());

		if (tilesize != scale) {
			createImageBuffer(scale);
		}

		if (drawWorld) {
			worldImageBuffer.draw(g2, cam.getPixelOffsetX(), cam.getPixelOffsetY(), cam.getWidth(), cam.getHeight(), 0,
					0, cam.getWidth(), cam.getHeight());
		}

		g2.setColor(Color.RED.darker());
		if (showHitboxes) {
			for (Rectangle rect : collisionRectangles) {
				rect = rect.scale(Screen.TILESIZE);
				rect.translate(-cam.getPixelOffsetX(), -cam.getPixelOffsetY());
				if (fillHitboxes) {
					g2.setColor(Color.BLUE);
					g2.fill(rect);
					g2.setColor(Color.BLACK);
					g2.draw(rect);
				} else {
					g2.setColor(Color.BLUE);
					g2.draw(rect);
				}
			}

			for (Polygon2D poly : collisionPolygons) {
				Graphics2D tran = (Graphics2D) g2.create();
				tran.translate(-cam.getPixelOffsetX(), -cam.getPixelOffsetY());
				if (fillHitboxes) {
					tran.setColor(Color.RED);
					tran.fill(poly.scale(Screen.TILESIZE));
					tran.setColor(Color.BLACK);
					tran.draw(poly.scale(Screen.TILESIZE));
				} else {
					tran.setColor(Color.RED);
					tran.draw(poly.scale(Screen.TILESIZE));
				}

			}
		}

		g2.setColor(Color.BLACK);
		for (GameObject g : actors) {
			g.draw(g2, cam);
		}

	}

	public void spawn(GameObject g, Vector2 pos) {
		g.setWorldIn(this);
		g.setPosition(pos);
		if (g instanceof Player) {
			this.player = (Player) g;
		}

		for (GameObject actor : actors) {
			if (actor.equals(g)) {
				return;
			}
		}

		this.actors.add(g);
		this.calcEnemies();
	}

	public void spawnQueue(GameObject g, Vector2 pos) {
		g.setWorldIn(this);
		g.setPosition(pos);
		if (g instanceof Player) {
			this.player = (Player) g;
		}
		g.setRemove(false);
		this.newActorQueue.add(g);
		this.calcEnemies();
	}

	public boolean outOfWorld(GameObject g) {
		boolean outOfWorld = false;

		if (g.getX() < 0) {
			outOfWorld = true;
		} else if (g.getX() > this.bounds.getWidth()) {
			outOfWorld = true;
		}

		if (g.getY() < 0) {
			outOfWorld = true;
		} else if (g.getY() > this.bounds.getHeight()) {
			outOfWorld = true;
		}

		return outOfWorld;
	}

	public void remove(GameObject g) {
		g.setRemove(true);
	}

	public Game getGame() {
		return this.game;
	}

	public Rectangle getBounds() {
		return this.bounds;
	}

	public boolean isShowHitboxes() {
		return showHitboxes;
	}

	public void setShowHitboxes(boolean showHitboxes) {
		this.showHitboxes = showHitboxes;
	}

	public void createImageBuffer(int tilesize) {
		this.tilesize = tilesize;
		this.worldImageBuffer = new Image2d(width * tilesize, height * tilesize);
		Graphics2D g2 = this.worldImageBuffer.createGraphics();
		System.out.println("Drawing world");

		int i = 0;
		for (int[][] layer : layerList) {
			i++;
			System.out.println("Layer " + i + "/" + layerList.size());
			for (int row = 0; row < height; row++) {
				for (int coll = 0; coll < width; coll++) {
					int n = layer[row][coll];
					if (n > 0) {
						n--;
						g2.drawImage(tilesets.get(n).getImage(), coll * tilesize, row * tilesize, tilesize, tilesize,
								null);
					}
				}
			}
		}
		g2.dispose();

		worldImageBuffer.backup();
	}

	private int[][] toIntArray(String sdata) {
		int[][] data;
		int width, height;

		String[] rows = sdata.split("\n");
		height = rows.length;
		width = rows[0].split(",").length;
		data = new int[height][width];

		for (int i = 0; i < rows.length; i++) {
			String[] row_values = rows[i].split(",");
			for (int j = 0; j < row_values.length; j++) {
				data[i][j] = Integer.parseInt(row_values[j]);
			}
		}

		return data;
	}

	public Vector2 getSpawnPoint() {
		return spawnPoint;
	}

	public void setSpawnPoint(Vector2 spawnPoint) {
		this.spawnPoint = spawnPoint;
	}

	public ArrayList<GameObject> getActors() {
		return actors;
	}

	public void setActors(ArrayList<GameObject> actors) {
		this.actors = actors;
	}

	public Input getInput() {
		return input;
	}

	public void setInput(Input input) {
		this.input = input;
	}

	public void setGame(Game game) {
		this.game = game;
	}

}