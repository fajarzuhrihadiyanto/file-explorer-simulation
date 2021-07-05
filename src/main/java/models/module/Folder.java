package main.java.models.module;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class represents Folder in the file tree.
 * This class extends property and behaviour of {@link Entity}
 * and implements {@link Comparable}
 *
 * @since June 23rd 2021
 * @author Fajar Zuhri Hadiyanto
 * @version 1.0
 * */
public class Folder extends Entity implements Comparable<Folder> {

    /** This field represents all content of this folder, could be another folder or a file */
    private final ArrayList<Entity> contents;

    /** This field represents number of files contained in this folder */
    private int nFiles;

    /** This field represents number of folders contained in this folder */
    private int nFolders;

    /**
     * This constructor is used to create a new folder
     *
     * @param name name of this folder
     * */
    public Folder(@NotNull String name) {
        super(name, TYPE.FOLDER);
        this.contents = new ArrayList<>();
    }

    /**
     * This method is used to get contents of this folder
     *
     * @return field {@link #contents}
     * */
    public ArrayList<Entity> getContents() {
        return this.contents;
    }

    /**
     * This method is used to get all direct child folder of this folder
     *
     * @return list of all direct child folder of this folder
     * */
    public ArrayList<Entity> getFolders() {
        return this.contents
                .stream()
                .filter(e -> e.type == TYPE.FOLDER)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * This method is used to get all direct child file of this folder
     *
     * @return list of all direct child file of this folder
     * */
    public ArrayList<Entity> getFiles() {
        return this.contents
                .stream()
                .filter(e -> e.type == TYPE.FILE)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * This method is utility method that used to add a number of a certain type of entity
     * with a given amount to this folder and all ancestor folder to the root.
     *
     * @param folder current folder state
     * @param type entity type
     * @param num amount of entity to be added
     * */
    private void addNumberContent(@NotNull TYPE type, @NotNull int num) {
        if (type == TYPE.FOLDER) this.nFolders += num;
        else this.nFiles += num;
    }

    /**
     * This method is used to add a new entity to this folder. It could be a folder or a file.
     * If entity is exist in this folder, then just skip.
     *
     * @param content new entity to be inserted to this folder
     * */
    public void addContent(@NotNull Entity content) {
        if(!this.contents.contains(content) && this.contents.add(content)) {
            content.setParent(this);
            this.addNumberContent(content.type, 1);
        }
    }

    /**
     * This method is used to remove existing entity from this folder.
     *
     * @param content entity to be removed from this folder
     * */
    public void removeContent(@NotNull Entity content) {
        if (this.contents.remove(content))
            this.addNumberContent(content.type, -1);
    }

    /**
     * This method is used to remove multiple entity from this folder.
     *
     * @param contents list of entity to be removed from this folder
     * */
    @SuppressWarnings("Unused")
    public void removeContents(@NotNull List<Entity> contents) {
        for (Entity entity: contents)
            this.removeContent(entity);
    }

    /**
     * This method is used to get number of all files contained in this folder
     *
     * @return field {@link #nFiles}
     * */
    public int getnFiles() {
        return this.nFiles;
    }

    /**
     * This method is used to get number of all folders contained in this folder
     *
     * @return field {@link #nFolders}
     * */
    public int getnFolders() {
        return this.nFolders;
    }

    /**
     * This method is used to search entity contained in this folder
     * by a given name as a keyword
     *
     * @param key search keyword
     * @return list of entities that meet requirement
     * */
    public ArrayList<Entity> search(@NotNull String key) {
        ArrayList<Entity> result = new ArrayList<>();
        searchUtil(result, this, key);
        return result;
    }

    /**
     * This method is used as a utility to dig deep until the
     * deepest folder while trying to look for entity which
     * its name matches the search keyword
     *
     * @param result temporary result list
     * @param folder current visited folder
     * @param key search keyword
     * */
    private void searchUtil(@NotNull ArrayList<Entity> result, @NotNull Folder folder, @NotNull String key) {
        for (Entity content: folder.contents) {
            if (content.name.trim().toLowerCase().contains(key.toLowerCase())) {
                result.add(content);
            }

            if (content instanceof Folder) {
                searchUtil(result, (Folder) content, key);
            }
        }
    }

    /**
     * This method is used to compare this file to another one
     * by comparing name of this folder to other's
     *
     * @param o another folder object
     * @return integer representation of comparison.
     * */
    @Override
    public int compareTo(@NotNull Folder o) {
        return this.name.toLowerCase().compareTo(o.name.toLowerCase());
    }

    /**
     * This method is used to check if this is the same object with a given object
     * by comparing name of both folders
     *
     * @param o another object to be compared with this folder
     * @return true if this folder is the same as a given object, else false
     * */
    @Override
    public boolean equals(@Nullable Object o) {
        if (o instanceof Folder) {
            return this.name.equalsIgnoreCase(((Folder) o).name);
        }
        return false;
    }
}
