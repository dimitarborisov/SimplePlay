package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;



public class Controller implements Initializable{

    @FXML private StackPane myStackPane;
    @FXML ImageView background;
    @FXML Button play;
    @FXML Button prev;
    @FXML Button next;
    @FXML Slider timeSlider;
    @FXML Label songName;
    @FXML MenuButton menuButton;
    @FXML Button x;
    @FXML MenuItem menuItem;


    private final FileChooser fileChooser = new FileChooser();
    private MusicPlayer musicPlayer;
    private Image pauseImage;
    private Image playImage;
    private ThreadSlider rSlider;
    private File currentSong;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        myStackPane.setStyle("-fx-background-color: transparent;");
        //currentSong = new File(Main.class.getResource("/Spring_In_My_Step.mp3").toString());

        musicPlayer = new MusicPlayer();

        rSlider = new ThreadSlider(timeSlider, musicPlayer);
        rSlider.setUpdate(false);
        rSlider.start();

        pauseImage = new Image(getClass().getResourceAsStream("/sprites/pause.png"));
        playImage = new Image(getClass().getResourceAsStream("/sprites/play.png"));

    }

    public void startPlay(double time){
        musicPlayer.play();
        if(time > 0){
            musicPlayer.seek(time);
        }
        rSlider.setUpdate(true);
        play.setGraphic(new ImageView(pauseImage));

    }

    public void pausePlay(){
        musicPlayer.pause();
        play.setGraphic(new ImageView(playImage));
    }

    public void setMainApplication(Stage stage) {

        menuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                File file = fileChooser.showOpenDialog(stage);
                if (file != null) {
                    currentSong = file;
                    songName.setText(currentSong.getName());
                    try {
                        musicPlayer.load(currentSong.toURI().toURL().toExternalForm().toString());
                    } catch (MalformedURLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        menuButton.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ColorAdjust brightLight = new ColorAdjust(0, 0, +0.60, +0.60);
                ImageView image = (ImageView) menuButton.getGraphic();
                image.setEffect(brightLight);
                menuButton.setGraphic(image);
            }
        });

        menuButton.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ColorAdjust brightLight = new ColorAdjust(0, 0, 0, 0);
                ImageView image = (ImageView) menuButton.getGraphic();
                image.setEffect(brightLight);
                menuButton.setGraphic(image);
            }
        });


        x.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                rSlider.setRunning(false);
                Platform.exit();
            }
        });

        x.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ColorAdjust brightLight = new ColorAdjust(0, 0, +0.60, +0.60);
                ImageView image = (ImageView) x.getGraphic();
                image.setEffect(brightLight);
                x.setGraphic(image);
            }
        });

        x.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ColorAdjust brightLight = new ColorAdjust(0, 0, 0, 0);
                ImageView image = (ImageView) x.getGraphic();
                image.setEffect(brightLight);
                x.setGraphic(image);
            }
        });

        timeSlider.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                rSlider.setUpdate(false);
            }
        });

        timeSlider.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                startPlay((timeSlider.getValue() * musicPlayer.getDuartion()) / 100);
            }
        });

        play.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ColorAdjust brightLight = new ColorAdjust(0, 0, -0.10, -0.10);
                ImageView image = (ImageView) play.getGraphic();
                image.setEffect(brightLight);
                play.setGraphic(image);
            }
        });

        play.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(musicPlayer.isPlaying()){
                    pausePlay();
                }else{
                    startPlay(0);
                }
            }
        });



        // allow the player background to be used to drag the clock around.
        Delta dragDelta = new Delta();
        background.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {
                // record a delta distance for the drag and drop operation.
                dragDelta.x = stage.getX() - mouseEvent.getScreenX();
                dragDelta.y = stage.getY() - mouseEvent.getScreenY();
            }
        });

        background.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {
                stage.setX(mouseEvent.getScreenX() + dragDelta.x);
                stage.setY(mouseEvent.getScreenY() + dragDelta.y);
            }
        });

        next.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {
                    }
                });

    }

    class Delta { double x, y; }

}
