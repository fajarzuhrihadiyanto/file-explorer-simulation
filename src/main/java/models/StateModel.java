package main.java.models;

import com.sun.istack.internal.NotNull;
import main.java.models.module.Folder;

import java.util.Stack;

/**
 * This class represents State Model for this application.
 *
 * @since June 23rd 2021
 * @author Fajar Zuhri Hadiyanto
 * @version 1.0
 * */
public class StateModel {

    /** This field represents history for undo action */
    private final Stack<Folder> undoHistories;

    /** This field represents history for redo action */
    private final Stack<Folder> redoHistories;

    /** This field represents current folder where application at */
    private Folder currentPos;

    /** This field represents state of this app, either in search state or not */
    private boolean isSearch;

    /**
     * This constructor will create new state model
     * */
    public StateModel(@NotNull Folder currentPos) {
        this.currentPos = currentPos;
        this.undoHistories = new Stack<>();
        this.redoHistories = new Stack<>();
        this.isSearch = false;
    }

    /**
     * This method is used to get undo histories of this application
     *
     * @return field {@link #undoHistories}
     * */
    public Stack<Folder> getUndoHistories() {
        return this.undoHistories;
    }

    /**
     * This method is used to get redo histories of this application
     *
     * @return field {@link #redoHistories}
     * */
    public Stack<Folder> getRedoHistories() {
        return this.redoHistories;
    }

    /**
     * This method is used to set new position of current folder
     *
     * @param currentPos new position
     * */
    @SuppressWarnings("Unused")
    public void setCurrentPos(@NotNull Folder currentPos) {
        this.currentPos = currentPos;
    }

    /**
     * This method is used to get current position of this application
     *
     * @return field {@link #currentPos}
     * */
    public Folder getCurrentPos() {
        return this.currentPos;
    }

    /**
     * This method is used to move application to a new folder
     * and add current position to the undo histories
     *
     * @param folder new destination folder
     * */
    public void moveFolder(@NotNull Folder folder) {
        this.undoHistories.push(this.currentPos);
        this.currentPos = folder;
        this.redoHistories.clear();
    }

    /**
     * This method is used to move application to parent folder
     * */
    public void up() {
        this.moveFolder(this.currentPos.getParent());
    }

    /**
     * This method is used to move application to the latest visited folder
     * in the undo histories and add current position to redo histories
     * */
    public void back() {
        this.redoHistories.push(this.currentPos);
        this.currentPos = this.undoHistories.pop();
    }

    /**
     * This method is used to move application to the latest visited folder
     * in the redo histories and add current position to undo histories
     * */
    public void forward() {
        this.undoHistories.push(this.currentPos);
        this.currentPos = this.redoHistories.pop();
    }

    /**
     * This method is used to set search state of this app
     *
     * @param isSearch search status
     * */
    public void setIsSearch(@NotNull boolean isSearch) {
        this.isSearch = isSearch;
    }

    /**
     * This method is used to get search state of this app
     *
     * @return field {@link #isSearch}
     * */
    public boolean getIsSearch() {
        return this.isSearch;
    }
}
