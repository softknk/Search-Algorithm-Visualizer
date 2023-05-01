package gui;

import javafx.scene.paint.Color;

public interface Constants {

    Color CIRCLE_FILL = Color.rgb(40, 42, 54);
    Color CIRCLE_STROKE = Color.rgb(81, 84, 101);
    Color CIRCLE_OBSTACLE_FILL = Color.rgb(81, 84, 101);
    Color CIRCLE_OBSTACLE_STROKE = Color.rgb(81, 84, 101);

    Color SOURCE_SELECTION_FILL = Color.rgb(227, 172, 86);
    Color DEST_SELECTION_FILL = Color.rgb(50, 74, 182);

    Color PATH_NODE_FILL = Color.rgb(86, 255, 43);

    Color EVAL_ANIM_FROM_FILL = Color.rgb(216, 135, 255);
    Color EVAL_ANIM_TO_FILL = Color.rgb(48, 153, 206);

    Color ANIM_END_FILL = Color.rgb(149, 10, 245);

    double CIRCLE_STROKE_WIDTH = 2;

    double CIRCLE_MIN_RADIUS = 2.0;

    int GRID_PANE_PADDING = 30;

    int DRAW_PATH_ANIM_SPEED = 15; // cycle duration in ms
    int CIRCLE_ANIM_SPEED = 100; // cycle duration in ms

    int CIRCLE_FILL_ANIM_SPEED = 1000; // cycle duration in ms

    int FIND_PATH_SPEED = 10; // cycle duration in ms

    double RAND_OBSTACLE_PROBABILITY = 0.27;
}
