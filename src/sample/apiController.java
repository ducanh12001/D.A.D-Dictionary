package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ResourceBundle;

public class apiController implements Initializable {
    @FXML
    public RadioMenuItem ev;
    @FXML
    private TextArea apiText;
    @FXML
    private TextArea apiMean;

    public void apiTrans(ActionEvent event) throws IOException {
        String sent = apiText.getText();
        if(ev.isSelected()) {
            apiMean.setText(textTranslate("en", "vi", sent));
        } else {
            apiMean.setText(textTranslate("vi", "en", sent));
        }
    }

    @FXML
    public void goBack(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("sample.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public String textTranslate(String langFrom, String langTo, String text) throws IOException {
        String link = "https://script.google.com/macros/s/AKfycbw2qKkvobro8WLNZUKi2kGwGwEO4W8cBavcKqcuCIGhGBBtVts/exec" +
                "?q=" + URLEncoder.encode(text, "UTF-8")
                + "&target=" + langTo +
                "&source=" + langFrom;
        URL url = new URL(link);
        StringBuilder textTrans = new StringBuilder();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            textTrans.append(inputLine);
        }
        in.close();
        return textTrans.toString();
    }
}
