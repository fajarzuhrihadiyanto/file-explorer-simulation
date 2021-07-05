package main.java.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import main.java.models.Model;
import main.java.models.module.Entity;
import main.java.models.module.Folder;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * This class represents controller that combines all controllers in the application
 * which are linked from the corresponding fxml file
 *
 * @since July 1st 2021
 * @author Fajar Zuhri Hadiyanto
 * @version 1.0
 * */
public class MainController implements Initializable {

    /** This field represents navigation controller */
    @FXML
    private NavigationController navigationController;

    /** This field represents breadcrumb controller */
    @FXML
    private BreadcrumbController breadcrumbController;

    /** This field represents search controller */
    @FXML
    private SearchController searchController;

    /** This field represents tree controller */
    @FXML
    private TreeController treeController;

    /** This field represents table controller */
    @FXML
    private ListController listController;

    /** This field represents status bar controller */
    @FXML
    private StatusController statusController;

    /** This field represents main model of this application */
    private Model model;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.model = new Model();
        this.updateUI();

        this.initTreeController();
        this.initListController();
        this.initNavigationController();
        this.initSearchController();
    }

    /**
     * This method is used to initialize behaviour of all tree element
     * */
    private void initTreeController() {
        this.treeController.showTree(this.model.getFileTreeModel().getRoot());
        this.treeController.getTree().getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.model.getStateModel().moveFolder(newValue.getValue());
            this.clearSearch();
        });
    }

    /**
     * This method is used to initialize behaviour of all table element
     * */
    private void initListController() {
        this.listController.setOnRowDoubleClickListener(param -> {
            this.model.getStateModel().moveFolder((Folder) param);
            this.clearSearch();
        });
        this.listController.initListContextMenu(() -> {
            this.updateUI();
            this.treeController.showTree(this.model.getFileTreeModel().getRoot());
        });
        this.listController.setOnNameColumnEditCommit(
                () -> this.model.getStateModel().getCurrentPos(),
                () -> this.treeController.showTree(this.model.getFileTreeModel().getRoot()));
    }

    /**
     * This method is used to initialize behaviour of all navigation element
     * */
    private void initNavigationController() {
        this.navigationController.getBackwardBtn().setOnMouseClicked(e -> {
            this.model.getStateModel().back();
            this.updateUI();
        });
        this.navigationController.getForwardBtn().setOnMouseClicked(e -> {
            this.model.getStateModel().forward();
            this.updateUI();
        });
        this.navigationController.getUpBtn().setOnMouseClicked(e -> {
            this.model.getStateModel().up();
            this.updateUI();
        });
        this.navigationController.setOnNewFileBtnClick(
                () -> this.listController.getList(),
                file -> {
                    this.model.getStateModel().getCurrentPos().addContent(file);

                    Folder currentPos = this.model.getStateModel().getCurrentPos();
                    this.statusController.setnEntity(currentPos.getnFolders(), currentPos.getnFiles());
                });
        this.navigationController.setOnNewFolderBtnClick(
                () -> this.listController.getList(),
                folder -> {
                    this.model.getStateModel().getCurrentPos().addContent(folder);

                    Folder currentPos = this.model.getStateModel().getCurrentPos();
                    this.statusController.setnEntity(currentPos.getnFolders(), currentPos.getnFiles());

                    this.treeController.showTree(this.model.getFileTreeModel().getRoot());
                });
    }

    /**
     * This method is used to initialize behaviour of all search element
     * */
    private void initSearchController() {
        this.searchController.setOnSearchInputEnter(s -> {
            ArrayList<Entity> entities = this.model.getStateModel().getCurrentPos().search(s);
            this.listController.addDirColumn();
            this.listController.showList(entities);
            this.statusController.setnEntity(
                    ((int) entities.stream().filter(entity -> entity.getType() == Entity.TYPE.FOLDER).count()),
                    ((int) entities.stream().filter(entity -> entity.getType() == Entity.TYPE.FILE).count())
            );
            this.model.getStateModel().setIsSearch(true);
            this.updateBtnAbility();
        });
        this.searchController.getCloseBtn().setOnMouseClicked(event -> clearSearch());
    }

    /**
     * This method is used to update the UI including
     * <ul>
     *     <li>Status bar</li>
     *     <li>Breadcrumb</li>
     *     <li>Main View (table of entities)</li>
     *     <li>Navigation and control button ability</li>
     * </ul>
     * This method does not include tree view update because
     * not every ui update involving tree element
     * */
    private void updateUI() {
        Folder currentPos = this.model.getStateModel().getCurrentPos();
        this.statusController.setnEntity(currentPos.getnFolders(), currentPos.getnFiles());
        this.breadcrumbController.setBreadcrumbText(currentPos.getDirectory(), currentPos);
        this.listController.showList(currentPos.getContents());

        this.updateBtnAbility();
    }

    /**
     * This method is used to update navigation and control button ability by current state
     * */
    private void updateBtnAbility() {
        boolean isSearch = this.model.getStateModel().getIsSearch();

        this.searchController.getCloseBtn().setDisable(!isSearch);

        this.navigationController.getNewFolderBtn().setDisable(isSearch);
        this.navigationController.getNewFileBtn().setDisable(isSearch);

        this.navigationController.getBackwardBtn().setDisable(isSearch || this.model.getStateModel().getUndoHistories().isEmpty());
        this.navigationController.getForwardBtn().setDisable(isSearch || this.model.getStateModel().getRedoHistories().isEmpty());
        this.navigationController.getUpBtn().setDisable(isSearch || this.model.getStateModel().getCurrentPos().getParent() == null);
    }

    /**
     * This method is used to handle search input clearance
     * */
    private void clearSearch() {
        this.model.getStateModel().setIsSearch(false);
        this.listController.removeDirColumn();
        this.searchController.getSearchInput().clear();
        this.updateUI();
    }
}
