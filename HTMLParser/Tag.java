package HTMLParser;

public class Tag implements Token {
    String tagName;

    public Tag(String tag) {
        tagName = tag;
    }

    public String toString() {
        return tagName;
    }

}
