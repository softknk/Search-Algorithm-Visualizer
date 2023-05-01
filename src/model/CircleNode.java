package model;

import gui.Constants;

import gui.Main;
import gui.Menu;
import javafx.animation.FillTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class CircleNode extends Circle {

    private final int row;
    private final int col;
    private boolean obstacle;
    private int distance;
    private CircleNode prev;
    private boolean animationRunning;
    private Timeline animation;

    public CircleNode(int row, int col, boolean obstacle) {
        this.row = row;
        this.col = col;

        this.obstacle = obstacle;

        setFill(Constants.CIRCLE_FILL);
        setStrokeWidth(Constants.CIRCLE_STROKE_WIDTH);
        setStroke(Constants.CIRCLE_STROKE);

        setOnMouseClicked(mouseEvent -> {
            if (Menu.source_selection) {
                Main.getData().source_selection(this);
                Menu.setSourceSelection(false);
            } else if (Menu.dest_selection) {
                Main.getData().destination_selection(this);
                Menu.setDestSelection(false);
            } else {
                if (obstacle)
                    setMovable();
                else
                    setObstacle();
            }
        });

        setOnMouseEntered(mouseEvent -> {
            if (mouseEvent.isAltDown())
                setObstacle();
            if (isMovable() && (Menu.source_selection || Menu.dest_selection)) {
                setStroke(Color.WHITE);
                if (Menu.source_selection)
                    setFill(Constants.SOURCE_SELECTION_FILL);
                else
                    setFill(Constants.DEST_SELECTION_FILL);
            }
        });

        setOnMouseExited(mouseEvent -> {
            if (isMovable() && (Menu.source_selection || Menu.dest_selection)) {
                setStroke(Constants.CIRCLE_STROKE);
                setFill(Constants.CIRCLE_FILL);
            }
        });
    }

    public void path_node_animation() {
        setFill(Constants.PATH_NODE_FILL);
        setStroke(Color.WHITE);
    }

    public void node_found_animation() {
        setFill(Constants.EVAL_ANIM_FROM_FILL);
        setStroke(Constants.EVAL_ANIM_FROM_FILL);
        setStrokeWidth(Constants.CIRCLE_STROKE_WIDTH);
    }

    public void evaluated_animation() {
        if (!animationRunning) {
            setStroke(Color.TRANSPARENT);

            FillTransition fill = new FillTransition();
            fill.setDuration(Duration.millis(Constants.CIRCLE_FILL_ANIM_SPEED));
            fill.setFromValue(Constants.EVAL_ANIM_FROM_FILL);
            fill.setToValue(Constants.EVAL_ANIM_TO_FILL);
            fill.setShape(this);
            fill.setAutoReverse(true);
            fill.setCycleCount(1);
            fill.play();

            fill.setOnFinished(actionEvent -> {
                setFill(Constants.ANIM_END_FILL);
                setStroke(Constants.ANIM_END_FILL);
            });

            double RADIUS = getRadius();
            setRadius(getRadius() / 4);
            animation = new Timeline(new KeyFrame(Duration.millis(Constants.CIRCLE_ANIM_SPEED / (getRadius() / 0.01)), event -> {
                if (getRadius() <= RADIUS && !Main.getData().get_current().paused) {
                    setRadius(getRadius() + 0.01);
                } else {
                    setRadius(RADIUS);
                    animation.stop();
                }
            }));

            animation.setCycleCount(Timeline.INDEFINITE);
            animation.play();

            animationRunning = true;
        }
    }

    public void reset() {
        setMovable();
        setFill(Constants.CIRCLE_FILL);
        setStroke(Constants.CIRCLE_STROKE);
        setStrokeWidth(Constants.CIRCLE_STROKE_WIDTH);
        animationRunning = false;
        prev = null;
        distance = 0;
    }

    public void setObstacle() {
        this.obstacle = true;

        setFill(Constants.CIRCLE_OBSTACLE_FILL);
        setStroke(Constants.CIRCLE_OBSTACLE_STROKE);
    }

    public void setMovable() {
        this.obstacle = false;

        setFill(Constants.CIRCLE_FILL);
        setStroke(Constants.CIRCLE_STROKE);
    }

    public boolean isAnimationRunning() {
        return animationRunning;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public CircleNode getPrev() {
        return prev;
    }

    public void setPrev(CircleNode prev) {
        this.prev = prev;
    }

    public boolean isObstacle() {
        return obstacle;
    }

    public boolean isMovable() {
        return !obstacle;
    }

    public int row() {
        return row;
    }

    public int column() {
        return col;
    }
}