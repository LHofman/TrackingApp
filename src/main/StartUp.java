package main;

import gui.WelcomePageController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The StartUp class of this project
 */
public class StartUp extends Application{
    
    public static void main(String[] args) {
        Application.launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        WelcomePageController welcomePage = new WelcomePageController();
        Scene mainScene = new Scene(welcomePage);
        primaryStage.setScene(mainScene);
        primaryStage.setMinWidth(625.0);
        primaryStage.setMinHeight(500.0);
        primaryStage.show();
    }
    
}
