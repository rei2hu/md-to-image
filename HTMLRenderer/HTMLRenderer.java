package HTMLRenderer;

import java.util.Map;
import java.util.Hashtable;
import java.io.File;
import java.lang.StringBuilder;
import java.io.IOException;

import java.net.URL;

import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.awt.FontMetrics;

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
    private boolean inlineNext = false;

    private Cursor cursor;
    
    public HTMLRenderer(String s) {
        ps = new HTMLParser(s);
    }

    private void makeTree() throws Exception {
        hpt = ps.parse();
    }

    public void createImage(int width, String path) throws Exception {
        makeTree();
        // height should be something
        image = new BufferedImage(width, 5000, BufferedImage.TYPE_INT_RGB);
        g = image.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        cursor = new Cursor(image, 15, 15);
        traverse(hpt.getRoot()); 
        
        ImageIO.write(image, "jpg", new File(path));
    }

    private void traverse(TagNode node) {
        traverse(node, 0);
    }

    private void traverse(TagNode node, int spaces) {
        drawNode(node, spaces);
        if (node.getLeft() != null)
            traverse(node.getLeft(), spaces + 4);
        if (node.getRight() != null)
            traverse(node.getRight(), spaces);
    }

    private void drawNode(TagNode node, int spaces) {
        // for (int i = 0; i < spaces; i++) System.out.print(" ");
        // System.out.println(node);
        if (node instanceof content) {
            drawNode((content) node, spaces);
        } else if (node instanceof hr) {
            drawNode((hr) node);
        } else if (node instanceof li) {
            drawNode((li) node, spaces);
        } else if (node instanceof p) {
            drawNode((p) node);
        } else if (node instanceof strong) {
            drawNode((strong) node);
        } else if (node instanceof u) {
            drawNode((u) node);
        } else if (node instanceof ul) {
            drawNode((ul) node);
        } else if (node instanceof img){
            drawNode((img) node);
        } else { // unknown
            drawNode((unknown) node);
        }
    }

    private void drawNode(img node) {
        
        // this has quotes on it for some reason
        String url = node.getAttribute("src").getValue();
        // missing protocol or something idk
        url = "http:" + url.substring(1, url.length() - 1); 
        cursor.drawImage(url);
        /*
        // System.out.println(url);
        BufferedImage image = null;
        try {
            image = ImageIO.read(new URL(url));
        } catch(IOException e) {
            System.out.println(e); 
        }
        // need to scale image down to minimum width but do later
        if (image != null)
            // g.drawImage(image, 15, textLineOffset, image.getWidth(), image.getHeight(), null);
        // else
            // put error loading image picture
        textLineOffset += image.getHeight() + 15;
        */
    }

    private void drawNode(unknown node) {
        // wee
    }

    private void drawNode(content node, int spaces) {
        cursor.writeText(node.toString(), false, spaces + modifier);
        modifier = 0;
    }

    private void drawNode(hr node) {
        cursor.drawLine(1);
    }

    private void drawNode(li node, int spaces) {
        cursor.drawBullet(spaces, 4);
    }

    private void drawNode(p node) {
    }
    
    private void drawNode(strong node) {
        modifier -= 4;
        cursor.setBold();
        inlineNext = true;
    }
    
    private void drawNode(u node) {
        modifier -= 4;
        cursor.setUnderline();
        inlineNext = true;
    }

    private void drawNode(ul node) {
    }

    // traverse and draw
    private void drawText(String line) {
        g.setFont(new Font("Times New Roman", Font.PLAIN, 13));
        g.setColor(Color.BLACK);
        g.drawString(line, 10, 23);
    }
}
