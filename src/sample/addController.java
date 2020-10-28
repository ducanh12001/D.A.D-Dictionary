package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class addController implements Initializable {
    @FXML
    private TextField tfWord;
    @FXML
    private TextField tfMean;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void addbackBut(ActionEvent event) throws IOException {
        String nw = tfWord.getText();
        String nm = tfMean.getText();

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Warning");
        alert.setHeaderText("This word is already had.");

        Alert alert1 = new Alert(AlertType.CONFIRMATION);
        alert1.setTitle("Warning");
        alert1.setHeaderText("Please add word with mean!");

        Alert alert2 = new Alert(AlertType.CONFIRMATION);
        alert2.setTitle("Notification");
        alert2.setHeaderText(nw + " is successfully added to the dictionary!");

        if(nw != null  && !nw.equals("") && nm != null && !nm.equals("") && !Controller.dictionary.containsKey(nw)) {
            Controller.dictionary.put(nw, nm);
            String sentence = nw + "\t" + nm + "\n";
            try {
                BufferedWriter out = new BufferedWriter(new FileWriter("dictionaries.txt", true));
                out.write(sentence);
                out.close();
            }
            catch (IOException e) {
                System.out.println(e);
            }
            alert2.showAndWait();
            goBack(event);
        } else if(nw.equals("") || nm.equals("")){
            alert1.showAndWait();
        } else if(Controller.dictionary.containsKey(nw)) {
            alert.showAndWait();
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
