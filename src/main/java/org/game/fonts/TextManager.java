package org.game.fonts;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Color;

import java.util.HashMap;
import java.util.Map;

public class TextManager {

    private Map<Integer, Font> fonts;

    public TextManager() {
        loadFonts();
    }

    private void loadFonts() {
        int[] sizes = {12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32, 34, 36, 38, 40, 42, 44, 46, 48,
            50, 52, 54, 56, 58, 60, 62, 64, 66, 68, 70, 72, 74, 76, 78, 80, 82, 84, 86, 88, 90,
            92, 94, 96, 98, 100};
        fonts = new HashMap<>();

        for(int s : sizes) {
            fonts.put(s, new Font("Monaco", Font.BOLD, s));
        }
    }

    public void draw(Text text, Graphics2D g2) {
        g2.setColor(text.getColor());
        g2.setFont(fonts.get(text.getSize()));
        g2.drawString(text.getText(), (float)text.getX(), (float)text.getY());
    }

    public void draw(String txt, int x, int y, int size, Color color, Graphics2D g2) {
        g2.setColor(color);
        g2.setFont(fonts.get(size));
        g2.drawString(txt, (float)x, (float)y);
    }
}
