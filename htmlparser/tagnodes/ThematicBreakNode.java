package htmlparser.tagnodes;

public class ThematicBreakNode extends BlockTagNode {

    public ThematicBreakNode(String tagName) {
        super(tagName);
    }

    public String toString() {
        return "<ThematicBreakNode />";
    }
}
