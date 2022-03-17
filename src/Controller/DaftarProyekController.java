/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

/**
 *
 * @author muham
 */
public class DaftarProyekController implements Initializable {
    
    @FXML
    private Label label;
    @FXML
    private Button button;
    @FXML
    private TextField tfSearchProyek;
    @FXML
    private ComboBox<?> cbLokasiProyek;
    @FXML
    private TextField cbTahun;
    @FXML
    private MenuItem cmEdit;
    @FXML
    private MenuItem cmBagikan;
    @FXML
    private MenuItem cmSelesai;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me! awikwok");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void sendToEdit(ActionEvent event) {
        System.out.println("Controller.DaftarProyekController.sendToEdit()");
    }

    @FXML
    private void sendToShare(ActionEvent event) {
        System.out.println("Controller.DaftarProyekController.sendToShare()");
    }

    @FXML
    private void sendToSelesai(ActionEvent event) {
        System.out.println("Controller.DaftarProyekController.sendToSelesai()");
    }
    
}
