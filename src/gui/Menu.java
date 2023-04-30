package gui;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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

    public static MenuButton start_vis;
    public static MenuButton clean;
    public static MenuButton pause;

    public static MenuButton source_selection_button;
    public static MenuButton dest_selection_button;
    public static MenuButton reset;
    public static MenuButton rand_obstacles;

    public static MenuButton maze;

    public static void init_menu() {
        menu.setSpacing(25);
        menu.setBackground(new Background(new BackgroundFill(Color.rgb(65, 68, 80), CornerRadii.EMPTY, Insets.EMPTY)));
        menu.setPadding(new Insets(20));

        rand_obstacles = new MenuButton("Random obstacles", actionEvent -> Main.getData().random_obstacles());
        clean = new MenuButton("Clean", actionEvent -> Main.getData().clean());
        reset = new MenuButton("Reset", actionEvent -> Main.getData().reset());
        pause = new MenuButton("Pause", actionEvent -> Main.getData().change_vis_pause_status());
        maze = new MenuButton("Maze", actionEvent -> Main.getData().maze());

        start_vis = new MenuButton("Visualize", actionEvent -> {
            boolean visualize = Main.getData().visualize();
            if (visualize)
                disable_menu_actions();
        });
        source_selection_button = new MenuButton("Source Selection", actionEvent -> {
            setSourceSelection(true);
            setDestSelection(false);
        });
        dest_selection_button = new MenuButton("Destination Selection", actionEvent -> {
            setDestSelection(true);
            setSourceSelection(false);
        });

        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("A*", "Dijkstra", "Greedy");

        comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            Main.getData().update_curr_algo(newValue);
        });

        menu.getChildren().add(start_vis);
        menu.getChildren().add(comboBox);
        menu.getChildren().add(maze);
        menu.getChildren().add(rand_obstacles);
        menu.getChildren().add(source_selection_button);
        menu.getChildren().add(dest_selection_button);
        menu.getChildren().add(clean);
        menu.getChildren().add(reset);
        menu.getChildren().add(pause);

   /*     menu.widthProperty().addListener((obs, oldVal, newVal) -> {
            double spacing = newVal.doubleValue() / 20;
            menu.setSpacing(spacing);
            ((MenuButton)menu.getChildren().get(1)).setPrefWidth(spacing * 2);
        }); */
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
