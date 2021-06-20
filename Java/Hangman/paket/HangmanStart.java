package paket;

import javax.swing.SwingUtilities;

public class HangmanStart
  implements Runnable
{
  public void run()
  {
    HangmanControl ctrl = new HangmanControl();
  }

  public static void main(String[] args)
  {
    HangmanStart prog = new HangmanStart();
    SwingUtilities.invokeLater(prog);
  }
}