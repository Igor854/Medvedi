package medvedi;

public class Player extends Creature {
    public int xPred;
    public int yPred;
    Player(int x, int y) {
        super(x, y);
    }
    public boolean goDown(World world) {
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
