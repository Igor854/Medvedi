package medvedi.Screens;

import asciiPanel.AsciiPanel;
import medvedi.*;

import javax.sound.sampled.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;

public class PlayScreen implements Screen {
        World world;
        Vspomog vspomog;
        public Options options;

    PlayScreen(Options options) {
        this.options = options;
        world = new WorldBuilder(20, 20).createLevel(options.numberOfBears, options.openMap).build();
        vspomog = new Vspomog();
        vspomog.razvedat(world);
    }

    public void goOutput(AsciiPanel terminal) {
        for (int x = 0; x < world.getWidth(); x++) {
            for (int y = 0; y < world.getHeight(); y++) {
                terminal.write(world.getGlyph(x, y), x, y, world.getTile(x, y).getColor());                             // здесь убрать разведку
            }
        }
        terminal.write((char)250, world.player.xPred, world.player.yPred, AsciiPanel.brightGreen);                      // отрисовать предыдущий ход
        if (options.search == true) {
            terminal.write("PRESS AND HOLD SPACE", 22,14, AsciiPanel.brightBlue);
            terminal.write("t - on/off draw thread of search", 22,15, AsciiPanel.brightRed);
            for (int i = 0; i < world.bears.size(); i++) {
                for (int j = 0; j < world.bears.get(i).posesh.size(); j++) {
                    terminal.write((char)177, (int)world.bears.get(i).posesh.get(j).getKey(), (int)world.bears.get(i).posesh.get(j).getValue(), AsciiPanel.brightBlue);
                }
            }

            if (options.searchThread == true) {
                for (int i = 0; i < world.bears.size(); i++) {
                    for (int j = 0; j < world.bears.get(i).put.size(); j++) {
                        terminal.write((char)177, (int)world.bears.get(i).put.get(j).getKey(), (int)world.bears.get(i).put.get(j).getValue(), AsciiPanel.brightRed);
                    }
                }
            }
        }
        if(options.openMap == true) {
            terminal.write("T", world.getTreasureCoordX(), world.getTreasureCoordY(), AsciiPanel.brightYellow);
            terminal.write("@", world.getPlayerCoordX(), world.getPlayerCoordY(), AsciiPanel.brightWhite);
            for (int i = 0; i < world.bears.size(); i++) {
                terminal.write((char)31, world.getBearCoordX(i), world.getBearCoordY(i), AsciiPanel.brightRed);
                if(vspomog.isBearsOnOneTile(world)) {
                    terminal.write((char)31, world.getBearCoordX(i), world.getBearCoordY(i), AsciiPanel.red);
                    terminal.write("Bears on one tile", 22, 6, AsciiPanel.brightYellow);
                }
            }
        } else {
            terminal.write("@", world.getPlayerCoordX(), world.getPlayerCoordY(), AsciiPanel.brightWhite);
            for (int i = 0; i < world.bears.size(); i++) {
                if(vspomog.isBearNear(i, world)) {
                    if(vspomog.isBearsOnOneTile(world)) {
                        terminal.write((char)31, world.getBearCoordX(i), world.getBearCoordY(i), AsciiPanel.red);
                        terminal.write("Bears on one tile", 22, 6);
                    } else {
                        terminal.write((char) 31, world.getBearCoordX(i), world.getBearCoordY(i), AsciiPanel.brightRed);
                    }
                }
            }
            if(vspomog.isTreasureNear(world)) {
                terminal.write("T", world.getTreasureCoordX(), world.getTreasureCoordY(), AsciiPanel.brightYellow);
            }
        }
        for (int i = 0; i < world.bears.size(); i++) {
            if(world.bears.get(i).otstal) {
                terminal.write("You broke away from the bear!", 22, 4, AsciiPanel.brightCyan);
            }
        }
        for (int i = 0; i < world.bears.size(); i++) {
            if(vspomog.isBearThroughTile(i, world)) {
                terminal.write("A bear is somewhere nearby!", 22, 5, Color.ORANGE);
                break;                                                                                                  // не отрисовываем несколько раз подряд, если несколько медведей поблизости
            }
        }
        for (int i = 0; i < world.bears.size(); i++) {
            if(vspomog.isBearNear(i, world)) {
                terminal.write("RUN!", 22, 3, AsciiPanel.brightRed);
                break;
            }
        }

        for (int i = 0; i < world.bears.size(); i++) {
            if(vspomog.isBearThroughTile(i, world)) {
                if(!world.bears.get(i).otstal) {
                    playSound("through.wav");
                    break;
                }
            }
        }
        terminal.write("wasd also works", 22, 8);
        terminal.write("space - skip a move", 22, 9);
        terminal.write("esc   - go to menu", 22,10);
        terminal.write("r     - restart game", 22,11);
        terminal.write("PRESS", 22, 12, AsciiPanel.brightYellow); terminal.write('X', 28, 12, AsciiPanel.brightCyan); terminal.write("TO WIN", 30, 12, AsciiPanel.brightYellow);
        for (int i = 0; i < 20; i++) {
            terminal.write((char)177, 20, i, AsciiPanel.yellow);
        }
    }
    //занятые y: 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 14, 15

    @Override
    public Screen react(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_R:
                return new PlayScreen(options);
            case KeyEvent.VK_ESCAPE:
                return new StartScreen(options);
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                if(world.player.goDown(world) == true) {                                                                //если попробовал наступить на стену или границы - попробовать еще раз
                    world.player.yPred = world.player.getCoordY() - 1;                                                  //обрабатываю отображение предыдущего кода
                    world.player.xPred = world.player.getCoordX();
                    break;
                } else return this;
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                if(world.player.goUp(world) == true) {
                    world.player.yPred = world.player.getCoordY() + 1;
                    world.player.xPred = world.player.getCoordX();
                    break;
                } else return this;
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                if(world.player.goLeft(world) == true) {
                    world.player.yPred = world.player.getCoordY();
                    world.player.xPred = world.player.getCoordX() + 1;
                    break;
                } else return this;
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                if(world.player.goRight(world) == true) {
                    world.player.yPred = world.player.getCoordY();
                    world.player.xPred = world.player.getCoordX() - 1;
                    break;
                } else return this;
            case KeyEvent.VK_SPACE:
                break;
            case KeyEvent.VK_X:
                return new WinScreen(options, world);
            case KeyEvent.VK_T: options.searchThread = options.searchThread == true ? false : true; return this;
            default: return this;
        }
        vspomog.razvedat(world);
        if(vspomog.isTreasureFound(world.player, world.treasure)) {                                                     // особенность - если игрок наступил на клетку с сокровищем и медведем - победил
            return new WinScreen(options, world);
        }
        for (int i = 0; i < world.bears.size(); i++) {
            if (!options.god) {                                                                                         //если не включено бессмертие - активировать проигрышные условия
                if(vspomog.isPlayerNear(world.player, world.bears.get(i))) {                                            // если игрок после своего хода оказался рядом с медведем - очевидно, что медведь следующим ходом
                    world.bears.get(i).setCoordX(world.getPlayerCoordX());                                              // его съест
                    world.bears.get(i).setCoordY(world.getPlayerCoordY());
                    return new LoseScreen(options, world);
                }
                if(vspomog.isPlayerEaten(world.player, world.bears.get(i))) {                                           // если игрок своим ходом наступил на медведя - проиграл
                    return new LoseScreen(options, world);
                }
            }
            world.bears.get(i).turn(world, this);                                                                // задумано, что в этой функции медведь никогда не сможет наступить на игрока
        }
        return this;
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