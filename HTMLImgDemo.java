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
        HTMLParser ps = new HTMLParser(content);
        ps.parse();
   }

}
