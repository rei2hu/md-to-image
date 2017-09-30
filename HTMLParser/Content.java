package HTMLParser;

public class Content implements Token {
    
    String content;

    public Content(String contents) {
        content = contents;
    }

    public String toString() {
        return content;
    }
}
