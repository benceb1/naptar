
package naptarfx;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import javafx.beans.property.SimpleStringProperty;


public class Feljegyzes { 
    
    private SimpleStringProperty id;
    private SimpleStringProperty cim;
    private SimpleStringProperty szoveg;
    private SimpleStringProperty datum;
    private SimpleStringProperty hatarIdo;
    private SimpleStringProperty lathatosag;
    SimpleDateFormat smpf= new SimpleDateFormat("yyyy-MM-dd");
    
    public Feljegyzes(String cim, String szoveg, String datum, boolean megosztva) {
        this.cim = new SimpleStringProperty(cim);
        this.szoveg = new SimpleStringProperty(szoveg);
        this.hatarIdo = new SimpleStringProperty("");
        this.id=new SimpleStringProperty("");
        this.datum=new SimpleStringProperty(datum);
        this.lathatosag=new SimpleStringProperty(megosztva ? "1" : "0");
    }

    
    public Feljegyzes(String cim, String szoveg, String datum,String hatarido, boolean megosztv) {
        this.cim = new SimpleStringProperty(cim);
        this.szoveg = new SimpleStringProperty(szoveg);
        this.hatarIdo = new SimpleStringProperty(hatarido);
        this.lathatosag = new SimpleStringProperty(megosztv ? "1" : "0");
        this.id=new SimpleStringProperty("");
        this.datum=new SimpleStringProperty(datum);
    }
    
    public Feljegyzes(String id, String cim, String szoveg, String datum, String hatarido, String lathatosag) {
        this.id = new SimpleStringProperty(id);
        this.cim = new SimpleStringProperty(cim);
        this.szoveg = new SimpleStringProperty(szoveg);
        this.datum = new SimpleStringProperty(datum);
        this.hatarIdo = new SimpleStringProperty(hatarido);
        this.lathatosag = new SimpleStringProperty(lathatosag);
    }
    
    public Feljegyzes(){
        this.id=new SimpleStringProperty("");
        this.cim=new SimpleStringProperty("");
        this.szoveg=new SimpleStringProperty("");
        this.datum=new SimpleStringProperty("");
        this.hatarIdo=new SimpleStringProperty("");
        this.lathatosag=new SimpleStringProperty("");
      
    }
    
    public String getId() {
        return id.get();
    }
    public void setId(String i){
        this.id.set(i);
    }
    
    public String getCim() {
        return cim.get();
    }
    public void setCim(String i){
        this.cim.set(i);
    }

    public String getSzoveg() {
        return szoveg.get();
    }
    public void setSzoveg(String i){
        this.szoveg.set(i);
    }

    public String getDatum() {
        return datum.get();
    }
    public void setDatum(String i){
        this.datum.set(i);
    }
    
     public String getHatarIdo() {
        return hatarIdo.get();
    }
    public void setHatarIdo(String i){
        this.hatarIdo.set(i);
    }
    
    public String getLathatosag() {
        return lathatosag.get();
    }
    public void setLathatosag(String i){
        this.lathatosag.set(i);
    }
     @Override
    public String toString() {
        return cim.get();
    }
}
