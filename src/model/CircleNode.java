package model;

import gui.Main;
import gui.Menu;
import javafx.animation.FillTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class CircleNode extends Circle {

    public static final Color CIRCLE_FILL = Color.rgb(40, 42, 54);
    public static final Color CIRCLE_STROKE = Color.rgb(81, 84, 101);
    public static final Color CIRCLE_OBSTACLE_FILL = Color.rgb(81, 84, 101);
    public static final Color CIRCLE_OBSTACLE_STROKE = Color.rgb(81, 84, 101);

    public static final double CIRCLE_STROKE_WIDTH = 2;

    public static final double MIN_RADIUS = 2.0;

    public int row;
    public int col;

    public int distance;

    public CircleNode prev;

    private boolean isObstacle;

    private boolean animationRunning;
    private Timeline animation;

    public CircleNode(int row, int col) {
        this.row = row;
        this.col = col;

        setStrokeWidth(CIRCLE_STROKE_WIDTH);
        setStroke(CircleNode.CIRCLE_STROKE);
        setFill(CircleNode.CIRCLE_FILL);

        this.isObstacle = false;

        setOnMouseClicked(event -> {
            if (Menu.source_selection) {
                Main.getData().source_selection(this);
                Menu.setSourceSelection(false);
            } else if (Menu.dest_selection) {
                Main.getData().destination_selection(this);
                Menu.setDestSelection(false);
            } else {
                if (isObstacle)
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
                    setFill(Color.rgb(227, 172, 86));
                else
                    setFill(Color.rgb(50, 74, 182));
            }
        });

        setOnMouseExited(mouseEvent -> {
            if (isMovable() && (Menu.source_selection || Menu.dest_selection)) {
                setStroke(CIRCLE_STROKE);
                setFill(CIRCLE_FILL);
            }
        });
    }

    public void path_node_animation() {
        setStroke(Color.WHITE);
        setStrokeWidth(CIRCLE_STROKE_WIDTH);
        setFill(Color.rgb(209, 13, 75));
    }

    public void evaluated_animation() {
        if (!animationRunning) {
            setFill(Color.rgb(59, 190, 255));
            setStroke(null);

            FillTransition fill = new FillTransition();
            fill.setDuration(Duration.millis(1600));
            fill.setFromValue(Color.rgb(51, 204, 255));
            fill.setToValue(Color.rgb(109, 0, 181));
            fill.setShape(this);
            fill.setAutoReverse(true);
            fill.setCycleCount(1);
            fill.play();

            // when the circle fill transition is finished the stroke gets activated
            fill.setOnFinished(actionEvent -> {
                setStroke(Color.rgb(109, 0, 181));
                setStrokeWidth(CircleNode.CIRCLE_STROKE_WIDTH);
            });

            double RADIUS = getRadius();
            setRadius(getRadius() / 4);
            animation = new Timeline(new KeyFrame(Duration.millis(time_per_duration(getRadius(), 0.01, 100)), event -> {
                if (getRadius() <= RADIUS && !Main.getData().get_current().paused)
                    setRadius(getRadius() + 0.01);
                else {
                    setRadius(RADIUS);
                    animation.stop();
                }
            }));

            animation.setCycleCount(Timeline.INDEFINITE);
            animation.play();

            animationRunning = true;
        }
    }

    private static double time_per_duration(double radius, double rate, double animation_time) {
        return animation_time / (radius / rate);
    }

    public void reset() {
        setMovable();
        setFill(CIRCLE_FILL);
        setStroke(CIRCLE_STROKE);
        setStrokeWidth(CIRCLE_STROKE_WIDTH);
        animationRunning = false;
        prev = null;
        distance = 0;
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
        return isObstacle;
    }

    public boolean isMovable() {
        return !isObstacle;
    }

    public void setObstacle() {
        this.isObstacle = true;

        setFill(CIRCLE_OBSTACLE_FILL);
        setStroke(CIRCLE_OBSTACLE_STROKE);
    }

    public void setMovable() {
        this.isObstacle = false;

        setFill(CIRCLE_FILL);
        setStroke(CIRCLE_STROKE);
    }
}