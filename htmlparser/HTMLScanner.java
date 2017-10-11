package htmlparser;

import java.io.BufferedReader;
import java.io.StringReader;
import java.lang.StringBuilder;
import java.io.Reader;

import htmlparser.tokens.*;

class HTMLScanner {

    private Reader scanner;
    private StringBuilder sb;
    private String s;
    Boolean openTag = false, closeTag = false, content = false;

    HTMLScanner(String s) {
        scanner = new BufferedReader(new StringReader(s));
        this.s = s;
        sb = new StringBuilder();
    }

    public HTMLScanner clone() {
        return new HTMLScanner(s);
    }

    // honestly I think this needs a rewrite
    Token nextToken() throws java.io.IOException {
        int c;
        Token t = null;
        while ((c = scanner.read()) != -1) {
            switch (c) {
                case '<':
                    // i mean if you see this tag then it cant be valid right
                    // since its like nested or something
                    // discard everything we've gotten cuz its
                    // all a sham... well unless its content
                    // so we need 1 lookahead
                    // <asd  - good start of tag
                    // </asd - good start of tag
                    // <_    - good start of content
                    // </_   - bad start of tag
                    c = scanner.read();
                    if (c == ' ') { // content
                        sb.append('<').append(' ');
                    } else if (c == '/') { // closing tag
                        closeTag = true;
                    } else if (c == '!') {
                        // should handle comments
                        while ((c = scanner.read()) != -1 && c != '>') {
                            // discarding comment stuff
                        }
                    } else { // an opening tag i hope well unicode might break it
                        openTag = true;
                    }
                    if (closeTag || openTag) {
                        if (content && sb.toString().trim().length() > 0) {
                            String temp = sb.toString();
                            sb.setLength(0);
                            t = new Content(temp);
                        }
                        sb.setLength(0);
                        if (openTag) {
                            sb.append((char) c);
                        }
                        if (t != null) {
                            return t;
                        }
                    }
                    break;
                case '>':
                    String temp = sb.toString();
                    if (openTag) {
                        // remember to handle attributes
                        String[] nameAttributes = (temp + " ").split(" ", 2); // lazy safe
                        if (sb.length() > 0 && sb.charAt(sb.length() - 1) == '/') {
                            nameAttributes = (temp.substring(0, temp.length() - 1) + " ").split(" ", 2);
                            t = new StandaloneTag(nameAttributes[0], nameAttributes[1]);
                        } else
                            t = new OpeningTag(nameAttributes[0], nameAttributes[1]);
                    } else if (closeTag) {
                        t = new ClosingTag(temp);
                    } else { // we were in content and this is just a random closing symbol?
                        sb.append((char) c);
                        break;
                    }
                    if (openTag || closeTag) {
                        openTag = closeTag = false;
                        sb.setLength(0);
                        return t;
                    }
                case '\n':
                case '\r':
                case '\t':
                    openTag = closeTag = false;
                    break;
                default:
                    content = !openTag && !closeTag;
                    sb.append((char) c);
            }
            scanner.mark(1);
        }
        if (sb.length() > 0) {
            t = new Content(sb.toString());
            sb.setLength(0);
            return t;
        }
        return null;
    }

    void close() throws java.io.IOException {
        scanner.close();
    }

}
