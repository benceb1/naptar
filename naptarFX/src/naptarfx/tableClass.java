
package naptarfx;

import java.util.Date;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author User
 */
public class tableClass {
    
    private SimpleStringProperty feljId;
    private SimpleStringProperty cim;
    private SimpleStringProperty felhaszn;
    private SimpleStringProperty datum;
    private SimpleStringProperty hatarido;
    
    
    public tableClass(){
       
        this.cim = new SimpleStringProperty("");
        this.felhaszn = new SimpleStringProperty("");
        this.datum = new SimpleStringProperty("");
        this.hatarido = new SimpleStringProperty("");
        
    }
    
    public tableClass(String c, String f, String d){
        
        this.cim = new SimpleStringProperty(c);
        this.felhaszn = new SimpleStringProperty(f);
        this.datum = new SimpleStringProperty(d);
        this.hatarido = new SimpleStringProperty("");
        
    }
    
    public tableClass(String c, String f, String d, String h){
        
        this.cim = new SimpleStringProperty(c);
        this.felhaszn = new SimpleStringProperty(f);
        this.datum = new SimpleStringProperty(d);
        this.hatarido = new SimpleStringProperty(h);
        
    }
    


    public String getCim() {
        return cim.get();
    }
    
    public void setCim(String c){
        cim.set(c);
    }

    public String getFelhaszn() {
        return felhaszn.get();
    }

    public void setFelhaszn(String f){
        felhaszn.set(f);
    }
    
    public String getDatum() {
        return datum.get();
    }

    public void setDatum(String d){
        datum.set(d);
    }
    
    public String getHatarido() {
        return hatarido.get();
    }  
    
    public void setHatarido(String h){
        hatarido.set(h);
    }
    
}
