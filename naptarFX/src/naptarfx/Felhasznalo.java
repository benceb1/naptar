
package naptarfx;

import javafx.beans.property.SimpleStringProperty;


public class Felhasznalo {
    private final SimpleStringProperty id;
    private final SimpleStringProperty nev;
    private final SimpleStringProperty fNev;
    private final SimpleStringProperty jelszo;
    private final SimpleStringProperty szulev;
    private final SimpleStringProperty ismerosok;

    
    
//    SimpleDateFormat smpf= new SimpleDateFormat();
    
    public Felhasznalo(Integer id, String nev,String fNev, String jelszo, String szulev, String ismerosok ){
        this.id=new SimpleStringProperty(String.valueOf(id));
        this.nev = new SimpleStringProperty(nev);
        this.szulev = new SimpleStringProperty(szulev);
        this.ismerosok = new SimpleStringProperty(ismerosok);
        this.fNev=new SimpleStringProperty(fNev);
        this.jelszo = new SimpleStringProperty(jelszo);
        
    }
    
    public Felhasznalo(String nev,String fNev, String jelszo, String szulev){
        this.id=new SimpleStringProperty("");
        this.nev = new SimpleStringProperty(nev);
        this.szulev = new SimpleStringProperty(szulev);
        this.ismerosok = new SimpleStringProperty("");
        this.fNev=new SimpleStringProperty(fNev);
        this.jelszo = new SimpleStringProperty(jelszo);
    }
    
    
    public Felhasznalo(){
        this.id=new SimpleStringProperty("");
        this.nev = new SimpleStringProperty("");
        this.szulev = new SimpleStringProperty("");
        this.ismerosok = new SimpleStringProperty("");
        this.fNev = new SimpleStringProperty("");
        this.jelszo = new SimpleStringProperty("");
    }
    
    public String getId() {
        return id.get();
    }
    public void setId(int i){
        this.id.set(String.valueOf(i));
    }

    public String getNev() {
        return nev.get();
    }
    public void setNev(String nev){
        this.nev.set(nev);
    }

    public String getSzulev() {
        return szulev.get();
    }
    public void setSzulev(String szev){
        this.szulev.set(szev);
    }

    public String getIsmerosok() {
        return ismerosok.get();
    }
    public void setIsmerosok(String i){
        this.nev.set(i);
    }
   
    public String getfNev() {
        return fNev.get();
    }
    public void setfNev(String i){
        this.fNev.set(i);
    }
    
    public String getJelszo() {
        return jelszo.get();
    }
    public void setJelszo(String i){
        this.jelszo.set(i);
    }
}
