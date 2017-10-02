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

    private BufferedImage image;
    private HTMLParseTree hpt;
    private HTMLParser ps;

    private Graphics g;

    private int modifier = 0;
    private int inlineNext = 0;
    private boolean justBroke = false;

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
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // g = image.getGraphics();
        // g.setColor(Color.WHITE);
        // g.fillRect(0, 0, width, height);
        cursor = new Cursor(image, xPad, yPad);
        draw(hpt.getRoot()); 
        ImageIO.write(cursor.getImage(), "jpg", new File(path));
    }

    private void draw(TagNode node) {
        drawNode(node, 0);
    }

    private void drawNode(TagNode node, int spaces) {
        // for (int i = 0; i < spaces; i++) System.out.print(" ");
        // System.out.println(node);
        if (node == null) return;
        if (node instanceof content) {
            // content node only has right
            drawNode((content) node, spaces);
            drawNode(node.getRight(), spaces);
        } else if (node instanceof hr) {
            // standalone tag only has right
            drawNode((hr) node);
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
            drawNode((strong) node);
            drawNode(node.getLeft(), 0); // content
            drawNode(node.getRight(), spaces);
        } else if (node instanceof u) {
            // this is also inline, technically a content node
            drawNode((u) node);
            drawNode(node.getLeft(), 0); // content
            drawNode(node.getRight(), spaces);
        } else if (node instanceof ul) {
            // cursor.lineBreak(spaces);
            drawNode(node.getLeft(), spaces + 4);
            cursor.lineBreak(spaces);
            drawNode(node.getRight(), spaces);
        } else if (node instanceof img){
            drawNode((img) node);
            drawNode(node.getRight(), spaces);
        } else { // unknown
            // drawNode((unknown) node);
            drawNode(node.getLeft(), spaces + 4);
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

    private void drawNode(content node, int spaces) {
        cursor.writeText(node.toString(), spaces + modifier);
    }

    private void drawNode(hr node) {
        cursor.drawLine(1);
    }

    private void drawNode(li node, int spaces) {
        cursor.drawBullet(spaces, 4);
    }

    private void drawNode(strong node) {
        cursor.setBold();
    }

    
    private void drawNode(u node) {
        cursor.setUnderline();
    }
    
}
