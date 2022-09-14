package com.example.rollingball.arena;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Time extends Group {
    private double time;
    private double maxTime;
    private double maxWidth;
    private double initialPosX;
    private Text text;
    private Rectangle line;
    private boolean timeout;

    public Time(double miliSeconds, double lineX, double lineY, double lineWidth, double lineHeight) {
        this.time = miliSeconds;
        this.maxTime = miliSeconds;

        this.timeout = false;

        this.line = new Rectangle(lineX, lineY, lineWidth, lineHeight);
        this.line.setFill(Color.RED);
        this.initialPosX = lineX;
        this.maxWidth = lineWidth;

        this.text = new Text(lineX + lineWidth*0.6, lineY+lineHeight*0.75, String.format("Time: %.2fs", time/1000));
        this.text.setStroke(Color.WHITE);

        this.getChildren().addAll(line, text);
    }

    public void addTime(double time){
        this.time += time;
    }

    public void decreaseTime(double time){
        this.time -= time;
        if(this.time <= 0) {
            this.time = 0;
            this.timeout = true;
        }

        this.text.setText(String.format("Time: %.2fs", this.time/1000));

        this.line.setWidth(this.maxWidth*this.time/this.maxTime);
        this.line.setX(this.initialPosX + this.maxWidth - this.line.getWidth());
    }

    public boolean getTimeout() {
        return this.timeout;
    }
}
