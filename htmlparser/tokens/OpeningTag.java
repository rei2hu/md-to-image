package htmlparser.tokens;

public class OpeningTag extends Tag {

    public OpeningTag(String tag) {
        super(tag);
    }

    public OpeningTag(String tag, String atrs) {
        super(tag);
        attributes = new Attributes(atrs);
    }

    public String toString() {
        return "<" + tagName + ">";
    }
}
