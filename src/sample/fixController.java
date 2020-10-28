package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class fixController implements Initializable {
    @FXML
    private TextField tfFixWord;
    @FXML
    private TextField tfFixMean;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void fixbackBut(ActionEvent event) throws IOException {
        String fixw = tfFixWord.getText();
        String fixm = tfFixMean.getText();
        if (Controller.dictionary.containsKey(fixw)) {
            Controller.dictionary.replace(fixw.trim(), fixm);
            Controller.writeChanges();
            System.out.println("fixed");
            goBack(event);
        } else {
                goBack(event);
        }
    }

    public void goBack(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("sample.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
    }
}
