package htmlparser.tagnodes;

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
