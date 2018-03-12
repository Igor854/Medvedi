package medvedi;

import asciiPanel.AsciiPanel;

import java.awt.Color;

public enum Tile {
        FLOOR((char)250, AsciiPanel.yellow),
        WALL((char)177, AsciiPanel.yellow),
        BOUNDS('x', AsciiPanel.brightBlack);

        char glyph;
        Color color;

        public char getGlyph() {
                return glyph;
        }

        public Color getColor() {
                return color;
        }
        Tile(char glyph, Color color) {
                this.glyph = glyph;
                this.color = color;
        }
}