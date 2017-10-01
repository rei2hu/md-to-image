package HTMLParser.TagNodes;

public class TagNodeMaker {

    public TagNodeMaker() {

    }

    public TagNode makeNode(String nodeType) {
        TagNode n;
        switch(nodeType) {
            case "p":
                n = new hr();
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
            default:
                n = new unknown(nodeType);
        }
        return n;
    }
}
