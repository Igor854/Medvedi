package medvedi;
import asciiPanel.AsciiPanel;
import medvedi.Screens.Screen;
import medvedi.Screens.StartScreen;

import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MainApplication extends JFrame implements KeyListener {
    private static final long id = 2813781237182312837L;
    Screen screen;
    AsciiPanel terminal;

    MainApplication() {
        super("Medvedi");
        terminal = new AsciiPanel(55, 20);
        add(terminal);
        pack();
        screen = new StartScreen();
        addKeyListener(this);
        repaint();
    }

    public void repaint() {
        terminal.clear();
        screen.goOutput(terminal);
        super.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        screen = screen.react(e);
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public static void main(String[] args) {
        MainApplication app = new MainApplication();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);
        app.setLocationRelativeTo(null);
    }
}