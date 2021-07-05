package main.java.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class represents controller that status bar element in the application
 *
 * @since July 1st 2021
 * @author Fajar Zuhri Hadiyanto
 * @version 1.0
 * */
public class StatusController implements Initializable {

    /** This field represents label that contains number of folders */
    @FXML
    private Label nFolders;

    /** This field represents label that contains number of files */
    @FXML
    private Label nFiles;

    @Override
    public void initialize(URL location, ResourceBundle resources) {}

    /**
     * This method is used to set view of status bar
     *
     * @param nFolder number of folder
     * @param nFile number of file
     * */
    public void setnEntity(int nFolder, int nFile) {
        this.nFolders.setText(nFolder + " Folders");
        this.nFiles.setText(nFile + " Files");
    }

}
