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

    HTMLScanner(String s) {
        scanner = new BufferedReader(new StringReader(s));
        this.s = s;
        sb = new StringBuilder();
    }

    public HTMLScanner clone() {
        return new HTMLScanner(s);
    }
    
    Token nextToken() throws java.io.IOException {
        int c;
        Token t;
        // hmm
        while ((c = scanner.read()) != -1) {
            switch(c) {
                case '<':
                    // don't want empty one
                    if (sb.toString().trim().length() > 0) {
                        t = new Content(sb.toString());
                        scanner.reset();
                    } else {
                        sb.setLength(0);
                        c = scanner.read();
                        // starts with LinkNode / so its closing
                        if (c == '/') {
                            while ((c = scanner.read()) != '>') {
                                sb.append((char) c);
                            }
                            t = new ClosingTag(sb.toString());
                        } else {
                            // so i read that spaces can be in tag
                            // names so this should be ok
                            // hm what about newlines {CHECK LATER}
                            sb.append((char) c);
                            while((c = scanner.read()) != '>') {
                                sb.append((char) c);
                            }
                            // if opening tag ends with / then it also closes e.g. <asd />
                           if (sb.charAt(sb.length() - 1) == '/') {
                                // remove /
                                sb.setLength(sb.length() - 1);
                                String s = sb.toString();
                                String[] kvs = s.split(" ", 2);
                                if (kvs.length > 1)
                                    t = new StandaloneTag(kvs[0], kvs[1]);
                                else
                                    t = new StandaloneTag(kvs[0]);
                            } else {
                                String s = sb.toString();
                                String[] kvs = s.split(" ", 2);
                                if (kvs.length > 1)
                                    t = new OpeningTag(kvs[0], kvs[1]);
                                else
                                    t = new OpeningTag(kvs[0]);
                            }
                        }
                    }
                    sb.setLength(0);
                    return t;
                case '\n':
                case '\r':
                case '\t':
                    // don't want newlines or linebreaks or tabs i guess
                    // actuall maybe use trim and see
                    break;
                default:
                    // ok this is about to be awkward
                    sb.append((char) c);
            }
            scanner.mark(1);
        }
        return null;
    }

    void close() throws java.io.IOException {
        scanner.close();
    }

}
