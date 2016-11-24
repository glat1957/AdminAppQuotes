// Giorgio Latour
// Admin App for Quotations
// IHRTLUHC
package adminappquotes;

import data.AdminDAO;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class FXMLDocumentController implements Initializable {

    AdminDAO model;

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private TextField category;
    @FXML
    private Button createUser;
    @FXML
    private Button createCategory;
    @FXML
    private Button removeUser;
    @FXML
    private Button removeCategory;
    @FXML
    private CheckBox checkBox;
    @FXML
    private Label userStatus;
    @FXML
    private Label categoryStatus;

    public void setModel(AdminDAO model) {
        this.model = model;
    }

    @FXML
    private void handleCreateUser(ActionEvent event) {
        // Code to add user to quotations database.

        String adminYN;

        // I give the admin the option to create other admin users.
        if (checkBox.isSelected()) {
            adminYN = "Y";
        } else {
            adminYN = "N";
        }

        // Make sure both a username and password have been entered to create a user.
        // Then make sure account doesn't already exist.
        if (!username.getText().trim().isEmpty() && !password.getText().trim().isEmpty()) {
            if (model.checkAccountExists(username.getText()) == false) {
                model.createAccount(username.getText(), password.getText(), adminYN);
                userStatus.setText("User created.");
                username.clear();
                password.clear();
            } else {
                userStatus.setText("User already exists.");
            }
        } else {
            userStatus.setText("Please enter a username AND password.");
        }

    }

    @FXML
    private void handleRemoveUser(ActionEvent event) {
        if (!username.getText().trim().isEmpty()) {
            if (model.checkAccountExists(username.getText()) == true) {
                model.removeAccount(username.getText());
                username.clear();
                password.clear();
                userStatus.setText("User removed.");
            } else {
                userStatus.setText("User does not exist.");
            }
        } else {
            userStatus.setText("Please enter a user to remove.");
        }
    }

    @FXML
    private void handleCreateCategory(ActionEvent event) {
        if (!category.getText().trim().isEmpty()) {
            if (model.checkCategoryExists(category.getText()) == false) {
                model.createCategory(category.getText());
                category.clear();
                categoryStatus.setText("Category created.");
            } else {
                categoryStatus.setText("Category already exists.");
            }
        } else {
            categoryStatus.setText("Please enter a category to create.");
        }
    }

    @FXML
    private void handleRemoveCategory(ActionEvent event) {
        if (!category.getText().trim().isEmpty()) {
            if (model.checkCategoryExists(category.getText()) == true) {
                model.removeCategory(category.getText());
                category.clear();
                categoryStatus.setText("Category removed.");
            } else {
                categoryStatus.setText("Category does not exist.");
            }
        } else {
            categoryStatus.setText("Please enter a category to remove.");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
