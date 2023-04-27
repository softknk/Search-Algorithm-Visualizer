package gui;

import javafx.scene.paint.Color;

public class Data {

    private final int num_horizontal_circles;
    private final int num_vertical_circles;
    private final CircleNode[][] circles;
    private SearchAlgo current;
    private CircleNode source, dest;

    public Data(final int num_horizontal_circles, final int num_vertical_circles) {

        this.num_horizontal_circles = num_horizontal_circles;
        this.num_vertical_circles = num_vertical_circles;

        // init circles data structure
        circles = new CircleNode[num_horizontal_circles][num_vertical_circles];

        for (int i = 0; i < num_horizontal_circles; i++) {
            for (int j = 0; j < num_vertical_circles; j++) {
                circles[i][j] = new CircleNode(j, i);
                circles[i][j].setRadius(CircleNode.MIN_RADIUS);
            }
        }
    }

    public boolean visualize() {
        if (source != null && dest != null) {
            if (current != null) {
                if (!current.paused)
                    return false;
            }
            current = new AStar(source, dest);
            current.findPath();
            return true;
        } else {
            return false;
        }
    }

    public void random_obstacles() {
        double obstacle_probability = 0.3;
        CircleNode source = this.source;
        CircleNode dest = this.dest;
        reset();
        for (int i = 0; i < num_horizontal_circles; i++) {
            for (int j = 0; j < num_vertical_circles; j++) {
                if (Math.random() < obstacle_probability)
                    circles[i][j].setObstacle(true);
            }
        }
        // restore source and destination nodes
        source_selection(source);
        destination_selection(dest);
    }

    public void reset() {
        for (int i = 0; i < num_horizontal_circles; i++) {
            for (int j = 0; j < num_vertical_circles; j++) {
                circles[i][j].reset();
            }
        }
        source = null;
        dest = null;
    }

    public void change_vis_pause_status() {
        if (current != null)
            current.setPaused(!current.getPaused());
    }

    public void clean() {
        if (current != null) {
            current.setPaused(true);
            current.clean();
        }
    }

    public void source_selection(CircleNode source) {
        if (source == null)
            return;

        if (this.source != null)
            this.source.reset();

        this.source = source;
        source.setObstacle(false);
        source.setFill(Color.ORANGE);
    }

    public void destination_selection(CircleNode dest) {
        if (dest == null)
            return;

        if (this.dest != null)
            this.dest.reset();

        this.dest = dest;
        dest.setObstacle(false);
        dest.setFill(Color.DARKCYAN);
    }

    public int num_horizontal_circles() {
        return num_horizontal_circles;
    }

    public int num_vertical_circles() {
        return num_vertical_circles;
    }

    public CircleNode get_circle_node_at(int row, int col) {
        if (row >= 0 && row < num_horizontal_circles && col >= 0 && col < num_vertical_circles)
            return circles[row][col];
        else
            throw new RuntimeException("Trying to access non existing circle node at: (" + row + ", " + col + ")");
    }

    public SearchAlgo get_current() {
        return current;
    }

    public CircleNode get_source() {
        return source;
    }

    public CircleNode get_destination() {
        return dest;
    }
}
