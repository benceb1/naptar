
package naptarfx;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;


public class ismerosKElem extends Pane{
    Label lbl;

    Felhasznalo felhasznalo;

    public Button button;
    
    public ismerosKElem(Felhasznalo f) {
//        felhasznalo = f;
        button = new Button("Felvétel");
        ImageView img = new ImageView(new Image("kep.png"));
        lbl = new Label(f.getfNev());
        img.setFitWidth(50);
        img.setFitHeight(50);
        this.getChildren().add(img);
        this.getChildren().add(lbl);
        lbl.layoutXProperty().set(60);
        lbl.layoutYProperty().bind(this.heightProperty().subtract(lbl.heightProperty()).divide(2));
        this.getChildren().add(button);
        button.layoutYProperty().bind(this.heightProperty().subtract(button.heightProperty()).divide(2));
        button.layoutXProperty().set(250);
        this.setStyle("-fx-background-color:#f2efe3");
        
        
    }
}
