package HTMLParser;

public class StandaloneTag extends Tag {

    public StandaloneTag(String tag) {
        super(tag);
    }

    public String toString() {
        return "<" + tagName + " />";
    }
}
