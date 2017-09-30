package HTMLParser.Tokens;

public class Tag implements Token {
    
    protected String tagName;

    public Tag(String tag) {
        tagName = tag;
    }

    public String toString() {
        return tagName;
    }

    public String getTagName() {
        return tagName;
    }

    public boolean equals(Object o) {
        if (!(o instanceof Tag)) return false;
        return ((Tag) o).getTagName().equals(this.tagName);
    }

}
