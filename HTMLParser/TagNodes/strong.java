package HTMLParser.TagNodes;

class strong extends InlineTagNode {

    public strong() {

    }

    public String toString() {
        return "<strong>" + content + "</strong>";
    }
}
