package org.game.fonts;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Text {

    private Map<Integer, Font> fonts;

    public Text() {
        loadFonts();
    }

    private void loadFonts() {
        int[] sizes = {12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32, 34, 36, 38, 40, 42, 44, 46, 48};
        fonts = new HashMap<>();

        for(int s : sizes) {
            fonts.put(s, new Font("Monaco", Font.BOLD, s));
        }
    }

    public void draw(String text, Graphics2D g2, int size, int x, int y, Color color) {
        g2.setColor(color);
        g2.setFont(fonts.get(size));
        g2.drawString(text, (float)x, (float)y);
    }
}
