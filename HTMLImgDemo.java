import java.lang.StringBuilder;
import java.io.File;
import java.util.Scanner;

import HTMLRenderer.HTMLRenderer;

public class HTMLImgDemo {

     private static StringBuilder sb = new StringBuilder();

     private static void getFiles(File folder) throws java.io.FileNotFoundException {
         for (File file: folder.listFiles()) {
             if (file.isDirectory()) {
                 // tehcnically recursive but nah
             } else if (file.getName().endsWith(".html")){
                 makeImage(file);
             }
         }
     }

     private static void makeImage(File file) throws java.io.FileNotFoundException {
         Scanner in = new Scanner(file);
         while (in.hasNextLine()) {
             sb.append(in.nextLine()).append("\n");
         }
         HTMLRenderer renderer = new HTMLRenderer(sb.toString());
         sb.setLength(0);
         try {
             System.out.println("Rendering image for " + file.getName());
             renderer.createImage(1000, 10, 10, file.getAbsolutePath() + ".jpg");
         } catch(Exception e) {
             e.printStackTrace();
         }

     }
     public static void main(String[] args) throws java.io.FileNotFoundException {
         if (args.length > 0)
             makeImage(new File(args[0]));
         else
            getFiles(new File("./tests"));
     }

}
