package HTMLParser.TagNodes;

class ul extends BlockTagNode {

    public ul() {
        
    }

    public String toString() {
        return "<ul>" + content + "</ul>";
    }
}
