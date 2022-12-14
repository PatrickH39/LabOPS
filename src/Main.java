import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("fxml/Home.fxml")); // Loads the FXML file
        root.getStylesheets().add("/style.css"); // Loads global CSS file

        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/icon.png"))); // Adds an icon to the window
        primaryStage.setTitle("LabOPS"); // Adds the title to the window
        primaryStage.setScene(new Scene(root, 900, 660)); // Set window size

        //primaryStage.setResizable(false); // Disallow resizing of window

        primaryStage.show();
        root.requestFocus(); // Do not highlight the input box
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Platform.exit();
                System.exit(0);

            }
        });
    }
    public static void main(String[] args) {
        launch(args);
    }
}