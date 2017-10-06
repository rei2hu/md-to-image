package htmlrenderer;

import java.awt.*;
import java.util.Stack;

class StyleManager {

    private Stack<StyleSetting> styleHistory;

    public StyleManager() {
        styleHistory = new Stack<StyleSetting>();
        styleHistory.push(StyleSetting.DEFAULT);
    }

    public void addStyle(Font font, Color color) {
        styleHistory.push(new StyleSetting(font, color));
    }

    public void setFont(Font font) {
        if (!styleHistory.isEmpty()) {
            StyleSetting style = styleHistory.peek();
            styleHistory.push(style.clone().setFont(font));
        }
    }

    public void setColor(Color color) {
        if (!styleHistory.isEmpty()) {
            StyleSetting style = styleHistory.peek();
            styleHistory.push(style.clone().setColor(color));
        }
    }

    public StyleSetting getStyle() {
        return styleHistory.peek();
    }

    public void resetStyle() {
        styleHistory.pop();
    }
}
