package medvedi.Screens;

import asciiPanel.AsciiPanel;

import java.awt.event.KeyEvent;

public interface Screen  {
        void goOutput(AsciiPanel terminal);
        Screen react(KeyEvent e);
}