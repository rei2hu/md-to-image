package htmlparser.tagnodes;

import htmlparser.Attributes;
import htmlparser.Attribute;
import htmlparser.tokens.Tag;

/**
 * A superclass for all nodes that will be found in the parse tree
 */

public class TagNode {

    private TagNode left;
    private TagNode right;
    private TagNode parent;
    private Attributes attributes;
    private String tag;

    public TagNode(String type) {
        tag = type;
        attributes = new Attributes();
    }

    public String getTagName() {
        return tag;
    }

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

    public void setAttribute(String key, String value) {
        System.out.println(key + ": " + value);
        attributes.add(key, value);
    }
    String attributesString() {
        if (attributes == null) return "";

        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        for (Attribute a: attributes) {
            sb.append(" ").append(a.toString());
        }
        return sb.toString();
    }

    public boolean equals(Object o) {
        if (!(o instanceof TagNode)) {
            if (!(o instanceof Tag))
                return false;
            return ((Tag) o).getTagName().equals(tag);
        }
        return ((TagNode) o).getTagName().equals(tag);

    }

    public String toString() {
        return "<" + getTagName() + attributesString() + ">";
    }
}



