package htmlparser.tokens;

public class OpeningTag extends Tag {

    public OpeningTag(String tag) {
        super(tag);
    }

    public OpeningTag(String tag, String[] atrs) {
        super(tag);
  
        for (String s: atrs) {
            String[] kv = s.split("=");
            if (kv.length >= 2)
                attributes.add(kv[0], kv[1]);
            else
                attributes.add(kv[0], "");
        }
    }

    public String toString() {
        return "<" + tagName + ">";
    }
}
