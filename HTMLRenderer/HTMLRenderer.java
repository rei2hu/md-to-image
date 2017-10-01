package HTMLRenderer;

import java.util.Map;
import java.util.Hashtable;
import java.io.File;
import java.lang.StringBuilder;

import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.awt.FontMetrics;

import javax.imageio.ImageIO;

import HTMLParser.*;
import HTMLParser.TagNodes.*;

public class HTMLRenderer {

    private BufferedImage image;
    private HTMLParseTree hpt;
    private HTMLParser ps;
    private int textLineOffset = 0;
    private FontSettings defaultFont = new FontSettings();
    private Map<TextAttribute, Object> underline = new Hashtable<>();

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
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
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
        } else { // unknown
            drawNode((unknown) node);
        }
    }

    private void drawNode(unknown node) {
        // wee
    }

    // pad with spaces
    private void drawNode(content node, int spaces) {
        Graphics g = image.getGraphics();
        FontMetrics metrics = g.getFontMetrics(defaultFont.getFont());
        g.setFont(defaultFont.getFont());
        g.setColor(defaultFont.getColor());
        int height = metrics.getHeight();
        String[] words = node.toString().split(" ");
        int startX = 10;
        int startY = textLineOffset;
        int index = 0;
        int maxWidth = 1000;
        while (index < words.length) {
            String line = new String(new char[spaces]).replace("\0", " ") + words[index++];
            while ((index < words.length) && (metrics.stringWidth(line + " " + words[index]) < maxWidth)) {
                line = line + " " + words[index++];
            }
            g.drawString(line, startX, startY);
            startY += height;
        }
        textLineOffset += 15; 
    }

    private void drawNode(hr node) {
        // + 5 padding
        // - 6.5 (minus half of font size)
        // - 30 for width
        // height very low
        Graphics g = image.getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(10 + 5, textLineOffset - 6, 1000 - 30, 2);
        textLineOffset += 15;
    }

    private void drawNode(li node, int spaces) {
        // in middleish need to consider rectangle size also
        Graphics g = image.getGraphics();
        g.setColor(Color.BLACK);
        // very arbitrary number to multiply by spaces
        // figure out later
        g.fillRect(10 + 5 + (spaces * 4), textLineOffset - 6, 3, 3);
    }

    private void drawNode(p node) {
        textLineOffset += 15; // skip a line or something i guess
    }
    
    private void drawNode(strong node) {
        Graphics g = image.getGraphics();
        g.setFont(new Font("Times New Roman", Font.BOLD, 13));
    }
    
    private void drawNode(u node) {
        // java plz
        Graphics g = image.getGraphics();
        g.setFont(defaultFont.getFont().deriveFont(underline));
    }

    private void drawNode(ul node) {
        textLineOffset += 15;
    }

    // traverse and draw
    private void drawText(String line) {
        Graphics g = image.getGraphics();
        g.setFont(new Font("Times New Roman", Font.PLAIN, 13));
        g.setColor(Color.BLACK);
        g.drawString(line, 10, 23);
    }
}
