package HTMLParser.TagNodes;

public class InlineTagNode extends TagNode {

    public InlineTagNode(String s) {
        super(s);
    }

    public boolean isInline() {
        return true;
    }

}
