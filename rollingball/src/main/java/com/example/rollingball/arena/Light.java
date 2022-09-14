package com.example.rollingball.arena;

import com.example.rollingball.Main;
import com.example.rollingball.Utilities;
import javafx.scene.Group;
import javafx.scene.PointLight;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Translate;

public class Light extends Group {
    private boolean isOn;
    PhongMaterial material;
    PointLight pointLight;

    public Light(double boxSize){
        this.material = new PhongMaterial(Color.GRAY);
        this.material.setSelfIlluminationMap(new Image(Main.class.getClassLoader().getResourceAsStream("light.jpg")));
        Box box = new Box(boxSize, boxSize, boxSize);
        box.setMaterial(this.material);
        this.pointLight = new PointLight(Color.WHITE);
        this.getChildren().addAll(box, this.pointLight);
        this.getTransforms().add(new Translate(0, Utilities.LIGHT_Y, 0));
        this.isOn = true;
    }

    public void switchLight(){
        if(!this.isOn) {
            this.getChildren().add(this.pointLight);
            this.material.setSelfIlluminationMap(new Image(Main.class.getClassLoader().getResourceAsStream("light.jpg")));
        }
        else {
            this.getChildren().remove(this.pointLight);
            this.material.setSelfIlluminationMap(null);
        }

        this.isOn = !this.isOn;
    }
}
