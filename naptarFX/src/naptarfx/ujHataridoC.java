/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package naptarfx;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 *
 * @author User
 */

public class ujHataridoC {
    
    SimpleDateFormat smpf= new SimpleDateFormat("yyyy-MM-dd");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    @FXML
    ComboBox <String> cbPerc;
    
    @FXML
    ComboBox <String> cbOra;
    
    @FXML
    DatePicker datepicker;
    
    @FXML
    TextField cimText;
    
    @FXML
    TextArea szovegText;
   
    @FXML
    CheckBox cbMegoszt;
    
    public void betolt(){
        
        for (int i = 0; i <= 23; i++) {
            cbOra.getItems().add(i<10? "0"+(String.valueOf(i)): String.valueOf(i));
        }
        
        for (int i = 0; i <= 59; i++) {
            cbPerc.getItems().add(i<10? "0"+(String.valueOf(i)): String.valueOf(i));
        }
    }
    
    public void kiir(){
       
        String ora = (String)cbOra.getValue()+":"+(String)cbPerc.getValue();
        
        String cim = cimText.getText().trim();
        
        String szoveg = szovegText.getText().trim();
       
        String datum = formatter.format(datepicker.getValue());
        
        System.out.println(cim+"\n"+szoveg+"\n"+datum+";"+ora+ (megosztva() ? "megosztva": "nincs meg osztva"));
    }
    
    public boolean megosztva(){
        return cbMegoszt.isSelected();
    }
    
    public Feljegyzes ujH() {
        String ora = (String)cbOra.getValue()+":"+(String)cbPerc.getValue();
        String datum = formatter.format(datepicker.getValue());
        String hatarido = datum+";"+ora;
        String cim = cimText.getText().trim();
        String szoveg = szovegText.getText().trim();
//        LocalDate datum = datumPicker.getValue();
        Date d = new Date();
        Feljegyzes newf = new Feljegyzes(cim, szoveg, smpf.format(d),hatarido, megosztva());
        FeljegyzesData.getInstance().addTodoItem(newf);
        return newf;
    }
}
