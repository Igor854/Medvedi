package medvedi;

import javafx.util.Pair;
import medvedi.Screens.PlayScreen;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

public class Bear extends Creature {
    public ArrayList<Pair> put = new ArrayList<>();
    public ArrayList<Pair> posesh = new ArrayList<>();
    final int ISSLEDOVAT = 0;
    final int PRESLEDOVAT = 1;
    int sostoyanie;
    int skolkoPresledovat;
    int playerX;
    int playerY;
    public boolean otstal;

    Bear(int x, int y, Player player) {
        super(x, y);
        if(isPlayerNear(player)) {
            sostoyanie = PRESLEDOVAT;
            skolkoPresledovat = (int) (Math.random() * 5) + 4;                   // от 4 до 8
            playerX = player.getCoordX();
            playerY = player.getCoordY();
        }
    }

    public void turn(World world, PlayScreen screen) {
        if(otstal) { otstal = false; }
        if(sostoyanie == ISSLEDOVAT) { issledovat(world, screen);}
        else if(sostoyanie == PRESLEDOVAT) { presledovat(world);}
    }

    public void issledovat(World world, PlayScreen screen) {
        Pair<Integer, Integer> pair = new Pair<>(x, y);
        posesh.add(pair);                                                                                               //текущую клетку добавить в посещенные
        if (!put.isEmpty()) {                                                                                           //если стоим на последней клетке пути - удалить эту клетку из массива
            if (x == put.get(0).getKey() && y == put.get(0).getValue()) {
                put.remove(0);
            }
        }
        ArrayList<Napravleniya> napravleniya = new ArrayList<>();
        napravleniya.add(Napravleniya.RIGHT);
        napravleniya.add(Napravleniya.DOWN);
        napravleniya.add(Napravleniya.LEFT);
        napravleniya.add(Napravleniya.UP);
        Collections.shuffle(napravleniya);
        if (isFloorAndNeposesh(napravleniya.get(0), world)) {
            goTo(napravleniya.get(0));
            put.add(0, pair);
        } else if (isFloorAndNeposesh(napravleniya.get(1), world)) {
            goTo(napravleniya.get(1));
            put.add(0, pair);
        } else if (isFloorAndNeposesh(napravleniya.get(2), world)) {
            goTo(napravleniya.get(2));
            put.add(0, pair);
        } else if (isFloorAndNeposesh(napravleniya.get(3), world)) {
            goTo(napravleniya.get(3));
            put.add(0, pair);
        } else {
            if (put.isEmpty()) {                                                                                        //либо стартанул в тупике (в текущей версии невозможно), либо посетил все клетки
                posesh.clear();                                                                                         //тогда очистить все посещенные клетки, и попытаться ходить снова
                issledovat(world, screen);
                return;
            }
            x = (Integer) put.get(0).getKey();
            y = (Integer) put.get(0).getValue();
        }
        if (!screen.options.blind) {                                                                                    //чит слепые медведи
            if(isPlayerNear(world)) {
                playSound("near.wav");
                sostoyanie = PRESLEDOVAT;
                put.clear();
                posesh.clear();
                skolkoPresledovat = (int) (Math.random() * 5) + 4;                                                      // от 4 до 8
                playerX = world.getPlayerCoordX();
                playerY = world.getPlayerCoordY();
            }
        }
    }

    public boolean isNeposesh(Integer x, Integer y, ArrayList<Pair> poseh) {
        Pair<Integer, Integer> pair = new Pair<>(x, y);
        for (Pair<Integer, Integer> pair2 :
                posesh) {
            if (pair.equals(pair2)) {
                return false;
            }
        }
        return true;
    }

    void goTo(Napravleniya napravlenie) {
        switch(napravlenie) {
            case RIGHT: x++; break;
            case DOWN: y++; break;
            case LEFT: x--; break;
            case UP: y--; break;
        }
    }

    boolean isFloorAndNeposesh(Napravleniya tile, World world) {
        switch (tile) {
            case RIGHT:
                if (world.getTile(x + 1, y) == Tile.FLOOR && isNeposesh(x + 1, y, posesh)) {
                    return true;
                } break;
            case DOWN:
                if (world.getTile(x, y + 1) == Tile.FLOOR && isNeposesh(x, y + 1, posesh)) {
                    return true;
                } break;
            case LEFT:
                if (world.getTile(x - 1, y) == Tile.FLOOR && isNeposesh(x - 1, y, posesh)) {
                    return true;
                } break;
            case UP:
                if (world.getTile(x, y - 1) == Tile.FLOOR && isNeposesh(x, y - 1, posesh)) {
                    return true;
                } break;
        }
        return false;
    }

    boolean isPlayerNear(Player player) {
        if (player.getCoordX() == x + 1 && player.getCoordY() == y) {
            return true;
        }
        if (player.getCoordX() == x && player.getCoordY() == y + 1) {
            return true;
        }
        if (player.getCoordX() == x - 1 && player.getCoordY() == y) {
            return true;
        }
        if (player.getCoordX() == x && player.getCoordY() == y - 1) {
            return true;
        }
        return false;
    }

    boolean isPlayerNear(World world) {
        if (world.getPlayerCoordX() == x + 1 && world.getPlayerCoordY() == y) {
            return true;
        }
        if (world.getPlayerCoordX() == x && world.getPlayerCoordY() == y + 1) {
            return true;
        }
        if (world.getPlayerCoordX() == x - 1 && world.getPlayerCoordY() == y) {
            return true;
        }
        if (world.getPlayerCoordX() == x && world.getPlayerCoordY() == y - 1) {
            return true;
        }
        return false;
    }

    void presledovat (World world){
        if(skolkoPresledovat == 0) {                                                                                    //медведь пропускает ход, если закончились ходы преследования, и заново обшаривает лабиринт
            otstal = true;
            sostoyanie = ISSLEDOVAT;
            return;
        }
        x = playerX;
        y = playerY;
        playerX = world.getPlayerCoordX();
        playerY = world.getPlayerCoordY();
        skolkoPresledovat--;
    }

    void playSound(String sound) {                                                                                      // подставить в параметр имя трека
        try {
            URL url = this.getClass().getClassLoader().getResource(sound);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
