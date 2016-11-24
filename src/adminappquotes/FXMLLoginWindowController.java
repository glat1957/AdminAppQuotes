// Giorgio Latour
// Admin App for Quotations
// IHRTLUHC
package adminappquotes;

import data.AdminDAO;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLLoginWindowController implements Initializable {

    AdminDAO model;
    
    @FXML
    private PasswordField password;
    @FXML
    private TextField username;
    @FXML
    private Button loginbutton;
    @FXML
    private Label couldNotLogIn;

    public void setModel(AdminDAO model) { this.model = model; }
    @FXML
    private void handleLogin(ActionEvent event) {
        //Code to handle login event.
        if (model.logInAdmin(username.getText(), password.getText())) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
                Parent root = (Parent) loader.load();

                FXMLDocumentController controller = (FXMLDocumentController) loader.getController();
                controller.setModel(model);
        
                
                Scene scene = new Scene(root);
                Stage stage = new Stage();

                stage.setScene(scene);
                stage.setResizable(false);
                stage.setTitle("Quotations Admin Utility");
                stage.show();
                loginbutton.getScene().getWindow().hide();
            } catch (Exception ex) {
                System.out.println("Could not log in to utility.");
                ex.printStackTrace();
            }
        } else {
            couldNotLogIn.setText("Error: Could not log in.");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
