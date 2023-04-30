package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class MenuButton extends Button {

    public MenuButton(String text, EventHandler<ActionEvent> event) {
        super(text);

        setOnAction(event);

        setPrefWidth(130);
        setBackground(new Background(new BackgroundFill(Color.rgb(65, 68, 80), new CornerRadii(5), Insets.EMPTY)));
        setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(1.5))));
        setTextFill(Color.WHITE);
        setAlignment(Pos.CENTER);
        setStyle("-fx-font-size: 14px; -fx-font-weight: 800;");

        // hover effect
        setOnMouseEntered(mouseEvent -> {
            setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(5), Insets.EMPTY)));
            setTextFill(Color.BLACK);
        });

        setOnMouseExited(mouseEvent -> {
            setBackground(new Background(new BackgroundFill(Color.rgb(65, 68, 80), new CornerRadii(5), Insets.EMPTY)));
            setTextFill(Color.WHITE);
        });
    }
}
