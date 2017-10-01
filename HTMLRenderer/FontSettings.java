package HTMLRenderer;

import java.awt.Color;
import java.awt.Font;

class FontSettings {

    Color color = Color.BLACK;
    Font font = new Font("Times New Roman", Font.PLAIN, 12);

    public FontSettings() {

    }

    public FontSettings(String name, int style, int size) {
        font = new Font(name, style, size);
    }

    public FontSettings(Color color, Font font) {
        this.color = color;
        this.font = font;
    }

    public Font getFont() {
        return font;
    }

    public Color getColor() {
        return color;
    }

    public FontSettings clone() {
        return new FontSettings(this.color, this.font);
    }
}
