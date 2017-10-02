package HTMLParser.TagNodes;

public class ul extends BlockTagNode {

    public ul() {
        
    }

    public String toString() {
        return "<ul" + attributesString() + ">";
    }
}
