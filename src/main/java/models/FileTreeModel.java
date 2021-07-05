package main.java.models;

import main.java.models.module.File;
import main.java.models.module.Folder;

/**
 * This class represents File Tree Model for this application.
 *
 * @since June 23rd 2021
 * @author Fajar Zuhri Hadiyanto
 * @version 1.0
 * */
public class FileTreeModel {

    /** This field represents root folder of this file tree system */
    private final Folder root;

    /**
     * This constructor will create new file tree system and root folder in it
     * */
    public FileTreeModel() {
        this.root = new Folder("root");
        this.root.setParent(null);

        /* Uncomment this line for testing only */
        this.init();
    }

    /** This method is used to generate folders and file for testing purpose */
    @SuppressWarnings("For testing purpose")
    private void init() {
        Folder folder1 = new Folder("Folder 1");
        Folder folder2 = new Folder("Folder 2");
        Folder folder3 = new Folder("Folder 3");
        Folder folder4 = new Folder("Folder 4");

        this.root.addContent(folder1);
        this.root.addContent(folder2);
        this.root.addContent(folder3);
        this.root.addContent(folder4);

        this.root.addContent(new File("abab", "txt"));
        this.root.addContent(new File("ABCD", "txt"));

        Folder child1 = new Folder("a");
        Folder child2 = new Folder("b");
        Folder child3 = new Folder("c");
        Folder child4 = new Folder("d");

        folder1.addContent(child1);
        folder1.addContent(new File("aatde", "txt"));
        folder1.addContent(new File("lorem", "txt"));

        folder2.addContent(child2);
        folder2.addContent(new File("nanana", "txt"));
        folder2.addContent(new File("anan", "txt"));

        folder3.addContent(child3);
        folder3.addContent(new File("NANANA", "txt"));
        folder3.addContent(new File("MirA", "txt"));

        folder4.addContent(child4);
        folder4.addContent(new File("hiyahiya", "txt"));
        folder4.addContent(new File("bcda", "txt"));
    }

    /**
     * This method is used to get root folder of this file tree system.
     *
     * @return field {@link #root}
     * */
    public Folder getRoot() {
        return this.root;
    }

}
