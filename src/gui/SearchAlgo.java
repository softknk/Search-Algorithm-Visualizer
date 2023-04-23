package gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public abstract class SearchAlgo {

    protected CircleNode start, target;
    protected boolean paused, stopped;

    public List<Line> lines;
    public List<Circle> circles;

    public abstract void clean();

    public abstract void findPath();

    public abstract int costs(CircleNode source);

    public void stop() {
        stopped = true;
    }

    public void drawPath() {
        return;
    }

    public void pause() {
        paused = true;
    }

    public boolean isPaused() {
        return paused;
    }

    public boolean isStopped() {
        return stopped;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public boolean getPaused() {
        return paused;
    }
}
