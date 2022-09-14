package com.example.rollingball.arena;

import com.example.rollingball.Utilities;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class LevelSelectButton extends Group {
    private Rectangle area;
    private int levelID;
    private Text text;

    public LevelSelectButton(double x, double y, double width, double height, int levelID, String text) {
        this.area = new Rectangle(width, height, Utilities.SELECT_BUTTON_DEFAULT_COLOR);
        this.area.setX(x);
        this.area.setY(y);

        this.levelID = levelID;

        this.text = new Text();
        this.text.setText(text);
        this.text.setX(x+width/2-this.text.getBoundsInLocal().getWidth()/2);
        this.text.setY(y+height/2+this.text.getBoundsInLocal().getHeight()/2);
        this.text.setStroke(Color.WHITE);

        this.addButtonEventHandlers();
        this.getChildren().addAll(this.area, this.text);
    }

    public int getLevelID() { return this.levelID; }

    private void addButtonEventHandlers() {
        this.area.addEventHandler(
                MouseEvent.MOUSE_ENTERED,
                mouseEvent -> {
                    this.area.setFill(Utilities.SELECT_BUTTON_HOVER_COLOR);
                }
        );

        this.area.addEventHandler(
                MouseEvent.MOUSE_EXITED_TARGET,
                mouseEvent -> {
                    this.area.setFill(Utilities.SELECT_BUTTON_DEFAULT_COLOR);
                }
        );

        this.text.addEventHandler(
                MouseEvent.MOUSE_ENTERED,
                mouseEvent -> {
                    this.area.setFill(Utilities.SELECT_BUTTON_HOVER_COLOR);
                }
        );
    }
}
