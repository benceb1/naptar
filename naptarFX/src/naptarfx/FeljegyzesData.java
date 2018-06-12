
package naptarfx;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;



public class FeljegyzesData {
    Date d = new Date();
    public static DB db;
    
    public static Felhasznalo felhasznalo;
    
    private static FeljegyzesData instance = new FeljegyzesData();
   

    private ObservableList<Feljegyzes> feljegyzesek;
     
    private ObservableList<Felhasznalo> ismerosok;
    
    private ObservableList<tableClass> tablaElemek;
   
    public static FeljegyzesData getInstance() {
        return instance;
    }
    
    public List<String>getOsszId(){
        List<String>idl = new ArrayList<>();
        idl.add(felhasznalo.getId());
        for (String s : getIsmerosokId()) {
            idl.add(s);
        }
        return  idl;
    }
    
    public List<String>getIsmerosokId(){
        List<String>idl = new ArrayList<>();
        for (Felhasznalo f : ismerosok) {
            idl.add(f.getId());
        }
        return idl;
    }
    
    public ObservableList<Feljegyzes> getFeljegyzesek() {
        return feljegyzesek;
    }

    public ObservableList<Felhasznalo> getIsmerosok() {
        return ismerosok;
    }
    
    public ObservableList<tableClass> getTablasorok() {
        return tablaElemek;
    }
    
    public void ujraTolt(){
        feljegyzesek.clear();
        loadTodoItems(felhasznalo, db);
    }
    
    public void addTodoItem(Feljegyzes item) {
        db.addFeljegyzes(felhasznalo, item);
        feljegyzesek.clear();
        loadTodoItems(felhasznalo, db);
        
    }

    public void loadTablaelemek(Date d){
        tablaElemek = FXCollections.observableArrayList();
        tablaElemek = db.tablaSorok(d,getOsszId());
    }
    
    public void loadTodoItems(Felhasznalo f, DB db) {

        feljegyzesek = FXCollections.observableArrayList();
        feljegyzesek = db.getFeljegyzes(f);
    }

    public void loadIsmerosok(Felhasznalo f){
       ismerosok = FXCollections.observableArrayList();
       ismerosok=db.getIsmerosok(f);
    }
}
