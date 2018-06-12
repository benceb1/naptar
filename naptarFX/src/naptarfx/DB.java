package naptarfx;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DB {

    final String URL = "jdbc:derby:naptarDB;create=true";
    final String USERNAME = "";
    final String PASSWORD = "";

    Connection conn = null;
    Statement createStatement = null;
    DatabaseMetaData dbmd = null;

    public DB() {
        //életre kelt proba
        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("A híd létrejött");
        } catch (SQLException ex) {
            System.out.println("Valami baj van a connection (híd) létrehozásakor.");
            System.out.println("" + ex);
        }

        if (conn != null) {
            try {
                createStatement = conn.createStatement();
            } catch (SQLException ex) {
                System.out.println(" baj van van a createStatamentlétrehozásakor.");
                System.out.println("" + ex);
            }
        }

        //létezik a tábla?
        try {
            dbmd = conn.getMetaData();
        } catch (SQLException ex) {
            System.out.println("Valami baj van a DatabaseMetaData (adatbázis leírása) létrehozásakor..");
            System.out.println("" + ex);
        }

        try {
            ResultSet rs = dbmd.getTables(null, "APP", "FELHASZNALO", null);
            if (!rs.next()) {
                //felhasz név és + jelszó
                createStatement.execute("create table felhasznalo("
                        + "id INT not null primary key GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                        + " nev varchar(50),"
                        + " fNev varchar(50),"
                        + " jelszo varchar(50),"
                        + " szulev date,"
                        + " ismerosok varchar(1000))");
            }
        } catch (SQLException ex) {
            System.out.println("Valami baj van a felhasznaló tábla létrehozásakor.");
            System.out.println("" + ex);
        }

        try {
            ResultSet rs = dbmd.getTables(null, "APP", "FELJEGYZES", null);
            if (!rs.next()) {
                createStatement.execute("create table feljegyzes("
                        + "fid int not null primary key GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                        + " uid int,"
                        + "cim varchar(50),"
                        + " datum date,"
                        + " hatarIdo varchar(30),"
                        + " szoveg varchar(1500),"
                        + " lathatosag varchar(1))");
                createStatement.execute("alter table feljegyzes add foreign key (uid) references felhasznalo(id)");
            }
        } catch (SQLException ex) {
            System.out.println("Valami baj van feljegyzéstábla létrehozásakor.");
            System.out.println("" + ex);
        }
    }

    public boolean belephet(String fnev, String jelszo){
        boolean ih = false;
        try {
           
            String sql = "select * from felhasznalo where fNev='"+fnev+"' and jelszo='"+jelszo+"'";
            ResultSet rs = createStatement.executeQuery(sql);
            while(rs.next()){
                ih = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ih;
    }
    public void addFelhasznalo(Felhasznalo f) {

        try {
            String sql = "insert into felhasznalo (nev,fNev,jelszo, szulev, ismerosok) values (?,?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setString(1, f.getNev());
            preparedStatement.setString(2, f.getfNev());
            preparedStatement.setString(3, f.getJelszo());
            preparedStatement.setString(4, f.getSzulev());
            preparedStatement.setString(5, f.getIsmerosok());

            preparedStatement.execute();
            System.out.println("felhasznalo hozzáadva");
        } catch (Exception e) {
            System.out.println("valami probléma a felhaszn hozzáadásakor");
            System.out.println(e);
        }
    }

    public void feljegyzestorles() {
        try {
            createStatement.execute("delete from feljegyzes");
            System.out.println("kitörölve");
        } catch (Exception e) {
            System.out.println("valami baj van a törlésel" + e);
        }
    }

    public void megmutat() {
        String sql = "select * from felhasznalo";
//String sql = "select * from felhasznalo where fNev LIKE 'g%'";
        try {
//            createStatement.execute("update felhasznalo set ismerosok='101;102;' where id=1");
            ResultSet rs = createStatement.executeQuery(sql);

            while (rs.next()) {
                String id = rs.getString("id");
                String nev = rs.getString("nev");
                String fNev = rs.getString("fNev");
                String jelszo = rs.getString("jelszo");

                String szulev = rs.getString("szulev");
                String ism = rs.getString("ismerosok");
                System.out.println(id + " | " + nev + " | " + fNev + " | " + jelszo + " | " + szulev + " | " + ism);
            }
        } catch (SQLException ex) {
            System.out.println("valami baj van a lekérdezéssel");
            System.out.println(ex);
        }
    }

    public void addFeljegyzes(Felhasznalo f, Feljegyzes fgyz) {
        SimpleDateFormat smpfl = new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date();

        try {
            String sql = "insert into feljegyzes (uid, cim, datum, hatarIdo, szoveg, lathatosag) values (?,?,?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            
            preparedStatement.setInt(1, Integer.parseInt(f.getId()));

            preparedStatement.setString(2, fgyz.getCim());

            preparedStatement.setString(3, smpfl.format(d));
            
            preparedStatement.setString(4, fgyz.getHatarIdo());

            preparedStatement.setString(5, fgyz.getSzoveg());

            preparedStatement.setString(6, fgyz.getLathatosag());

            preparedStatement.execute();

            System.out.println("hozzáadva");
        } catch (Exception e) {
            System.out.println("nem sikerült hozzáadni a feljegyzést\n" + e);

        }

    }

    public Felhasznalo getFelhasznalo(String fnev, String jelsz) {
        Felhasznalo f = null;
        try {

            String sql = "select * from felhasznalo where fNev = '"+fnev+"' and jelszo='"+jelsz+"'";
            ResultSet rs = createStatement.executeQuery(sql);
            while (rs.next()) {
                String id = rs.getString("id");
                String nev = rs.getString("nev");
                String fNev = rs.getString("fNev");
                String jelszo = rs.getString("jelszo");
                String szulev = rs.getString("szulev");
                String ism = rs.getString("ismerosok");
//                System.out.println(id+" | "+nev+" | "+szulev+" | "+ism);
                f = new Felhasznalo(Integer.parseInt(id), nev, fNev, jelszo, szulev, ism);
                System.out.println("felhasználó kinyerése megtörtént");
            }

        } catch (Exception e) {
            System.out.println("nincs ilyen felhasznalo\n" + e);
        }
        return f;
    }

    public ObservableList<Feljegyzes> getFeljegyzes(Felhasznalo f) {
        ObservableList<Feljegyzes> a = FXCollections.observableArrayList();
        try {
            String sql = "select * from feljegyzes where uid=" + f.getId();
            ResultSet rs = createStatement.executeQuery(sql);
            while (rs.next()) {

                String fid = rs.getString("fid");
                String cim = rs.getString("cim");

                String datum = rs.getString("datum");
                
                String hatarido = rs.getString("hatarIdo");
                String szoveg = rs.getString("szoveg");
                String lathatosag = rs.getString("lathatosag");
//                System.out.println(b);
                if (!hatarido.equals("")) {
                    cim += " (H)";
                }
                Feljegyzes felj = new Feljegyzes(fid, cim, szoveg, datum, hatarido, lathatosag);
                a.add(felj);
            }
        } catch (Exception e) {
            System.out.println("nem sikerült kinyerni a feljegyzést\n" + e);
        }
        return a;
    }

    public void deleteFeljegyzes(Feljegyzes f) {
        String id = f.getId();
        try {
            createStatement.execute("delete from feljegyzes where fid=" + id);
        } catch (Exception e) {
        }

    }

    public ObservableList<Felhasznalo> getIsmerosok(Felhasznalo f) {
        ObservableList<Felhasznalo> i = FXCollections.observableArrayList();
        String kod = "";
        try {
            String sql = "select * from felhasznalo where id=" + f.getId();
            ResultSet rs = createStatement.executeQuery(sql);
            while (rs.next()) {
                kod += rs.getString("ismerosok");
            }
            String[] t = kod.split(";");
            for (String t1 : t) {
                rs = createStatement.executeQuery("select * from felhasznalo where id=" + t1);
                while (rs.next()) {
                    String id = rs.getString("id");
                    String nev = rs.getString("nev");
                    String fNev = rs.getString("fNev");
                    String jelszo = rs.getString("jelszo");
                    String szulev = rs.getString("szulev");
                    String ism = rs.getString("ismerosok");
                    Felhasznalo felhaszn = new Felhasznalo(Integer.parseInt(id), nev, fNev, jelszo, szulev, ism);
                    i.add(felhaszn);
                }
            }
        } catch (Exception e) {
            System.out.println("nem jó\n" + e);
        }
        return i;
    }

    //MŰTÉT
    public void ismerosTorles(Felhasznalo f, Felhasznalo i) {
        try {
            String sql = "select * from felhasznalo where id=" + f.getId();
            ResultSet rs = createStatement.executeQuery(sql);
            String ism = "";
            while (rs.next()) {
                ism = rs.getString("ismerosok");
            }
            ism = ism.replace((i.getId() + ";"), "");

            try {
                createStatement.execute("update felhasznalo set ismerosok='" + ism + "' where id=" + f.getId());
                System.out.println("törölve az ismerősök közül");
            } catch (Exception e) {
                System.out.println("nim sikerült cserélni az ismerőseidet" + e);
            }
        } catch (Exception e) {
            System.out.println("nem sikerült törölni az ismerőst" + e);
        }

        try {
            String sql = "select * from felhasznalo where id=" + i.getId();
            ResultSet rs = createStatement.executeQuery(sql);
            String ism = "";
            while (rs.next()) {
                ism = rs.getString("ismerosok");
            }
            ism = ism.replace((f.getId() + ";"), "");
            try {
                createStatement.execute("update felhasznalo set ismerosok='" + ism + "' where id=" + i.getId());
                System.out.println("törölve az ismerősök közül");
            } catch (Exception e) {
                System.out.println("nem sikerült téged kicserélni az ismerőseinél" + e);
            }
        } catch (Exception e) {
            System.out.println("new sikerült téged törölni az ismerősei közül");
        }
    }

    //MŰTÉT2
    public void addIsmeros(Felhasznalo f, Felhasznalo i) {
        try {
            
            String sql = "select * from felhasznalo where id=" + f.getId();
            ResultSet rs = createStatement.executeQuery(sql);
            String ism = "";
            while (rs.next()) {
                ism = rs.getString("ismerosok");
                
            }
            
            ism += (i.getId() + ";");
            
            try {
                createStatement.execute("update felhasznalo set ismerosok='" + ism + "' where id=" + f.getId());
                System.out.println("hozzáadva az ismerősökhöz");
            } catch (Exception e) {
                System.out.println("nem sikerült hozzáadni az ismerősttt" + e);
            }
        } catch (Exception e) {
            System.out.println("nem sikerült hozzáadni az ismerőst" + e);
        }

        try {
            String sql = "select * from felhasznalo where id=" + i.getId();
            ResultSet rs = createStatement.executeQuery(sql);
            String ism = "";
            while (rs.next()) {
                ism = rs.getString("ismerosok");
            }
            ism += (f.getId() + ";");
            try {
                createStatement.execute("update felhasznalo set ismerosok='" + ism + "' where id=" + i.getId());
                System.out.println("hozzáadva az ismerősökhöz");
            } catch (Exception e) {
                System.out.println("nem sikerült téged kicserélni az ismerőseinél" + e);
            }
        } catch (Exception e) {
            System.out.println("nem sikerült téged hozzáadni");
        }
    }

    public ObservableList<Felhasznalo> felhasznKeresList(Felhasznalo f, String szo) {
        ObservableList<Felhasznalo> i = FXCollections.observableArrayList();
        List<String>ismerosoklista=FeljegyzesData.getInstance().getIsmerosokId();/*new ArrayList<>();*/
//        String[] t = f.getIsmerosok().split(";");
//        for (int j = 0; j < t.length; j++) {
//            ismerosoklista.add(t[j]);
//        }
        try {
            String sql = "select * from felhasznalo where fNev LIKE '"+szo+"%'";
            ResultSet rs = createStatement.executeQuery(sql);
            while(rs.next()){
                String id = rs.getString("id");
                String nev = rs.getString("nev");
                String fNev = rs.getString("fNev");
                String jelszo = rs.getString("jelszo");
                String szulev = rs.getString("szulev");
                String ism = rs.getString("ismerosok");
                
                if((!ismerosoklista.contains(id))&& id!=f.getId()){
                    
                    Felhasznalo felhaszn = new Felhasznalo(Integer.parseInt(id), nev, fNev, jelszo, szulev, ism);
                    i.add(felhaszn);
                }
            }
            ismerosoklista.clear();
        } catch (Exception e) {
            System.out.println("nem sikerült keresni a felhasználók között"+e);
        }
        return i;
        
        
    }
    public ObservableList<tableClass> tablaSorok(Date d, List<String>idk) {
        SimpleDateFormat smpf = new SimpleDateFormat("yyyy-MM-dd");
        String dat = smpf.format(d);
        ObservableList<tableClass> l = FXCollections.observableArrayList();
        for (String string : idk) {
            
        
        try {
            
            String sql = "select feljegyzes.uid, feljegyzes.lathatosag, felhasznalo.nev, feljegyzes.cim, feljegyzes.datum, feljegyzes.hatarido"
                    + " from feljegyzes inner join felhasznalo on feljegyzes.uid = felhasznalo.id "
                    + "where datum = '"+dat+"' and lathatosag='1' and uid="+string;
            ResultSet rs = createStatement.executeQuery(sql);
            while(rs.next()){
                
                String nev = rs.getString("nev");              
                String cim = rs.getString("cim");
                String da = rs.getString("datum");
                String h = rs.getString("hatarido");
                
                tableClass a = new tableClass(cim, nev, da, h);
                l.add(a);
                
            }
        } catch (Exception e) {
            System.out.println("valami nem jó"+e);
        }
        }
        return l;
    }
    
    public void teszt(){
        try {
           
        } catch (Exception e) {
            System.out.println("baj a teszttel");
        }
            
        
    }
//    public void pelda(){
//        createStatement.execute("asd");
//    }
}
