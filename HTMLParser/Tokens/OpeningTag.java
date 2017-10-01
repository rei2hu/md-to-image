package HTMLParser.Tokens;

import java.util.ArrayList;

public class OpeningTag extends Tag {

    private ArrayList<Attributes> attributes;

    public OpeningTag(String tag) {
        super(tag);
        attributes = new ArrayList<>();
    }

    public OpeningTag(String tag, String[] atrs) {
        super(tag);
        attributes = new ArrayList<>();
        for (int i = 0; i < atrs.length; i++) {
            String[] kv = atrs[i].split("=");
            attributes.add(new Attributes(kv[0], kv[1]));
        }
    }

    public String toString() {
        return "<" + tagName + ">";
    }
}
