package htmlparser.tagnodes;

import java.lang.StringBuilder;

/**
 * A parse tree
 */

public class HTMLParseTree {
    
    private TagNode root;

    /**
     * The constructor
     * @param root The root node
     */
    public HTMLParseTree(TagNode root) {
        this.root = root;
    }

    /**
     * Get's the root of the parse tree
     * @return The root
     */
    public TagNode getRoot() {
        return root;
    }

    /**
     * A basic to string method
     * @return A string representing the tree
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        traverse(root, 0, sb);
        return sb.toString();
    }

    /**
     * A basic tree traversal used for printing the tree
     * @param node The node the tree to traverse is rooted at
     * @param spaces For printing purposes
     * @param s The string representing the tree
     */
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

    /**
     * The contents of a node in the tree
     * @param node The node to check
     * @param closing Uh what
     * @return I really don't know do I use this anywhere?
     */
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
