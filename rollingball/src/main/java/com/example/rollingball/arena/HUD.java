package com.example.rollingball.arena;

import com.example.rollingball.Utilities;
import javafx.scene.Group;
import javafx.scene.SubScene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Translate;

import java.util.ArrayList;

public class HUD extends SubScene {
    private Group root;
    private SpeedMap speedMap;
    private int lives;
    private ArrayList<Circle> livesVisuals;
    private int score;
    private Text scoreText;
    private Time timeLeft;

    public HUD(Group root, double width, double height){
        super(root, width, height);
        this.root = root;

        // lives
        this.livesVisuals = new ArrayList<>();
        this.lives = Utilities.STARTING_LIVES;
        for(int i = 0; i < Utilities.STARTING_LIVES; i++){
            Circle circle = new Circle(
                    Utilities.WINDOW_WIDTH*0.05 + i*Utilities.LIFE_CIRCLE_RADIUS*2.5,
                    Utilities.WINDOW_HEIGHT*0.05,
                    Utilities.LIFE_CIRCLE_RADIUS
                    );
            circle.setFill(Color.RED);
            this.livesVisuals.add(circle);
        }
        this.root.getChildren().addAll(this.livesVisuals);

        // SpeedMap
        this.speedMap = new SpeedMap(Utilities.SPEEDMAP_WIDTH, Utilities.SPEEDMAP_HEIGHT);
        this.speedMap.getTransforms().addAll(new Translate(Utilities.SPEEDMAP_WIDTH/2, height - Utilities.SPEEDMAP_HEIGHT/2));
        this.root.getChildren().add(this.speedMap);

        // score
        this.scoreText = new Text("Score: " + this.score);
        this.scoreText.setFont(new Font(20));
        this.scoreText.setX(Utilities.WINDOW_WIDTH*0.875);
        this.scoreText.setY(Utilities.WINDOW_HEIGHT*0.05);
        this.scoreText.setStroke(Color.RED);
        this.root.getChildren().add(this.scoreText);

        // time
        this.timeLeft = new Time(Utilities.STARTING_TIME, Utilities.WINDOW_WIDTH*0.65, Utilities.WINDOW_HEIGHT * 0.9, Utilities.WINDOW_WIDTH*0.30, Utilities.WINDOW_HEIGHT*0.025);
        this.root.getChildren().add(this.timeLeft);

    }

    public SpeedMap getSpeedMap() { return speedMap; }

    public void addLife(){
        if(this.lives + 1 <= Utilities.MAX_LIVES) {
            Circle circle = new Circle(
                    Utilities.WINDOW_WIDTH * 0.05 + this.lives * Utilities.LIFE_CIRCLE_RADIUS * 2.5,
                    Utilities.PODIUM_HEIGHT * 0.05,
                    Utilities.LIFE_CIRCLE_RADIUS
            );
            circle.setFill(Color.RED);

            this.livesVisuals.add(circle);
            this.root.getChildren().add(circle);
            this.lives++;
        }
    }

    public void removeLife(){
        this.lives--;
        this.root.getChildren().remove(this.livesVisuals.get(this.lives));
    }

    public boolean IsGameOver(){
        return this.lives <= 0;
    }

    public void ShowGameOverText(){
        Text gameOverText = new Text("GAME OVER");
        gameOverText.setFont(new Font(40));
        gameOverText.setX((Utilities.WINDOW_WIDTH - gameOverText.getLayoutBounds().getWidth())/2);
        gameOverText.setY((Utilities.WINDOW_HEIGHT - gameOverText.getLayoutBounds().getHeight())/2);
        gameOverText.setStroke(Color.RED);
        this.root.getChildren().add(gameOverText);
    }

    public void addScore(int score){
        this.score += score;
        this.scoreText.setText("Score: " + this.score);
    }

    public Time getTimeLeft(){ return this.timeLeft; }
}
