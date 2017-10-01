package HTMLParser.TagNodes;

class p extends BlockTagNode {

    public p() {
        
    }

    public String toString() {
        return "<p>" + content + "</p>";
    }
}
