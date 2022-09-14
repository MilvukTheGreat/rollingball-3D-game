package com.example.rollingball.arena;

import com.example.rollingball.Main;
import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Translate;

public class NormalObstacle extends Cylinder {

    public NormalObstacle(double radius, double height, double x, double y, double z){
        super(radius, height);
        this.getTransforms().addAll(new Translate(x, y, z));

        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(new Image(Main.class.getClassLoader().getResourceAsStream("obstacle.jpg")));
        this.setMaterial(material);
    }
}
