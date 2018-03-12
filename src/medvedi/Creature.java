package medvedi;

public class Creature {
    Integer x;
    Integer y;

    Creature(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getCoordX() {
        return x;
    }

    public int getCoordY() {
        return y;
    }
    public void setCoordX(int x) {
        this.x = x;
    }
    public void setCoordY(int y) {
        this.y = y;
    }

    public boolean goDown(World world) {                                                                                // недостаток, лишние проверки
        if(world.getTile(x, y + 1) == Tile.FLOOR) {
            y++;
            return true;
        }
        return false;
    }

    public boolean goUp(World world) {
        if(world.getTile(x, y - 1) == Tile.FLOOR) {
            y--;
            return true;
        }
        return false;
    }

    public boolean goLeft(World world) {
        if(world.getTile(x - 1, y) == Tile.FLOOR) {
            x--;
            return true;
        }
        return false;
    }

    public boolean goRight(World world) {
        if(world.getTile(x + 1, y) == Tile.FLOOR) {
            x++;
            return true;
        }
        return false;
    }
}
