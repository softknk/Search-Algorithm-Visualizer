package gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.*;

public class Dijkstra extends SearchAlgo {

    private final List<CircleNode> openList;
    private final Set<CircleNode> closedList;
    private Timeline timeline;

    public Dijkstra(CircleNode start, CircleNode target) {
        this.start = start;
        this.target = target;

        openList = new ArrayList<>();
        closedList = new HashSet<>();
    }

    @Override
    public void findPath() {
        if (start != null && target != null)
            _findPath();
    }

    @Override
    public int costs(CircleNode source) {
        return source.getDistance();
    }

    private void _findPath() {
        openList.clear();
        closedList.clear();

        initCells();
        start.setDistance(0);
        openList.add(start);

        timeline = new Timeline(new KeyFrame(Duration.millis(20), event -> {
            if (!paused) {
                if (step()) {
                    openList.forEach(c -> {
                        c.setFill(Color.YELLOW);
                        c.setStroke(Color.TRANSPARENT);
                    });
                    closedList.forEach(c -> {
                        if (c != start)
                            c.animate();
                    });
                } else {
                    drawPath();
                    timeline.stop();
                }
            }

            if (stopped) {
                clean();
                timeline.stop();
            }

        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @Override
    public void clean() {
        return;
    }

    private boolean step() {
        if (!openList.isEmpty()) {
            CircleNode current = getCellWithLowestCosts();

            if (current == target)
                return false;

            List<CircleNode> adjacentCells = getAdjacentCells(current);
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
        Iterator<CircleNode> iterator = openList.iterator();
        while (iterator.hasNext()) {
            CircleNode current = iterator.next();
            if (costs(current) < costs(lowest))
                lowest = current;
        }
        return lowest;
    }

    private void initCells() {
        for (int i = 0; i < Main.circles.length; i++) {
            for (int j = 0; j < Main.circles[0].length; j++) {
                Main.circles[i][j].setDistance(Integer.MAX_VALUE / 2);
            }
        }
    }

    public List<CircleNode> getAdjacentCells(CircleNode cell) {
        CircleNode[] adjacentCells = new CircleNode[4];

        if (cell.row - 1 >= 0)
            adjacentCells[0] = Main.circles[cell.col][cell.row - 1];
        if (cell.col + 1 < Main.circles.length)
            adjacentCells[1] = Main.circles[cell.col + 1][cell.row];
        if (cell.row + 1 < Main.circles[0].length)
            adjacentCells[2] = Main.circles[cell.col][cell.row + 1];
        if (cell.col - 1 >= 0)
            adjacentCells[3] = Main.circles[cell.col - 1][cell.row];

        List<CircleNode> result = new ArrayList<>();

        for (CircleNode adjacentCell : adjacentCells) {
            if (adjacentCell != null && !adjacentCell.isObstacle())
                result.add(adjacentCell);
        }

        return result;
    }
}

