import java.lang.StringBuilder;
import java.io.File;
import java.util.Scanner;

import HTMLParser.*;
import HTMLParser.TagNodes.TagNode;

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
        TagNode t;
        try {
            t = ps.parse();
        } catch(java.lang.Exception e) {
            e.printStackTrace();
            return;
        }
        travers(t, 0);        
    }

    public static void travers(TagNode root, int spaces) {
        
        if (root.getLeft() != null) {
            travers(root.getLeft(), spaces + 4);
        }
        for (int i = 0; i < spaces; i++) {
            System.out.print(" ");
        }
        System.out.println(root.toString());
        if (root.getRight() != null) {
            travers(root.getRight(), spaces);
        }
    }

}
