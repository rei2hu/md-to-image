package htmlrenderer;

import java.awt.*;
import java.io.File;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import htmlparser.*;
import htmlparser.tagnodes.*;

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

        // attributes to care about for blocks:
        // color
        // font-family
        // font-size

        // font-family
        // well good luck here
        // node.getAttribute("font-family")


        // possible size values
        // px - this is fine
        // em - probably will ignore this
        // what if theres nothing?
        // node.getAttribute("font-size");


        // possible color values
        // rgb  - rgb(10, 20, 30)
        // name - red
        // hex  - #ff0000
        // node.getAttribute("color");

        if (node instanceof ContentNode) {
            // ContentNode node only has right
            cursor.writeText(node.toString(), spaces);
            drawNode(node.getLeft(), spaces);
            drawNode(node.getRight(), spaces);
        } else if (node instanceof ThematicBreakNode) {
            // standalone tag only has right
            cursor.drawLine(1);
            drawNode(node.getRight(), spaces);
        } else if (node instanceof ListItemNode) {
            cursor.lineBreak(spaces);
            cursor.drawBullet(spaces, 3);
            drawNode(node.getLeft(), spaces);
            // cursor.lineBreak(spaces);
            drawNode(node.getRight(), spaces);
        } else if (node instanceof BlockNode) {
            cursor.lineBreak(spaces);
            drawNode(node.getLeft(), spaces);
            cursor.lineBreak(spaces);
            drawNode(node.getRight(), spaces);
        } else if (node instanceof BoldNode) {
            // this is inline, technically LinkNode ContentNode node
            cursor.startBlock(Cursor.FontStyle.BOLD);
            drawNode(node.getLeft(), 0); // ContentNode
            cursor.endBlock();
            drawNode(node.getRight(), spaces);
        } else if (node instanceof UnderlineNode) {
            // this is also inline, technically LinkNode ContentNode node
            cursor.startBlock(Cursor.FontStyle.UNDERLINE);
            drawNode(node.getLeft(), 0); // ContentNode
            cursor.endBlock();
            drawNode(node.getRight(), spaces);
        } else if (node instanceof HeaderNode) {
            cursor.lineBreak(spaces);
            // kind of arbitrary size but whatever
            cursor.startBlock(((HeaderNode) node).getSize() * 3 + 8);
            drawNode(node.getLeft(), 0);
            cursor.endBlock();
            cursor.lineBreak(spaces);
            drawNode(node.getRight(), spaces);
        } else if (node instanceof UnorderedListNode) {
            // cursor.lineBreak(spaces);
            drawNode(node.getLeft(), spaces + 4);
            cursor.lineBreak(spaces);
            drawNode(node.getRight(), spaces);
        } else if (node instanceof ImageNode) {
            drawNode((ImageNode) node);
            drawNode(node.getRight(), spaces);
        } else if (node instanceof LineBreakNode) {
            cursor.lineBreak(spaces);
            drawNode(node.getRight(), spaces);
        } else if (node instanceof UnknownNode){ // UnknownNode
            System.out.println("Encountered UnknownNode node (" + ((UnknownNode) node).getTagName() + ").");
            // drawNode((UnknownNode) node);
            drawNode(node.getLeft(), spaces + 4);
            drawNode(node.getRight(), spaces);
        } else if (node instanceof LinkNode) {
            cursor.startBlock("Times New Roman", 16, Color.BLUE, Cursor.FontStyle.UNDERLINE);
            drawNode(node.getLeft(), spaces);
            cursor.endBlock();
            drawNode(node.getRight(), spaces);
        } else if (node instanceof ItalicNode) {
            cursor.startBlock(Cursor.FontStyle.ITALICIZE);
            drawNode(node.getLeft(), spaces);
            cursor.endBlock();
            drawNode(node.getRight(), spaces);
        }
    }

    private void drawNode(ImageNode node) {
        // this has quotes on it for some reason
        String url = node.getAttribute("src").getValue();
        // missing protocol or something idk
        url = "http:" + url.substring(1, url.length() - 1); 
        cursor.drawImage(url);
    }

}
