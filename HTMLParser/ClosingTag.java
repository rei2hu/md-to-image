package HTMLParser;

public class ClosingTag extends Tag {

    public ClosingTag(String tag) {
        super(tag);
    }

    public String toString() {
        return "</" + tagName + ">";
    }
}
