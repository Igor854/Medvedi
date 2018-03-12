package medvedi;

import javafx.util.Pair;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class WorldBuilder {
    Tile[][] kletki;
    boolean[][] razvedannie;
    int height;
    int width;
    Player player;
    ArrayList<Bear> bears = new ArrayList<>();
    Treasure treasure;
    ArrayList<Pair> svobodnieKletki;


    public WorldBuilder(int width, int height) {
        this.width = width;
        this.height = height;
        this.kletki = new Tile[width][height];
    }

    public Tile getTile(int x, int y){
        if (x < 0 || x >= width || y < 0 || y >= height)
            return Tile.BOUNDS;
        else
            return kletki[x][y];
    }

    public World build() {
        return new World(kletki, player, treasure, bears, razvedannie);
    }

    public WorldBuilder fillTiles() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (Math.random() < 0.4) {
                    kletki[x][y] = Tile.WALL;
                } else {
                    kletki[x][y] = Tile.FLOOR;
                }
            }
        }
        return this;
    }

    public WorldBuilder createLevel(int numberOfBears, boolean openMap) {                                               //генерировать до тех пор пока сокровище не будет  в пределах досягаемости медведей и игрока
        WorldBuilder worldBuilder;

        do {
            do {
                worldBuilder = fillTiles().getSvobodnieKletki().placePlayer().placeTreasure();
            } while (worldBuilder == null);
            worldBuilder = worldBuilder.placeBear(numberOfBears);
        } while (worldBuilder == null);
        for (int i = 0; i < bears.size(); i++) {                                                                        // если проигрывание поместить в конструктор, в отброшенных вариантах лабиринта тоже будут
            if(bears.get(i).sostoyanie == bears.get(i).PRESLEDOVAT) {                                                   //проигрываться звуки
                playSound("near.wav");
            }
        }
        player.xPred = player.getCoordX();                                                                              // для того, чтобы точка, которая указывает предыдущий ход, не появлялась в неправ. месте в начале игры
        player.yPred = player.getCoordY();
        razvedannie = new boolean[width][height];
        if(openMap == true) {
            for (int x = 0; x < razvedannie.length; x++) {
                for (int y = 0; y < razvedannie[0].length; y++) {
                    razvedannie[x][y] = true;
                }
            }
    }
        return worldBuilder;
    }
//при этой реализации медведь и сокровище могут появиться рядом с героем
    public WorldBuilder getSvobodnieKletki() {
        ArrayList<Pair> svobodnieKletki = new ArrayList<Pair>();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if(kletki[x][y] == Tile.FLOOR) {
                    Pair <Integer, Integer> pair = new Pair<>(x, y);
                    svobodnieKletki.add(pair);
                }
            }
        }
        this.svobodnieKletki = svobodnieKletki;
        return this;
    }

    public WorldBuilder placePlayer() { //ставим игрока
        int index = (int) (Math.random()*svobodnieKletki.size());
        Pair<Integer, Integer> pair = svobodnieKletki.get(index);
        player = new Player(pair.getKey(), pair.getValue());
        svobodnieKletki.remove(index);
        return this;
    }

    public WorldBuilder placeTreasure() { //ставим сокровище
        int index = (int) (Math.random()*svobodnieKletki.size());
        Pair<Integer, Integer> pair = svobodnieKletki.get(index);
        treasure = new Treasure(pair.getKey(), pair.getValue());
        if(!isProhodimo(kletki, player, treasure)) {
            return null;
        }
        svobodnieKletki.remove(index);
        return this;
    }

    public WorldBuilder placeBear(int times) {
        for (int i = 0; i < times; i++) {
            int index = (int) (Math.random()*svobodnieKletki.size());
            Pair<Integer, Integer> pair = svobodnieKletki.get(index);
            bears.add(new Bear(pair.getKey(), pair.getValue(), player));
            if(Math.random() < 0.5) {
                if(isProhodimo(kletki, bears.get(i), treasure) || isProhodimo(kletki, bears.get(i), player)) {
                } else { bears.clear(); return null; }
            } else {
                if(isProhodimo(kletki, bears.get(i), player) || isProhodimo(kletki, bears.get(i), treasure)) {
                } else { bears.clear(); return null; }
            }

            svobodnieKletki.remove(index);
        }
        return this;
    }

    boolean isProhodimo (Tile[][] kletki, Creature a, Creature b) {                                                     // использует излишнюю функцию isProhodimo2, нужен ли новый класс? не использую удобный getTile
        Tile[][] tempKletki = new Tile[kletki.length][kletki[0].length]; // скопировали во временный массив
        for (int i = 0; i < kletki.length; i++) {
            for (int j = 0; j < kletki[0].length; j++) {
                tempKletki[i][j] = kletki[i][j];
            }
        }
        int ax = a.getCoordX(); // костыль
        int ay = a.getCoordY();
        int bx = b.getCoordX();
        int by = b.getCoordY();
        boolean u;
        u = isNashel(tempKletki, ax, ay, bx, by);
        return u;
    }

    boolean isNashel(Tile[][] tempKletki, int ax, int ay, int bx, int by) {
        if(ax == bx && ay == by) {
            return true;
        }

        tempKletki[ax][ay] = Tile.WALL;

        if(ax < width - 1 && tempKletki[ax + 1][ay] == Tile.FLOOR) {
            if(isNashel(tempKletki, ax + 1, ay, bx, by)) {
                return true;
            }
        }

        if(ay < height - 1 && tempKletki[ax][ay + 1] == Tile.FLOOR) {
            if(isNashel(tempKletki, ax, ay + 1, bx, by)) {
                return true;
            }
        }

        if(ax > 0 && tempKletki[ax - 1][ay] == Tile.FLOOR) {
            if(isNashel(tempKletki, ax - 1, ay, bx, by)) {
                return true;
            }
        }

        if(ay > 0 && tempKletki[ax][ay - 1] == Tile.FLOOR) {
            if(isNashel(tempKletki, ax, ay - 1, bx, by)) {
                return true;
            }
        }

        return false;
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