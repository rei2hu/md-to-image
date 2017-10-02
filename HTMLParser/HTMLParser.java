package HTMLParser;

import java.util.Stack;

import HTMLParser.Tokens.*;
import HTMLParser.TagNodes.TagNode;
import HTMLParser.TagNodes.HTMLParseTree;
import HTMLParser.TagNodes.TagNodeMaker;

public class HTMLParser {

    private HTMLScanner sc;
    private TagNodeMaker tnm;

    public HTMLParser(String s) {
        sc = new HTMLScanner(s);
        tnm = new TagNodeMaker();
    }

    public HTMLParseTree parse() throws Exception {
        verifyTagsMatch();
        try {
            return new HTMLParseTree(parse2());
        } finally {
            sc.close();
        }
    }

    private TagNode parse2() throws java.io.IOException {
        Token t;
        t = sc.nextToken();
        if (t == null) return null;
        if (t instanceof OpeningTag) {
            Tag temp = (Tag) t;
            TagNode n = tnm.makeNode(temp.getTagName());
            n.setAttributes(temp.getAttributes());        
            n.setLeft(parse2());
            n.setRight(parse2());
            return n;
        } else if (t instanceof ClosingTag) {
            return null;
        } else if (t instanceof StandaloneTag) {
            Tag temp = (Tag) t;
            TagNode n = tnm.makeNode(temp.getTagName());
            n.setAttributes(temp.getAttributes());
            n.setLeft(null); // should NOT have left
            n.setRight(parse2());
            return n;
        } else { // content
            TagNode n = tnm.makeNode("content", t.toString());
            n.setLeft(null);
            n.setRight(parse2());
            return n;
        }
    }

    private void verifyTagsMatch() throws Exception {
        HTMLScanner sc = this.sc.clone();
        Stack<Tag> stack = new Stack<>();
        Token t;
        try {
            while ((t = sc.nextToken()) != null) {
                if (t instanceof OpeningTag) {
                    stack.push((Tag) t);
                } else if (t instanceof ClosingTag) {
                    Tag s = stack.peek();
                    if (s.equals(t)) {
                        stack.pop();
                    } else {
                        throw new Exception(String.format("Expected %s but got %s", s, t));
                    }
                } else if (t instanceof StandaloneTag) {
                    // no need to match
                } else {
                    // no need to match
                }
            }
        } catch(java.io.IOException e) {
            throw e;
        }
    }

}
