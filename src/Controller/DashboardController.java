/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author muham
 */
public class DashboardController implements Initializable {

    @FXML
    private JFXButton btnDashboard;
    @FXML
    private JFXButton btnDaftarProyek;
    @FXML
    private JFXButton btnLogout;
    @FXML
    private AnchorPane sidebar;
    @FXML
    private AnchorPane blankpage;
    public AnchorPane home;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        createPage();
    }    
    
    public void createPage() {
        try {
            home = FXMLLoader.load(getClass().getResource("/View/Home.fxml"));
            setNode(home);
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void navigateToDaftarProject(ActionEvent event) {
        try {
            home = FXMLLoader.load(getClass().getResource("/View/DaftarProyek.fxml"));
            setNode(home);
//            home.setPrefHeight(blankpage.getHeight());
//            home.setPrefWidth(blankpage.getWidth());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void setNode(Node node){
        blankpage.getChildren().clear();
        blankpage.getChildren().add((Node) node);
        
        AnchorPane.setTopAnchor(node, 0.0);
        AnchorPane.setRightAnchor(node, 0.0);
        AnchorPane.setLeftAnchor(node, 0.0);
        AnchorPane.setBottomAnchor(node, 0.0);
        
        blankpage.getChildren().setAll(node);
        
        
        FadeTransition ft = new FadeTransition(Duration.millis(500));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }

    @FXML
    private void navigateToHomePage(ActionEvent event) {
        try {
            home = FXMLLoader.load(getClass().getResource("/View/Home.fxml"));
            setNode(home);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void navigateToLogout(ActionEvent event) {
        System.out.println("Logout");
        System.exit(0);
    }
    
    
    
}
