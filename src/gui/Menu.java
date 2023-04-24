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

    public static void init_menu() {
        Label l = new Label("Hallo Test");
        l.setTextFill(Color.rgb(255, 255, 255));
        menu.getChildren().add(l);
        Button b = new Button("Hallo");
        menu.getChildren().add(b);
        menu.setSpacing(25);
        menu.setBackground(new Background(new BackgroundFill(Color.rgb(65, 68, 80), CornerRadii.EMPTY, Insets.EMPTY)));
        menu.setPadding(new Insets(10));

        Button source_selection = new Button("Source Selection");
        Button dest_selection = new Button("Destination Selection");

        source_selection.setOnAction(event -> {
            setSourceSelection(true);
            setDestSelection(false);
        });

        dest_selection.setOnAction(event -> {
            setDestSelection(true);
            setSourceSelection(false);
        });

        menu.getChildren().add(source_selection);
        menu.getChildren().add(dest_selection);

        menu.widthProperty().addListener((obs, oldVal, newVal) -> {
            double spacing = newVal.doubleValue() / 20;
            menu.setSpacing(spacing);
            ((Button)menu.getChildren().get(1)).setPrefWidth(spacing * 2);
        });
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
