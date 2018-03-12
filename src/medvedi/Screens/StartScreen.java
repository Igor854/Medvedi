package medvedi.Screens;

import asciiPanel.AsciiPanel;
import medvedi.Options;

import javax.sound.sampled.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;

public class StartScreen implements Screen {
    Options options;
    public StartScreen(Options options) {
        this.options = options;
    }
    public StartScreen() {
        playSound("start.wav");
        options = new Options();
    }

    public void goOutput(AsciiPanel terminal) {
        terminal.writeCenter("Medvedi", 1);
        terminal.writeCenter("PRESS ENTER TO START", 9, AsciiPanel.brightYellow);
        terminal.writeCenter("o - options", 18);
    }
    public Screen react(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_ENTER: return new PlayScreen(options);
            case KeyEvent.VK_O: return new OptionsScreen(options);
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