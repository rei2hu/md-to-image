package htmlparser.tagnodes;

/**
 * This represents a thematic break tag (hr)
 */

public class ThematicBreakNode extends BlockTagNode {

    public ThematicBreakNode(String tagName) {
        super(tagName);
    }

    public String toString() {
        return "<ThematicBreakNode />";
    }
}
