package com.example.rollingball;

import javafx.scene.paint.Color;

import java.util.Arrays;



public class Utilities {
	// window
	public static final double WINDOW_WIDTH  = 800;
	public static final double WINDOW_HEIGHT = 800;

	// camera
	public static final double CAMERA_FAR_CLIP = 100000;
	public static final double CAMERA_Z        = -5000;
	public static final double CAMERA_X_ANGLE  = -45;

	// max values
	public static final double MAX_ANGLE_OFFSET = 30;
	public static final double MAX_ACCELERATION = 400;

	// podium
	public static final double PODIUM_LENGTH  = 2000;
	public static final double PODIUM_WIDTH  = 2000;
	public static final double PODIUM_HEIGHT = 10;

	// fence
	public static final double FENCE_LENGTH = Utilities.PODIUM_WIDTH*0.6;
	public static final double FENCE_WIDTH = 20;
	public static final double FENCE_HEIGHT = 50;

	// SpeedMap
	public static final double SPEEDMAP_WIDTH = Utilities.WINDOW_WIDTH/8;
	public static final double SPEEDMAP_HEIGHT = Utilities.WINDOW_HEIGHT/8;

	// lives
	public static final int MAX_LIVES = 10;
	public static final int STARTING_LIVES = 5;
	public static final double LIFE_CIRCLE_RADIUS = 10;

	// ball
	public static final double BALL_RADIUS = 50;
	public static final double BALL_DAMP = 0.999;
	public static final double BALL_ACCELERATION_FACTOR_1 = 1;
	public static final double BALL_ACCELERATION_FACTOR_2 = 2;
	public static final double BALL_ACCELERATION_FACTOR_3 = 3;

	// hole
	public static final double HOLE_RADIUS = 2 * Utilities.BALL_RADIUS;
	public static final double HOLE_HEIGHT = Utilities.PODIUM_HEIGHT;
	public static final int HOLE_GOOD_SCORE = 5;
	public static final int HOLE_BAD_SCORE = -5;

	// coin
	public static final double COIN_RADIUS = 10;
	public static final double COIN_HEIGHT = 30;
	public static final int COIN_SCORE = 10;

	// arena
	public static final double ARENA_DAMP = 0.995;

	// light
	public static final double LIGHT_Y = -1500;
	public static final double LIGHT_BOX_SIZE = 50;

	// normalObstacle
	public static final double NORMAL_OBSTACLE_RADIUS = 70;
	public static final double NORMAL_OBSTACLE_HEIGHT = 100;
	public static final double NORMAL_OBSTACLE_Y = -(Utilities.PODIUM_HEIGHT + Utilities.NORMAL_OBSTACLE_HEIGHT)/2;

	// specialObstacle
	public static final double SPECIAL_OBSTACLE_RADIUS = 100;
	public static final double SPECIAL_OBSTACLE_HEIGHT = 150;
	public static final double SPECIAL_OBSTACLE_Y = -(Utilities.PODIUM_HEIGHT + Utilities.SPECIAL_OBSTACLE_HEIGHT)/2;
	public static final double SPECIAL_OBSTACLE_BUMP_MULTIPLYER = 3;

	// time
	public static final double MS_IN_S = 1e3;
	public static final double TIME_SCALE = 1e11;
	public static final double STARTING_TIME = 30 * Utilities.MS_IN_S;

	// buttons
	public static final double LEVEL_SELECT_BUTTON_WIDTH  = Utilities.WINDOW_WIDTH/5;
	public static final double LEVEL_SELECT_BUTTON_HEIGHT  = Utilities.WINDOW_HEIGHT/10;
	public static final double BALL_SELECT_BUTTON_WIDTH  = Utilities.WINDOW_WIDTH/10;
	public static final double BALL_SELECT_BUTTON_HEIGHT  = Utilities.WINDOW_HEIGHT/10;
	public static final Color SELECT_BUTTON_DEFAULT_COLOR  = Color.DARKBLUE;
	public static final Color SELECT_BUTTON_HOVER_COLOR  = Color.LIGHTBLUE;

	public static double clamp ( double value, double min, double max ) {
		return Math.max ( min, Math.min ( value, max ) );
	}
}
