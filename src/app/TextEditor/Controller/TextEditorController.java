package app.TextEditor.Controller;

import javafx.animation.KeyFrame;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class TextEditorController extends Parent {
    @FXML public TextArea textArea;
    @FXML private MenuItem saveButton;
    @FXML private MenuItem closeButton;
    @FXML private MenuItem exitButton;
    @FXML private MenuItem menuItem;
    @FXML private Timeline autosave;

    @FXML protected void clickedSavedButton(ActionEvent event)
    {
        String text = textArea.getText();
    }

    @FXML protected void clickedCloseButton(ActionEvent event)
    {

    }

    @FXML protected void clickedExitButton(ActionEvent event)
    {
        Platform.exit();
        System.exit(0);
    }


    @FXML protected void textAreaCapture(MouseEvent event)
    {
        String text = textArea.getText();
        System.out.println(text);

    }

    @FXML protected void initialize()
    {
        autosave = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println(textArea.getText());
            }
        }));
        autosave.setCycleCount(Timeline.INDEFINITE);
        autosave.play();
    }


}
