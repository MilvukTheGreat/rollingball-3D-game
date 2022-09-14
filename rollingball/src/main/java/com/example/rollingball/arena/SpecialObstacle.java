package com.example.rollingball.arena;

import com.example.rollingball.Main;
import com.example.rollingball.Utilities;
import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Translate;

public class SpecialObstacle  extends Cylinder {
    private double bumpMultiplyer;
    public SpecialObstacle(double radius, double height, double x, double y, double z){
        super(radius, height);
        this.getTransforms().addAll(new Translate(x, y, z));

        this.bumpMultiplyer = Utilities.SPECIAL_OBSTACLE_BUMP_MULTIPLYER;
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(new Image(Main.class.getClassLoader().getResourceAsStream("specialobstacle.jpg")));
        this.setMaterial(material);
    }

    public double getBumpMultiplyer() {return this.bumpMultiplyer; }
}
