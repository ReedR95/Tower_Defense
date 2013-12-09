import java.awt.*;
import java.awt.event.*;

public class KeyHandler implements MouseMotionListener, MouseListener{

    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {
        Window.store.click(e.getButton());
    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mouseDragged(MouseEvent e) {
        Window.mouse = new Point((e.getX()),
                                 (e.getY()));
    }

    public void mouseMoved(MouseEvent e) {
        Window.mouse = new Point((e.getX()),
                                 (e.getY()));
    }
}
