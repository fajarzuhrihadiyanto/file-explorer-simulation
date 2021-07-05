package main.java.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import main.java.models.module.Entity;
import main.java.models.module.File;
import main.java.models.module.Folder;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * This class represents controller that control navigation element in the application
 *
 * @since July 1st 2021
 * @author Fajar Zuhri Hadiyanto
 * @version 1.0
 * */
public class NavigationController implements Initializable {

    /** This field represents backward button */
    @FXML
    private Button backwardBtn;

    /** This field represents forward button */
    @FXML
    private Button forwardBtn;

    /** This field represents up button */
    @FXML
    private Button upBtn;

    /** This field represents create folder button */
    @FXML
    private Button newFolderBtn;

    /** This field represents create file button */
    @FXML
    private Button newFileBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {}

    /**
     * This method is used to set click handler for create new file button
     *
     * @param supplier table view supplier
     * @param next next method
     * */
    public void setOnNewFileBtnClick(Supplier<TableView<Entity>> supplier, Consumer<File> next) {
        this.newFileBtn.setOnMouseClicked(event -> {
            /* Get table view with supplier */
            TableView<Entity> table =  supplier.get();

            /* Create new file with default file name */
            int x = 1;
            while(table.getItems().contains(new File("New File " + x, "txt"))) x++;
            File newFile = new File("New File " + x, "txt");

            /* Add created file to the table */
            table.getItems().add(newFile);
            table.edit(table.getItems().size() - 1, table.getColumns().get(1));

            /* call next function */
            next.accept(newFile);
        });
    }

    /**
     * This method is used to set click handler for create new file button
     *
     * @param supplier table view supplier
     * @param next next method
     * */
    public void setOnNewFolderBtnClick(Supplier<TableView<Entity>> supplier, Consumer<Folder> next) {
        this.newFolderBtn.setOnMouseClicked(event -> {
            /* Get table view with supplier */
            TableView<Entity> table =  supplier.get();

            /* Create new folder with default folder name */
            int x = 1;
            while(table.getItems().contains(new Folder("New Folder " + x))) x++;
            Folder newFolder = new Folder("New Folder " + x);

            /* Add created folder to the table */
            table.getItems().add(newFolder);
            table.edit(table.getItems().size() - 1, table.getColumns().get(1));

            /* call next function */
            next.accept(newFolder);
        });
    }

    /**
     * This method is used to get backward button
     *
     * @return field {@link #backwardBtn}
     * */
    public Button getBackwardBtn() {
        return this.backwardBtn;
    }

    /**
     * This method is used to get forward button
     *
     * @return field {@link #forwardBtn}
     * */
    public Button getForwardBtn() {
        return this.forwardBtn;
    }

    /**
     * This method is used to get up button
     *
     * @return field {@link #upBtn}
     * */
    public Button getUpBtn() {
        return this.upBtn;
    }

    /**
     * This method is used to get create file button
     *
     * @return field {@link #newFileBtn}
     * */
    public Button getNewFileBtn() {
        return this.newFileBtn;
    }

    /**
     * This method is used to get create folder button
     *
     * @return field {@link #newFolderBtn}
     * */
    public Button getNewFolderBtn() {
        return this.newFolderBtn;
    }
}
