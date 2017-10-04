package htmlparser.tagnodes;

public class strong extends InlineTagNode {

    public strong() {
        super("strong");
    }

    public strong(boolean isActuallyB) {
        super("b");
    }

    public String toString() {
        return "<strong" + attributesString() + ">";
    }
}
