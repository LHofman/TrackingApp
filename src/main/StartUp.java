package main;

import domain.DomainController;
import javafx.application.Application;
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
        DomainController controller = new DomainController();
    }
    
}
