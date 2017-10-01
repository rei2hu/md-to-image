package HTMLParser;

import java.util.Stack;

import HTMLParser.Tokens.*;
import HTMLParser.TagNodes.*;

public class HTMLParser {

    private HTMLScanner sc;
    private TagNodeMaker tnm;

    public HTMLParser(String s) {
        sc = new HTMLScanner(s);
        tnm = new TagNodeMaker();
    }

    public TagNode parse() throws Exception {
        verifyTagsMatch();
        System.out.println("ASD");
        TagNode node = new RootNode();
        node.setLeft(parse2(node));
        node.setRight(parse2(node));
        try {
            sc.close();
        } catch(java.io.IOException e) {

        }
        return node;
    }

    private TagNode parse2(TagNode node) {
        Token t;
        try {
            t = sc.nextToken();
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
        if (t == null || t instanceof ClosingTag) return null;
        if (t instanceof OpeningTag) {
            TagNode n = tnm.makeNode(((Tag) t).getTagName());
            n.setLeft(parse2(n));
            n.setRight(parse2(n));
            return n;
        } else if (t instanceof StandaloneTag) {
            TagNode n = tnm.makeNode(((Tag) t).getTagName());
            n.setLeft(null); // should NOT have left
            n.setRight(parse2(n));
            return n;
        } else {
            node.addContent(t.toString());
            return parse2(node);
        }
    }

    private void verifyTagsMatch() throws Exception {
        HTMLScanner sc = this.sc.clone();
        TagNode node = new RootNode();
        Stack<Tag> stack = new Stack<>();
        Token t;
        int indent = 0;
        try {
            while ((t = sc.nextToken()) != null) {
                if (t instanceof OpeningTag) {
                    stack.push((Tag) t);
                } else if (t instanceof ClosingTag) {
                    Tag s = stack.peek();
                    if (s.equals((Tag) t)) {
                        stack.pop();
                    } else {
                        throw new Exception(String.format("Expected %s but got %s", s, (Tag) t));
                    }
                } else if (t instanceof StandaloneTag ){
                    // no need to match
                } else {
                    // no need to match
                }
            }
            sc.close();
        } catch(java.io.IOException e) {
            throw new Exception(e.getMessage()); 
        }
    }

}
