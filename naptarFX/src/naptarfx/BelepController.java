
package naptarfx;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class BelepController {
    
    @FXML
    public Button btnBelep;
    
    @FXML
    TextField tbFnev;  
    
    @FXML
    TextField tbJelszo; 
          
    Stage s;
    public Button getButton(){
        return btnBelep;
    }
    public void setStage(Stage st){
        s=st;
    }
    
    @FXML
    public void regAction(ActionEvent e){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RegAblak.fxml"));
            Parent root = (Parent) loader.load();
            Stage stage = new Stage();
            stage.setTitle("Regisztráció");
            stage.setScene(new Scene(root));
            stage.show();
            
        } catch (IOException ex) {
            System.out.println("nem sikerült betölteni"+ex);
        }
    }
  
}
