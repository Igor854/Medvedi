package medvedi;

import java.util.ArrayList;

public class World {
    Tile[][] kletki;
    int height;
    int width;
    public Player player;
    public ArrayList<Bear> bears;
    public Treasure treasure;
    public boolean[][] razvedannie;

    World(Tile[][] kletki, Player player, Treasure treasure, ArrayList<Bear> bears, boolean[][] razvedannie) {
        this.kletki = kletki;
        width = kletki.length;
        height = kletki[0].length;
        this.player = player;
        this.treasure = treasure;
        this.bears = bears;
        this.razvedannie = razvedannie;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Tile getTile(int x, int y){
        if (x < 0 || x >= width || y < 0 || y >= height)
            return Tile.BOUNDS;
        else
            return kletki[x][y];
    }

    public char getGlyph(int x, int y) {
        if(razvedannie[x][y] == true) {
            return getTile(x, y).getGlyph();
        }
        return ' ';
    }

    public int getPlayerCoordX() {
        return player.getCoordX();
    }
    public int getPlayerCoordY() {
        return player.getCoordY();
    }
    public int getTreasureCoordX() {
        return treasure.getCoordX();
    }
    public int getTreasureCoordY() {
        return treasure.getCoordY();
    }
    public int getBearCoordX(int index) {
        return bears.get(index).getCoordX();
    }
    public int getBearCoordY(int index) {
        return bears.get(index).getCoordY();
    }
}