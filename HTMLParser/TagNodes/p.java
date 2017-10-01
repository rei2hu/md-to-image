package HTMLParser.TagNodes;

public class p extends BlockTagNode {

    public p() {
        
    }

    public String toString() {
        return "<p" + attributesString() + ">";
    }
}
