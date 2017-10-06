package htmlrenderer;

import java.awt.*;

class StyleSetting {

    Font font;
    Color color;

    static final StyleSetting DEFAULT = new StyleSetting(new Font("Times new Roman", Font.PLAIN, 16), Color.BLACK);

    StyleSetting(Font font, Color color) {
        this.font = font;
        this.color = color;
    }

    public StyleSetting clone() {
        return new StyleSetting(font, color);
    }

    public StyleSetting setColor(Color color) {
        this.color = color;
        return this;
    }

    public StyleSetting setFont(Font font) {
        this.font = font;
        return this;
    }

}
