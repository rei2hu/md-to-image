package htmlparser.tagnodes;

/**
 * A node that represents a header tag (h1, h2, h3, etc)
 */

public class HeaderNode extends BlockTagNode {

    private int size;

    public HeaderNode(String tagName, int size) {
        super(tagName);
        this.size = 7 - size;
    }

    public int getSize() {
        return size;
    }
}
