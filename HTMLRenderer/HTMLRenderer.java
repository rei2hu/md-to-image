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

    private int textLineOffset = 0;
    private FontSettings defaultFont = new FontSettings();
    private Map<TextAttribute, Object> underline = new Hashtable<>();
    private FontMetrics metrics;
    private Graphics g;

    private int width = 0;
    private boolean undl = false;
    private boolean bold = false;
    private boolean lipadding = false;

    // s is the html string
    public HTMLRenderer(String s) {
        ps = new HTMLParser(s);
        underline.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
    }

    private void makeTree() throws Exception {
        hpt = ps.parse();
    }

    public void createImage(int width, int height) throws Exception {
        makeTree();
        this.width = width;
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        g = image.getGraphics();
        metrics = g.getFontMetrics(defaultFont.getFont());
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        traverse(hpt.getRoot()); 
        
        ImageIO.write(image, "jpg", new File("test.jpg"));
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
        System.out.println(url);
        BufferedImage image = null;
        try {
            image = ImageIO.read(new URL(url));
        } catch(IOException e) {
            System.out.println(e); 
        }
        // need to scale image down to minimum width but do later
        if (image != null)
            g.drawImage(image, 15, textLineOffset, image.getWidth(), image.getHeight(), null);
        // else
            // put error loading image picture
        textLineOffset += image.getHeight() + 15;
    }

    private void drawNode(unknown node) {
        // wee
    }

    // pad with spaces

    private int calculateLeftPadding(int spaces) {
        return metrics.stringWidth(new String(new char[spaces]).replace("\0", " ")); 
    }

    private int spaceWidth() {
        return metrics.stringWidth(" ");
    }

    private void drawNode(content node, int spaces) {

        g.setFont(defaultFont.getFont());
        g.setColor(defaultFont.getColor());

        if (bold)
            g.setFont(new Font("Times New Roman", Font.BOLD, 13));
        if (undl)
            g.setFont(defaultFont.getFont().deriveFont(underline));    
        if (lipadding)
            spaces += 1;

        metrics = g.getFontMetrics(defaultFont.getFont());
        
        int height = metrics.getHeight();
        String[] words = node.toString().split(" ");
        int startX = calculateLeftPadding(spaces);
        int startY = textLineOffset;
        int rightPadding = 5;
        int index = 0;
        while (index < words.length) {
            String line = words[index++];
            while ((index < words.length) && (metrics.stringWidth(line + " " + words[index]) < width - startX - rightPadding)) {
                line = line + " " + words[index++];
            }
            g.drawString(line, startX, startY);
            startY += height;
        }
        lipadding = bold = undl = false;
        textLineOffset += 15; 
    }

    private void drawNode(hr node) {
        // + 5 padding
        // - 6.5 (minus half of font size)
        // - 30 for width
        // height very low
        g.setColor(Color.GRAY);
        g.fillRect(10 + 5, textLineOffset - 6, 1000 - 30, 1);
        textLineOffset += 15;
    }

    private void drawNode(li node, int spaces) {
        // in middleish need to consider rectangle size also
        g.setColor(Color.BLACK);
        // very arbitrary number to multiply by spaces
        // figure out later
        g.fillRect(spaces * spaceWidth() + 10 + 5, textLineOffset - 6, 3, 3);
        lipadding = true;
    }

    private void drawNode(p node) {
        textLineOffset += 15; // skip a line or something i guess
    }
    
    private void drawNode(strong node) {
        bold = true;
    }
    
    private void drawNode(u node) {
        // java plz
        undl = true;
    }

    private void drawNode(ul node) {
        textLineOffset += 15;
    }

    // traverse and draw
    private void drawText(String line) {
        g.setFont(new Font("Times New Roman", Font.PLAIN, 13));
        g.setColor(Color.BLACK);
        g.drawString(line, 10, 23);
    }
}
