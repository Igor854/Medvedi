package medvedi;

public class Vspomog {

    public boolean isTreasureFound(Player player, Treasure treasure) {
        if(player.getCoordX() == treasure.getCoordX() && player.getCoordY() == treasure.getCoordY()) {
            return true;
        }
        return false;
    }

    public boolean isPlayerEaten(Player player, Bear bear) {
        if(player.getCoordX() == bear.getCoordX() && player.getCoordY() == bear.getCoordY()) {
            return true;
        }
        return false;
    }

    public void razvedat(World world) {
        world.razvedannie[world.getPlayerCoordX()][world.getPlayerCoordY()] = true;
        if(world.getTile(world.getPlayerCoordX() + 1, world.getPlayerCoordY()) != Tile.BOUNDS) {
            world.razvedannie[world.getPlayerCoordX() + 1][world.getPlayerCoordY()] = true;
        }
        if(world.getTile(world.getPlayerCoordX(), world.getPlayerCoordY() + 1) != Tile.BOUNDS) {
            world.razvedannie[world.getPlayerCoordX()][world.getPlayerCoordY() + 1] = true;
        }
        if(world.getTile(world.getPlayerCoordX() - 1, world.getPlayerCoordY()) != Tile.BOUNDS) {
            world.razvedannie[world.getPlayerCoordX() - 1][world.getPlayerCoordY()] = true;
        }
        if(world.getTile(world.getPlayerCoordX(), world.getPlayerCoordY() - 1) != Tile.BOUNDS) {
            world.razvedannie[world.getPlayerCoordX()][world.getPlayerCoordY() - 1] = true;
        }
    }

    public boolean isPlayerNear(Player player, Bear bear) {
        if (player.getCoordX() == bear.getCoordX() + 1 && player.getCoordY() == bear.getCoordY()) {
            return true;
        }
        if (player.getCoordX() == bear.getCoordX() && player.getCoordY() == bear.getCoordY() + 1) {
            return true;
        }
        if (player.getCoordX() == bear.getCoordX() - 1 && player.getCoordY() == bear.getCoordY()) {
            return true;
        }
        if (player.getCoordX() == bear.getCoordX() && player.getCoordY() == bear.getCoordY() - 1) {
            return true;
        }
        return false;
    }

    public boolean isBearThroughTile(int i, World world) {
        int nachX = world.getPlayerCoordX();
        int nachY = world.getPlayerCoordY();
        if(world.player.goRight(world) == true) {
            if(isBearNear(i, world)) {
                world.player.setCoordX(nachX);
                world.player.setCoordY(nachY);
                return true;
            }
            world.player.setCoordX(nachX);
            world.player.setCoordY(nachY);
        }
        if(world.player.goDown(world) == true) {
            if(isBearNear(i, world)) {
                world.player.setCoordX(nachX);
                world.player.setCoordY(nachY);
                return true;
            }
            world.player.setCoordX(nachX);
            world.player.setCoordY(nachY);
        }
        if(world.player.goLeft(world) == true) {
            if(isBearNear(i, world)) {
                world.player.setCoordX(nachX);
                world.player.setCoordY(nachY);
                return true;
            }
            world.player.setCoordX(nachX);
            world.player.setCoordY(nachY);
        }
        if(world.player.goUp(world) == true) {
            if(isBearNear(i, world)) {
                world.player.setCoordX(nachX);
                world.player.setCoordY(nachY);
                return true;
            }
            world.player.setCoordX(nachX);
            world.player.setCoordY(nachY);
        }
        return false;
    }

    public boolean isBearNear(int i, World world) {
        if(world.getPlayerCoordX() == world.getBearCoordX(i) + 1 && world.getPlayerCoordY() == world.getBearCoordY(i)) {
            return true;
        }
        if(world.getPlayerCoordX() == world.getBearCoordX(i) && world.getPlayerCoordY() == world.getBearCoordY(i) + 1) {
            return true;
        }
        if(world.getPlayerCoordX() == world.getBearCoordX(i) - 1 && world.getPlayerCoordY() == world.getBearCoordY(i)) {
            return true;
        }
        if(world.getPlayerCoordX() == world.getBearCoordX(i) && world.getPlayerCoordY() == world.getBearCoordY(i) - 1) {
            return true;
        }
        return false;
    }

    public boolean isTreasureNear(World world) {
        if(world.getPlayerCoordX() == world.getTreasureCoordX() + 1 && world.getPlayerCoordY() == world.getTreasureCoordY()) {
            return true;
        }
        if(world.getPlayerCoordX() == world.getTreasureCoordX() && world.getPlayerCoordY() == world.getTreasureCoordY() + 1) {
            return true;
        }
        if(world.getPlayerCoordX() == world.getTreasureCoordX() - 1 && world.getPlayerCoordY() == world.getTreasureCoordY()) {
            return true;
        }
        if(world.getPlayerCoordX() == world.getTreasureCoordX() && world.getPlayerCoordY() == world.getTreasureCoordY() - 1) {
            return true;
        }
        return false;
    }

    public boolean isBearsOnOneTile(World world) {                                                                      //корректно работает только с 2 медведями
        if(world.bears.size() > 1) {
            if (world.getBearCoordX(0) == world.getBearCoordX(1) && world.getBearCoordY(0) == world.getBearCoordY(1)) {
                return true;
            }
        }
        return false;
    }
}
