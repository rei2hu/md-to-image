package HTMLParser.Tokens;

import java.util.ArrayList;

public class StandaloneTag extends Tag {
   
    public StandaloneTag(String tag) {
        super(tag);
    }
    
    public StandaloneTag(String tag, String[] atrs) {
        super(tag);
        for (int i = 0; i < atrs.length; i++) {
            String[] kv = atrs[i].split("=", 2);
            if (kv.length >= 2)
                attributes.add(kv[0], kv[1]);
            else
                attributes.add(kv[0], "");
        }
    }


    public String toString() {
        return "<" + tagName + " />";
    }
}
