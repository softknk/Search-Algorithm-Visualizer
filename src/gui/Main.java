package gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    private static final int MIN_CIRCLE_RADIUS = 5;
    private static final int MAX_CIRCLE_RADIUS = 10000;
    private static final int GRID_X_CIRCLES = 48;
    private static final int GRID_Y_CIRCLES = 27;
    private static final int GRID_PADDING = 25;
    private static final int CIRCLE_MARGIN = 2;

    public static CircleNode[][] circles = new CircleNode[GRID_X_CIRCLES][GRID_Y_CIRCLES];

    public static SearchAlgo current;

    @Override
    public void start(Stage primaryStage) {
        VBox vbox = new VBox();

        HBox menu = new HBox();
        Label l = new Label("Hallo Test");
        l.setTextFill(Color.rgb(255, 255, 255));
        menu.getChildren().add(l);
        Button b = new Button("Hallo");
        menu.getChildren().add(b);
        menu.setSpacing(25);
        menu.setBackground(new Background(new BackgroundFill(Color.rgb(65, 68, 80), CornerRadii.EMPTY, Insets.EMPTY)));
        menu.setPadding(new Insets(10));

        GridPane gridPane = new GridPane();
        // add nodes to the grid here

        for (int i = 0; i < circles.length; i++) {
            for (int j = 0; j < circles[0].length; j++) {
                circles[i][j] = new CircleNode(j, i);
                circles[i][j].setStroke(Color.rgb(200, 200, 200));
                circles[i][j].setFill(Color.rgb(40, 42, 54));
                circles[i][j].setRadius(MIN_CIRCLE_RADIUS);

                gridPane.setPadding(new Insets(GRID_PADDING));

                GridPane.setMargin(circles[i][j], new Insets(CIRCLE_MARGIN));

                gridPane.add(circles[i][j], i, j);

                // set hgrow and vgrow constraints on the label
                GridPane.setHgrow(circles[i][j], javafx.scene.layout.Priority.ALWAYS);
                GridPane.setVgrow(circles[i][j], javafx.scene.layout.Priority.ALWAYS);
            }
        }

        current = new AStar(circles[0][0], circles[30][16]);
        circles[30][16].setFill(Color.BLACK);

        gridPane.setBackground(new Background(new BackgroundFill(Color.rgb(40, 42, 54), CornerRadii.EMPTY, Insets.EMPTY)));

        double minWidth = GRID_X_CIRCLES * MIN_CIRCLE_RADIUS * 2 + 2 * GRID_PADDING + GRID_X_CIRCLES * 2 * CIRCLE_MARGIN;
        double minHeight = GRID_Y_CIRCLES * MIN_CIRCLE_RADIUS * 2 + 2 * GRID_PADDING + GRID_Y_CIRCLES * 2 * CIRCLE_MARGIN;

        menu.widthProperty().addListener((obs, oldVal, newVal) -> {
            double spacing = newVal.doubleValue() / 20;
            menu.setSpacing(spacing);
            ((Button)menu.getChildren().get(1)).setPrefWidth(spacing * 2);
        });

        primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
            primaryStage.setWidth(newVal.doubleValue());
            primaryStage.setHeight(primaryStage.getWidth() * (9/16.0));
            double w = primaryStage.getWidth();
            w -= GRID_PADDING * 2;
            w -= GRID_X_CIRCLES * 2 * CIRCLE_MARGIN;
            double new_rad = (w / GRID_X_CIRCLES) / 2;
            new_rad /= 2;
            new_rad *= 0.85;
            for (int i = 0; i < circles.length; i++)
            {
                for (int j = 0; j < circles[0].length; j++)
                {
                    circles[i][j].setRadius(new_rad);
                }
            }
        });

        primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> {
            primaryStage.setHeight(newVal.doubleValue());
            primaryStage.setWidth(primaryStage.getHeight() * (16/9.0));
            double h = primaryStage.getWidth();
            h -= GRID_PADDING * 2;
            h -= GRID_Y_CIRCLES * 2 * CIRCLE_MARGIN;
            double new_rad = (h / GRID_Y_CIRCLES) / 2;
            new_rad /= 2;
            new_rad *= 0.85;
            for (int i = 0; i < circles.length; i++)
            {
                for (int j = 0; j < circles[0].length; j++)
                {
                    circles[i][j].setRadius(new_rad);
                }
            }
        });

        vbox.prefWidthProperty().bind(primaryStage.widthProperty());
        vbox.prefHeightProperty().bind(primaryStage.heightProperty());

        gridPane.prefWidthProperty().bind(vbox.widthProperty());
        gridPane.prefHeightProperty().bind(vbox.heightProperty());

        vbox.getChildren().add(menu);
        vbox.getChildren().add(gridPane);
        vbox.setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0), CornerRadii.EMPTY, Insets.EMPTY)));

        Scene scene = new Scene(vbox, minWidth + 150, minHeight + 150);
        primaryStage.setScene(scene);

        primaryStage.setMaxWidth(GRID_X_CIRCLES * MAX_CIRCLE_RADIUS * 2 + 2 * GRID_PADDING + GRID_X_CIRCLES * 2 * CIRCLE_MARGIN);
        primaryStage.setMaxHeight(GRID_Y_CIRCLES * MAX_CIRCLE_RADIUS * 2 + 2 * GRID_PADDING + GRID_Y_CIRCLES * 2 * CIRCLE_MARGIN);

        primaryStage.setMinHeight(minHeight);
        primaryStage.setMinWidth(minWidth);

        current.findPath();

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

