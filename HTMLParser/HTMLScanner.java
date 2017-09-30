package HTMLParser;

import java.io.BufferedReader;
import java.io.StringReader;
import java.lang.StringBuilder;
import java.io.Reader;

public class HTMLScanner {

    Reader scanner;

    public HTMLScanner(String s) {
        scanner = new BufferedReader(new StringReader(s));
    }

    /**
     * get the next token
     * @return the next valid token (a tag block or whatever you call it)
     */
    public Token nextToken() throws java.io.IOException {
        int c;
        StringBuilder sb = new StringBuilder();
        while((c = scanner.read()) != -1) {
            switch(c) {
                case '<':
                    c = scanner.read();
                    // starts with a / so its closing
                    if (c == '/') {
                        while ((c = scanner.read()) != '>') {
                            sb.append((char) c);
                        }
                        return new ClosingTag(sb.toString());
                    } else {
                        // so i read that spaces can be in tag
                        // names so this should be ok
                        // hm what about newlines {CHECK LATER}
                        sb.append((char) c);
                        while((c = scanner.read()) != '>') {
                            sb.append((char) c);
                        }
                        return new OpeningTag(sb.toString());
                    } 
                default:
                   // probably content 
            }



        }
        return null;
    }


}
