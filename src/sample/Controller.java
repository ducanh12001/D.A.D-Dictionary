package sample;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
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
    private Button speakBut;

    private final ObservableList observableList = FXCollections.observableArrayList();
    private List<String> listKey = new ArrayList<String>();
    static Map<String, String> dictionary = new HashMap<String, String>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Image image = new Image(getClass().getResourceAsStream("/sample/sound.png"));
        speakBut.setGraphic(new ImageView(image));

        try {
            this.initializeWordList();
            listView.setOnMouseClicked(event -> {
                String searchedWord = listView.getSelectionModel().getSelectedItem();
                if (searchedWord != null && searchedWord.equals("") == false) {
                    String wordMeaning = dictionary.get(searchedWord);
                    textArea.setText(wordMeaning);
                    textField.setText(searchedWord);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Doc file
    public void initializeWordList() throws IOException {
        sortFile();
        File file = new File("dictionaries.txt");
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        while ((line = br.readLine()) != null) {
            dictionary.put(line.substring(0, line.indexOf("\t")), line.substring(line.indexOf("\t") + 1));
            listKey.add(line.substring(0, line.indexOf("\t")));
        }
        observableList.addAll(dictionary.keySet());
        SortedList<String> sortedList = new SortedList(observableList);
        listView.setItems(sortedList.sorted());
    }

    //sap xep file
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
        } else if (dictionary.containsKey(searchedWord.trim())) {
            String wordMeaning = dictionary.get(searchedWord);
            textArea.setText(wordMeaning);
        } else {
            textArea.setText("Our dictionary doesn't have this word!");
        }
    }

    @FXML
    public void upWords(KeyEvent keyEvent) {
        String text = textField.getText();
        observableList.clear();
        for (int i = 0; i < listKey.size(); i++) {
            if (listKey.get(i).startsWith(text)) {
                observableList.add(listKey.get(i));
            }
        }
        SortedList<String> sortedList1 = new SortedList(observableList);
        listView.setItems(sortedList1.sorted());
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
        FileWriter out = new FileWriter("dictionaries.txt");
        String sentence =  fileWriteContent();
        out.write(sentence);
        out.close();
    }

    //nut xoa tu
    @FXML
    public void delete(ActionEvent event) throws IOException {
        String chosenWord = listView.getSelectionModel().getSelectedItem();
        if (dictionary.containsKey(chosenWord)) {
            dictionary.remove(chosenWord.trim());
            observableList.remove(chosenWord);
            listKey.remove(chosenWord);
            writeChanges();
            textField.setText("");
            textArea.setText("");
            System.out.println(chosenWord + " has been deleted from the dictionary");
        } else {
            textArea.setText("Please choose a word to delete!");
        }
    }

    //nut Add Word
    @FXML
    public void addScene(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("addWord.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
    }

    //nut Fix Word
    @FXML
    public void fixScene(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("fixWord.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
    }

    //nut Heart
    @FXML
    public void heartClick(ActionEvent event) throws IOException {
        String favW = listView.getSelectionModel().getSelectedItem();
        heart.setStyle("-fx-shape:  \"M 400 150 A 50 50 0 1 1 500 200 Q 450 250 400 300 L 300 200 A 50 50 0 1 1 400 150 \"; -fx-background-color: #ea1515");
    }

    @FXML
    public void doQuit(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    private Desktop desktop = Desktop.getDesktop();
    @FXML void showGuide(ActionEvent event) {
        File file = new File("Guide.txt");
        try {
            desktop.open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void showInfor(ActionEvent event) {
        File file = new File("Information.txt");
        try {
            desktop.open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //nut Speak
    private static final String voice_name = "kevin16";
    @FXML
    public void soundOn(ActionEvent event) {
        String word = textField.getText();
        Voice voice;
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        VoiceManager voiceManager = VoiceManager.getInstance();
        voice = voiceManager.getVoice(voice_name);
        voice.allocate();
        if(dictionary.containsKey(word)) {
            voice.speak(word);
        }
    }

    //nut API
    @FXML
    public void apiScene(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Warning!");
        alert.setHeaderText("Please check your internet connection!");

        Socket socket = new Socket();
        InetSocketAddress address = new InetSocketAddress("www.google.com", 80);
        try {
            socket.connect(address, 3000);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("APIScene.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            stage.setScene(scene);
        } catch (Exception e) {
            alert.showAndWait();
        }
    }
}
