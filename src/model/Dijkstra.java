package model;

import gui.Main;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.*;

public class Dijkstra extends SearchAlgo {

    private final List<CircleNode> openList;
    private final Set<CircleNode> closedList;
    private Timeline timeline;

    public Dijkstra(CircleNode start, CircleNode target) {
        super(start, target);

        openList = new ArrayList<>();
        closedList = new HashSet<>();
    }

    @Override
    public void findPath() {
        openList.clear();
        closedList.clear();

        initCells();
        start.setDistance(0);
        openList.add(start);

        timeline = new Timeline(new KeyFrame(Duration.millis(20), event -> {
            if (!paused) {
                if (step()) {
                    openList.forEach(c -> {
                        if (c != target) {
                            c.setFill(Color.rgb(59, 190, 255));
                            c.setStroke(CircleNode.CIRCLE_STROKE);
                        }
                    });
                    closedList.forEach(c -> {
                        if (c != start)
                            c.evaluated_animation();
                    });
                } else {

                    // Create a PauseTransition for 3 seconds
                    PauseTransition pause = new PauseTransition(Duration.seconds(1));

                    // Set the event handler for the pause to resume the Timeline when the pause is complete
                    pause.setOnFinished(finishedEvent -> {
                        drawPath();
                        timeline.stop();
                    });

                    pause.play();
                }
            }
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @Override
    public int costs(CircleNode source) {
        return source.getDistance();
    }

    @Override
    public void clean() {
        openList.forEach(circleNode -> {
            if (circleNode != Main.getData().get_source() && circleNode != Main.getData().get_destination())
                circleNode.reset();
        });
        closedList.forEach(circleNode -> {
            if (circleNode != Main.getData().get_source() && circleNode != Main.getData().get_destination())
                circleNode.reset();
        });
    }

    private boolean step() {
        if (!openList.isEmpty()) {
            CircleNode current = getCellWithLowestCosts();

            if (current == target)
                return false;

            List<CircleNode> adjacentCells = adjacent_nodes(current);
            for (CircleNode adjacentCell : adjacentCells) {
                if (!closedList.contains(adjacentCell)) {
                    computeShortestCellDistance(current, adjacentCell);
                    if (!openList.contains(adjacentCell))
                        openList.add(adjacentCell);
                }
            }

            openList.remove(current);
            closedList.add(current);
            return true;
        }
        return false;
    }

    private void computeShortestCellDistance(CircleNode source, CircleNode destination) {
        if (destination.getDistance() > source.getDistance() + 1) {
            destination.setDistance(source.getDistance() + 1);
            destination.setPrev(source);
        }
    }

    private CircleNode getCellWithLowestCosts() {
        CircleNode lowest = openList.get(0);
        for (CircleNode current : openList) {
            if (costs(current) < costs(lowest))
                lowest = current;
        }
        return lowest;
    }

    private void initCells() {
        for (int i = 0; i < Main.getData().num_rows(); i++) {
            for (int j = 0; j < Main.getData().num_columns(); j++) {
                Main.getData().get_circle_node_at(i, j).setDistance(Integer.MAX_VALUE / 2);
            }
        }
    }
}

