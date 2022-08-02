import Models.Driver;
import Models.Trip;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class DriverMainPage {
    private static Driver driver;
    private static Database db;
    private static  Trip t;

    @FXML private  Tab tab_Search;
    @FXML private  Tab tab_finishTrip;
    @FXML private  VBox tripAccessPane;
    @FXML private  VBox TripsPane;
    @FXML private HBox Runing;
    @FXML private Text msg;

    public static void showIt(Driver d, Database db, Stage stage) {
        DriverMainPage.driver = d;
        DriverMainPage.db = db;

        try {
            Parent root = FXMLLoader.load(PassengerMainPage.class.getResource("DriverMainPage.fxml"));
            Scene scene = new Scene(root );
            stage.setTitle("تاکسی آنلاین");
            stage.setScene(scene);
            stage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tabChange(Event event) {
        if (tab_Search != null && tab_Search.isSelected())
            refresh(null);

    }

    public void refresh(ActionEvent actionEvent) {
        tripAccessPane.getChildren().clear();
        List<Trip> trips =  db.getAllTripDosentHaveDriver() ;
        for ( Trip t : trips ) {
            Button btn = new Button( t.toString() );
            btn.setAlignment(Pos.CENTER);
            tripAccessPane.getChildren().add(btn);

            btn.setOnAction(e -> {
                db.reservingTrip(t.getTripId() , driver.getId());

                DriverMainPage.t = t;

                Runing.setVisible(true);
                msg.setText( "شما در سفری با مشخصات زیر هستید : "  + "\n" + t.toString() );

            });
        }
    }

    public void finish(ActionEvent actionEvent) {
        Runing.setVisible(false);
        db.finishTrip(t.getTripId());
        refresh(null);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("پایان سفر");
        alert.showAndWait();

    }

    public void tabChangeTrip(Event event) {
        if (tab_finishTrip.isSelected()){
            TripsPane.getChildren().clear();
            List <Trip> ts = db.getAllFinishedTripsOfDriver(driver.getId()) ;
            for ( Trip t : ts ) {
                Button btn = new Button( t.toString() );
                btn.setAlignment(Pos.CENTER);
                TripsPane.getChildren().add(btn);

                btn.setOnAction(e ->{
                    if (t.getDriverRate() == 0){
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

                            db.addDriverRate(t.getTripId() , Integer.valueOf(selected));
                            tabChangeTrip(null);
                        }


                    }
                });
            }
        }
    }
}
