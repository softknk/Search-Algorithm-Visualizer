package gui;

import javafx.scene.paint.Color;
import model.*;
import model.maze_gen.PrimsAlgorithm;

public class Data {

    private final int num_rows;
    private final int num_columns;
    private final CircleNode[][] nodes;
    private SearchAlgo current;
    private CircleNode source, dest;

    private String curr_algo;

    public Data(final int num_rows, final int num_columns) {

        this.num_rows = num_rows;
        this.num_columns = num_columns;

        // init circles data structure
        nodes = new CircleNode[num_rows][num_columns];

        for (int i = 0; i < num_rows; i++) {
            for (int j = 0; j < num_columns; j++) {
                nodes[i][j] = new CircleNode(i, j);
                nodes[i][j].setRadius(CircleNode.MIN_RADIUS);
            }
        }
    }

    public boolean visualize() {
        if (source != null && dest != null) {
            if (current != null) {
                if (current.isRunning())
                    return false;
            }
            if (curr_algo == null)
                return false;
            switch (curr_algo) {
                case "A*":
                    current = new AStar(source, dest);
                    break;
                case "Dijkstra":
                    current = new Dijkstra(source, dest);
                    break;
                case "Greedy":
                    current = new Greedy(source, dest);
                    break;
                default:
                    throw new RuntimeException("ERROR: Algo not available");
            }
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
        for (int i = 0; i < num_rows; i++) {
            for (int j = 0; j < num_columns; j++) {
                if (Math.random() < obstacle_probability)
                    nodes[i][j].setObstacle();
            }
        }
        // restore source and destination nodes
        source_selection(source);
        destination_selection(dest);
    }

    public void maze() {
        reset();

        PrimsAlgorithm.generate_maze(nodes);
    }

    public void reset() {
        for (int i = 0; i < num_rows; i++) {
            for (int j = 0; j < num_columns; j++) {
                nodes[i][j].reset();
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
        source.setMovable();
        source.setFill(Color.rgb(227, 172, 86));
        source.setStroke(Color.WHITE);
    }

    public void update_curr_algo(String algo) {
        // check if algo is correct and available
        this.curr_algo = algo;
    }

    public void destination_selection(CircleNode dest) {
        if (dest == null)
            return;

        if (this.dest != null)
            this.dest.reset();

        this.dest = dest;
        dest.setMovable();
        dest.setFill(Color.rgb(50, 74, 182));
        dest.setStroke(Color.WHITE);
    }

    public int num_rows() {
        return num_rows;
    }

    public int num_columns() {
        return num_columns;
    }

    public CircleNode get_circle_node_at(int row, int col) {
        if (row >= 0 && row < num_rows && col >= 0 && col < num_columns)
            return nodes[row][col];
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
