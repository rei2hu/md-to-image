package htmlparser.tagnodes;

import java.lang.StringBuilder;

public class HTMLParseTree {
    
    private TagNode root;

    public HTMLParseTree(TagNode root) {
        this.root = root;
    }

    public TagNode getRoot() {
        return root;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        traverse(root, 0, sb);
        return sb.toString();
    }

    private void traverse(TagNode node, int spaces, StringBuilder s) {
        for (int i = 0; i < spaces; i++) {
            s.append(" ");
        }
        s.append(getContents(node, false)).append("\n");
        if (node.getLeft() != null) {
            traverse(node.getLeft(), spaces + 4, s);
        }
        if (!(node instanceof ContentNode)) {

            for (int i = 0; i < spaces; i++) {
                s.append(" ");
            }
            
            s.append(getContents(node, true)).append("\n");
        }
        if (node.getRight() != null) {
            traverse(node.getRight(), spaces, s);
        }
    }

    private String getContents(TagNode node, boolean closing) {
        if (node instanceof ContentNode) {
            return ((ContentNode) node).toString();
        }
        String s = "<";
        if (closing) s += "/";
        s += node.getClass().getName() + ">";
        return s;
    } 

}
