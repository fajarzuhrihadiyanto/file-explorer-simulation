package main.java.models;

/**
 * This class represents Model for this application.
 *
 * @since June 23rd 2021
 * @author Fajar Zuhri Hadiyanto
 * @version 1.0
 * */
public class Model {

    /** This field represents file tree model as a part of this model */
    private final FileTreeModel fileTreeModel;

    /** This field represents state model as a part of this model */
    private final StateModel stateModel;

    /**
     * This constructor will create new model by creating all model part
     * */
    public Model() {
        this.fileTreeModel = new FileTreeModel();
        this.stateModel = new StateModel(this.fileTreeModel.getRoot());
    }

    /**
     * This method is used to get file tree model as a part of this model
     *
     * @return field {@link #fileTreeModel}
     * */
    public FileTreeModel getFileTreeModel() {
        return this.fileTreeModel;
    }

    /**
     * This method is used to get state model as a part of this model
     *
     * @return field {@link #stateModel}
     * */
    public StateModel getStateModel() {
        return this.stateModel;
    }
}
