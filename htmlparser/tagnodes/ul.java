package htmlparser.tagnodes;

public class ul extends BlockTagNode {

    public ul() {
       super("ul");
    }

    public String toString() {
        return "<ul" + attributesString() + ">";
    }
}
