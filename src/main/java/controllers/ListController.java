package main.java.controllers;

import com.sun.istack.internal.NotNull;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.java.models.module.Entity;
import main.java.models.module.File;
import main.java.models.module.Folder;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * This class represents table controller that control table element in the application
 *
 * @since June 30th 2021
 * @author Fajar Zuhri Hadiyanto
 * @version 1.0
 * */
public class ListController implements Initializable {

    /** This field represents FXML element with id list in the loader FXML */
    @FXML
    private TableView<Entity> list;

    /** This field represents column name of the table */
    private TableColumn<Entity, String> nameColumn;

    /** This field represents icon map of the table in the icon column */
    private final HashMap<Entity.TYPE, String> icons = new HashMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /* INITIALIZE ICON */
        icons.put(Entity.TYPE.FOLDER, "images/folder.png");
        icons.put(Entity.TYPE.FILE, "images/document.png");

        initColumn();
    }

    /**
     * This method is used to initialize the column in the table
     * */
    private void initColumn() {
        /* ICON COLUMN */
        TableColumn<Entity, ImageView> iconColumn = new TableColumn<>();
        iconColumn.setPrefWidth(30);
        iconColumn.setCellValueFactory(param -> {
            ImageView imageView = new ImageView(new Image(icons.get(param.getValue().getType())));
            imageView.setFitWidth(20);
            imageView.setFitHeight(20);
            return new SimpleObjectProperty<>(imageView);
        });

        /* NAME COLUMN */
        this.nameColumn = new TableColumn<>("Name");
        this.nameColumn.setPrefWidth(150);
        this.nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        /* CREATED DATE COLUMN */
        TableColumn<Entity, String> createdDateColumn = new TableColumn<>("Created Date");
        createdDateColumn.setCellValueFactory(param -> {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = dateFormat.format(param.getValue().getCreatedDate());
            return new SimpleStringProperty(dateString);
        });

        /* UPDATED DATE COLUMN */
        TableColumn<Entity, String> updatedDateColumn = new TableColumn<>("Updated Date");
        updatedDateColumn.setCellValueFactory(param -> {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = dateFormat.format(param.getValue().getUpdatedDate());
            return new SimpleStringProperty(dateString);
        });

        /* TYPE COLUMN */
        TableColumn<Entity, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(param -> {
            if (param.getValue().getType() == Entity.TYPE.FOLDER)
                return new SimpleStringProperty("Folder");

            return new SimpleStringProperty("File");
        });

        /* CONFIGURE THE TABLE AND ADD ALL CREATED COLUMN */
        this.list.setEditable(true);
        this.list.getColumns().add(iconColumn);
        this.list.getColumns().add(this.nameColumn);
        this.list.getColumns().add(createdDateColumn);
        this.list.getColumns().add(updatedDateColumn);
        this.list.getColumns().add(typeColumn);
    }

    /**
     * This method is used to add directory column in case application
     * is in the search state and directory column is not exist yet
     * */
    public void addDirColumn() {
        /* Check if directory column is already exist in the table */
        if(this.list.getColumns().stream().noneMatch(entityTableColumn -> entityTableColumn.getText().equals("Directory"))) {

            /* Create new directory column */
            TableColumn<Entity, String> dirColumn = new TableColumn<>("Directory");
            dirColumn.setCellValueFactory(param -> {
                Stack<Folder> dir = param.getValue().getDirectory();

                StringBuilder dirString = new StringBuilder();

                while (!dir.isEmpty()) {
                    dirString.append(dir.pop().getName()).append(" / ");
                }

                return new SimpleStringProperty(dirString.toString());
            });

            /* Add it to the third column of the table */
            this.list.getColumns().add(2, dirColumn);
        }
    }

    /**
     * This method is used to remove directory column from the table in case application
     * is back from the search state to explore state and directory column is exist
     * */
    public void removeDirColumn() {
        /* Check if directory column is already exist in the table */
        if(this.list.getColumns().stream().anyMatch(entityTableColumn -> entityTableColumn.getText().equals("Directory"))) {

            /* Remove it as a third column */
            this.list.getColumns().remove(2);
        }
    }

    /**
     * This method is used to initialize context menu of this table
     * with remove item as one and only menu item
     *
     * @param next next method to be called
     * */
    public void initListContextMenu(@NotNull Runnable next) {
        /* Create new context menu with remove item menu */
        ContextMenu contextMenu = new ContextMenu();
        MenuItem removeMenuItem = new MenuItem("Remove");
        removeMenuItem.setOnAction(event -> {
            /* Get and remove selected item from its parent on action */
            Entity selectedItem = this.getList().getSelectionModel().getSelectedItem();
            this.getList().getItems().remove(selectedItem);
            selectedItem.getParent().removeContent(selectedItem);
            /* Call the next method */
            next.run();
        });
        contextMenu.getItems().add(removeMenuItem);

        /* Add the context menu to the table */
        this.list.setContextMenu(contextMenu);
    }

    /**
     * This method is used to display list of entities (could be folder or file)
     * in the table
     *
     * @param entities list of entities
     * */
    public void showList(@NotNull ArrayList<Entity> entities) {
        /* Clear the list first */
        this.list.getItems().clear();

        /* Then add new entities one by one */
        for (Entity entity : entities) {
            this.list.getItems().add(entity);
        }
    }

    /**
     * This method is used to set double click handler for the table by given event
     *
     * @param consumer double click handler for the table
     * */
    public void setOnRowDoubleClickListener(@NotNull Consumer<Entity> consumer) {
        /* Set custom row factory */
        this.list.setRowFactory(entity -> {
            TableRow<Entity> row = new TableRow<>();

            /* Set on mouse click */
            row.setOnMouseClicked(event -> {
                TableRow<Entity> source = (TableRow<Entity>) event.getSource();

                /* Check if it is double click and row is not empty */
                if (event.getClickCount() == 2 && !source.isEmpty()) {
                    Entity content  = source.getTableView().getSelectionModel().getSelectedItem();

                    if (content instanceof Folder) {
                        /* Call the consumer */
                        consumer.accept(content);
                    }
                }
            });
            return row;
        });
    }

    /**
     * This method is used to set edit commit handler for column name in the table
     *
     * @param supplier folder supplier to get current folder
     * @param next next method
     * */
    public void setOnNameColumnEditCommit(@NotNull Supplier<Folder> supplier, @NotNull Runnable next) {
        this.nameColumn.setOnEditCommit(event -> {
            /* Get edited row */
            Entity entity = event.getRowValue();

            /* Get current folder */
            Folder currentFolder = supplier.get();

            /* Check if folder or file is exist in this current folder */
            if ((entity.getType() == Entity.TYPE.FOLDER && currentFolder.getFolders().contains(new Folder(event.getNewValue())))
                ||
                (entity.getType() == Entity.TYPE.FILE && currentFolder.getFiles().contains(new File(event.getNewValue(), "txt")))
            ) {
            } else {
                if (!event.getOldValue().equals(event.getNewValue())) {
                    entity.setName(event.getNewValue());
                    entity.setUpdatedDate(new Date());
                    next.run();
                }
            }
        });
    }

    /**
     * This method is used to get the table
     *
     * @return field {@link #list}
     * */
    public TableView<Entity> getList() {
        return this.list;
    }

}
