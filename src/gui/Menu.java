package gui;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class Menu {

    private static final HBox menu = new HBox();

    public static boolean source_selection;
    public static boolean dest_selection;

    public static Button clean;
    public static Button pause;

    public static Button source_selection_button;
    public static Button dest_selection_button;
    public static Button reset;
    public static Button rand_obstacles;

    public static void init_menu() {
        Button start_vis = new Button("Visualize");
        menu.getChildren().add(start_vis);

        rand_obstacles = new Button("Random obstacles");
        menu.getChildren().add(rand_obstacles);

        menu.setSpacing(25);
        menu.setBackground(new Background(new BackgroundFill(Color.rgb(65, 68, 80), CornerRadii.EMPTY, Insets.EMPTY)));
        menu.setPadding(new Insets(15));

        source_selection_button = new Button("Source Selection");
        dest_selection_button = new Button("Destination Selection");

        clean = new Button("Clean");

        reset = new Button("Reset");
        pause = new Button("Pause");


        reset.setOnAction(event -> {
            Main.getData().reset();
        });

        source_selection_button.setOnAction(event -> {
            setSourceSelection(true);
            setDestSelection(false);
        });

        dest_selection_button.setOnAction(event -> {
            setDestSelection(true);
            setSourceSelection(false);
        });

        start_vis.setOnAction(event -> {
            boolean visualize = Main.getData().visualize();
            if (visualize)
                disable_menu_actions();
        });

        clean.setOnAction(event -> {
            Main.getData().clean();
        });

        pause.setOnAction(event -> {
            Main.getData().change_vis_pause_status();
        });

        rand_obstacles.setOnAction(event -> {
            Main.getData().random_obstacles();
        });

        menu.getChildren().add(source_selection_button);
        menu.getChildren().add(dest_selection_button);
        menu.getChildren().add(clean);
        menu.getChildren().add(reset);
        menu.getChildren().add(pause);

        menu.widthProperty().addListener((obs, oldVal, newVal) -> {
            double spacing = newVal.doubleValue() / 20;
            menu.setSpacing(spacing);
            ((Button)menu.getChildren().get(1)).setPrefWidth(spacing * 2);
        });
    }

    public static void disable_menu_actions() {
        source_selection_button.setDisable(true);
        dest_selection_button.setDisable(true);
        clean.setDisable(true);
        reset.setDisable(true);
        rand_obstacles.setDisable(true);
    }

    public static void enable_menu_actions() {
        source_selection_button.setDisable(false);
        dest_selection_button.setDisable(false);
        clean.setDisable(false);
        reset.setDisable(false);
        rand_obstacles.setDisable(false);
    }

    public static HBox getMenu() {
        return menu;
    }

    public static void setSourceSelection(boolean new_source_selection) {
        source_selection = new_source_selection;
    }

    public static void setDestSelection(boolean new_dest_selection) {
        dest_selection = new_dest_selection;
    }
}
