package htmlparser.tagnodes;

public class TagNodeMaker {

    public TagNodeMaker() {

    }

    public TagNode makeNode(String nodeType) {
        return makeNode(nodeType, "");
    }

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
