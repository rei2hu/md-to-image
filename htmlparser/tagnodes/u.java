package htmlparser.tagnodes;

public class u extends InlineTagNode {

    public u() {
        super("u");
    }

    public String toString() {
        return "<u" + attributesString() + ">";
    }
}
