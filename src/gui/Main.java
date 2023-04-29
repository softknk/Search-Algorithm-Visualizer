package gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.CircleNode;

public class Main extends Application {

    private static Data data;
    public static GridPane gridPane;

    private static final int GRID_PADDING = 20;
    private static double CIRCLE_MARGIN = 2;

    @Override
    public void start(Stage primaryStage) {
        data = new Data(45, 70);

        VBox vbox = new VBox();

        Menu.init_menu();

        gridPane = new GridPane();
        gridPane.setPadding(new Insets(GRID_PADDING));
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setBackground(new Background(new BackgroundFill(CircleNode.CIRCLE_FILL, CornerRadii.EMPTY, Insets.EMPTY)));

        for (int i = 0; i < data.num_rows(); i++) {
            for (int j = 0; j < data.num_columns(); j++) {
                GridPane.setMargin(data.get_circle_node_at(i, j), new Insets(CIRCLE_MARGIN));
                gridPane.add(data.get_circle_node_at(i, j), j, i);
            }
        }

        int num_rows = data.num_rows();
        int num_cols = data.num_columns();

        double minWidth = num_cols * CircleNode.MIN_RADIUS * 2 + 2 * GRID_PADDING + num_cols * 2 * CIRCLE_MARGIN;
        double minHeight = num_rows * CircleNode.MIN_RADIUS * 2 + 2 * GRID_PADDING + num_rows * 2 * CIRCLE_MARGIN;

        primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
            primaryStage.setWidth(newVal.doubleValue());
            primaryStage.setHeight(primaryStage.getWidth() * (9/16.0));

            double cell_size = primaryStage.getWidth() / data.num_columns();
            double diameter = cell_size - 2 * CIRCLE_MARGIN - CircleNode.CIRCLE_STROKE_WIDTH;
            double r = diameter / 2.0;
            r *= 0.85;

            CIRCLE_MARGIN = r * 0.1;

            for (int i = 0; i < num_rows; i++)
            {
                for (int j = 0; j < num_cols; j++)
                {
                    data.get_circle_node_at(i, j).setRadius(r);
                    GridPane.setMargin(data.get_circle_node_at(i, j), new Insets(CIRCLE_MARGIN));
                }
            }
        });

        primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> {
            primaryStage.setHeight(newVal.doubleValue());
            primaryStage.setWidth(primaryStage.getHeight() * (16/9.0));
            primaryStage.setHeight(primaryStage.getHeight());

            double cell_size = primaryStage.getWidth() / data.num_columns();
            double diameter = cell_size - 2 * CIRCLE_MARGIN - CircleNode.CIRCLE_STROKE_WIDTH;
            double r = diameter / 2.0;
            r *= 0.85;

            CIRCLE_MARGIN = r * 0.1;

            for (int i = 0; i < num_rows; i++)
            {
                for (int j = 0; j < num_cols; j++)
                {
                    data.get_circle_node_at(i, j).setRadius(r);
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