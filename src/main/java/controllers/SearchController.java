package main.java.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

/**
 * This class represents controller that control search element in the application
 *
 * @since July 1st 2021
 * @author Fajar Zuhri Hadiyanto
 * @version 1.0
 * */
public class SearchController implements Initializable {

    /** This field represents close button */
    @FXML
    public Button closeBtn;

    /** This field represents search input field */
    @FXML
    private TextField searchInput;

    @Override
    public void initialize(URL location, ResourceBundle resources) {}

    /**
     * This method is used to set enter handler for search input field
     *
     * @param consumer String consumer
     * */
    public void setOnSearchInputEnter(Consumer<String> consumer) {
        this.searchInput.setOnKeyPressed(event -> {
            String value = ((TextField) event.getSource()).getText().trim();
            if (event.getCode().equals(KeyCode.ENTER) && !value.equals("")) {
                consumer.accept(value);
            }
        });
    }

    /**
     * This method is used to get search input field
     *
     * @return field {@link #searchInput}
     * */
    public TextField getSearchInput() {
        return this.searchInput;
    }

    /**
     * This method is used to get close button
     *
     * @return field {@link #closeBtn}
     * */
    public Button getCloseBtn() {
        return this.closeBtn;
    }
}
