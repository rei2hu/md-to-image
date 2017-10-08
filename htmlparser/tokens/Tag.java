package htmlparser.tokens;


import htmlparser.Attributes;

public class Tag implements Token {
    
    String tagName;
    Attributes attributes = new Attributes();

    Tag(String tag) {
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
        // fuck UnderlineNode intellij!!
        if (!(o instanceof Tag)) return false;
        return ((Tag) o).getTagName().equals(this.tagName);
    }

}
