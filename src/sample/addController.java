package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
        if(nw != null  && !nw.equals("") && nm != null && !nm.equals("") && !Controller.dictionary.containsKey(nw)) {
            Controller.dictionary.put(nw, nm);
            String sentence = nw + "\t" + nm + "\n";
            try {
                BufferedWriter out = new BufferedWriter(new FileWriter("dictionaries.txt", true));
                out.write(sentence);
                out.close();
                System.out.println(nw + " successfully added to the dictionary");
            }
            catch (IOException e) {
                System.out.println(e);
            }
            Controller.sortFile();
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