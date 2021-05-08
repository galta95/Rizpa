package App;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.net.URL;

public class App extends Application {

    public static void main(String[] args) throws JAXBException, FileNotFoundException {
        //StockMarketApi stockMarket = new StockMarket("");
        Thread.currentThread().setName("main");
        launch(App.class);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Rizpa Stock Exchange");
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = getClass().getResource("/App/app.fxml");
        fxmlLoader.setLocation(url);
        Parent root = fxmlLoader.load(url.openStream());

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
