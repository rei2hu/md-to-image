package HTMLParser.TagNodes;

public class img extends BlockTagNode {

    public img() {
        super("img");
    }

    public String toString() {
        return "<img" + attributesString() + " />";
    }
}
