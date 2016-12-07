package app.TextEditor.Controller;

import app.FileSystem.Controller.FileSystemController;
import app.FileSystem.FileAccessRequestItem;
import app.FileSystem.Model.FileSystemAPIDataManager;
import app.FileSystem.Model.FileSystemModel;
import app.TextEditor.*;
import app.TextEditor.Model.TextEditorAPIDataManager;
import app.TextEditor.Model.TextEditorModel;
import com.sun.org.apache.regexp.internal.RE;
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
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;
import app.FileSystem.*;

import java.io.IOException;

public class TextEditorController extends Parent {
    @FXML public TextArea textArea;
    @FXML private MenuItem saveButton;
    @FXML private MenuItem closeButton;
    @FXML private MenuItem exitButton;
    @FXML private MenuItem menuItem;
    @FXML private Timeline autosave;
    @FXML private TextField titleField;
          public TextFile file;
          public TextEditorModel model;
    public String text;
    public String title;
    private FileAccessItem accessItem;
    public String user_id;
    public Stage primaryStage;

    @FXML protected void clickedSavedButton(ActionEvent event)
    {
        String text = textArea.getText();
        file.textfile_id = accessItem.textfile_id;
        file.text = textArea.getText();
        model.saveText(file);
        System.out.println(text);

    }

    @FXML protected void clickedCloseButton(ActionEvent event)
    {
        //Transition back to fileViewer



        try {
            FXMLLoader fileSystemLoader = new FXMLLoader();
            Parent fileSystemView = fileSystemLoader.load(getClass().getResource("/app/FileSystem/View/FileSystem.fxml").openStream());
            FileSystemController fController = (FileSystemController) fileSystemLoader.getController();
            fController.model = new FileSystemModel();
            fController.model.controller = fController;
            fController.model.fileSystemAPIDataManager = new FileSystemAPIDataManager();
            Platform.runLater(new Runnable() {
                @Override
                public void run() {

                    primaryStage.setTitle("File System");
                    primaryStage.setScene(new Scene(fileSystemView));
                    primaryStage.show();
                    fController.loadFiles(user_id);
                    fController.primaryStage = primaryStage;

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML protected void clickedExitButton(ActionEvent event)
    {
        Platform.exit();
        System.exit(0);
    }


    @FXML protected void textAreaCapture(MouseEvent event)
    {
        String text = textArea.getText();
//        file.textfile_id = accessItem.textfile_id;
       // file.text = text;
        model.saveText(file);
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
        textArea.requestLayout();


    }

    public void prepareTextEditor(FileAccessItem aItem)
    {
        System.out.println("TextArea: " + textArea);
        this.accessItem = aItem;
        textArea.setWrapText(true);
        if (aItem.type.equals("v"))
        {
            textArea.setEditable(false);
            titleField.setEditable(false);
        }
        Platform.runLater(new Runnable()
        {
            @Override
            public void run()
            {
                //set the retrieve text
                titleField.textProperty().setValue(aItem.title);
                String content = aItem.text;//model.retrieveText(fileId);
                textArea.setText(aItem.text);
                textArea.requestFocus();
                textArea.requestLayout();
            }
        });
    }


}
