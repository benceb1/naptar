package naptarfx;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FXMLDocumentController implements Initializable {

    @FXML
    Label datumkiir;

    @FXML
    TableView table;

    @FXML
    Pane minP;

    @FXML
    StackPane menuPane;

    @FXML
    Button btnNaptar;

    @FXML
    Button btnFeljegyzes;

    @FXML
    Button btnUzenet;

    @FXML
    Pane tPane;

    @FXML
    Pane lPane;

    @FXML
    ImageView profView;

    @FXML
    AnchorPane mainPane;

    @FXML
    private ListView<Feljegyzes> fListView;

    @FXML
    private TextArea fTextArea;

    @FXML
    private Label datumLabel;

    @FXML
    private Label hataridoLabel;

    @FXML
    HBox hataridobox;

    @FXML
    public Label honapLabel;

    @FXML
    Button btnBalra;

    @FXML
    Button btnJobbra;

    @FXML
    Pane kontaktokPane;

    @FXML
    Button btnKontaktok;

    @FXML
    VBox vbMeglevo;

    @FXML
    VBox vbKeres;

    @FXML
    TextField tfKeres;

    minNaptar mn;
    public static Label lbl;

    public static TableView tb;

    @FXML
    public void minValtAction(ActionEvent e) {

        if (e.getSource().equals(btnJobbra)) {
            minNaptar.pluszminusz++;
            mn = new minNaptar(minP);
            mn.honapNev(honapLabel);
        } else if (e.getSource().equals(btnBalra)) {
            minNaptar.pluszminusz--;
            mn = new minNaptar(minP);
            mn.honapNev(honapLabel);
        }
    }

    @FXML
    public void btnNaptarAblakAction(ActionEvent e) {
        if (e.getSource().equals(btnNaptar)) {
            actualPane.setVisible(false);
            actualPane = tPane;
            actualPane.setVisible(true);
        } else if (e.getSource().equals(btnFeljegyzes)) {
            actualPane.setVisible(false);
            actualPane = lPane;
            actualPane.setVisible(true);
        } else if (e.getSource().equals(btnKontaktok)) {
            actualPane.setVisible(false);
            actualPane = kontaktokPane;
            actualPane.setVisible(true);
        }
    }

    @FXML
    ComboBox cbHh;

    @FXML
    public void ujHAction(ActionEvent e) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainPane.getScene().getWindow());
        dialog.setTitle("+Új határidő");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("hatarido.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());

        } catch (IOException ex) {
            System.out.println("valami baj van a betöltéskor");
            ex.printStackTrace();
            return;
        }

        ujHataridoC ac = fxmlLoader.getController();
        ac.betolt();

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            ac.kiir();
//            
            Feljegyzes newItem = ac.ujH();

            fListView.getSelectionModel().select(newItem);
            fListView.getItems().setAll(FeljegyzesData.getInstance().getFeljegyzesek());

        }
        tb.setItems(data);
    }

    @FXML
    public void TorlesAction(ActionEvent e) {
        Feljegyzes item = fListView.getSelectionModel().getSelectedItem();
        FeljegyzesData.db.deleteFeljegyzes(item);
        FeljegyzesData.getInstance().ujraTolt();

        fListView.getItems().setAll(FeljegyzesData.getInstance().getFeljegyzesek());
        hataridobox.setVisible(false);
        fTextArea.setText("");
        FeljegyzesData.getInstance().loadTablaelemek(new Date());
        data = FeljegyzesData.getInstance().getTablasorok();
        tb.setItems(data);
    }

    @FXML
    public void ujFAction(ActionEvent e) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainPane.getScene().getWindow());
        dialog.setTitle("+Új feljegyzés");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("ujFeljegyzes.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());

        } catch (IOException ex) {
            System.out.println("valami baj van a betöltéskor");
            ex.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            UjFeljegyzesController controller = fxmlLoader.getController();
            if (controller.megosztva()) {
                System.out.println("megosztva");
            } else {
                System.out.println("nincs meg osztva");
            }
            Feljegyzes newItem = controller.ujF();
            fListView.getSelectionModel().select(newItem);
            fListView.getItems().setAll(FeljegyzesData.getInstance().getFeljegyzesek());

        }
        tb.setItems(data);
    }

    @FXML
    public void belepAction(ActionEvent e) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("belepAblak.fxml"));
            Parent root = (Parent) loader.load();
            Stage stage = new Stage();
            stage.setTitle("Belépés");
            stage.setScene(new Scene(root));
            stage.show();
            BelepController c = loader.getController();
            c.setStage(stage);
            Button b = c.getButton();
            b.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    if (FeljegyzesData.db.belephet(c.tbFnev.getText(), c.tbJelszo.getText())) {

                        FeljegyzesData.felhasznalo = FeljegyzesData.db.getFelhasznalo(c.tbFnev.getText(), c.tbJelszo.getText());
                        mindenbetolt();
                        stage.close();
                    } else {
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Hiba");

                        // Header Text: null
                        alert.setHeaderText(null);
                        alert.setContentText("Hibás jelszó, vagy felhasználónév!");

                        alert.showAndWait();
                        c.tbFnev.setText("");
                        c.tbJelszo.setText("");
                    }
                }
            });
        } catch (IOException ex) {
            System.out.println("nem sikerült betölteni az ablakot");
        }
    }

    public static boolean valt = false;

    Pane actualPane;

    public Felhasznalo jelenFelhasznalo;

    public DB db;

    public static ObservableList<tableClass> data = FXCollections.observableArrayList();

    public void betolt() {
        data = FeljegyzesData.getInstance().getTablasorok();
        actualPane = tPane;
//layoutY="51.0" prefHeight="506.0" prefWidth="742.0"
        tb = new TableView();

        tb.setLayoutY(51);
        tb.prefHeight(506);
        tb.prefWidth(742);
        tPane.getChildren().add(tb);

        TableColumn felhaszn = new TableColumn("Felhasználó");
        felhaszn.setMinWidth(150);
        felhaszn.setCellFactory(TextFieldTableCell.forTableColumn());
        felhaszn.setCellValueFactory(new PropertyValueFactory<tableClass, String>("felhaszn"));

        TableColumn cim = new TableColumn("Cím");
        cim.setMinWidth(215);
        cim.setCellFactory(TextFieldTableCell.forTableColumn());
        cim.setCellValueFactory(new PropertyValueFactory<tableClass, String>("cim"));

        TableColumn datum = new TableColumn("Létrehozás dátuma");
        datum.setCellFactory(TextFieldTableCell.forTableColumn());
        datum.setCellValueFactory(new PropertyValueFactory<tableClass, String>("datum"));
        datum.setMinWidth(150);

        TableColumn hatarid = new TableColumn("Határidő");
        hatarid.setMinWidth(210);
        hatarid.setCellFactory(TextFieldTableCell.forTableColumn());
        hatarid.setCellValueFactory(new PropertyValueFactory<tableClass, String>("hatarido"));

        tb.getColumns().addAll(felhaszn, cim, datum, hatarid);
        tb.setItems(data);

        table.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {

//                System.out.println(table.getColumns().);
            }
        });

        vbMeglevoUjratolt();

        tfKeres.textProperty().addListener((observable, oldValue, newValue) -> {
            vbKeres.getChildren().clear();
            if (!newValue.equals("")) {
                ObservableList<Felhasznalo> keresett = FeljegyzesData.db.felhasznKeresList(FeljegyzesData.felhasznalo, newValue);
                for (Felhasznalo f : keresett) {

                    ismerosKElem elem = new ismerosKElem(f);
                    vbKeres.getChildren().add(elem);
                    elem.button.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {
                            tfKeres.setText("");
                            FeljegyzesData.db.addIsmeros(FeljegyzesData.felhasznalo, f);
                            FeljegyzesData.getInstance().loadIsmerosok(FeljegyzesData.felhasznalo);
                            vbMeglevo.getChildren().clear();

                            vbMeglevoUjratolt();
                        }
                    });

                }
            }

        });
    }

    public void vbMeglevoUjratolt() {
        vbMeglevo.getChildren().clear();
        for (Felhasznalo f : FeljegyzesData.getInstance().getIsmerosok()) {
            ismerosElem ie = new ismerosElem(f);
            vbMeglevo.getChildren().add(ie);

            ie.button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    FeljegyzesData.db.ismerosTorles(FeljegyzesData.felhasznalo, f);

                    FeljegyzesData.getInstance().loadIsmerosok(FeljegyzesData.felhasznalo);

                    vbMeglevoUjratolt();
                }
            });
        }
    }

    public void tfKeresBetolt(String newValue) {
        vbKeres.getChildren().clear();
        if (!newValue.equals("")) {
            ObservableList<Felhasznalo> keresett = FeljegyzesData.db.felhasznKeresList(FeljegyzesData.felhasznalo, newValue);
            for (Felhasznalo f : keresett) {

                ismerosKElem elem = new ismerosKElem(f);
                vbKeres.getChildren().add(elem);
                elem.button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        FeljegyzesData.db.addIsmeros(FeljegyzesData.felhasznalo, f);
                        FeljegyzesData.getInstance().loadIsmerosok(FeljegyzesData.felhasznalo);
                        vbMeglevo.getChildren().clear();
                        for (Felhasznalo f : FeljegyzesData.getInstance().getIsmerosok()) {
                            ismerosElem ie = new ismerosElem(f);
                            vbMeglevo.getChildren().add(ie);
                            vbKeres.getChildren().clear();
                        }
                    }
                });

            }
        }
    }

    SimpleDateFormat smpf = new SimpleDateFormat("yyyy-MM-dd");

    public void peldaadat() {

        fListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Feljegyzes>() {
            @Override
            public void changed(ObservableValue<? extends Feljegyzes> observable, Feljegyzes oldValue, Feljegyzes newValue) {
                if (newValue != null) {
                    Feljegyzes item = fListView.getSelectionModel().getSelectedItem();
                    if (!item.getHatarIdo().equals("")) {

                        String[] t = item.getHatarIdo().split(";");
                        hataridobox.setVisible(true);
                        hataridoLabel.setText(t[0] + "\t(" + t[1] + ")");
                        fTextArea.setText(item.getSzoveg());
                        datumLabel.setText(item.getDatum());
                    } else {
                        hataridobox.setVisible(false);
                        fTextArea.setText(item.getSzoveg());
                        datumLabel.setText(item.getDatum());
                    }
                }
            }
        });

        fListView.getItems().setAll(FeljegyzesData.getInstance().getFeljegyzesek());
        fListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fListView.getSelectionModel().selectFirst();

    }

    public void mindenbetolt() {
        minNaptar.pluszminusz = 0;
        mn = new minNaptar(minP);
        mn.honapNev(honapLabel);
        datumkiir.setText(mn.mainap());

        FeljegyzesData.getInstance().loadTodoItems(FeljegyzesData.felhasznalo, FeljegyzesData.db);
        FeljegyzesData.getInstance().loadIsmerosok(FeljegyzesData.felhasznalo);
        FeljegyzesData.getInstance().loadTablaelemek(new Date());

        System.out.println("betöltött");
        peldaadat();
        betolt();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        FeljegyzesData.db = new DB();

//        if(FeljegyzesData.felhasznalo!=null){
//        jelenFelhasznalo= new Felhasznalo("bence","bence","bence","1998-04-19",""); 
//       
//       FeljegyzesData.db.addFelhasznalo(jelenFelhasznalo);//teszt
//        try {     teszt
//            for (int i = 0; i < feljegyzesek.size(); i++) {
//            db.addFeljegyzes(jelenFelhasznalo, feljegyzesek.get(i));
//                System.out.println(i+"feljegyzés ok");
//        }
//        } catch (Exception e) {
//            System.out.println("nem jött össze a feltöltés\n"+e);
//        }
//        
//        for (Feljegyzes f : feljegyzesek) {
//            System.out.println(f.getSzoveg());
//        }
//        for (Feljegyzes s : FeljegyzesData.getInstance().getFeljegyzesek()) {
//            System.out.println(s.getCim()+s.getId()+s.getSzoveg()+s.getDatum()+s.getHatarIdo()+s.getLathatosag());
//        }
        //ismerősök kilistázása
    }

}
