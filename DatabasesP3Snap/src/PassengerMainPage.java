import Models.*;

import javafx.application.Platform;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


public class PassengerMainPage {

    private static Passenger passenger;
    private static Database db;
    private static Trip t;

    @FXML private TextField x_src;
    @FXML private TextField y_src;
    @FXML private TextField x_dst;
    @FXML private TextField y_dst;
    @FXML private TextField discountCode;
    @FXML private AnchorPane setTrip;
    @FXML private Button subTaxi;
    @FXML private Button search;
    @FXML private Button cancelBtn;
    @FXML private Text msg;

    @FXML private TextField x_sl;
    @FXML private TextField y_sl;
    @FXML private TextField name_sl;
    @FXML private TextField addr_sl;

    @FXML private Tab tab_SavedLoc;
    @FXML private Tab tab_finishTrip;
    @FXML private Tab main;

    @FXML private VBox savedLocationPane;
    @FXML private VBox TripsPane;

    @FXML private ComboBox<PaymentMethod> payment;
    @FXML private ComboBox<SavedLocation> comboMab;
    @FXML private ComboBox<SavedLocation> comboMaq;
    @FXML private HBox Runing;
    @FXML private CheckBox mabCh;
    @FXML private CheckBox maqCh;


    public static void showIt(Passenger passenger, Database db , Stage stage) {
        PassengerMainPage.passenger = passenger;
        PassengerMainPage.db = db;

        try {
            Parent root = FXMLLoader.load(PassengerMainPage.class.getResource("PassengerMainPage.fxml"));
            Scene scene = new Scene(root );
            stage.setTitle("تاکسی آنلاین");
            stage.setScene(scene);
            stage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void handleSubmitButtonAction(ActionEvent actionEvent) {


        Location src = null;
        SavedLocation srcSaved = null;

        Location dst = null;
        SavedLocation dstSaved = null;

        if (mabCh.isSelected())
            srcSaved = comboMab.getValue();
        else
            src = new Location(Double.valueOf(x_src.getText()) , Double.valueOf(y_src.getText()));

        if (maqCh.isSelected())
            dstSaved = comboMaq.getValue();
        else
            dst = new Location(Double.valueOf(x_dst.getText()) , Double.valueOf(y_dst.getText()));




        t = new Trip( passenger , payment.getValue() ,
                 src , dst
                 , srcSaved , dstSaved ,
                discountCode.getText().isEmpty() ? null : Integer.valueOf(discountCode.getText())
        );
        try {
        t.setTripId( db.addRequestTrip( t ) );

            Platform.runLater(new Runnable(){
                @Override
                public void run() {
                    Runing.setVisible(true);
                    disableAddTrip(true);
                }
            });

        }catch (IllegalArgumentException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("خطا در درج اطلاعات");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }


    public void showStatusOfTrip(){
       switch (db.showStatusTrip(t)){
           case 0:
               Platform.runLater(new Runnable(){
                   @Override
                   public void run() {
                       Runing.setVisible(true);
                       disableAddTrip(true);

                       msg.setFill(Color.CRIMSON);
                       msg.setText("در حال پیدا کردن سفیر");
                   }
               });
               break;
           case 1:
               t.setDriver( db.DriverAcceptedTrip(t.getTripId()) );
               Platform.runLater(new Runnable(){
                   @Override
                   public void run() {
                       Runing.setVisible(true);
                       disableAddTrip(true);
                       cancelBtn.setVisible(false);
                       msg.setVisible(true);
                       msg.setFill(Color.BLUE);
                       msg.setText("راننده با مشخصات زیر برای شما پیدا شد :" + "\n" + t.getDriver().toString());
                   }
               });
               break;
           case 2:
               Platform.runLater(new Runnable(){
                   @Override
                   public void run() {
                       Alert alert = new Alert(Alert.AlertType.INFORMATION);
                       alert.setTitle("پایان سفر");
                       alert.showAndWait();
                       Runing.setVisible(false);
                       disableAddTrip(false);
                   }
               });
               break;

       }
    }

    private void disableAddTrip(boolean b){
        x_src.setDisable(b);
        y_src.setDisable(b);

        x_dst.setDisable(b);
        y_dst.setDisable(b);

        discountCode.setDisable(b);

        payment.setDisable(b);

        subTaxi.setDisable(b);

        search.setVisible(b);
        
        
    }

    public void cansel(ActionEvent actionEvent) {
        db.cancelTrip(t.getTripId());

        Runing.setVisible(false);
        disableAddTrip(false);
    }


    public void tabChange(Event event) {
        if (tab_SavedLoc.isSelected()){
            savedLocationPane.getChildren().clear();
            passenger.setSavedLocations( db.getAllSavedLocationOfPassenger(passenger) );
            for ( SavedLocation sl : passenger.getSavedLocations() ) {
                Button btn = new Button( sl.toString() );
                btn.setAlignment(Pos.CENTER);
                savedLocationPane.getChildren().add(btn);
            }
        }
    }

    public void tabChangeTrip(Event event) {
        if (tab_finishTrip.isSelected()){
            TripsPane.getChildren().clear();
            List <Trip> ts = db.getAllFinishedTripsOfPassenger(passenger.getId()) ;
            for ( Trip t : ts ) {
                Button btn = new Button( t.toStringFromPassengers() );
                btn.setAlignment(Pos.CENTER);
                TripsPane.getChildren().add(btn);

                btn.setOnAction(e ->{
                    if (t.getPassengerRate() == 0){
                        Dialog dialog = new Dialog();
                        final String [] arrayData = {"1", "2", "3", "4" , "5", "6", "7", "8" , "9" , "10" };
                        List<String> dialogData;
                        dialogData = Arrays.asList(arrayData);

                        dialog = new ChoiceDialog(dialogData.get(0), dialogData);
                        dialog.setTitle("");
                        dialog.setHeaderText("Select your choice");

                        Optional<String> result = dialog.showAndWait();
                        String selected = "cancelled.";

                        if (result.isPresent()) {

                            selected = result.get();

                            db.addPassRate(t.getTripId() , Integer.valueOf(selected));
                            tabChangeTrip(null);
                        }


                    }
                });
            }
        }
    }

    public void addSavedLoc(ActionEvent actionEvent) {
        SavedLocation sl = new SavedLocation(passenger.getId(), name_sl.getText() ,
                new Location( Double.valueOf(x_sl.getText()) , Double.valueOf(y_sl.getText()) ) , addr_sl.getText() );

        db.addSavedLocationForPassenger(sl);

        tabChange(null);

    }


    public void tabChangeMain(Event event) {
        if (main.isSelected()){

            List<SavedLocation> savedLocations = db.getAllSavedLocationOfPassenger(passenger);
            for (SavedLocation sl: savedLocations)
                comboMab.getItems().add(sl);

            comboMaq.setItems(comboMab.getItems());

        }
    }
}
