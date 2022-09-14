package com.example.rollingball.arena;

import com.example.rollingball.Utilities;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;

public class Fence extends Group {
    private Box[] fences;

    public Fence(){
        PhongMaterial material = new PhongMaterial(Color.YELLOW);

        this.fences = new Box[4];
        Translate[] translates = {
                new Translate(Utilities.PODIUM_WIDTH / 2, -(Utilities.FENCE_HEIGHT + Utilities.PODIUM_HEIGHT) / 2, 0),
                new Translate(Utilities.PODIUM_LENGTH / 2, -(Utilities.FENCE_HEIGHT + Utilities.PODIUM_HEIGHT) / 2, 0),
                new Translate(Utilities.PODIUM_WIDTH / 2, -(Utilities.FENCE_HEIGHT + Utilities.PODIUM_HEIGHT) / 2, 0),
                new Translate(Utilities.PODIUM_LENGTH / 2, -(Utilities.FENCE_HEIGHT + Utilities.PODIUM_HEIGHT) / 2, 0)
        };

        for(int i = 0; i < this.fences.length; i++) {
            this.fences[i] = new Box(Utilities.FENCE_WIDTH, Utilities.FENCE_HEIGHT, Utilities.FENCE_LENGTH);
            this.fences[i].setMaterial(material);
            Rotate rotate = new Rotate(i * 90, Rotate.Y_AXIS);
            this.fences[i].getTransforms().addAll(rotate, translates[i]);
        }
        this.getChildren().addAll(this.fences);
    }

    public Box[] getFences() { return fences; }
}
