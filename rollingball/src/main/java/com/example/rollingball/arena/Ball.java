package com.example.rollingball.arena;

import com.example.rollingball.Main;
import com.example.rollingball.Utilities;
import javafx.geometry.Bounds;
import javafx.geometry.Point3D;
import javafx.scene.paint.Material;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Translate;

public class Ball extends Sphere {
	private Translate initialPosition;
	private Translate position;
	private Point3D speed;
	private double accelerationFactor;
	
	public Ball ( double radius, Material material, Translate position, double accelerationFactor ) {
		super ( radius );
		super.setMaterial ( material );

		this.initialPosition = new Translate(position.getX(), position.getY(), position.getZ());
		this.position = position;
		this.accelerationFactor = accelerationFactor;
		super.getTransforms ( ).add ( this.position );
		
		this.speed = new Point3D ( 0, 0, 0 );
	}
	
	public boolean update (
			double deltaSeconds,
			double top,
			double bottom,
			double left,
			double right,
			double xAngle,
			double zAngle,
			double maxAngleOffset,
			double maxAcceleration,
			double damp
	) {
		double newPositionX = this.position.getX ( ) + this.speed.getX ( ) * deltaSeconds;
		double newPositionZ = this.position.getZ ( ) + this.speed.getZ ( ) * deltaSeconds;
		
		this.position.setX ( newPositionX );
		this.position.setZ ( newPositionZ );
		
		double accelerationX = (maxAcceleration * zAngle / maxAngleOffset)*this.accelerationFactor;
		double accelerationZ = (-maxAcceleration * xAngle / maxAngleOffset)*this.accelerationFactor;
		
		double newSpeedX = ( this.speed.getX ( ) + accelerationX * deltaSeconds ) * damp;
		double newSpeedZ = ( this.speed.getZ ( ) + accelerationZ * deltaSeconds ) * damp;
		
		this.speed = new Point3D ( newSpeedX, 0, newSpeedZ );
		
		boolean xOutOfBounds = ( newPositionX > right ) || ( newPositionX < left );
		boolean zOutOfBounds = ( newPositionZ > top ) || ( newPositionZ < bottom );
		
		return xOutOfBounds || zOutOfBounds;
	}

	public boolean handleFenceCollision(Box fence) {
		Bounds fenceBounds = fence.getBoundsInParent();
		Bounds ballBounds = this.getBoundsInParent();
		double ballCenterX = ballBounds.getCenterX();
		double ballCenterZ = ballBounds.getCenterZ();
		double sideX = Utilities.clamp(ballCenterX, fenceBounds.getMinX(), fenceBounds.getMaxX());
		double sideZ = Utilities.clamp(ballCenterZ, fenceBounds.getMinZ(), fenceBounds.getMaxZ());
		double dx = sideX - ballCenterX;
		double dz = sideZ - ballCenterZ;
		boolean collision = dx * dx + dz * dz < this.getRadius() * this.getRadius();
		if (collision) {
			if (sideX != fenceBounds.getMaxX() && sideX != fenceBounds.getMinX()) {
				if (sideZ == fenceBounds.getMaxZ() || sideZ == fenceBounds.getMinZ()) {
					this.speed = new Point3D(this.speed.getX(), 0, -this.speed.getZ());
				}
			} else {
				this.speed = new Point3D(-this.speed.getX(), 0, this.speed.getZ());
			}
		}
		return collision;
	}

	public void resetPosition() {
		this.position.setX(this.initialPosition.getX());
		this.position.setY(this.initialPosition.getY());
		this.position.setZ(this.initialPosition.getZ());
		this.speed = new Point3D(0, 0, 0);
	}

	public void setInitialPosition(double x, double z){
		this.initialPosition.setX(x);
		this.initialPosition.setZ(z);

		this.resetPosition();
	}

	public boolean handleCoinCollision(Coin coin) {
		return this.getBoundsInParent().intersects(coin.getBoundsInParent());
	}

	public boolean handleObstacleCollision(NormalObstacle normalObstacle) {
		Bounds ballBounds = this.getBoundsInParent();
		Bounds obstacleBounds = normalObstacle.getBoundsInParent();
		double dx = ballBounds.getCenterX() - obstacleBounds.getCenterX();
		double dz = ballBounds.getCenterZ() - obstacleBounds.getCenterZ();
		double dr = this.getRadius() + normalObstacle.getRadius();
		boolean collision = dx * dx + dz * dz < dr * dr;
		if (collision) {
			Point3D normal = (new Point3D(ballBounds.getCenterX() - obstacleBounds.getCenterX(), 0, ballBounds.getCenterZ() - obstacleBounds.getCenterZ())).normalize();
			this.speed = this.speed.subtract(normal.multiply(2 * this.speed.dotProduct(normal)));
		}
		return collision;
	}

	public boolean handleObstacleCollision(SpecialObstacle specialObstacle) {
		Bounds ballBounds = this.getBoundsInParent();
		Bounds obstacleBounds = specialObstacle.getBoundsInParent();
		double dx = ballBounds.getCenterX() - obstacleBounds.getCenterX();
		double dz = ballBounds.getCenterZ() - obstacleBounds.getCenterZ();
		double dr = this.getRadius() + specialObstacle.getRadius();
		boolean collision = dx * dx + dz * dz < dr * dr;
		if (collision) {
			Point3D normal = (new Point3D(ballBounds.getCenterX() - obstacleBounds.getCenterX(), 0, ballBounds.getCenterZ() - obstacleBounds.getCenterZ())).normalize();
			this.speed = this.speed.subtract(normal.multiply(2 * this.speed.dotProduct(normal) * specialObstacle.getBumpMultiplyer()));
		}
		return collision;
	}
	
}
