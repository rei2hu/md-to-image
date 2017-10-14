package htmlparser.tagnodes;

/**
 * This class is used to create nodes based on the string given
 */

public class TagNodeMaker {

    public TagNodeMaker() {

    }

    /**
     * Helper method
     * @param nodeType The string representing the tag name
     * @return A node
     */
    public TagNode makeNode(String nodeType) {
        return makeNode(nodeType, "");
    }

    /**
     * Create a node based on a tag's name
     * @param nodeType The string representing the tag name
     * @param content The content of the node
     * @return
     */
    public TagNode makeNode(String nodeType, String content) {
        switch(nodeType) {
            case "p":
            case "div": // lol
                return new BlockNode(nodeType);
            case "u":
                return new UnderlineNode(nodeType);
            case "ul":
                return new UnorderedListNode(nodeType);
            case "b":
            case "strong":
                return new BoldNode(nodeType);
            case "li":
                return new ListItemNode(nodeType);
            case "hr":
                return new ThematicBreakNode(nodeType);
            case "content":
                return new ContentNode(content);
            case "img":
                return new ImageNode(nodeType);
            case "br":
                return new LineBreakNode(nodeType);
            case "a":
                return new LinkNode(nodeType);
            case "em":
                return new ItalicNode(nodeType);
            case "h1":
                return new HeaderNode(nodeType, 1);
            case "h2":
                return new HeaderNode(nodeType, 2);
            case "h3":
                return new HeaderNode(nodeType, 3);
            case "h4":
                return new HeaderNode(nodeType, 4);
            case "h5":
                return new HeaderNode(nodeType, 5);
            case "h6":
                return new HeaderNode(nodeType, 6);
            default:
                return new UnknownNode(nodeType);
        }
    }
}
