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
    private Font font = new Font("Times New Roman", Font.PLAIN, 12);
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

        x_offset = horizPad;
        y_offset = vertPad + wordHeight();

        g.setFont(font);
        g.setColor(color);
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
        if (image != null)
            g.drawImage(image, xPad, y_offset, width, height, null);
        else
            System.out.println("couldnt ge timage");
        y_offset += wordHeight() + yPad + height;
    }

    public void drawBullet(int spaces, int size) {
        Color temp = g.getColor();
        g.setColor(Color.BLACK);
        g.fillRect(xPad + (spaces + 10) * spaceWidth(), y_offset - ((wordHeight() - size)/ 2), size, size);
        x_offset = (spaces + 10) * spaceWidth() + xPad + spaceWidth() + size + 3;
        list = true;
    }

    public void drawLine(int width) {
        Color temp = g.getColor();
        g.setColor(Color.GRAY);
        g.fillRect(xPad, y_offset - (wordHeight() / 2), image.getWidth() - xPad, width);
        y_offset += wordHeight() + xPad;
        g.setColor(temp);
    }
    
    public void writeText(String text, boolean inline, int spaces) {
        
        if (strong)
            g.setFont(g.getFont().deriveFont(Font.BOLD));
        if (u)
            g.setFont(g.getFont().deriveFont(underline));

        String[] words = text.split(" ");
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                             RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        int startX = x_offset;
        int startY = y_offset;
        int index = 0;
        // System.out.printf("Started on x=%d, y=%d inline=%b \n", x_offset, y_offset, inline);
        String line = "";
        /*
        if (inline) {
            line = words[index++];
            while ((index < words.length) && 
                (metrics.stringWidth(line + " " + words[index])) < image.getWidth() - startX - xPad) {
                    line = line + " " + words[index++];
                }
            g.drawString(line, startX, startY);
            startY += wordHeight() + yPad;
            startX += metrics.stringWidth(line);
        }
        line = "";
        
        if (!inline || (index < words.length && metrics.stringWidth(line + " " + words[index]) >= image.getWidth() - startX - xPad))
        */
        if (!list)
            startX = xPad + spaces * spaceWidth();
        list = false;
        while (index < words.length) {
            line = words[index++];
            while ((index < words.length) && 
                (metrics.stringWidth(line + " " + words[index])) < image.getWidth() - startX - xPad) {
                    line = line + " " + words[index++];
                }    
            g2d.drawString(line, startX, startY);
            startY += wordHeight() + yPad;
            startX = xPad + spaces * spaceWidth();
        }
        x_offset = startX; // + metrics.stringWidth(line);
        y_offset = startY; // startY - wordHeight() - yPad;
        // System.out.printf("Ended on x=%d, y=%d \n", x_offset, y_offset);
    }

    public void underlineNext() {
        u = true;
    }

    public void boldNext() {
        strong = true;
    }
}
