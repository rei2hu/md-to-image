package HTMLParser.TagNodes;

public class em extends InlineTagNode {

    public em() {

    }

    public String toString() {
        return "<em " + attributesString() + ">";
    }
}
