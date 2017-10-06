package htmlparser.tagnodes;

public class TagNodeMaker {

    public TagNodeMaker() {

    }

    public TagNode makeNode(String nodeType) {
        return makeNode(nodeType, "");
    }

    public TagNode makeNode(String nodeType, String content) {
        TagNode n;
        switch(nodeType) {
            case "p":
            case "div": // lol
                n = new BlockNode(nodeType);
                break;
            case "u":
                n = new UnderlineNode(nodeType);
                break;
            case "ul":
                n = new UnorderedListNode(nodeType);
                break;
            case "b":
            case "strong":
                n = new BoldNode(nodeType);
                break;
            case "li":
                n = new ListItemNode(nodeType);
                break;
            case "hr":
                n = new ThematicBreakNode(nodeType);
                break;
            case "content":
                n = new ContentNode(content);
                break;
            case "img":
                n = new img();
                break;
            case "br":
                n = new LineBreakNode(nodeType);
                break;
            case "a":
                n = new a();
                break;
            case "em":
                n = new ItalicNode(nodeType);
                break;
            default:
                n = new UnknownNode(nodeType);
        }
        return n;
    }
}
