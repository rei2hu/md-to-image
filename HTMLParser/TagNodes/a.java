package HTMLParser.TagNodes;

public class a extends InlineTagNode {

    public a() {

    }

    public String toString() {
        return "<a" + attributesString() + ">";
    }
}
