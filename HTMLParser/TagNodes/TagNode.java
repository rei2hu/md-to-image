package HTMLParser.TagNodes;

public class TagNode {

    protected String content = "";
    protected TagNode left;
    protected TagNode right;
    protected TagNode parent;

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

    public TagNode getLeft() {
        return left;
    }

    public TagNode getRight() {
        return right;
    }

    public TagNode getParent() {
        return parent;
    }

    public String toString() {
        return "<BASE>" +  content + "</BASE>";
    }
}

