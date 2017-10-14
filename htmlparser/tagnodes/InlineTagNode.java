package htmlparser.tagnodes;

/**
 * A superclass of nodes that will be inline
 */

public class InlineTagNode extends TagNode {

    public InlineTagNode(String s) {
        super(s);
    }

    public boolean isInline() {
        return true;
    }

}
