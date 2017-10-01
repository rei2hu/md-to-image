package HTMLParser.TagNodes;

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
                n = new p();
                break;
            case "u":
                n = new u();
                break;
            case "ul":
                n = new ul();
                break;
            case "strong":
                n = new strong();
                break;
            case "li":
                n = new li();
                break;
            case "hr":
                n = new hr();
                break;
            case "content":
                n = new content(content);
                break;
            default:
                n = new unknown(nodeType);
        }
        return n;
    }
}
