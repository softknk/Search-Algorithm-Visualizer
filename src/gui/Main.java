package gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    private static Data data;
    public static GridPane gridPane;

    private static final int GRID_PADDING = 25;
    private static double CIRCLE_MARGIN = 1.5;

    @Override
    public void start(Stage primaryStage) {
        data = new Data(64, 36);

        VBox vbox = new VBox();

        Menu.init_menu();

        gridPane = new GridPane();
        gridPane.setPadding(new Insets(GRID_PADDING));
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setBackground(new Background(new BackgroundFill(CircleNode.CIRCLE_FILL, CornerRadii.EMPTY, Insets.EMPTY)));

        for (int i = 0; i < data.num_horizontal_circles(); i++) {
            for (int j = 0; j < data.num_vertical_circles(); j++) {
                GridPane.setMargin(data.get_circle_node_at(i, j), new Insets(CIRCLE_MARGIN));
                gridPane.add(data.get_circle_node_at(i, j), i, j);
            }
        }

        /*
        **************************************************************************
         */

        int num_hor = data.num_horizontal_circles();
        int num_vert = data.num_vertical_circles();

        double minWidth = num_hor * CircleNode.MIN_RADIUS * 2 + 2 * GRID_PADDING + num_hor * 2 * CIRCLE_MARGIN;
        double minHeight = num_vert * CircleNode.MIN_RADIUS * 2 + 2 * GRID_PADDING + num_vert * 2 * CIRCLE_MARGIN;

        primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
            primaryStage.setWidth(newVal.doubleValue());
            primaryStage.setHeight(primaryStage.getWidth() * (9/16.0));
            double w = primaryStage.getWidth();
            w -= GRID_PADDING * 2;
            w -= num_hor * 2 * CIRCLE_MARGIN;
            double new_rad = (w / num_hor) / 2;
            new_rad /= 2;

            CIRCLE_MARGIN = new_rad * 0.1;

            for (int i = 0; i < num_hor; i++)
            {
                for (int j = 0; j < num_vert; j++)
                {
                    data.get_circle_node_at(i, j).setRadius(new_rad);
                    GridPane.setMargin(data.get_circle_node_at(i, j), new Insets(CIRCLE_MARGIN));
                }
            }
        });

        primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> {
            primaryStage.setHeight(newVal.doubleValue());
            primaryStage.setWidth(primaryStage.getHeight() * (16/9.0));
            double h = primaryStage.getWidth();
            h -= GRID_PADDING * 2;
            h -= num_vert * 2 * CIRCLE_MARGIN;
            double new_rad = (h / num_vert) / 2;
            new_rad /= 2;

            CIRCLE_MARGIN = new_rad * 0.1;

            for (int i = 0; i < num_hor; i++)
            {
                for (int j = 0; j < num_vert; j++)
                {
                    data.get_circle_node_at(i, j).setRadius(new_rad);
                    GridPane.setMargin(data.get_circle_node_at(i, j), new Insets(CIRCLE_MARGIN));
                }
            }
        });

        vbox.prefWidthProperty().bind(primaryStage.widthProperty().subtract(100));
        vbox.prefHeightProperty().bind(primaryStage.heightProperty());

        gridPane.prefWidthProperty().bind(vbox.widthProperty());
        gridPane.prefHeightProperty().bind(vbox.heightProperty());

        vbox.getChildren().add(Menu.getMenu());
        vbox.getChildren().add(gridPane);
        vbox.setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0), CornerRadii.EMPTY, Insets.EMPTY)));

        Scene scene = new Scene(vbox, minWidth, minHeight);
        primaryStage.setScene(scene);

        primaryStage.setMinHeight(minHeight);
        primaryStage.setMinWidth(minWidth);

        primaryStage.setTitle("Search Algorithm Visualizer");

        primaryStage.show();
    }

    public static Data getData() {
        return data;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

