package model.maze_gen;

import model.CircleNode;

import java.util.*;

public class PrimsAlgorithm {

    public static void generate_maze(CircleNode[][] nodes) {
        // set up all nodes as obstacles
        for (int i = 0; i < nodes.length; i++) {
            for (int j = 0; j < nodes[0].length; j++)
                nodes[i][j].setObstacle();
        }

        // keep track of the parent node for every node (node which evaluates frontier node)
        // every node get mapped to its parent node [0] and the node between them [1]
        Map<CircleNode, CircleNode> parents = new HashMap<>();

        // define frontiers data structure
        List<CircleNode> frontiers = new LinkedList<>();

        // choose random node to start with and add it to frontiers
        int rand_row = new Random().nextInt(nodes.length - 1);
        int rand_col = new Random().nextInt(nodes[0].length - 1);
        frontiers.add(nodes[rand_row][rand_col]);

        // main maze generation loop
        while (!frontiers.isEmpty()) {
            // pick random frontier
            int rand_index = frontiers.size() == 1 ? 0 : new Random().nextInt(frontiers.size() - 1);
            CircleNode rand_frontier = frontiers.get(rand_index);
            // mark the random frontier as movable
            rand_frontier.setMovable();

            // if parent node is also movable, make the node between them two also movable
            CircleNode parent = parents.get(rand_frontier);
            if (parent != null) {
                if (parent.isMovable()) {
                    int wall_row = (rand_frontier.row + parent.row) / 2;
                    int wall_col= (rand_frontier.col + parent.col) / 2;
                    CircleNode wall_node = nodes[wall_row][wall_col];
                    wall_node.setMovable();
                }
            }

            // add all frontiers of rand_frontier node to frontiers list
            add_frontiers_of(rand_frontier, nodes, frontiers, parents);

            frontiers.remove(rand_frontier);
        }
    }

    private static void add_frontiers_of(CircleNode node,
                                         CircleNode[][] nodes,
                                         List<CircleNode> frontiers,
                                         Map<CircleNode, CircleNode> parents) {
        CircleNode[] adj_nodes = new CircleNode[4];

        if (is_valid_pos(node.row, node.col - 2, nodes.length, nodes[0].length))
            adj_nodes[0] = nodes[node.row][node.col - 2];
        if (is_valid_pos(node.row, node.col + 2, nodes.length, nodes[0].length))
            adj_nodes[1] = nodes[node.row][node.col + 2];
        if (is_valid_pos(node.row - 2, node.col, nodes.length, nodes[0].length))
            adj_nodes[2] = nodes[node.row - 2][node.col];
        if (is_valid_pos(node.row + 2, node.col, nodes.length, nodes[0].length))
            adj_nodes[3] = nodes[node.row + 2][node.col];

        for (int i = 0; i < adj_nodes.length; i++) {
            CircleNode adj = adj_nodes[i];
            if (adj == null)
                continue;
            if (adj.isObstacle() && !frontiers.contains(adj)) {
                frontiers.add(adj);
                parents.put(adj, node);
            }
        }
    }

    private static boolean is_valid_pos(int row, int col, int grid_rows, int grid_cols) {
        return row >= 0 && row < grid_rows && col >= 0 && col < grid_cols;
    }
}
