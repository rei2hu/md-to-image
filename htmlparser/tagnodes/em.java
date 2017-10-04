package htmlparser.tagnodes;

public class em extends InlineTagNode {

    public em() {
        super("em");
    }

    public String toString() {
        return "<em " + attributesString() + ">";
    }
}
