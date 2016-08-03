package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @FXML
    ImageView background;

    public static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();


        Controller controller = (Controller) loader.getController();
        controller.setMainApplication(primaryStage);

        primaryStage.setTitle("Hello World");
        primaryStage.initStyle(StageStyle.TRANSPARENT);

        Scene scene = new Scene(root, 500, 367);
        scene.setFill(null);
        //scene.getStylesheets().add("assets/css/styles.css");
        primaryStage.setScene(scene);

        primaryStage.show();

        this.stage = primaryStage;

    }


    public static void main(String[] args) {
        launch(args);
    }
}
