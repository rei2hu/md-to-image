package HTMLParser.Tokens;

import java.util.ArrayList;

public class OpeningTag extends Tag {

    public OpeningTag(String tag) {
        super(tag);
    }

    public OpeningTag(String tag, String[] atrs) {
        super(tag);
  
        for (int i = 0; i < atrs.length; i++) {
            String[] kv = atrs[i].split("=");
            attributes.add(kv[0], kv[1]);
        }
    }

    public String toString() {
        return "<" + tagName + ">";
    }
}
