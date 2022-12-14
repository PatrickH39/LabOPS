package controllers;

import data.IOHandler;
import data.User;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserManagerController implements Initializable {

    // Add Information
    ObservableList<String> classroomList = FXCollections.observableArrayList("212", "214", "216", "224A", "316", "320");
    public TextField fieldNickname;
    public TextField fieldFirstName;
    public TextField fieldLastName;
    public ChoiceBox choiceClassroom;
    public ListView<data.User> listUsers = new ListView<>();

    // Display Information
    public Label lblHomeroom;
    public Label lblNickname;
    public GridPane gridUserInfo;
    public Label lblFirstName;
    public Label lblLastName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateUserList();
        choiceClassroom.setItems(classroomList);
    }

    /**
     * Requires: Nothing
     * Modifies: root, stage, scene
     * Effects: Returns to previous GUI page with same window, which is the administration page
     *
     * @param actionEvent
     * @throws IOException
     */
    public void goBack(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Admin.fxml"));
        root.getStylesheets().add("/style.css");
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Requires: fieldNickname, fieldFirstName, fieldLastName, choiceClassroom
     * Modifies: alert, fieldNickname, fieldFirstName, fieldLastName, fieldClassroom
     * Effects: Adds a new User to the user list then updates GUI.
     *
     * @param actionEvent
     */
    public void addUser(ActionEvent actionEvent) {
        if (fieldNickname.getText().isEmpty() || fieldFirstName.getText().isEmpty() || fieldLastName.getText().isEmpty() || choiceClassroom.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Missing Information");

            // Header Text: null
            alert.setHeaderText(null);
            alert.setContentText("Please check your entries");

            alert.showAndWait();
            return;
        }

        IOHandler.addItemAllUsers(new User(
                fieldNickname.getText(),
                fieldFirstName.getText(),
                fieldLastName.getText(),
                (String) choiceClassroom.getValue()
        ));

        fieldNickname.clear();
        fieldFirstName.clear();
        fieldLastName.clear();
        choiceClassroom.setValue(null);

        updateUserList();
    }

    /**
     * Requires: Nothing
     * Modifies: IOHandler.listUsers
     * Effects: Propagates ListView with Users
     */
    private void updateUserList() {
        listUsers.getItems().clear();

        for (data.User item : data.IOHandler.getAllUsers()) {
            listUsers.getItems().add(item);
        }

        User selected = listUsers.getSelectionModel().getSelectedItem();
        if (selected == null) gridUserInfo.setVisible(false);

        listUsers.getSelectionModel().select(0);
    }

    /**
     * Requires: Nothing
     * Modifies: lblFirstName, lblLastName, lblNickname, lblHomeroom, gridUserInfo
     * Effects: Lists available information about a user
     *
     * @param mouseEvent
     */
    public void showUsers(MouseEvent mouseEvent) {
        if (listUsers.getItems().isEmpty()){
            gridUserInfo.setVisible(false);
            return;
        }

        gridUserInfo.setVisible(true);

        User selected = listUsers.getSelectionModel().getSelectedItem();

        lblFirstName.setText(selected.getFirstName());
        lblLastName.setText(selected.getLastName());
        lblNickname.setText(selected.getNickname());
        lblHomeroom.setText(selected.getHomeroom());
    }

    /**
     * Deletes a user
     *
     * @param actionEvent
     */
    public void deleteUser(ActionEvent actionEvent) {
        User selected = listUsers.getSelectionModel().getSelectedItem();
        IOHandler.removeItemAllUsers(selected);
        updateUserList();
    }
}
