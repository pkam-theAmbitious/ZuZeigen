package paket;

import java.awt.Graphics;
import javax.swing.JPanel;

public class HangmanZeichnung extends JPanel
{
  private HangmanData str;

  public void paintComponent(Graphics g)
  {
    if (this.str.getFehler() >= 1) {
      g.fillArc(0, 75, 100, 75, 180, -180);
    }
    if (this.str.getFehler() >= 2) {
      g.drawLine(50, 75, 50, 0);
    }
    if (this.str.getFehler() >= 3) {
      g.drawLine(50, 0, 175, 0);
    }
    if (this.str.getFehler() >= 4) {
      g.drawLine(50, 25, 75, 0);
    }
    if (this.str.getFehler() >= 5) {
      g.drawLine(175, 0, 175, 12);
    }
    if (this.str.getFehler() >= 6) {
      g.drawOval(170, 12, 13, 13);
    }
    if (this.str.getFehler() >= 7) {
      g.drawLine(175, 25, 175, 60);
    }
    if (this.str.getFehler() >= 8) {
      g.drawLine(175, 60, 160, 75);
    }
    if (this.str.getFehler() >= 9) {
      g.drawLine(175, 60, 190, 75);
    }
    if (this.str.getFehler() >= 10) {
      g.drawLine(175, 35, 160, 15);
    }
    if (this.str.getFehler() >= 11)
      g.drawLine(175, 35, 190, 15);
  }

  public HangmanZeichnung(HangmanData newStr)
  {
    this.str = newStr;
  }
  public void neuMalen() {
    repaint();
  }
}