package model;

import gui.Data;
import gui.Main;
import gui.Menu;
import javafx.animation.FillTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.util.Duration;

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
        timelines[0] = new Timeline(new KeyFrame(Duration.millis(40), event -> {
            if (!paused) {
                if (tmp[0].getPrev() != null) {

                    tmp[0].path_node_animation();

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

    public List<CircleNode> adjacent_nodes(CircleNode node) {
        CircleNode[] adj_nodes = new CircleNode[4];

        if (node.row + 1 < Main.getData().num_rows())
            adj_nodes[0] = Main.getData().get_circle_node_at(node.row + 1, node.col);
        if (node.row - 1 >= 0)
            adj_nodes[1] = Main.getData().get_circle_node_at(node.row - 1, node.col);

        if (node.col + 1 < Main.getData().num_columns())
            adj_nodes[2] = Main.getData().get_circle_node_at(node.row, node.col + 1);
        if (node.col - 1 >= 0)
            adj_nodes[3] = Main.getData().get_circle_node_at(node.row, node.col - 1);

        List<CircleNode> result = new ArrayList<>();

        for (CircleNode adj_node : adj_nodes) {
            if (adj_node != null && adj_node.isMovable())
                result.add(adj_node);
        }

        return result;
    }

    public void pause() {
        paused = true;
    }

    public boolean isRunning() {
        return !paused;
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
