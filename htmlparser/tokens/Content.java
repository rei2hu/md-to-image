package htmlparser.tokens;

public class Content implements Token {
    
    private String content;

    public Content(String contents) {
        content = contents;
    }

    public String getContent() {
        return content;
    }

    public String toString() {
        return content;
    }
}
