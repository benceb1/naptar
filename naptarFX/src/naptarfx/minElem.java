
package naptarfx;

import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 *
 * @author User
 */
public class minElem extends Pane {
    private Label szam;
    public Date elemDatuma;
    SimpleDateFormat hanyadiakf= new SimpleDateFormat("d");
    SimpleDateFormat honap= new SimpleDateFormat("M");
    
    public minElem(Date d){
        elemDatuma=d;
        this.setPrefSize(28, 29);
        int thishonap=Integer.parseInt(honap.format(d));
        int mosthonap = Integer.parseInt(honap.format(new Date()));
        
        if(mosthonap == thishonap)
           this.setStyle("-fx-background-color: #fcfcfc;"); 
        else 
            this.setStyle("-fx-background-color: #e5e5e0;");
        
        String s=hanyadiakf.format(d);
        szam = new Label(s);
        this.getChildren().add(szam);
        szam.layoutXProperty().bind(this.widthProperty().subtract(szam.widthProperty()).divide(2));
        szam.layoutYProperty().bind(this.heightProperty().subtract(szam.heightProperty()).divide(2));
        
//        szam.setLayoutY(USE_PREF_SIZE);
    }
}
