package com.example.rollingball.arena;

import com.example.rollingball.Utilities;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;

import java.util.ArrayList;

public class BallSelectButton extends Group {
    private static int pressedID;
    private static ArrayList<BallSelectButton> buttons;

    private Rectangle area;
    private int buttonID;
    private boolean isPressed;
    private Circle circle;
    private Color ballColor;
    private double ballAcceleration;

    public BallSelectButton(double x, double y, double width, double height, int buttonID){
        this.area = new Rectangle(width, height, Utilities.SELECT_BUTTON_DEFAULT_COLOR);
        this.area.setX(x);
        this.area.setY(y);

        this.buttonID = buttonID;

        this.circle = new Circle(width/3);
        if(buttonID == 0) {
            this.ballColor = Color.RED;
        }
        else if(buttonID == 1) {
            this.ballColor = Color.PURPLE;
        }
        else {
            this.ballColor = Color.WHITE;
        }
        this.circle.setFill(this.ballColor);

        circle.getTransforms().addAll(
                new Translate(x + width/2, y + height/2)
        );

        if(buttonID == 0) this.ballAcceleration = Utilities.BALL_ACCELERATION_FACTOR_1;
        else if(buttonID == 1) this.ballAcceleration = Utilities.BALL_ACCELERATION_FACTOR_2;
        else this.ballAcceleration = Utilities.BALL_ACCELERATION_FACTOR_3;

        this.addButtonEventHandlers();
        this.getChildren().addAll(this.area, this.circle);
    }

    public static Color getBallColor() { return BallSelectButton.buttons.get(BallSelectButton.pressedID).ballColor; }

    public static void setButtons(ArrayList<BallSelectButton> buttons) { BallSelectButton.buttons = buttons; }

    public static int getPressedID() { return pressedID; }

    public static double getBallAcceleration() {
        return BallSelectButton.buttons.get(BallSelectButton.pressedID).ballAcceleration;
    }

    public void setPressed () {
        this.area.setFill(Utilities.SELECT_BUTTON_HOVER_COLOR);
        this.isPressed = true;
        BallSelectButton.pressedID = this.buttonID;
    }

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
                    if(!this.isPressed) this.area.setFill(Utilities.SELECT_BUTTON_DEFAULT_COLOR);
                }
        );

        this.area.addEventHandler(
                MouseEvent.MOUSE_PRESSED,
                mouseEvent -> {
                    if(!this.isPressed) {
                        BallSelectButton.unpress();
                        this.setPressed();
                    }
                }
        );

        this.circle.addEventHandler(
                MouseEvent.MOUSE_PRESSED,
                mouseEvent -> {
                    if(!this.isPressed) {
                        BallSelectButton.unpress();
                        this.setPressed();
                    }
                }
        );

        this.circle.addEventHandler(
                MouseEvent.MOUSE_ENTERED,
                mouseEvent -> {
                    this.area.setFill(Utilities.SELECT_BUTTON_HOVER_COLOR);
                }
        );
    }

    private static void unpress() {
        BallSelectButton t = BallSelectButton.buttons.get(BallSelectButton.pressedID);
        t.area.setFill(Utilities.SELECT_BUTTON_DEFAULT_COLOR);
        t.isPressed = false;
    }
}
