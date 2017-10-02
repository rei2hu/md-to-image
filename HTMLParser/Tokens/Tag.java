package HTMLParser.Tokens;

import java.util.ArrayList;

public class Tag implements Token {
    
    protected String tagName;
    protected Attributes attributes = new Attributes();

    public Tag(String tag) {
        tagName = tag;
    }

    public String toString() {
        return tagName;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public String getTagName() {
        return tagName;
    }

    public boolean equals(Object o) {
        if (!(o instanceof Tag)) return false;
        return ((Tag) o).getTagName().equals(this.tagName);
    }

}
