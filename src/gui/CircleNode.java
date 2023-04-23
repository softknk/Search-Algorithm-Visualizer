package gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class CircleNode extends Circle {

    public int row;
    public int col;

    public int distance;

    public CircleNode prev;

    private boolean animationRunning;
    private Timeline animation;

    public CircleNode(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public void animate(Paint start_paint, Paint final_paint) {
        if (!animationRunning) {
            setFill(start_paint);
            double RADIUS = getRadius();
            setRadius(getRadius() / 4);
            animation = new Timeline(new KeyFrame(Duration.millis(35), event -> {
                if (getRadius() <= RADIUS)
                    setRadius(getRadius() + 0.2);
                else {
                    setRadius(RADIUS);
                    setFill(final_paint);
                    animation.stop();
                }
            }));
            animation.setCycleCount(Timeline.INDEFINITE);
            animation.play();
            animationRunning = true;
        }
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
}
