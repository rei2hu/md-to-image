package HTMLParser.TagNodes;

class li extends InlineTagNode {

    public li() {

    }

    public String toString() {
        return "<li>" + content + "</li>";
    }
}
