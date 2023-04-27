package gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class CircleNode extends Circle {

    public static final Color CIRCLE_FILL = Color.rgb(40, 42, 54);
    public static final Color CIRCLE_STROKE = Color.rgb(200, 200, 200);
    public static final Color CIRCLE_OBSTACLE_FILL = Color.rgb(200, 200, 200);
    public static final Color CIRCLE_OBSTACLE_STROKE = Color.rgb(200, 200, 200);

    public static final double MIN_RADIUS = 5.0;

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

        setStrokeWidth(2);
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
                setObstacle(!isObstacle);
            }
        });

        setOnMouseEntered(event -> {
            if (event.isAltDown())
                setObstacle(true);
        });
    }

    public void animate() {
        if (!animationRunning) {
            setFill(Color.rgb(59, 190, 255));
            setStroke(Color.WHITE);
            double RADIUS = getRadius();
            setRadius(getRadius() / 4);
            animation = new Timeline(new KeyFrame(Duration.millis(time_per_duration(getRadius(), 0.01, 300)), event -> {
                if (getRadius() <= RADIUS && !Main.getData().get_current().paused)
                    setRadius(getRadius() + 0.01);
                else {
                    setRadius(RADIUS);
                    setFill(Color.rgb(116, 46, 255));
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
        setObstacle(false);
        setFill(CIRCLE_FILL);
        setStroke(CIRCLE_STROKE);
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

    public void setObstacle(boolean obstacle) {
        this.isObstacle = obstacle;

        if (obstacle) {
            setFill(CIRCLE_OBSTACLE_FILL);
            setStroke(CIRCLE_OBSTACLE_STROKE);
        } else {
            setFill(CIRCLE_FILL);
            setStroke(CIRCLE_STROKE);
        }
    }

    public void setAnimationRunning(boolean animationRunning) {
        this.animationRunning = animationRunning;
    }
}
