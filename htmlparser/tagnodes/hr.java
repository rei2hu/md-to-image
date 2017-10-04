package htmlparser.tagnodes;

public class hr extends BlockTagNode {

    public hr() {
        super("hr");
    }

    public String toString() {
        return "<hr />";
    }
}
