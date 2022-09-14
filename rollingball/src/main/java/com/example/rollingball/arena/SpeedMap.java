package com.example.rollingball.arena;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

public class SpeedMap extends Group {
    private double width;
    private double height;
    private Rectangle area;
    private Rectangle arrow;
    private Rotate rotate;
    private Scale scale;

    public SpeedMap(double width, double height) {
        this.width = width;
        this.height = height;
        this.area = new Rectangle(this.width, this.height, Color.DARKGREEN);
        this.area.getTransforms().add(new Translate(-this.width / 2, -this.height / 2));
        this.area.setStroke(Color.RED);
        this.area.setStrokeWidth(4);

        double arrowHeight = height * 0.01;
        this.arrow = new Rectangle(1, arrowHeight);
        this.arrow.setFill(Color.RED);
        this.rotate = new Rotate(0);
        this.scale = new Scale(0, 1);
        this.arrow.getTransforms().addAll(this.rotate, this.scale, new Translate(0, -arrowHeight / 2));

        super.getChildren().addAll(this.area, this.arrow);
    }

    public void update(double angleX, double angleZ, double maxAngleOffset) {
        double x = angleZ / maxAngleOffset * this.width / 2;
        double y = -angleX / maxAngleOffset * this.height / 2;
        this.scale.setX(Math.sqrt(x * x + y * y));
        double angle = (new Point2D(x, y)).normalize().angle(new Point2D(1, 0));
        angle = angleX < 0 ? angle : 360 - angle;
        this.rotate.setAngle(-angle);
    }
}
