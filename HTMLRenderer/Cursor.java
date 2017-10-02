package HTMLRenderer;

import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Color;
import java.awt.RenderingHints;

import javax.imageio.ImageIO;
import java.net.URL;

import java.awt.font.TextAttribute;

// this class will handle all text 'writing'
public class Cursor {
    
    static java.util.Hashtable<TextAttribute, Object> underline = new java.util.Hashtable<>();
    static {
        underline.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
    }
    private int x_offset = 0;
    private int y_offset = 0;

    private int xPad = 0;
    private int yPad = 0;

    private BufferedImage image;
    private FontMetrics metrics;
    private Font font = new Font("Times New Roman", Font.PLAIN, 16);
    private Color color = Color.BLACK;
    private Graphics g;

    private boolean u = false;
    private boolean strong = false;
    private boolean list = false;

    public Cursor(BufferedImage i, int horizPad, int vertPad) {
        xPad = horizPad;
        yPad = vertPad;

        image = i;
        g = image.getGraphics();
        metrics = g.getFontMetrics();
        
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, image.getWidth(), image.getHeight());

        x_offset = horizPad;
        y_offset(vertPad + wordHeight());

        g.setFont(font);
        g.setColor(color);
    }

    private void y_offset(int y) {
        y_offset = y;
        if (y_offset + wordHeight() > image.getHeight()) {
            BufferedImage temp = image;
            image = new BufferedImage(temp.getWidth(), (int) (y_offset * 1.5), 
                BufferedImage.TYPE_INT_RGB);
            g = image.getGraphics();
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, image.getWidth(), image.getHeight());
            g.drawImage(temp, 0, 0, temp.getWidth(), temp.getHeight(), null);
            g.setColor(color);
            g.setFont(font);
        }
    }

    private int spaceWidth() {
        return metrics.stringWidth(" ");
    }

    private int wordHeight() {
        return metrics.getHeight();
    }

    public void drawImage(String url) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new URL(url));
        } catch(java.io.IOException e) {

        }
        int height = image.getHeight();
        int width = image.getWidth();
        if (image.getWidth() > this.image.getWidth() - xPad * 2) {
            double scalingFactor = 1.0 * width / (this.image.getWidth() - xPad * 2);
            height = (int) (height / scalingFactor);
            width = (int) (width / scalingFactor);
        }
        int temp = y_offset; 
        y_offset(y_offset + wordHeight() + yPad + height);
        if (image != null)
            g.drawImage(image, xPad, temp, width, height, null);
        else
            System.out.println("couldnt ge timage");
    }

    public void drawBullet(int spaces, int size) {
        Color temp = g.getColor();
        g.setColor(Color.BLACK);
        g.fillRect(xPad + (spaces) * spaceWidth(), y_offset - ((wordHeight() - size)/ 2), size, size);
        x_offset = (spaces) * spaceWidth() + xPad + spaceWidth() + size + 3;
    }

    public BufferedImage getImage() {
        return image.getSubimage(0, 0, image.getWidth(), y_offset + yPad);
    }

    public void drawLine(int width) {
        Color temp = g.getColor();
        g.setColor(Color.GRAY);
        g.fillRect(xPad, y_offset - (wordHeight() / 2), image.getWidth() -  2 * xPad, width);
        y_offset(y_offset + wordHeight() + xPad);
        g.setColor(temp);
    }
   
    public void setUnderline() {
        u = true;
    }

    public void setBold() {
        strong = true;
    }

    public void lineBreak(int spaces) {
        y_offset(y_offset + yPad + wordHeight());
        resetX(spaces);
    }

    public void resetX(int spaces) {
        x_offset = xPad + spaces * spaceWidth();
    }

    public void writeText(String text, int spaces) {
        
        // System.out.println("[" + spaces + "] " + text);

        Font temp = g.getFont();
        if (strong)
            g.setFont(g.getFont().deriveFont(Font.BOLD));
        if (u)
            g.setFont(g.getFont().deriveFont(underline));

        metrics = g.getFontMetrics();
        u = strong = false;

        String[] words = text.split(" ");
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                             RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
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
            g2d.drawString(line, startX, startY);
            y_offset(y_offset + wordHeight() + yPad);
            startY += wordHeight() + yPad; 
            startX = spaces * spaceWidth() + xPad;
        }
        if (newline) // if moved to a new line
            x_offset = metrics.stringWidth(line) + startX;
        else
            x_offset += metrics.stringWidth(line) + spaceWidth();
        // can break here if too much content
        y_offset(startY - wordHeight() - yPad); // startY - wordHeight() - yPad;
        // System.out.printf("end   x=%d, y=%d\n", x_offset, y_offset);
        g.setFont(temp);
        metrics = g.getFontMetrics();
    }

    public void underlineNext() {
        u = true;
    }

    public void boldNext() {
        strong = true;
    }
}
