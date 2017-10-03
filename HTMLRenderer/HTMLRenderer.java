package HTMLRenderer;

import java.util.Map;
import java.util.Hashtable;
import java.io.File;
import java.lang.StringBuilder;
import java.io.IOException;

import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import javax.imageio.ImageIO;

import HTMLParser.*;
import HTMLParser.TagNodes.*;
import HTMLParser.Tokens.Attribute;

public class HTMLRenderer {

    private HTMLParseTree hpt;
    private HTMLParser ps;

    private Cursor cursor;
    
    public HTMLRenderer(String s) {
        ps = new HTMLParser(s);
    }

    private void makeTree() throws Exception {
        hpt = ps.parse();
    }

    public void createImage(int width, int xPad, int yPad, String path) throws Exception {
        makeTree();
        // height should be something
        int height = 100;
        cursor = new Cursor(new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB), xPad, yPad);
        drawNode(hpt.getRoot(), 0);
        ImageIO.write(cursor.getImage(), "jpg", new File(path));
    }

    private void drawNode(TagNode node, int spaces) {
        // for (int i = 0; i < spaces; i++) System.out.print(" ");
        // System.out.println(node);
        if (node == null) return;
        if (node instanceof content) {
            // content node only has right
            cursor.writeText(node.toString(), spaces);
            drawNode(node.getLeft(), spaces);
            drawNode(node.getRight(), spaces);
        } else if (node instanceof hr) {
            // standalone tag only has right
            cursor.drawLine(1);
            drawNode(node.getRight(), spaces);
        } else if (node instanceof li) {
            cursor.lineBreak(spaces);
            cursor.drawBullet(spaces, 3);
            drawNode(node.getLeft(), spaces);
            // cursor.lineBreak(spaces);
            drawNode(node.getRight(), spaces);
        } else if (node instanceof p) {
            cursor.lineBreak(spaces);
            drawNode(node.getLeft(), spaces);
            cursor.lineBreak(spaces);
            drawNode(node.getRight(), spaces);
        } else if (node instanceof strong) {
            // this is inline, technically a content node
            cursor.setBold(true);
            drawNode(node.getLeft(), 0); // content
            cursor.setBold(false);
            drawNode(node.getRight(), spaces);
        } else if (node instanceof u) {
            // this is also inline, technically a content node
            cursor.setUnderline(true);
            drawNode(node.getLeft(), 0); // content
            cursor.setUnderline(false);
            drawNode(node.getRight(), spaces);
        } else if (node instanceof ul) {
            // cursor.lineBreak(spaces);
            drawNode(node.getLeft(), spaces + 4);
            cursor.lineBreak(spaces);
            drawNode(node.getRight(), spaces);
        } else if (node instanceof img) {
            drawNode((img) node);
            drawNode(node.getRight(), spaces);
        } else if (node instanceof br) {
            cursor.lineBreak(spaces);
            drawNode(node.getRight(), spaces);
        } else if (node instanceof unknown){ // unknown
            System.out.println("Encountered unknown node (" + ((unknown) node).getType() + ").");
            // drawNode((unknown) node);
            drawNode(node.getLeft(), spaces + 4);
            drawNode(node.getRight(), spaces);
        } else if (node instanceof a) {
            cursor.setUnderline(true);
            drawNode(node.getLeft(), spaces);
            cursor.setUnderline(false);
            drawNode(node.getRight(), spaces);
        } else if (node instanceof em) {
            cursor.setItalic(true);
            drawNode(node.getLeft(), spaces);
            cursor.setItalic(false);
            drawNode(node.getRight(), spaces);
        }
    }

    private void drawNode(img node) {
        // this has quotes on it for some reason
        String url = node.getAttribute("src").getValue();
        // missing protocol or something idk
        url = "http:" + url.substring(1, url.length() - 1); 
        cursor.drawImage(url);
    }

    private void drawNode(unknown node) {
        // wee
    }

}
