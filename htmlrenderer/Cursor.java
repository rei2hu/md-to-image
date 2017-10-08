package htmlrenderer;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Color;
import java.awt.RenderingHints;

import javax.imageio.ImageIO;
import java.net.URL;

import java.awt.font.TextAttribute;

// this class will handle all text 'writing'
class Cursor {
    
    private static java.util.Hashtable<TextAttribute, Object> underline = new java.util.Hashtable<>();
    static {
        underline.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
    }

    enum FontStyle {
        BOLD, UNDERLINE, ITALICIZE, PLAIN;
    }

    private int x_offset = 0;
    private int y_offset = 0;

    private int xPad = 0;
    private int yPad = 0;

    private BufferedImage image;
    private FontMetrics metrics;

    private StyleManager styleManager;
    private Graphics2D g;

    // consider LinkNode stack to keep track of font/colors

    Cursor(BufferedImage i, int horizPad, int vertPad) {
        xPad = horizPad;
        yPad = vertPad;

        styleManager = new StyleManager();

        image = i;
        g = (Graphics2D) image.getGraphics();
        g.setFont(styleManager.getStyle().font);

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, image.getWidth(), image.getHeight());

        g.setColor(styleManager.getStyle().color);
        metrics = g.getFontMetrics();
        x_offset = horizPad;
        y_offset(vertPad + wordHeight());
    }

    public void endBlock() {
        styleManager.resetStyle();
        StyleSetting style = styleManager.getStyle();
        g.setFont(style.font);
        g.setColor(style.color);
        metrics = g.getFontMetrics();
    }

    public void startBlock() {
        styleManager.addStyle(styleManager.getStyle());
        // same style so no real need to update font or color or metrics
        // well really no reason to add the same style
    }

    private Font deriveFont(Font font, FontStyle fStyle) {
         switch (fStyle) {
            case BOLD:
                return font.deriveFont(Font.BOLD);
            case ITALICIZE:
                return font.deriveFont(Font.ITALIC);
            case UNDERLINE:
                return font.deriveFont(underline);
            default:
                return font;
        }
    }

    public void startBlock(FontStyle fontStyle) {
        Font font = styleManager.getStyle().font;
        Color color = styleManager.getStyle().color;
        font = deriveFont(font, fontStyle);
        styleManager.addStyle(font, color);
        g.setFont(font);
        g.setColor(color);
        metrics = g.getFontMetrics();
    }

    public void startBlock(String fontName, int fontSize, Color color) {
        startBlock(fontName, fontSize, color, FontStyle.PLAIN);
    }

    public void startBlock(String fontName, int fontSize, Color color, FontStyle fStyle) {
        Font newFont = new Font(fontName, Font.PLAIN, fontSize);
        newFont = deriveFont(newFont, fStyle);
        styleManager.addStyle(newFont, color);
        g.setFont(newFont);
        g.setColor(color);
        metrics = g.getFontMetrics();
    }

    private void y_offset(int y) {
        y_offset = y;
        if (y_offset + wordHeight() > image.getHeight()) {
            BufferedImage temp = image;
            image = new BufferedImage(temp.getWidth(), (int) (y_offset * 1.5), 
                BufferedImage.TYPE_INT_RGB);

            Color oldColor = g.getColor();
            Font oldFont = g.getFont();

            g = (Graphics2D) image.getGraphics();
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                             RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, image.getWidth(), image.getHeight());
            g.drawImage(temp, 0, 0, temp.getWidth(), temp.getHeight(), null);

            g.setColor(oldColor);
            g.setFont(oldFont);
            metrics = g.getFontMetrics();
        }

    }

    private int spaceWidth() {
        return metrics.stringWidth(" ");
    }

    private int wordHeight() {
        return metrics.getHeight();
    }

    void drawImage(String url) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new URL(url));
        } catch(java.io.IOException e) {

        }
        if (image != null) {
            int height = image.getHeight();
            int width = image.getWidth();
            if (image.getWidth() > this.image.getWidth() - xPad * 2) {
                double scalingFactor = 1.0 * width / (this.image.getWidth() - xPad * 2);
                height = (int) (height / scalingFactor);
                width = (int) (width / scalingFactor);
            }
            int temp = y_offset;
            y_offset(y_offset + wordHeight() + yPad + height);
            g.drawImage(image, xPad, temp, width, height, null);
        } else {
            int temp = y_offset;
            y_offset(y_offset + wordHeight() + yPad + 100);
            BufferedImage tempFailed = new BufferedImage(500, 200, BufferedImage.TYPE_INT_RGB);
            // looks liked drawstring doesnt care about linebreaks
            // makes sense i guess
            tempFailed.getGraphics().drawString("Couldn't load " + url, xPad, 100);
            g.drawImage(tempFailed, xPad, temp, this.image.getWidth() - xPad * 2, 200, null);

        }
    }

    void drawBullet(int spaces, int size) {
        Color temp = g.getColor();
        g.setColor(Color.BLACK);
        g.fillRect(xPad + (spaces - 2) * spaceWidth(), y_offset - ((wordHeight() - size)/ 2), size, size);
        // x_offset = (spaces) * spaceWidth() + xPad + spaceWidth();
        g.setColor(temp);
    }

    BufferedImage getImage() {
        return image.getSubimage(0, 0, image.getWidth(), y_offset + yPad);
    }

    void drawLine(int width) {
        Color temp = g.getColor();
        g.setColor(Color.GRAY);
        g.fillRect(xPad, y_offset - (wordHeight() / 2), image.getWidth() -  2 * xPad, width);
        y_offset(y_offset + wordHeight() + xPad);
        g.setColor(temp);
    }

    void lineBreak(int spaces) {
        y_offset(y_offset + yPad + wordHeight());
        resetX(spaces);
    }

    private void resetX(int spaces) {
        x_offset = xPad + spaces * spaceWidth();
    }

    void writeText(String text, int spaces) {
        // System.out.println(g.getColor() + ", " + g.getFont() + ": " + text);
        String[] words = text.split(" ", -1);
        // System.out.println(java.util.Arrays.toString(words));
        int startX = x_offset;
        int startY = y_offset;
        int index = 0;
        boolean newline = false;
        String line = ""; 
        // System.out.printf("start x=%d, y=%d\n", startX, startY);
        while (index < words.length) {
            line = words[index++];
            while ((index < words.length) && 
                (metrics.stringWidth(line + " " + words[index])) < image.getWidth() - startX - xPad) {
                    line = line + " " + words[index++];
                }
            newline = newline || index < words.length;
            g.drawString(line, startX, startY);
            y_offset(y_offset + wordHeight() + yPad);
            startY += wordHeight() + yPad; 
            startX = spaces * spaceWidth() + xPad;
        }
        if (newline) // if moved to LinkNode new line
            x_offset = metrics.stringWidth(line) + startX;
        else
            x_offset += metrics.stringWidth(line);
        // can break here if too much ContentNode
        y_offset(startY - wordHeight() - yPad); // startY - wordHeight() - yPad;
        // System.out.printf("end   x=%d, y=%d\n", x_offset, y_offset);
    }
}
