/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 */
package javafxTest;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

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
            Scene scene = new Scene(page,1200,800, Color.GREY);
            primaryStage.setScene(scene);
            primaryStage.setTitle("MiniMetro test");
            primaryStage.show();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}