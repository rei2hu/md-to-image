package HTMLParser.TagNodes;

public class TagNode {

    String content = "";
    TagNode left;
    TagNode right;
    TagNode parent;

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

    public void addContent(String stuff) {
        content += stuff;
    }

    public String toString() {
        return content;
    }
}

