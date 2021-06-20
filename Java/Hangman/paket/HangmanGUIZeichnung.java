package paket;

import javax.swing.JFrame;

public class HangmanGUIZeichnung extends JFrame
{
  private HangmanZeichnung hz;

  public HangmanGUIZeichnung(HangmanData newStr)
  {
    super("Fehler");
    super.setLayout(null);
    this.hz = new HangmanZeichnung(newStr);

    super.setBounds(200, 600, 200, 200);

    super.add(this.hz);
    this.hz.setBounds(0, 30, 200, 200);
  }
}