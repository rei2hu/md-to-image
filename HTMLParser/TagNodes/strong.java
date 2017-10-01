package HTMLParser.TagNodes;

public class strong extends InlineTagNode {

    public strong() {

    }

    public String toString() {
        return "<strong>" + content + "</strong>";
    }
}
