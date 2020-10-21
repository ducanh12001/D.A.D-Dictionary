package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.*;
import java.net.URL;
import java.text.Collator;
import java.util.*;
import java.util.List;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    @FXML
    private TextField textField;
    @FXML
    private TextArea textArea;
    @FXML
    private ListView<String> listView;
    @FXML
    private Button heart;
    @FXML
    private MenuButton favList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.initializeWordList();
            listView.setOnMouseClicked(event -> {
                String searchedWord = listView.getSelectionModel().getSelectedItem();
                if (searchedWord != null && searchedWord.equals("") == false) {
                    System.out.println("Searched World: " + searchedWord);
                    String wordMeaning = dictionary.get(searchedWord);
                    textArea.setText(wordMeaning);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Doc file
    static Map<String, String> dictionary = new HashMap<String, String>();
    public void initializeWordList() throws IOException {
        sortFile();
        try {
            File file = new File("dictionaries.txt");
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                dictionary.put(line.substring(0, line.indexOf("\t")), line.substring(line.indexOf("\t") + 1));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        listView.getItems().addAll(dictionary.keySet());
    }

    //sap xep
    public static void sortFile() throws IOException {
        FileReader fileReader = new FileReader("dictionaries.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        List<String> lines = new ArrayList<String>();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            lines.add(line);
        }
        bufferedReader.close();

        Collections.sort(lines, Collator.getInstance());

        FileWriter writer = new FileWriter("dictionaries.txt");
        for(String str: lines) {
            writer.write(str + "\r\n");
        }
        writer.close();
    }

    //nut Search
    @FXML
    public void doSearch(ActionEvent event) {
        String searchedWord = textField.getText();
        if(searchedWord.length() == 0) {
            textArea.setText("Please write down a word!");
        } else if (searchedWord.matches(".*\\d+.*")) {
            textArea.setText("Please don't include numbers!");
        } else if(searchedWord.contains(" ")) {
            textArea.setText("Please don't include spaces while searching!");
        } else if (searchedWord != null && searchedWord.equals("") == false) {
            System.out.println("Searched World: " + searchedWord);
            String wordMeaning = dictionary.get(searchedWord);
            textArea.setText(wordMeaning);
        }
    }

    //nut xoa tu
    @FXML
    public void delete(ActionEvent event) throws IOException {
        String chosenWord = listView.getSelectionModel().getSelectedItem();
        dictionary.remove(chosenWord.trim());
        writeChanges();
        System.out.println(chosenWord + " has been deleted from the dictionary");
    }

    public static String fileWriteContent(){
        String sentence = "";
        Iterator hmIterator = dictionary.entrySet().iterator();

        while (hmIterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry)hmIterator.next();
            String meaning = (String.valueOf(mapElement.getValue()));
            sentence += mapElement.getKey() + "\t" + meaning + "\r\n";
        }
        return sentence;
    }

    public static void writeChanges() throws IOException {
        try {
            FileWriter out = new FileWriter("dictionaries.txt");
            String sentence =  fileWriteContent();
            out.write(sentence);
            out.close();
        }
        catch (IOException e) {
            System.out.println("exception occoured" + e);
        }
    }

    //nut them tu
    @FXML
    public void addScene(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("addWord.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
    }


    //nut sua tu
    @FXML
    public void fixScene(ActionEvent event) throws IOException {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("fixWord.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            String wordFix = listView.getSelectionModel().getSelectedItem();
            stage.setScene(scene);
    }

    //but Heart
    @FXML
    public void heartClick(ActionEvent event) {
        listView.getSelectionModel().getSelectedItem();
        heart.setStyle("-fx-shape:  \"M 400 150 A 50 50 0 1 1 500 200 Q 450 250 400 300 L 300 200 A 50 50 0 1 1 400 150 \"; -fx-background-color: pink");

    }

    //menuList
    @FXML
    public void menuClick(ActionEvent event) {

    }
    @FXML
    public void doQuit(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    public void openList(ActionEvent event) {

    }

    @FXML void showGuide(ActionEvent event) {

    }

    @FXML void showInfor(ActionEvent event) {

    }
}
