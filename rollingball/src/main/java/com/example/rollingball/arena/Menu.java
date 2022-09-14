package com.example.rollingball.arena;
import com.example.rollingball.Utilities;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.transform.Translate;

import java.util.ArrayList;

public class Menu extends Group {
    private ArrayList<LevelSelectButton> levelButtons;
    private ArrayList<BallSelectButton> ballSelectButtons;

    public Menu() {
        this.ballSelectButtons = new ArrayList<>();
        this.levelButtons = new ArrayList<>();

        this.addBallSelectButtons();
        this.createLevelButtons();
    }

    private void createLevelButtons() {
        for(int i = 0; i < 3; i++)
            this.levelButtons.add(new LevelSelectButton(
                    Utilities.WINDOW_WIDTH/2 - Utilities.LEVEL_SELECT_BUTTON_WIDTH/2,
                    Utilities.WINDOW_HEIGHT/2 - Utilities.LEVEL_SELECT_BUTTON_HEIGHT/2 + i*Utilities.LEVEL_SELECT_BUTTON_HEIGHT*1.5,
                    Utilities.LEVEL_SELECT_BUTTON_WIDTH,
                    Utilities.LEVEL_SELECT_BUTTON_HEIGHT,
                    i + 1,
                    String.format("Level %d", i + 1)
            ));

        this.getChildren().addAll(this.levelButtons);
    }

    private void addBallSelectButtons() {
        for (int i = 0; i < 3; i++)
            this.ballSelectButtons.add(new BallSelectButton(
                    Utilities.WINDOW_WIDTH / 2 - Utilities.BALL_SELECT_BUTTON_WIDTH * 1.5 - Utilities.BALL_SELECT_BUTTON_WIDTH / 2 + i * Utilities.BALL_SELECT_BUTTON_WIDTH * 1.5,
                    Utilities.WINDOW_HEIGHT / 4 - Utilities.BALL_SELECT_BUTTON_HEIGHT / 2,
                    Utilities.BALL_SELECT_BUTTON_WIDTH,
                    Utilities.BALL_SELECT_BUTTON_HEIGHT,
                    i
            ));
        this.ballSelectButtons.get(0).setPressed();

        BallSelectButton.setButtons(this.ballSelectButtons);
        this.getChildren().addAll(this.ballSelectButtons);
    }

    public ArrayList<LevelSelectButton> getLevelButtons() { return levelButtons; }
}

