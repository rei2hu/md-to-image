package HTMLParser.TagNodes;

public class unknown extends BlockTagNode {
    
    unknown(String name) {
        super(name);
    }

    public String toString() {
        return "<" + getTagName() + attributesString() + ">";
    }
}
