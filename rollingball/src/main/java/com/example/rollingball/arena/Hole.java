package com.example.rollingball.arena;

import com.example.rollingball.Utilities;
import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Translate;

public class Hole extends Cylinder {
	private int score;

	public Hole ( double radius, double height, Translate position, boolean isGood ) {
		super ( radius, height );
		PhongMaterial material;

		if(isGood) {
			material = new PhongMaterial(Color.GREENYELLOW);
			this.score = Utilities.HOLE_GOOD_SCORE;
		}
		else {
			material = new PhongMaterial(Color.RED);
			this.score = Utilities.HOLE_BAD_SCORE;
		}

		super.setMaterial ( material );
		
		super.getTransforms ( ).add ( position );
	}
	
	public boolean handleCollision ( Sphere ball ) {
		Bounds ballBounds = ball.getBoundsInParent ( );
		
		double ballX = ballBounds.getCenterX ( );
		double ballZ = ballBounds.getCenterZ ( );
		
		Bounds holeBounds = super.getBoundsInParent ( );
		double holeX      = holeBounds.getCenterX ( );
		double holeZ      = holeBounds.getCenterZ ( );
		double holeRadius = super.getRadius ( );
		
		double dx = holeX - ballX;
		double dz = holeZ - ballZ;
		
		double distance = dx * dx + dz * dz;
		
		boolean isInHole = distance < holeRadius * holeRadius;
		
		return isInHole;
	}

	public int getScore() { return score; }
}
