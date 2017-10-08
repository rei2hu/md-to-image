package htmlrenderer;

import java.awt.*;
import java.util.Stack;

class StyleManager {

    private Stack<StyleSetting> styleHistory;

    public StyleManager() {
        styleHistory = new Stack<>();
        styleHistory.push(StyleSetting.DEFAULT);
    }


    public void addStyle(Font font, Color color) {
        styleHistory.push(new StyleSetting(font, color));
    }

    public void addStyle(StyleSetting style) {
        styleHistory.push(style);
    }

    public StyleSetting getStyle() {
        return styleHistory.peek();
    }

    public void resetStyle() {
        styleHistory.pop();
    }
}
