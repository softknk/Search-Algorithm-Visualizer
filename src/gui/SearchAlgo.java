package gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class SearchAlgo {

    protected CircleNode start, target;
    protected boolean paused;
    protected Data data;

    public SearchAlgo(CircleNode start, CircleNode target) {
        if (start == null || target == null)
            throw new RuntimeException("source or destination node not set properly.");

        this.start = start;
        this.target = target;
    }

    public abstract void clean();

    public abstract void findPath();

    public abstract int costs(CircleNode source);

    public void drawPath() {
        final CircleNode[] tmp = {target};
        tmp[0] = tmp[0].getPrev();

        Timeline[] timelines = new Timeline[]{new Timeline()};
        timelines[0] = new Timeline(new KeyFrame(Duration.millis(50), event -> {
            if (!paused) {
                if (tmp[0].getPrev() != null) {
                    tmp[0].setFill(Color.BLACK);
                    tmp[0] = tmp[0].getPrev();
                } else {
                    timelines[0].stop();
                    setPaused(true);
                }
            }
        }));

        timelines[0].setCycleCount(Timeline.INDEFINITE);
        timelines[0].play();
    }

    public List<CircleNode> getAdjacentCells(CircleNode cell) {
        CircleNode[] adjacentCells = new CircleNode[4];

        if (cell.row - 1 >= 0)
            adjacentCells[0] = Main.getData().get_circle_node_at(cell.col, cell.row - 1);
        if (cell.col + 1 < Main.getData().num_horizontal_circles())
            adjacentCells[1] = Main.getData().get_circle_node_at(cell.col + 1, cell.row);
        if (cell.row + 1 < Main.getData().num_vertical_circles())
            adjacentCells[2] = Main.getData().get_circle_node_at(cell.col, cell.row + 1);
        if (cell.col - 1 >= 0)
            adjacentCells[3] = Main.getData().get_circle_node_at(cell.col - 1, cell.row);

        List<CircleNode> result = new ArrayList<>();

        for (CircleNode adjacentCell : adjacentCells) {
            if (adjacentCell != null && !adjacentCell.isObstacle())
                result.add(adjacentCell);
        }

        return result;
    }

    public void pause() {
        paused = true;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
        if (paused)
            Menu.enable_menu_actions();
        else
            Menu.disable_menu_actions();
    }

    public boolean getPaused() {
        return paused;
    }
}
