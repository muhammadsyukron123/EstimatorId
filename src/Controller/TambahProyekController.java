/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Database.DatabaseConnection;
import Database.DatabaseHelper;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author muham
 */
public class TambahProyekController implements Initializable {

    @FXML
    private ImageView ivFotoProyek;
    @FXML
    private JFXButton btCropFoto;
    @FXML
    private JFXButton btHapusFoto;
    @FXML
    private JFXTextField tfNamaProyek;
    private JFXComboBox<String> cbLokasiProyek;
    @FXML
    private JFXTextField tfPemilikProyek;
    @FXML
    private JFXTextField tfJasaKontraktor;
    @FXML
    private JFXTextArea taKeterangan;
    @FXML
    private JFXButton btTambahDokumen;
    @FXML
    private JFXTextField tfPajak;
    @FXML
    private JFXButton btSimpan;
    @FXML
    private JFXButton btBatal;
    @FXML
    private AnchorPane apTambahProyek;
    public AnchorPane home;
    @FXML
    private JFXListView<String> listDokumen;
    
    private static final long LIMIT = 4194300;
    
    public File fImg;
    @FXML
    private JFXTextField tfLokasiProyek;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("Input Required");
        
        tfJasaKontraktor.getValidators().add(validator);
        tfJasaKontraktor.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal) tfJasaKontraktor.validate();
        });
        
        tfNamaProyek.getValidators().add(validator);
        tfNamaProyek.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal) tfNamaProyek.validate();
        });
        
        tfPajak.getValidators().add(validator);
        tfPajak.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal) tfPajak.validate();
        });
        
        tfPemilikProyek.getValidators().add(validator);
        tfPemilikProyek.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal) tfPemilikProyek.validate();
        });
        
        tfLokasiProyek.getValidators().add(validator);
        tfLokasiProyek.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal) tfLokasiProyek.validate();
        });
        
        defaultImage();
        
        //method to add data proyek
        
        
        
    }    
    
    public void setNode(Node node){
        apTambahProyek.getChildren().clear();
        apTambahProyek.getChildren().add((Node) node);
        
        AnchorPane.setTopAnchor(node, 0.0);
        AnchorPane.setRightAnchor(node, 0.0);
        AnchorPane.setLeftAnchor(node, 0.0);
        AnchorPane.setBottomAnchor(node, 0.0);
        
        apTambahProyek.getChildren().setAll(node);

        FadeTransition ft = new FadeTransition(Duration.millis(500));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }
    
    public void backToPreviousScene(){
        try {
            home = FXMLLoader.load(getClass().getResource("/View/DaftarProyek.fxml"));
            setNode(home);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private Stage getStage() {
        return (Stage) ivFotoProyek.getScene().getWindow();
    }
    
    private Stage getStageDokumen(){
        return (Stage) btTambahDokumen.getScene().getWindow();
    }
    
    private File getImageSelected() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image files (*.jpg, *.jpeg *.png, *.gif)", "*.JPG", "*.JPEG" , "*.PNG", "*.GIF" );
        fileChooser.getExtensionFilters().addAll(extFilter);
        fileChooser.setTitle("Select an image");

        File selectedImage = fileChooser.showOpenDialog(getStage());
        fImg = selectedImage;
        
        return selectedImage;
    }
    
//    private void getImageFile(){
//        try {
//            fis = new FileInputStream(getImageSelected());
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(TambahProyekController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
    private File getDocumentSelected() {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Select Document");

        File selectedDocument = fileChooser.showOpenDialog(getStageDokumen());
        
        getFile(selectedDocument);
        
        return selectedDocument;
    }
    
    private void showImageToIv(File photo){
        Image imagefoto = new Image(fImg.toURI().toString());
        ivFotoProyek.setImage(imagefoto);
        ivFotoProyek.setFitWidth(320);
        ivFotoProyek.setFitHeight(320);
        ivFotoProyek.setPreserveRatio(true);
        ivFotoProyek.setSmooth(true);
        ivFotoProyek.setCache(true);
        ivFotoProyek.setFocusTraversable(false);
        
        
        double newMeasure = (ivFotoProyek.getImage().getWidth() < ivFotoProyek.getImage().getHeight()) ? ivFotoProyek.getImage().getWidth() : ivFotoProyek.getImage().getHeight();
        double x = (ivFotoProyek.getImage().getWidth() - newMeasure) / 2;
        double y = (ivFotoProyek.getImage().getHeight() - newMeasure) / 2;

        Rectangle2D rect = new Rectangle2D(x, y, newMeasure, newMeasure);
        ivFotoProyek.setViewport(rect);
        ivFotoProyek.setFitWidth(320);
        ivFotoProyek.setFitHeight(320);
        ivFotoProyek.setSmooth(true);
        
        FadeTransition ft = new FadeTransition(Duration.millis(500));
        ft.setNode(ivFotoProyek);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
        
        
    }
    
    private void defaultImage(){
        Image defaultImages = new Image("assets/emptyimage.jpeg");
        ivFotoProyek.setImage(defaultImages);
        ivFotoProyek.setFitWidth(320);
        ivFotoProyek.setFitHeight(320);
        ivFotoProyek.setPreserveRatio(true);
        ivFotoProyek.setSmooth(true);
        ivFotoProyek.setCache(true);
        ivFotoProyek.setFocusTraversable(false);
        
        double newMeasure = (ivFotoProyek.getImage().getWidth() < ivFotoProyek.getImage().getHeight()) ? ivFotoProyek.getImage().getWidth() : ivFotoProyek.getImage().getHeight();
        double x = (ivFotoProyek.getImage().getWidth() - newMeasure) / 2;
        double y = (ivFotoProyek.getImage().getHeight() - newMeasure) / 2;

        Rectangle2D rect = new Rectangle2D(x, y, newMeasure, newMeasure);
        ivFotoProyek.setViewport(rect);
        ivFotoProyek.setFitWidth(320);
        ivFotoProyek.setFitHeight(320);
        ivFotoProyek.setSmooth(true);
        
        FadeTransition ft = new FadeTransition(Duration.millis(500));
        ft.setNode(ivFotoProyek);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
        
    }
    
    private void getFile(File document){
        String fileName = document.getName();
        listDokumen.getItems().add(fileName);
        
    }
    
    
    private void showAlert(Alert.AlertType alertType, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(alertType.toString());
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
   
    
    @FXML
    private void addFotoProyek(MouseEvent event) {
//        getImageSelected();
        showImageToIv(getImageSelected());

    }
    
    private void validasiTextField(){
        if(tfJasaKontraktor.getText().trim().isEmpty()){
            System.out.println("tfKontraktor isEmpty");
        }
        else if(tfNamaProyek.getText().trim().isEmpty()){
            System.out.println("tfNamaProyek isEmpty");
        }
        else if(tfPajak.getText().trim().isEmpty()){
            System.out.println("tfPajak isEmpty");
        }
        else if(tfPemilikProyek.getText().trim().isEmpty()){
            System.out.println("tfPemilikProyek isEmpty");
        }
        else if (tfLokasiProyek.getText().trim().isEmpty()){
            System.out.println("tfPemilikProyek isEmpty");
        }
    }
    
    private void simpanDokumen(){
            
//            String namaProyek = tfNamaProyek.getText();
//            String lokasiProyek = cbLokasiProyek.getValue();
//            String ownerProyek = tfPemilikProyek.getText();
//
//            String persenPajakString = tfPajak.getText();
//            Double persenPajak = Double.valueOf(persenPajakString);
//            String persenKontraktorString = tfJasaKontraktor.getText();
//            Double persenKontraktor = Double.valueOf(persenKontraktorString);
////            File imageSelected = fImg;
//            
////            DatabaseHelper.insertNewProject(namaProyek, ownerProyek, lokasiProyek, persenKontraktor, persenPajak, fImg);
//            try{
//                FileInputStream image = new FileInputStream(fImg);
//                String sql = "INSERT INTO tb_proyek (nama_proyek, owner_proyek, lokasi_proyek, persen_kontraktor, persen_pajak, image) VALUES (?, ?, ?, ?, ?, ?)";
//                PreparedStatement stmt = DatabaseConnection.getInstance().getConnection().prepareStatement(sql);
//                stmt.setString(1, namaProyek);
//                stmt.setString(2, ownerProyek);
//                stmt.setString(3, lokasiProyek);
//                stmt.setDouble(4, persenKontraktor);
//                stmt.setDouble(5, persenPajak);
//                stmt.setBlob(6, image);
//            
//            }catch(SQLException | FileNotFoundException ex){
//                Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
//            }
            
//            backToPreviousScene();

//            boolean result = 
//            if (result) {
//                    showAlert(Alert.AlertType.INFORMATION, "Success, nice job.", "The file was successfully added.");
//            } 
//            else {
//                    showAlert(Alert.AlertType.ERROR, "Oops.", "Connection error to Mysql. Please check your connection.");
//            }
//            if (imageSelected != null) {
//                long imgLength = imageSelected.length();
//                if (imgLength > LIMIT) {
//                    showAlert(Alert.AlertType.ERROR, "Oops.", "This image exceeds the weight limit to save. "
//                            + "Select another image.\n" + imgLength + " bytes > " + LIMIT + " bytes");
//                } else {
//                    boolean result = DatabaseHelper.insertNewProject(namaProyek, ownerProyek, lokasiProyek, persenKontraktor, persenPajak, imageSelected);
//                    if (result) {
//                        showAlert(Alert.AlertType.INFORMATION, "Success, nice job.", "The file was successfully added.");
//                    } else {
//                        showAlert(Alert.AlertType.ERROR, "Oops.", "Connection error to Mysql. Please check your connection.");
//                    }
//                }
               
                
//            } else {
//                showAlert(Alert.AlertType.ERROR, "Oops.", "No file has been selected.");
//            }
    }
    
    private void simpanDanLanjutkan(ActionEvent event) {
       
        
    }

    @FXML
    private void backToDaftarProyek(ActionEvent event) {
        backToPreviousScene();
    }

    @FXML
    private void addDocument(ActionEvent event) {
        getDocumentSelected();
        
    }

    @FXML
    private void cropFoto(ActionEvent event) {
    }

    @FXML
    private void hapusFoto(ActionEvent event) {
        defaultImage();
    }

    @FXML
    private void inputValidatorJasaKontraktor(ActionEvent event) {
    }

    @FXML
    private void inputValidatorPajak(ActionEvent event) {
    }

    @FXML
    private void simpanDanLanjutkan(MouseEvent event) {
//        validasiTextField();
//        simpanDokumen();
         String namaProyek = tfNamaProyek.getText();
            String lokasiProyek = tfLokasiProyek.getText();
            String ownerProyek = tfPemilikProyek.getText();

            String persenPajakString = tfPajak.getText();
            Double persenPajak = Double.valueOf(persenPajakString);
            String persenKontraktorString = tfJasaKontraktor.getText();
            Double persenKontraktor = Double.valueOf(persenKontraktorString);
//            File imageSelected = fImg;
            
            DatabaseHelper.insertNewProject(namaProyek, ownerProyek, lokasiProyek, persenKontraktor, persenPajak, fImg);
            backToPreviousScene();
//            try{
//                FileInputStream image = new FileInputStream(fImg);
//                String sql = "INSERT INTO tb_proyek (nama_proyek, owner_proyek, lokasi_proyek, persen_kontraktor, persen_pajak, image) VALUES (?, ?, ?, ?, ?, ?)";
//                PreparedStatement stmt = DatabaseConnection.getInstance().getConnection().prepareStatement(sql);
//                stmt.setString(1, namaProyek);
//                stmt.setString(2, ownerProyek);
//                stmt.setString(3, lokasiProyek);
//                stmt.setDouble(4, persenKontraktor);
//                stmt.setDouble(5, persenPajak);
//                stmt.setBlob(6, image);
//            
//            }catch(SQLException | FileNotFoundException ex){
//                Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
//            }

    }

    @FXML
    private void simpanProyek(ActionEvent event) {
        simpanDokumen();
    }
    
    
    
}
