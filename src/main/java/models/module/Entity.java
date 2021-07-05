package main.java.models.module;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.util.Date;
import java.util.Stack;

/**
 * This abstract class represents Entity for file tree.
 * Entity could be a {@link Folder} or a {@link File}
 *
 * @since June 23rd 2021
 * @author Fajar Zuhri Hadiyanto
 * @version 1.0
 * */
public abstract class Entity {

    /** This field is a helper for folder and file type */
    public enum TYPE {
        FOLDER,
        FILE
    }

    /**
     * This field represents what type of this entity.
     * */
    protected final TYPE type;

    /** This field represents name of this entity (folder name or file name) */
    protected String name;

    /** This field represents when this entity is created */
    protected final Date createdDate;

    /** This field represents when last time this entity updated */
    protected Date updatedDate;

    /** This field represents parent folder of this entity */
    protected Folder parent;

    /**
     * This constructor will create new entity with a given name and type
     * also set created date and updated date to instantiate date of this object
     *
     * @param name name of this entity
     * @param type type of this entity
     * */
    public Entity(@NotNull String name, @NotNull TYPE type) {
        this.name = name;
        this.type = type;
        this.createdDate = new Date();
        this.updatedDate = new Date();
    }

    /**
     * This method is used to get type of this entity
     *
     * @return field {@link #type}
     * */
    public TYPE getType() {
        return this.type;
    }

    /**
     * This method is used to update name of this entity
     *
     * @param name new name of this entity
     * */
    public void setName(@NotNull String name) {
        this.name = name;
        this.setUpdatedDate(new Date());
    }

    /**
     * This method is used to get name of this entity
     *
     * @return field {@link #name}
     * */
    public String getName() {
        return this.name;
    }

    /**
     * This method is used to get created date of this entity
     *
     * @return field {@link #createdDate}
     * */
    @SuppressWarnings("Unused")
    public Date getCreatedDate() {
        return this.createdDate;
    }

    /**
     * This method is used to set new updated date of this entity
     *
     * @param updatedDate new date when this entity is updated, usually when this method is invoked
     * */
    public void setUpdatedDate(@NotNull Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * This method is used to get latest updated date of this entity
     *
     * @return field {@link #updatedDate}
     * */
    public Date getUpdatedDate() {
        return this.updatedDate;
    }

    /**
     * This method is used to set parent folder of this entity
     *
     * @param parent parent folder of this entity
     * */
    public void setParent(@Nullable Folder parent) {
        this.parent = parent;
    }

    /**
     * This method is used to get parent folder of this entity
     *
     * @return field {@link #parent}
     * */
    public Folder getParent() {
        return this.parent;
    }

    /**
     * This method is used to get directory from root down to direct parent of this entity
     * by dig deeper into field {@link #parent}
     *
     * @return stack of folders
     * */
    public Stack<Folder> getDirectory() {
        Stack<Folder> folders = new Stack<>();

        Folder temp = this.parent;
        while (temp != null) {
            folders.push(temp);
            temp = temp.getParent();
        }

        return folders;
    }

}
