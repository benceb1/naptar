
package naptarfx;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javafx.beans.property.BooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class UjFeljegyzesController {
    SimpleDateFormat smpf= new SimpleDateFormat("yyyy-MM-dd");
    DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    @FXML
    private TextField cimText;

    @FXML
    private TextArea szovegText;

    @FXML
    CheckBox cbMegoszt;

    
    public boolean megosztva (){
        return cbMegoszt.isSelected();
    }
    public Feljegyzes ujF() {
        
        String cim = cimText.getText().trim();
        String szoveg = szovegText.getText().trim();
//        LocalDate datum = datumPicker.getValue();
        Date d = new Date();
        Feljegyzes newf = new Feljegyzes(cim, szoveg, smpf.format(d), megosztva());
        FeljegyzesData.getInstance().addTodoItem(newf);
        return newf;
    }
}
