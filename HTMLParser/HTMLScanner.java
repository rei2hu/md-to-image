package HTMLParser;

import java.io.BufferedReader;
import java.io.StringReader;
import java.lang.StringBuilder;
import java.io.Reader;

public class HTMLScanner {

    Reader scanner;
    StringBuilder sb;

    public HTMLScanner(String s) {
        scanner = new BufferedReader(new StringReader(s));
        sb = new StringBuilder();
    }

    /**
     * get the next token
     * @return the next valid token (a tag block or whatever you call it)
     */
    public Token nextToken() throws java.io.IOException {
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
                        // starts with a / so its closing
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
                                // remove space and /
                                sb.setLength(sb.length() - 2);
                                t = new StandaloneTag(sb.toString());
                            } else {
                                t = new OpeningTag(sb.toString());
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


}
