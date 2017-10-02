package HTMLParser.TagNodes;

import HTMLParser.Tokens.Attributes;
import HTMLParser.Tokens.Attribute;

public class TagNode {

    protected TagNode left;
    protected TagNode right;
    protected TagNode parent;
    protected Attributes attributes;

    public TagNode setLeft(TagNode l) {
        left = l;
        return this;
    }
    
    public TagNode setRight(TagNode r) {
        right = r;
        return this;
    }

    public TagNode setParent(TagNode p) {
        parent = p;
        return this;
    }

    public TagNode setAttributes(Attributes a) {
        attributes = a;
        return this;
    }

    public TagNode getLeft() {
        return left;
    }

    public TagNode getRight() {
        return right;
    }

    public TagNode getParent() {
        return parent;
    }

    public Attribute getAttribute(String key) {
        return attributes.get(key);
    }

    public boolean isInline() {
        return false;
    }

    public String attributesString() {
        if (attributes == null) return "";

        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        for (Attribute a: attributes) {
            sb.append(" ").append(a.toString());
        }
        return sb.toString();
    }

    public String toString() {
        return "<TAGNODE" + attributesString() + ">";
    }
}

