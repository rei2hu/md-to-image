package HTMLParser.TagNodes;

public class li extends InlineTagNode {

    public li() {
        super("li");
    }

    public String toString() {
        return "<li" + attributesString() + ">";
    }
}
