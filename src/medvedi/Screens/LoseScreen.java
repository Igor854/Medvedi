package medvedi.Screens;

import asciiPanel.AsciiPanel;
import medvedi.Options;
import medvedi.World;

import javax.sound.sampled.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;

public class LoseScreen implements Screen {
    Options options;
    World world;
    public LoseScreen(Options options, World world) {
        this.options = options;
        this.world = world;
        playSound("lose.wav");
    }
    public void goOutput(AsciiPanel terminal) {
        for (int x = 0; x < world.getWidth(); x++) {
            for (int y = 0; y < world.getHeight(); y++) {
                terminal.write(world.getTile(x, y).getGlyph(), x, y, AsciiPanel.white);                                 // здесь убрать разведку
            }
        }
        terminal.write("T", world.getTreasureCoordX(), world.getTreasureCoordY(), AsciiPanel.white);
        for (int i = 0; i < world.bears.size(); i++) {
            if (isAtePlayer(i)) {
                terminal.write((char) 31, world.getBearCoordX(i), world.getBearCoordY(i), AsciiPanel.brightRed);
            } else {
                terminal.write((char) 31, world.getBearCoordX(i), world.getBearCoordY(i), AsciiPanel.white);
            }
            terminal.write("You lose! The bear ate you!", 22, 7, AsciiPanel.brightRed);
            terminal.write("esc   - go to menu", 22, 10);
            terminal.write("r     - restart game", 22, 11);
            for (int j = 0; j < 20; j++) {
                terminal.write((char) 177, 20, j, AsciiPanel.white);
            }
        }
    }

    boolean isAtePlayer(int index) {
        if(world.getPlayerCoordX() == world.getBearCoordX(index) && world.getPlayerCoordY() == world.getBearCoordY(index)) {
            return true;
        } else return false;
    }

    public Screen react(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_R:
                return new PlayScreen(options);
            case KeyEvent.VK_ESCAPE:
                return new StartScreen(options);
            default: return this;
        }
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