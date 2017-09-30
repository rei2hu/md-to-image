package HTMLParser;

import java.util.Stack;

import HTMLParser.Tokens.*;

public class HTMLParser {

    private HTMLScanner sc;

    public HTMLParser(String s) {
        sc = new HTMLScanner(s);
    }

    public void parse() {
        Stack<Tag> stack = new Stack<>();
        Token t;
        int indent = 0;
        try {
            while ((t = sc.nextToken()) != null) {
                if (t instanceof OpeningTag) {
                    stack.push((Tag) t);
                    System.out.println(new String(new char[indent]).replace("\0", " ") + ((Tag) t).getTagName());
                    indent += 4;
                    // System.out.println((OpeningTag) t);
                } else if (t instanceof ClosingTag) {
                    Tag s = stack.peek();
                    if (s.equals((Tag) t)) {
                        stack.pop();
                        indent -= 4;
                        System.out.println(new String(new char[indent]).replace("\0", " ") + s.getTagName());
                    } else {
                        System.out.println(s.getTagName() + " " + ((Tag) t).getTagName());
                        throw new Error("Improperly formatted tags " + t);
                    }
                    // System.out.println((ClosingTag) t);
                } else if (t instanceof StandaloneTag ){
                    // System.out.println((StandaloneTag) t);
                    // just append to the right
                } else {
                    System.out.println(new String(new char[indent]).replace("\0", " ") + t);
                }
            }
        } catch(java.lang.Exception e) {
            e.printStackTrace();
        }
    }

}
