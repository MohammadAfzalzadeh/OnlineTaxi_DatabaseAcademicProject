
import Models.Passenger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AddPassenger {

    private static Database db;
    private static Stage primaryStage;

    @FXML private TextField name;
    @FXML private TextField phone;
    @FXML private ComboBox gender;
    @FXML private PasswordField pass;
    @FXML private TextField iden;

    public static void showIt( Database db , Stage stage) {

        primaryStage = stage;
        AddPassenger.db = db;

        try {
            Parent root = FXMLLoader.load(PassengerMainPage.class.getResource("AddPassenger.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("تاکسی آنلاین");
            primaryStage.setScene(scene);
            primaryStage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void submit(ActionEvent actionEvent) {
        try {
            db.addPassengers(new Passenger(phone.getText() , name.getText() , 1
                    , pass.getText() , iden.getText().equals("") ? -1 : Integer.valueOf(iden.getText()) ));

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(" درج اطلاعات ");
            alert.setContentText(" اطلاعات با موفقیت درج شد ");
            alert.showAndWait();
            MainPage.showIt();
        }catch (IllegalArgumentException ie){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("خطا در درج اطلاعات");
            alert.setContentText(ie.getMessage());
            alert.showAndWait();

            showIt(db , primaryStage );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void back(ActionEvent actionEvent) {
        try {
            MainPage.showIt();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
