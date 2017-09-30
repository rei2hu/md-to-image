package HTMLParser;

public class OpeningTag extends Tag {

    public OpeningTag(String tag) {
        super(tag);
    }

    public String toString() {
        return "<" + tagName + ">";
    }
}
