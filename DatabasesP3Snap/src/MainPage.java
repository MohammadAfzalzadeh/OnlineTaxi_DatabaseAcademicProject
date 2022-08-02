import Models.Driver;
import Models.Passenger;
import Models.UserType;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class MainPage extends Application {
    @FXML private Text actiontarget;
    @FXML private TextField userName;
    @FXML private PasswordField passwordField;
    private static Database db;
    private static Stage stage;

    public static void main(String[] args) {
        db = new Database();

        launch(args);
    }

    public static void showIt() throws IOException {
        Parent root = FXMLLoader.load(MainPage.class.getResource("MainPage.fxml"));
        Scene scene = new Scene(root , 350 , 250);
        stage.setTitle("تاکسی آنلاین");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void start(Stage stage) throws IOException {
        MainPage.stage = stage;
        showIt();
    }

    @FXML public void handleSubmitButtonAction(ActionEvent actionEvent) {
       actiontarget.setText("");
        int PersonId =  db.isUserValid(userName.getText() ,  passwordField.getText());
        actiontarget.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
        actiontarget.setFill(Color.RED);

        Passenger p = new Passenger(null , null , 1 , null ,  1);
        p.setId(PersonId);

        if (PersonId == -1) {
            actiontarget.setText("شماره تلفن یا رمز عیور اشتباه است !");
            userName.setText("");
            passwordField.setText("");
        }
        else {
            UserType userType = db.findTypeOfUser(PersonId);
            if ( userType == UserType.Passenger)
                PassengerMainPage.showIt( p , db , stage);
            else if (userType == UserType.Driver)
                DriverMainPage.showIt(new Driver(p.getId()) , db , stage);
        }
    }

    public void addPass(ActionEvent actionEvent) {
        AddPassenger.showIt(db , stage);
    }
}
