package com.example.rollingball.arena;

import com.example.rollingball.Utilities;
import javafx.scene.PerspectiveCamera;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;

public class DefaultCamera  extends PerspectiveCamera {
    private Translate position;
    private Rotate rotateX;
    private Rotate rotateY;
    private double oldX;
    private double oldY;

    public DefaultCamera() {
        super(true);
        this.position = new Translate(0, 0, Utilities.CAMERA_Z);
        this.rotateX = new Rotate(Utilities.CAMERA_X_ANGLE, Rotate.X_AXIS);
        this.rotateY = new Rotate(0, Rotate.Y_AXIS);
        super.getTransforms().addAll(this.rotateY, this.rotateX, this.position);
    }

    public void handleMouseEvent(MouseEvent event) {
        double positionX = event.getSceneX();
        double positionY = event.getSceneY();
        if (event.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
            this.oldX = positionX;
            this.oldY = positionY;
        } else if (event.getEventType().equals(MouseEvent.MOUSE_DRAGGED)) {
            double dX = positionX - this.oldX;
            double dY = positionY - this.oldY;
            this.oldX = positionX;
            this.oldY = positionY;
            this.rotateX.setAngle(Utilities.clamp(this.rotateX.getAngle() - dY * 0.6, -90, 0));
            this.rotateY.setAngle(this.rotateY.getAngle() - dX * 0.6);
        }

    }

    public void handleScrollEvent(ScrollEvent event) {
        if (event.getDeltaY() > 0) {
            this.position.setZ(this.position.getZ() + 500);
        } else {
            this.position.setZ(this.position.getZ() - 500);
        }

    }
}
