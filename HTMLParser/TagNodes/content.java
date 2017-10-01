package HTMLParser.TagNodes;

public class content extends InlineTagNode {

    private String content = "";

    public content(String s) {
        content = s.replaceAll("&rsquo;", "'")
                   .replaceAll("&hellip;", "...")
                   .replaceAll("&lsquo;", "'")
                   .replaceAll("&quot;", "'")
                   .replaceAll("&ndash;", "-");
    }

    public String toString() {
        return content;
    }
}
