/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package naptarfx;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 *
 * @author User
 */
public class minNaptar {

    public static int pluszminusz;
    static SimpleDateFormat sm = new SimpleDateFormat("Y. MMMMM");

    public minNaptar(Pane p) {
        minElem[][] pm = new minElem[6][7];
        int asd = 0;

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {

                //+nap hozzáadás az elemhez
                pm[i][j] = new minElem(tablanapok().get(asd));
                pm[i][j].setLayoutX(3 + j * 29);
                pm[i][j].setLayoutY(65 + i * 30);
                pm[i][j].setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        minElem s = (minElem)e.getSource();  
                        FeljegyzesData.getInstance().loadTablaelemek(s.elemDatuma);
                        
                        FXMLDocumentController.data=FeljegyzesData.getInstance().getTablasorok();
                        FXMLDocumentController.tb.setItems(FXMLDocumentController.data);
                    }
                });
                p.getChildren().add(pm[i][j]);
                asd++;
            }
        }
    }

    public String mainap() {
        Date d = new Date();

        SimpleDateFormat smpf = new SimpleDateFormat("Y.  MMMMM d.  (EEEEE)");

        String s = smpf.format(d);
        return s;
    }

    public void honapNev(Label lbl) {

        Calendar c1 = Calendar.getInstance();
        c1.add(Calendar.MONTH, pluszminusz);
        Date enap = c1.getTime();
        lbl.setText(sm.format(enap));

    }

    public static String elso() {
        Calendar c1 = Calendar.getInstance();
        c1.add(Calendar.MONTH, pluszminusz);
        c1.set(Calendar.DAY_OF_MONTH, 1);
        SimpleDateFormat smpf1 = new SimpleDateFormat("E");
        Date enap = c1.getTime();

        String s1 = smpf1.format(enap);
        return s1;
    }

    public static int neleje() {
        int sz = 0;
        switch (elso()) {
            case "H":
                sz = 0;
                break;
            case "K":
                sz = 1;
                break;
            case "Sze":
                sz = 2;
                break;
            case "Cs":
                sz = 3;
                break;
            case "P":
                sz = 4;
                break;
            case "Szo":
                sz = 5;
                break;
            case "V":
                sz = 6;
                break;
            default:;
                break;
        }
        return sz;
    }

    public static List<Date> tablanapok() {

        List<Date> napok = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, pluszminusz);//teszt
        cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DATE));
        cal.add(Calendar.DATE, -neleje());
//        cal.add(Calendar.MONTH, );//teszt
        for (int i = 0; i < 42; i++) {
            Date d = cal.getTime();
            napok.add(d);
            cal.add(Calendar.DATE, +1);
        }
        return napok;
    }

}
