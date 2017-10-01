package HTMLParser.TagNodes;

public class li extends InlineTagNode {

    public li() {

    }

    public String toString() {
        return "<li>" + content + "</li>";
    }
}
