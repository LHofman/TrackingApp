package main;

import domain.DomainController;
import domain.user.User;
import gui.GUIHelper;
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
        DomainController.setUser((User)DomainController.getInstance().getEntityFromId(User.class, 1));
        WelcomePageController welcomePage = new WelcomePageController();
        Scene mainScene = new Scene(welcomePage);
        primaryStage.setTitle("Tracking App");
        primaryStage.setScene(mainScene);
        primaryStage.setMinWidth(GUIHelper.STAGEWIDTH);
        primaryStage.setMinHeight(GUIHelper.STAGEHEIGHT);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    
}
