package medvedi.Screens;

import asciiPanel.AsciiPanel;
import medvedi.Options;

import java.awt.event.KeyEvent;

public class OptionsScreen implements Screen {
    Options options;
    OptionsScreen(Options options) {
        this.options = options;
    }

    public void goOutput(AsciiPanel terminal) {
        terminal.writeCenter("Options", 1);
        if(options.openMap == true) {
            terminal.write("Map is open:                yes (default no)", 1, 3);
        } else {
            terminal.write("Map is open:                no (default no)", 1, 3);
        }
        if(options.god == true) {
            terminal.write("God mode:                   yes (default no)", 1, 4);
        } else {
            terminal.write("God mode:                   no (default no)", 1, 4);
        }
        if(options.blind == true) {
            terminal.write("Blind bears:                yes (default no)", 1, 5);
        } else {
            terminal.write("Blind bears:                no (default no)", 1, 5);
        }
        if(options.numberOfBears == 2) {
            terminal.write("Bears:                      2 (default 2)", 1, 6);
        } else {
            terminal.write("Bears:                      1 (default 2)", 1, 6);
        }
        if(options.search == true) {
            terminal.write("Draw bear search algorithm: yes (default no)", 1, 7);
        } else {
            terminal.write("Draw bear search algorithm: no (default no)", 1, 7);
        }
        terminal.write("m - open/close map", 1, 12);
        terminal.write("g - on/off godmode", 1, 13);
        terminal.write("b - on/off blind bears", 1, 14);
        terminal.write("n - number of bears", 1, 15);
        terminal.write("s - on/off draw bear search algorithm", 1, 16);
        terminal.writeCenter("esc - go to menu", 18);
    }
    public Screen react(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE: return new StartScreen(options);
            case KeyEvent.VK_B: options.blind = options.blind == true ? false : true; break;
            case KeyEvent.VK_M: options.openMap = options.openMap == true ? false : true; break;
            case KeyEvent.VK_G: options.god = options.god == true ? false : true; break;
            case KeyEvent.VK_N: options.numberOfBears = options.numberOfBears == 2 ? 1 : 2; break;
            case KeyEvent.VK_S: if(options.search == true) {
                options.search = false;
            } else {
                options.search = true;
                options.numberOfBears = 1;
                options.openMap = true;
                options.god = true;
                options.blind = true;
            } break;
        }
        return this;
    }
}