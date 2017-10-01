package HTMLParser.TagNodes;

class unknown extends BlockTagNode {
    
    public String type;

    public unknown(String name) {
        type = name;
    }

    public String toString() {
        return "<" + type + ">" + content + "</" + type + ">";
    }
}
