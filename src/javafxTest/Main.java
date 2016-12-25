/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 */
package javafxTest;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(Main.class, (String[])null);
    }

    @Override
    public void start(Stage primaryStage) {
        try {

            Group page = (Group) FXMLLoader.load(Main.class.getResource("sample.fxml"));
            Color colorScene = new Color((double)240/255, (double)240/255,(double)240/255,0.5);
            Scene scene = new Scene(page,1200,600, colorScene);
            primaryStage.setScene(scene);
            primaryStage.setTitle("MiniMetro test");
            primaryStage.show();
            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    Platform.exit();
                }
            });
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}