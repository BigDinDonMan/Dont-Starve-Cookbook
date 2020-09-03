package dontstarvecookbook.core;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.InputStream;

public class DontStarveCookbookMain extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent p = FXMLLoader.load(getClass().getResource("/dontstarvecookbook/fxml/mainwindow.fxml"));
        Scene s = new Scene(p);
        try (InputStream is = getClass().getResourceAsStream("/images/appicon.png")) {
            stage.getIcons().add(new Image(is));
        }
        s.getStylesheets().add(getClass().getResource("/dontstarvecookbook/css/appstyle.css").toExternalForm());
        stage.setScene(s);
        stage.setResizable(false);
        stage.setTitle("Don't Starve Cookbook");
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
