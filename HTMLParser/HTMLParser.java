package HTMLParser;

import java.util.LinkedList;
import java.util.Queue;
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

    private TagNode parse2() throws Exception {
        // if just put opening tag on
        // next node will be nested to the left unless closing
        //
        // closing tag means remove
        // then go next thing is to the right of what was closed
        //
        // if standalone tag
        // go right unless just opened
        //
        // content
        // treater same as standalone

        Queue<Tag> unmatchedTags = verifyTagsMatch();
        // System.out.println(unmatchedTags.size());
        Stack<TagNode> stack = new Stack<>();
        Token token;
        TagNode node, root = null;
        Tag tag;
        boolean goingLeft = false;

        while ((token = sc.nextToken()) != null) {
            boolean temp;

            if (token instanceof OpeningTag) {
                tag = (Tag) token;
                node = tnm.makeNode(tag.getTagName());
                node.setAttributes(tag.getAttributes());
                if (token.equals(unmatchedTags.peek())) {
                    // System.out.println("An unmatched tag was found " + unmatchedTags.remove());
                    unmatchedTags.remove();
                    temp = false;
                } else
                    temp = true;
            } else if (token instanceof ClosingTag) {
                TagNode nested;
                // System.out.println(nested + " " + token + (nested.equals(token)));
                while(!stack.isEmpty()) {
                    nested = stack.peek();
                    if (nested.equals(token)) break;
                    stack.pop();
                }
                goingLeft = false;
                continue;
            } else if (token instanceof StandaloneTag) {
                Tag tmp = (Tag) token;
                node = tnm.makeNode(tmp.getTagName());
                node.setAttributes(tmp.getAttributes());
                temp = false;
            } else {
                node = tnm.makeNode("content", token.toString());
                temp = false;
            }
            if (!stack.isEmpty()) {
                if (goingLeft)
                    stack.peek().setLeft(node);
                else
                    stack.peek().setRight(node);
            }
            stack.push(node);
            goingLeft = temp;
            if (root == null)
                root = node; // idk
        }
        return root;
    }

    private Queue verifyTagsMatch() throws Exception {
        HTMLScanner sc = this.sc.clone();
        Stack<Tag> stack = new Stack<>();
        Queue<Tag> queue = new LinkedList<>();
        Token t;
        while ((t = sc.nextToken()) != null) {
            if (t instanceof OpeningTag) {
                stack.push((Tag) t);
            } else if (t instanceof ClosingTag) {
                Tag s = stack.pop();
                while (!s.equals(t) && !stack.isEmpty()) {
                    queue.add(s);
                    s = stack.pop();
                }
                if (!s.equals(t)) {
                    throw new Exception(String.format("Could not find a matching opening tag for %s", t));
                }
            } else if (t instanceof StandaloneTag) {
                // no need to match
            } else {
                // no need to match
            }
        }
        Queue<Tag> temp = new LinkedList<>();
        while (!stack.isEmpty())
            temp.add(stack.pop());
        while(!temp.isEmpty())
            stack.add(temp.remove());
        while(!stack.isEmpty())
            queue.add(stack.pop());
        return queue;
    }

}
