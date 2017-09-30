import java.lang.StringBuilder;
import java.io.File;
import java.util.Scanner;

import HTMLParser.*;

public class HTMLImgDemo {

    public static void main(String[] args) throws java.io.FileNotFoundException {
        if (args.length < 1) {
            System.out.println("Provide a file");
            return;
        }
        Scanner in = new Scanner(new File(args[0]));
        StringBuilder sb = new StringBuilder();
        while (in.hasNextLine()) {
            sb.append(in.nextLine()).append("\n");
        }
        String content = sb.toString();
        HTMLScanner sc = new HTMLScanner(content);
        Token t;
        try {
            while ((t = sc.nextToken()) != null) {
                if (t instanceof OpeningTag)
                    System.out.println((OpeningTag) t);
                else if (t instanceof ClosingTag)
                    System.out.println((ClosingTag) t);
                else
                    System.out.println(t);
            }
        } catch(java.io.IOException e) {
            e.printStackTrace();
        }
    }

}
