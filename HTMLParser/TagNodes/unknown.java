package HTMLParser.TagNodes;

public class unknown extends BlockTagNode {
    
    private String type;

    public unknown(String name) {
        type = name;
    }

    public String getType() {
        return type;
    }

    public String toString() {
        return "<" + type + ">" + content + "</" + type + ">";
    }
}
