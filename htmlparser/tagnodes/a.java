package htmlparser.tagnodes;

public class a extends InlineTagNode {

    public a() {
        super("a");
    }

    public String toString() {
        return "<a" + attributesString() + ">";
    }
}
