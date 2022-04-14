/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Database.DatabaseConnection;
import Database.DatabaseHelper;
import com.jfoenix.controls.JFXAutoCompletePopup;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXMasonryPane;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialicons.MaterialIcon;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

/**
 *
 * @author muham
 */
public class DaftarProyekController implements Initializable {
    

    @FXML
    private Button button;

    @FXML
    private ScrollPane scrollPane;

    private final JFXMasonryPane masonryPane = new JFXMasonryPane();
    @FXML
    private JFXTextField tfNamaProyek;
    @FXML
    private JFXTextField tfTahunProyek;
    @FXML
    private JFXComboBox<String> cbLokasiProyek;
    
    @FXML
    private AnchorPane apDaftarProyek;
    public AnchorPane home;
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        masonryPane.setPadding(new Insets(15, 15, 15, 15));
        masonryPane.setVSpacing(15);
        masonryPane.setHSpacing(15);
        masonryPane.setCellHeight(450);
        masonryPane.setCellWidth(300);
        cbLokasiProyek.setStyle("-fx-font-family: Quicksand; -fx-font-size: 14px");
        
        

        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setFitToWidth(true);
        scrollPane.setContent(masonryPane);
        
        
        loadImages();
    }    

    
    private void loadImages() {
        masonryPane.getChildren().clear();
        
        
        try {
            String sql = "SELECT DISTINCT * FROM tb_proyek";
            PreparedStatement stmt = DatabaseConnection.getInstance().getConnection().prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                InputStream imageFile = rs.getBinaryStream("image");
                cbLokasiProyek.getItems().addAll(rs.getString("lokasi_proyek"));
                
                
                if (imageFile != null) {
                    Image image = new Image(imageFile, 200, 200, true, true);
                    createTile(image, rs.getInt("id"), rs.getString("nama_proyek"), rs.getString("owner_proyek"), rs.getString("lokasi_proyek"), rs.getString("thn_proyek"));
                    
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaftarProyekController.class.getName()).log(Level.SEVERE, null, ex);
            showAlert(Alert.AlertType.ERROR, "Oops.", "Connection error to Mysql. Please check your connection.");
        }
    }
    
     
     
    private void showAlert(Alert.AlertType alertType, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(alertType.toString());
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    private void autoCompleteComboBox(){
        JFXAutoCompletePopup<String> autoCompletePopup = new JFXAutoCompletePopup<>();
        
        autoCompletePopup.getSuggestions().addAll(cbLokasiProyek.getItems());
        
        //SelectionHandler sets the value of the comboBox
        autoCompletePopup.setSelectionHandler(event -> {
            cbLokasiProyek.setValue(event.getObject());
        });
        

        TextField editor = cbLokasiProyek.getEditor();
        editor.textProperty().addListener(observable -> {
        //The filter method uses the Predicate to filter the Suggestions defined above
        //I choose to use the contains method while ignoring cases
        autoCompletePopup.filter(item -> item.toLowerCase().contains(editor.getText().toLowerCase()));
        //Hide the autocomplete popup if the filtered suggestions is empty or when the box's original popup is open
        if (autoCompletePopup.getFilteredSuggestions().isEmpty() || cbLokasiProyek.showingProperty().get()) {
            autoCompletePopup.hide();
        } 
        else {
            autoCompletePopup.show(editor);
        }
        });
        
    }
    
    private void createTile(Image image, int id, String name, String owner, String lokasi, String tahun) {
//        autoCompleteComboBox();
        new AutoCompleteComboBoxListener<>(cbLokasiProyek);
        
        //Icon Owner
        FontAwesomeIconView iconOwner = new FontAwesomeIconView(FontAwesomeIcon.USER);
        iconOwner.setSize("16");
        iconOwner.setStyle("-fx-icon-color: grey");
        
        //Icon Location
        FontAwesomeIconView iconLocation = new FontAwesomeIconView(FontAwesomeIcon.LOCATION_ARROW);
        iconLocation.setSize("16");
        
        //Icon Year
        FontAwesomeIconView iconYear = new FontAwesomeIconView(FontAwesomeIcon.CALENDAR);
        iconYear.setSize("16");
       
        ImageView iv = new ImageView(image);
        
        //HBox Container for the ImageView
        HBox boxImg = new HBox(iv);
        boxImg.setAlignment(Pos.CENTER);
        boxImg.setStyle("-fx-border-color: white");
        boxImg.setPrefHeight(iv.getFitHeight());
        boxImg.setPrefWidth(iv.getFitWidth());
        
        //ImageView Center Crop property
        
        iv.setFitWidth(boxImg.getWidth());
        iv.setFitHeight(boxImg.getHeight());
        iv.setPreserveRatio(true);
        iv.setStyle("-fx-background-radius: 10px; -fx-border-radius: 10px");
        iv.setSmooth(true);
        iv.setCache(true);
       
        iv.setFocusTraversable(false);
        
        double newMeasure = (iv.getImage().getWidth() < iv.getImage().getHeight()) ? iv.getImage().getWidth() : iv.getImage().getHeight();
        double x = (iv.getImage().getWidth() - newMeasure) / 2;
        double y = (iv.getImage().getHeight() - newMeasure) / 2;

        Rectangle2D rect = new Rectangle2D(x, y, newMeasure, newMeasure);
        iv.setViewport(rect);
        iv.setFitWidth(250);
        iv.setFitHeight(250);
        iv.setSmooth(true);
        

        //Label Image name
        Label label = new Label(name);
        label.setFont(new Font("System Bold", 18));
        label.setAlignment(Pos.CENTER);
        
        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0);
        ds.setOffsetX(3.0);

        ds.setColor(Color.GRAY);
        
        //HBox Project / image name
        HBox boxProject = new HBox(label);
        boxProject.setAlignment(Pos.CENTER);
        
      
        // Owner
        Label labelOwner = new Label(owner);
        labelOwner.setFont(new Font("System Bold", 14));
        labelOwner.setTextFill(Color.GREY);
        
        HBox boxOwner = new HBox();
        labelOwner.setPadding(new Insets(5,5,5,5));
        boxOwner.setAlignment(Pos.CENTER_LEFT);
        boxOwner.getChildren().addAll(iconOwner, labelOwner);
        
        //Location
        Label labelLocation = new Label(lokasi);
//        labelLocation.setText("location");
        labelLocation.setFont(new Font("System Bold", 14));
        labelLocation.setTextFill(Color.GREY);
        
        HBox boxLocation = new HBox();
        labelLocation.setPadding(new Insets(5,5,5,5));
        boxLocation.setAlignment(Pos.CENTER_LEFT);
        boxLocation.getChildren().addAll(iconLocation, labelLocation);
        
        //Year
        Label labelYear = new Label(tahun);
        labelYear.setFont(new Font("System Bold", 14));
        labelYear.setTextFill(Color.GREY);
        
        HBox boxYear = new HBox();
        labelYear.setPadding(new Insets(5,5,5,5));
        boxYear.setAlignment(Pos.CENTER_LEFT);
        boxYear.getChildren().addAll(iconYear, labelYear);
        
        
        VBox root = new VBox();
        root.setEffect(ds);
        root.setStyle("-fx-background-color: white; -fx-background-radius: 5px");
       
        root.setPrefSize(300, 430);
        root.setPadding(new Insets(25, 25, 25, 25));
        root.setAlignment(Pos.TOP_CENTER);
        root.setSpacing(5);
        root.getChildren().addAll(boxImg,boxProject,boxOwner, boxLocation, boxYear);
        

        MenuItem delete = new MenuItem("Delete");
        MenuItem update = new MenuItem("Update");
        

        ContextMenu menu = new ContextMenu(delete, update);
        menu.setAutoHide(true);
        menu.setAutoFix(true);
        menu.setConsumeAutoHidingEvents(true);
        menu.setHideOnEscape(true);
        
            
//        ctMenu.setAutoHide(true);
//        ctMenu.setAutoFix(true);
//        ctMenu.setConsumeAutoHidingEvents(true);
//        ctMenu.setHideOnEscape(true);
     

        delete.setOnAction(ev -> {
            boolean result = DatabaseHelper.deleteProject(id);
            if (result) {
                loadImages();
                showAlert(Alert.AlertType.INFORMATION, "Success, nice job.", "The file was successfully removed.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Oops.", "Connection error to Mysql. Please check your connection.");
            }
        });

        update.setOnAction(ev -> {
//            actionToUpdateImage(id);
              System.out.println("update");
        });
        
        root.setOnContextMenuRequested(ev -> {
            menu.show(root, ev.getScreenX(), ev.getScreenY());
        });

//        root.setOnMouseClicked(ev -> {
//            if (ev.getButton().equals(MouseButton.PRIMARY) && ev.getClickCount() == 2) {
//                Image img = DatabaseHelper.getImage(id);
//                Double height = img.getHeight();
//                Double witdh = img.getWidth();
//
//                Stage stage = new Stage();
//                stage.setTitle(name);
//
//                ImageView imageView = new ImageView(img);
//                imageView.setPreserveRatio(true);
//                imageView.setSmooth(true);
//                imageView.setCache(true);
//
//                AnchorPane anchorPane = new AnchorPane();
//                anchorPane.getChildren().add(imageView);
//
//                if (height > 1000 && witdh > 600) {
//                    imageView.setFitHeight(height / 2);
//                    imageView.setFitWidth(witdh / 2);
//
//                    Scene scene = new Scene(anchorPane);
//                    stage.setScene(scene);
//                    stage.setHeight(imageView.getFitHeight());
//                    stage.setWidth(imageView.getFitWidth());
//                } else {
//                    Scene scene = new Scene(anchorPane);
//                    stage.setScene(scene);
//                    stage.setWidth(witdh);
//                    stage.setHeight(height);
//                }
//                stage.show();
//            }
//        });

        masonryPane.getChildren().addAll(root);
    }
    
    public void setNode(Node node){
        apDaftarProyek.getChildren().clear();
        apDaftarProyek.getChildren().add((Node) node);
        
        AnchorPane.setTopAnchor(node, 0.0);
        AnchorPane.setRightAnchor(node, 0.0);
        AnchorPane.setLeftAnchor(node, 0.0);
        AnchorPane.setBottomAnchor(node, 0.0);
        
        apDaftarProyek.getChildren().setAll(node);
        
        
        FadeTransition ft = new FadeTransition(Duration.millis(500));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }
    
   

    @FXML
    private void navigateToTambahProject(ActionEvent event) {
        try {
            home = FXMLLoader.load(getClass().getResource("/View/TambahProyek.fxml"));
            setNode(home);
//            home.setPrefHeight(blankpage.getHeight());
//            home.setPrefWidth(blankpage.getWidth());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void autoCompleteCbLokasi(ActionEvent event) {
//        autoCompleteComboBox();
        

    }

    

   
    
}
