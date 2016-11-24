// Giorgio Latour
// Admin App for Quotations
// IHRTLUHC
package adminappquotes;

import data.AdminDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AdminAppQuotes extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLLoginWindow.fxml"));
        Parent root = (Parent)loader.load();
        
        FXMLLoginWindowController controller = (FXMLLoginWindowController) loader.getController();
        AdminDAO dao = new AdminDAO();
        controller.setModel(dao);
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Quotations Admin Utility Login");
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
