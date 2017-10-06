package htmlparser.tokens;

public class StandaloneTag extends Tag {
   
    public StandaloneTag(String tag) {
        super(tag);
    }
    
    public StandaloneTag(String tag, String atrs) {
        super(tag);
        attributes = new Attributes(atrs);
    }


    public String toString() {
        return "<" + tagName + " />";
    }
}
