package com.example.rollingball.arena;

import com.example.rollingball.Utilities;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

public class Coin extends Cylinder {
    private Translate translate;
    private Rotate rotate;
    private int score;

    public Coin(Translate translate){
        super(Utilities.COIN_HEIGHT, Utilities.COIN_RADIUS);

        this.score = Utilities.COIN_SCORE;

        this.translate = translate;
        this.setMaterial(new PhongMaterial(Color.YELLOW));

        this.rotate = new Rotate(0, Rotate.X_AXIS);
        this.getTransforms().addAll(this.translate, new Rotate(90, Rotate.Z_AXIS), this.rotate);
        Timeline timelineTranslate = new Timeline(
                new KeyFrame(Duration.seconds(0), new KeyValue(this.translate.yProperty(), -Utilities.COIN_HEIGHT*1.5)),
                new KeyFrame(Duration.seconds(2), new KeyValue(this.translate.yProperty(), -Utilities.COIN_HEIGHT*2.5)),
                new KeyFrame(Duration.seconds(4), new KeyValue(this.translate.yProperty(), -Utilities.COIN_HEIGHT*1.5))
        );
        timelineTranslate.setCycleCount(Timeline.INDEFINITE);
        timelineTranslate.play();

        Timeline timelineRotate = new Timeline(
                new KeyFrame(Duration.seconds(0), new KeyValue(this.rotate.angleProperty(), 0, Interpolator.LINEAR)),
                new KeyFrame(Duration.seconds(3), new KeyValue(this.rotate.angleProperty(), 360, Interpolator.LINEAR))
        );
        timelineRotate.setCycleCount(Timeline.INDEFINITE);
        timelineRotate.play();
    }

    public int getScore() { return this.score; }
}
