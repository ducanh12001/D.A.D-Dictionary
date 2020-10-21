package sample;

public class Dictionary {
    private String word;
    private String word_mean;
    private String word_type;
    private String word_structure;

    public Dictionary(String word, String word_mean, String word_type, String word_structure) {
        this.word = word;
        this.word_mean = word_mean;
        this.word_type = word_type;
        this.word_structure = word_structure;
    }

    public Dictionary() {
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getWord_mean() {
        return word_mean;
    }

    public void setWord_mean(String word_mean) {
        this.word_mean = word_mean;
    }

    public String getWord_type() {
        return word_type;
    }

    public void setWord_type(String word_type) {
        this.word_type = word_type;
    }

    public String getWord_structure() {
        return word_structure;
    }

    public void setWord_structure(String word_structure) {
        this.word_structure = word_structure;
    }
}
