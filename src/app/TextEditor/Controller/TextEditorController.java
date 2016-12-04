package app.TextEditor.Controller;

import app.TextEditor.*;
import javafx.animation.KeyFrame;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
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
          public int fileId;
          public TextFile file;

    @FXML protected void clickedSavedButton(ActionEvent event)
    {
        String text = textArea.getText();
        file.Id = fileId;
        file.textFile = text;
        //Call sendText(TextFile)
        System.out.println(text);

    }

    @FXML protected void clickedCloseButton(ActionEvent event)
    {
        //Transition back to fileViewer
    }

    @FXML protected void clickedExitButton(ActionEvent event)
    {
        Platform.exit();
        System.exit(0);
    }


    @FXML protected void textAreaCapture(MouseEvent event)
    {
        String text = textArea.getText();
        file.Id = fileId;
        file.textFile = text;
        //Call sendText(TextFile)
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
        //set the retrieve text
        textArea.textProperty().set("HelloWorld");
    }


}
