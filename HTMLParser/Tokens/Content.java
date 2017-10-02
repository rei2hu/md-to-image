package HTMLParser.Tokens;

public class Content implements Token {
    
    private String content;

    public Content(String contents) {
        content = contents;
    }

    // hm literally same as tostring
    public String getContent() {
        return content;
    }

    public String toString() {
        return content;
    }
}
