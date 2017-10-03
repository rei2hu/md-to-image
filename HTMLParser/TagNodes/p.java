package HTMLParser.TagNodes;

public class p extends BlockTagNode {

    public p() {
      super("p");
    }

    public p(boolean isActuallyDiv) {
        super("div");
    }

    public String toString() {
        return "<p" + attributesString() + ">";
    }
}
