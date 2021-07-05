package main.java.models.module;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.util.Date;

/**
 * This class represents File in the file tree.
 * This class extends property and behaviour of {@link Entity}
 * and implements {@link Comparable}
 *
 * @since June 23rd 2021
 * @author Fajar Zuhri Hadiyanto
 * @version 1.0
 * */
public class File extends Entity implements Comparable<File> {

    /** This field represents extension of this file */
    private String extension;

    /**
     * This constructor will create new file with a given name and extension
     *
     * @param name name of this file
     * @param extension extension of this file
     * */
    public File(@NotNull String name, @NotNull String extension) {
        super(name, TYPE.FILE);
        this.extension = extension;
    }

    /**
     * This method is used to update this file by change extension with a new one
     *
     * @param extension new extension of this file
     * */
    @SuppressWarnings("Unused")
    public void setExtension(@NotNull String extension) {
        this.extension = extension;
        this.setUpdatedDate(new Date());
    }

    /**
     * This method is used to get extension of this file
     *
     * @return field {@link #extension}
     * */
    public String getExtension() {
        return this.extension;
    }

    /**
     * This method is used to compare this file to another one by comparing
     * <ul>
     *     <li>field {@link #name}</li>
     *     <li>field {@link #extension}</li>
     *     <li>field {@link #createdDate}</li>
     *     <li>field {@link #updatedDate}</li>
     * </ul>
     *
     * @param o another file object
     * @return integer representation of comparison.
     * */
    @Override
    public int compareTo(@NotNull File o) {
        if (this.name.toLowerCase().compareTo(o.name.toLowerCase()) == 0) {
            if (this.extension.toLowerCase().compareTo(o.extension.toLowerCase()) == 0) {
                if (this.createdDate.compareTo(o.createdDate) == 0) {
                    return this.updatedDate.compareTo(o.updatedDate);
                } else return this.createdDate.compareTo(o.createdDate);
            } else return this.extension.toLowerCase().compareTo(o.extension.toLowerCase());
        } else return this.name.toLowerCase().compareTo(o.name.toLowerCase());
    }

    /**
     * This method is used to check if this is the same object with a given object
     * by comparing name and extension of both files
     *
     * @param o another object to be compared with this file
     * @return true if this file is the same as a given object, else false
     * */
    @Override
    public boolean equals(@Nullable Object o) {
        if (o instanceof File) {
            return this.name.equalsIgnoreCase(((File) o).getName()) &&
                    this.extension.equalsIgnoreCase(((File) o).getExtension());
        }
        return false;
    }
}
