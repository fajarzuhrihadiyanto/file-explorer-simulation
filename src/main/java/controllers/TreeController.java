package main.java.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.java.models.module.Entity;
import main.java.models.module.Folder;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class represents controller that status bar element in the application
 *
 * @since July 1st 2021
 * @author Fajar Zuhri Hadiyanto
 * @version 1.0
 * */
public class TreeController implements Initializable {

    /** This field represents main tree view */
    @FXML
    private TreeView<Folder> tree;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.tree.setCellFactory(param -> new TreeCell<Folder>() {
            @Override
            public void updateItem(Folder item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setText("");
                    setGraphic(null);
                } else {
                    String imageName = item.getParent() != null ? "folder.png" : "computer.png";
                    ImageView rootIcon = new ImageView(new Image("images/" + imageName));
                    rootIcon.setFitHeight(20);
                    rootIcon.setFitWidth(20);

                    setText(item.getName());
                    setGraphic(rootIcon);
                    setPrefHeight(30);
                }
            }
        });
    }

    /**
     * This method is used to get main tree view
     *
     * @return field {@link #tree}
     * */
    public TreeView<Folder> getTree() {
        return tree;
    }

    /**
     * This method is used to show tree view with a given root folder
     *
     * @param root root folder
     * */
    public void showTree(Folder root) {
        if (root != null) {
            TreeItem<Folder> rootTree = new TreeItem<>(root);
            showTreeUtil(rootTree, root);
            this.tree.setRoot(rootTree);
            this.tree.getRoot().setExpanded(true);
        }
    }

    /**
     * This method is a utility method used to dig deep into
     * every folder and trace every file in the given folder
     *
     * @param currentItem current tree item view
     * @param currentFolder current parent folder
     * */
    public void showTreeUtil(TreeItem<Folder> currentItem, Folder currentFolder) {
        if (currentFolder != null && currentItem != null) {
            for (Entity folder : currentFolder.getFolders()) {
                TreeItem<Folder> item = new TreeItem<>((Folder) folder);
                currentItem.getChildren().add(item);
                showTreeUtil(item, (Folder) folder);
            }
        }
    }
}
