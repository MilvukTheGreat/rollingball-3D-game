package com.example.rollingball;

import com.example.rollingball.arena.*;
import com.example.rollingball.timer.Timer;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main extends Application {
	private Group root;
	private Group HUDRoot;
	private Group sceneRoot;
	private HUD hud;

	//ball
	private Ball ball;
	private PhongMaterial ballMaterial;
	private Translate ballPosition;

	// time
	private double timeElapsed;

	private Light light;
	private Arena arena;
	private Fence fence;

	private ArrayList<Hole> holes;
	private ArrayList<Coin> coins;
	private ArrayList<NormalObstacle> normalObstacles;
	private ArrayList<SpecialObstacle> specialObstacles;

	private DefaultCamera defaultCamera;
	private Camera birdViewCamera;
	private SubScene gameSubScene;
	private Scene scene;
	private Menu menu;
	private Timer timer;
	private int levelSelected;

	@Override
	public void start ( Stage stage ) throws IOException {
		this.sceneRoot = new Group();
		this.menu = new Menu();
		this.sceneRoot.getChildren().add(this.menu);

		this.timer = new Timer (
				deltaSeconds -> {
					this.arena.update(Utilities.ARENA_DAMP);
					this.hud.getSpeedMap().update(this.arena.getXAngle(), this.arena.getZAngle(), Utilities.MAX_ANGLE_OFFSET);

					if ( Main.this.ball != null ) {
						boolean outOfArena = Main.this.ball.update (
								deltaSeconds,
								Utilities.PODIUM_LENGTH / 2,
								-Utilities.PODIUM_LENGTH / 2,
								-Utilities.PODIUM_WIDTH / 2,
								Utilities.PODIUM_WIDTH / 2,
								this.arena.getXAngle ( ),
								this.arena.getZAngle ( ),
								Utilities.MAX_ANGLE_OFFSET,
								Utilities.MAX_ACCELERATION,
								Utilities.BALL_DAMP
						);

						// collisions
						for(NormalObstacle normalObstacle : this.normalObstacles){
							if(this.ball.handleObstacleCollision(normalObstacle)){
								break;
							}
						}

						for(SpecialObstacle specialObstacle : this.specialObstacles){
							if(this.ball.handleObstacleCollision(specialObstacle)){
								break;
							}
						}

						for(Box fence : this.fence.getFences()){
							if(this.ball.handleFenceCollision(fence)){ break; }
						}

						for(Coin coin : this.coins){
							if(this.ball.handleCoinCollision(coin)){
								this.hud.addScore(coin.getScore());
								this.arena.getChildren().remove(coin);
								this.coins.remove(coin);
								break;
							}
						}

						boolean isInHole = false;
						for(Hole hole : this.holes){
							if(hole.handleCollision(this.ball)){
								isInHole = true;
								this.hud.addScore(hole.getScore());
								break;
							}
						}

						// update time indicator
						double newTime = System.currentTimeMillis();
						this.timeElapsed = (newTime - this.timeElapsed) / Utilities.TIME_SCALE;
						this.hud.getTimeLeft().decreaseTime(this.timeElapsed);
						
						if ( outOfArena || isInHole) {
							this.hud.removeLife();
							if(!this.hud.IsGameOver()){
								this.arena.resetPosition();
								this.ball.resetPosition();
							}
							// game over
							else{
								this.arena.getChildren().remove(this.ball);
								this.ball = null;
								this.hud.ShowGameOverText();
							}
						}

						if(this.hud.getTimeLeft().getTimeout()){
							this.arena.getChildren().remove(this.ball);
							this.ball = null;
							this.hud.ShowGameOverText();
						}

					}
				}
		);

		for(LevelSelectButton levelSeclectButton : this.menu.getLevelButtons()){
			levelSeclectButton.addEventHandler(
					MouseEvent.MOUSE_PRESSED,
					mouseEvent -> {
						this.levelSelected = levelSeclectButton.getLevelID();
						this.createLevel();
					}
			);
		}

		// scene
		this.scene = new Scene(
				new Group(this.sceneRoot),
				Utilities.WINDOW_WIDTH,
				Utilities.WINDOW_HEIGHT,
				true,
				SceneAntialiasing.BALANCED
		);

		Image image  = new Image(Main.class.getClassLoader().getResourceAsStream("background.jpg"));
		this.scene.setFill(new ImagePattern(image));

		stage.setTitle("Rolling Ball");
		stage.setResizable(false);
		stage.setScene(this.scene);
		stage.show();
	}

	private void handleKeyEvent(KeyEvent event){
		if(event.getEventType().equals(KeyEvent.KEY_PRESSED)){
			if(event.getCode().equals(KeyCode.DIGIT1) || event.getCode().equals(KeyCode.NUMPAD1))
				this.gameSubScene.setCamera(this.defaultCamera);
			else if(event.getCode().equals(KeyCode.DIGIT2) || event.getCode().equals(KeyCode.NUMPAD2))
				this.gameSubScene.setCamera(this.birdViewCamera);
			else if(event.getCode().equals(KeyCode.DIGIT0) || event.getCode().equals(KeyCode.NUMPAD0))
				this.light.switchLight();
		}
	}

	private void createLevel(){
		this.createUniversalObjects();

		if(this.levelSelected == 1) this.createLevel1Objects();
		else if(this.levelSelected == 2) this.createLevel2Objects();
		else this.createLevel3Objects();

		this.sceneRoot.getChildren().remove(this.menu);
		this.sceneRoot.getChildren().addAll(this.gameSubScene, this.hud);

		this.timeElapsed = System.currentTimeMillis();
		this.timer.start();
	}

	// ===================================================== //
	// ====================== LEVEL 1 ====================== //
	// ===================================================== //

	private void addCoins1(){
		this.coins = new ArrayList<>();
		this.coins.add(new Coin(new Translate(0, 0, 0)));
		this.coins.add(new Coin(new Translate(Utilities.PODIUM_WIDTH/3, 0, 0)));
		this.coins.add(new Coin(new Translate(-Utilities.PODIUM_WIDTH/3, 0, 0)));
		this.coins.add(new Coin(new Translate(0, 0, Utilities.PODIUM_LENGTH/3)));
		this.coins.add(new Coin(new Translate(0, 0, -Utilities.PODIUM_LENGTH/3)));

		this.arena.getChildren().addAll(this.coins);
	}

	private void addNormalObstacles1(){
		this.normalObstacles = new ArrayList<>();

		this.normalObstacles.add(new NormalObstacle(Utilities.NORMAL_OBSTACLE_RADIUS, Utilities.NORMAL_OBSTACLE_HEIGHT, Utilities.PODIUM_WIDTH/4, Utilities.NORMAL_OBSTACLE_Y ,0));
		this.normalObstacles.add(new NormalObstacle(Utilities.NORMAL_OBSTACLE_RADIUS, Utilities.NORMAL_OBSTACLE_HEIGHT, -Utilities.PODIUM_WIDTH/4, Utilities.NORMAL_OBSTACLE_Y ,0));

		this.arena.getChildren().addAll(this.normalObstacles);
	}

	private void addSpecialObstacles1(){
		this.specialObstacles = new ArrayList<>();

		this.specialObstacles.add(new SpecialObstacle(Utilities.SPECIAL_OBSTACLE_RADIUS, Utilities.SPECIAL_OBSTACLE_HEIGHT, 0, Utilities.SPECIAL_OBSTACLE_Y ,Utilities.PODIUM_LENGTH/4));
		this.specialObstacles.add(new SpecialObstacle(Utilities.SPECIAL_OBSTACLE_RADIUS, Utilities.SPECIAL_OBSTACLE_HEIGHT, 0, Utilities.SPECIAL_OBSTACLE_Y ,-Utilities.PODIUM_LENGTH/4));

		this.arena.getChildren().addAll(this.specialObstacles);
	}

	private void addHoles1(){
		this.holes = new ArrayList<>();
		this.holes.add(new Hole (
				Utilities.HOLE_RADIUS,
				Utilities.HOLE_HEIGHT,
				new Translate( Utilities.PODIUM_WIDTH * 0.375, -30, -Utilities.PODIUM_LENGTH * 0.375 ),
				true
		));
		this.holes.add(new Hole (
				Utilities.HOLE_RADIUS,
				Utilities.HOLE_HEIGHT,
				new Translate( -Utilities.PODIUM_WIDTH * 0.375, -30, -Utilities.PODIUM_LENGTH * 0.375 ),
				false
		));
		this.holes.add(new Hole (
				Utilities.HOLE_RADIUS,
				Utilities.HOLE_HEIGHT,
				new Translate( Utilities.PODIUM_WIDTH * 0.375, -30, Utilities.PODIUM_LENGTH * 0.375 ),
				false
		));

		this.arena.getChildren ( ).addAll ( this.holes );
	}

	// ===================================================== //
	// ====================== LEVEL 2 ====================== //
	// ===================================================== //

	private void addCoins2(){
		this.coins = new ArrayList<>();
		for(int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				this.coins.add(new Coin(new Translate(-Utilities.PODIUM_WIDTH / 2 + (i + 1) * Utilities.PODIUM_LENGTH / 11, 0, -Utilities.PODIUM_LENGTH / 2 + (j + 1) * Utilities.PODIUM_LENGTH / 11)));
			}
		}

		this.arena.getChildren().addAll(this.coins);
	}

	private void addNormalObstacles2(){
		this.normalObstacles = new ArrayList<>();

		this.arena.getChildren().addAll(this.normalObstacles);
	}

	private void addSpecialObstacles2(){
		this.specialObstacles = new ArrayList<>();

		this.arena.getChildren().addAll(this.specialObstacles);
	}

	private void addHoles2(){
		this.holes = new ArrayList<>();

		this.arena.getChildren ( ).addAll ( this.holes );
	}

	// ===================================================== //
	// ====================== LEVEL 3 ====================== //
	// ===================================================== //

	private void addCoins3(){
		this.coins = new ArrayList<>();

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++){
				if(i == 1 && j == 1) continue;
				this.coins.add(new Coin(new Translate(-Utilities.PODIUM_WIDTH/9 + i * Utilities.PODIUM_WIDTH/9, 0, -Utilities.PODIUM_LENGTH/9 + j * Utilities.PODIUM_LENGTH/9)));
			}
		}

		this.arena.getChildren().addAll(this.coins);
	}

	private void addNormalObstacles3(){
		this.normalObstacles = new ArrayList<>();

		this.arena.getChildren().addAll(this.normalObstacles);
	}

	private void addSpecialObstacles3(){
		this.specialObstacles = new ArrayList<>();

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++){
				if(i == 1 && j == 1) continue;
				this.specialObstacles.add(new SpecialObstacle(Utilities.SPECIAL_OBSTACLE_RADIUS, Utilities.SPECIAL_OBSTACLE_HEIGHT,
						-Utilities.PODIUM_WIDTH/5 + i * Utilities.PODIUM_WIDTH/5,
						Utilities.SPECIAL_OBSTACLE_Y,
						-Utilities.PODIUM_LENGTH/5 + j * Utilities.PODIUM_LENGTH/5));
			}
		}

		this.arena.getChildren().addAll(this.specialObstacles);
	}

	private void addHoles3(){
		this.holes = new ArrayList<>();
		this.holes.add(new Hole (
				Utilities.HOLE_RADIUS,
				Utilities.HOLE_HEIGHT,
				new Translate( 0, -30, 0 ),
				false
		));

		this.arena.getChildren ( ).addAll ( this.holes );
	}

	// ===================================================== //
	// ===================== UNIVERSAL ===================== //
	// ===================================================== //

	private void createUniversalObjects(){
		this.root = new Group();
		this.gameSubScene = new SubScene (
				this.root,
				Utilities.WINDOW_WIDTH,
				Utilities.WINDOW_HEIGHT,
				true,
				SceneAntialiasing.BALANCED
		);

		Box podium = new Box (
				Utilities.PODIUM_WIDTH,
				Utilities.PODIUM_HEIGHT,
				Utilities.PODIUM_LENGTH
		);
		podium.setMaterial ( new PhongMaterial ( Color.BLUE ) );

		// ball
		this.ballMaterial = new PhongMaterial ( BallSelectButton.getBallColor() );

		this.ballPosition = new Translate (
				- ( Utilities.PODIUM_WIDTH / 2 - 2 * Utilities.BALL_RADIUS ),
				- ( Utilities.BALL_RADIUS + Utilities.PODIUM_HEIGHT / 2 ),
				Utilities.PODIUM_LENGTH / 2 - 2 * Utilities.BALL_RADIUS
		);
		this.ball = new Ball ( Utilities.BALL_RADIUS, ballMaterial, ballPosition, BallSelectButton.getBallAcceleration() );

		// cameras
		this.defaultCamera = new DefaultCamera();
		this.defaultCamera.setFarClip ( Utilities.CAMERA_FAR_CLIP );
		this.root.getChildren ( ).add (this.defaultCamera);
		this.gameSubScene.setCamera (this.defaultCamera);

		this.birdViewCamera = new PerspectiveCamera(true);
		this.birdViewCamera.setFarClip ( Utilities.CAMERA_FAR_CLIP );
		this.birdViewCamera.getTransforms().addAll(
				new Translate(0, -3000, 0),
				ballPosition,
				new Rotate(-90, Rotate.X_AXIS)
		);

		// arena
		this.arena = new Arena ( );
		this.arena.getChildren ( ).add ( podium );
		this.arena.getChildren().add(this.ball);
		this.root.getChildren ( ).add ( this.arena );

		// fence
		this.fence = new Fence();
		this.arena.getChildren().addAll(this.fence);

		// HUD
		this.HUDRoot = new Group();
		this.hud = new HUD(this.HUDRoot, Utilities.WINDOW_WIDTH, Utilities.WINDOW_HEIGHT);

		// light
		this.light = new Light(Utilities.LIGHT_BOX_SIZE);
		this.root.getChildren().add(light);

		// eventhandlers
		this.addEventHandlers();
	}

	private void addEventHandlers(){
		// scene event handlers
		this.scene.addEventHandler ( KeyEvent.ANY, event -> this.arena.handleKeyEvent ( event, Utilities.MAX_ANGLE_OFFSET ) );
		this.scene.addEventHandler( KeyEvent.ANY, this::handleKeyEvent);
		this.scene.addEventHandler(MouseEvent.ANY, (event) -> {
			this.defaultCamera.handleMouseEvent(event);
		});
		this.scene.addEventHandler(ScrollEvent.ANY, (event) -> {
			this.defaultCamera.handleScrollEvent(event);
		});
	}

	private void createLevel1Objects(){
		this.addCoins1();
		this.addNormalObstacles1();
		this.addSpecialObstacles1();
		this.addHoles1();
	}

	private void createLevel2Objects(){
		this.ball.setInitialPosition(0, 0);
		this.addCoins2();
		this.addNormalObstacles2();
		this.addSpecialObstacles2();
		this.addHoles2();
	}

	private void createLevel3Objects(){
		this.addCoins3();
		this.addNormalObstacles3();
		this.addSpecialObstacles3();
		this.addHoles3();
	}

	public static void main ( String[] args ) {
		launch ( );
	}
}