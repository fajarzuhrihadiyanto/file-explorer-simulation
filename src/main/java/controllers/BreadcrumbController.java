package main.java.controllers;

import com.sun.istack.internal.NotNull;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import main.java.models.module.Entity;
import main.java.models.module.Folder;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;

/**
 * This class represents controller that control breadcrumb element in the application
 *
 * @since July 1st 2021
 * @author Fajar Zuhri Hadiyanto
 * @version 1.0
 * */
public class BreadcrumbController implements Initializable {

    /** This field represents container that contain breadcrumb */
    @FXML
    private FlowPane breadcrumbContainer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {}

    /**
     * This method is used to set breadcrumb text with a given stack of folder object
     *
     * @param folders stack of folder object from root to direct parent
     * @param entity current folder
     * */
    public void setBreadcrumbText(@NotNull Stack<Folder> folders, @NotNull Entity entity) {
        /* Reset the breadcrumb */
        this.breadcrumbContainer.getChildren().clear();

        /* Add all folders from stack to container */
        while (!folders.empty()) {
            Label text = new Label(folders.pop().getName());
            text.setPadding(new Insets(0, 10, 0, 0));
            Label separator = new Label("/");
            separator.setPadding(new Insets(0, 10, 0, 0));

            this.breadcrumbContainer.getChildren().add(text);
            this.breadcrumbContainer.getChildren().add(separator);
        }

        /* Add current folder to container */
        Label text = new Label(entity.getName());
        this.breadcrumbContainer.getChildren().add(text);
    }
}
