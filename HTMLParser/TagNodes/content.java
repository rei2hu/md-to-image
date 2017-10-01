package HTMLParser.TagNodes;

class content extends InlineTagNode {

    String content = "";

    public content(String s) {
        content = s;
    }

    public String toString() {
        return content;
    }
}
