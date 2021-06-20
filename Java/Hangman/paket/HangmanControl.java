package paket;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HangmanControl
  implements ActionListener
{
  private HangmanGUI gui;
  private HangmanData str;
  private HangmanGUIEingabe gui_eingabe;
  private HangmanGUIZeichnung gui_zeichnung;

  public HangmanControl()
  {
    this.gui = new HangmanGUI(this);
    this.gui_eingabe = new HangmanGUIEingabe(this);
    this.str = new HangmanData();
    this.gui_zeichnung = new HangmanGUIZeichnung(this.str);

    this.gui.setOutput(this.str.getgesucht());
    this.gui.setVisible(true);
    this.gui_eingabe.setVisible(true);
    this.gui_zeichnung.setVisible(true);
  }

  public void actionPerformed(ActionEvent event)
  {
    String what = event.getActionCommand();

    if (what.equals("Neu")) {
      this.str.reset();
    }
    else if (!what.equals("Info"))
    {
      if (what.equals("Beenden")) {
        System.exit(0);
      }
      else if (what.equals("Neues Wort")) {
        String n = this.gui_eingabe.getInputWort();
        this.str.setwort(n);
        String m = this.gui_eingabe.getInputBedeutung();
        this.str.setbedeutung(m);
        this.str.umwandeln();
        this.gui_eingabe.setInputResetExtern();
        this.gui.setInputReset();
      }
      else if (what.equals("Reset")) {
        this.str.reset();
      }
      else if (what.equals("A")) {
        this.str.tastatur_buchstabe('A', 'a');
      }
      else if (what.equals("B")) {
        this.str.tastatur_buchstabe('B', 'b');
      }
      else if (what.equals("C")) {
        this.str.tastatur_buchstabe('C', 'c');
      }
      else if (what.equals("D")) {
        this.str.tastatur_buchstabe('D', 'd');
      }
      else if (what.equals("E")) {
        this.str.tastatur_buchstabe('E', 'e');
      }
      else if (what.equals("F")) {
        this.str.tastatur_buchstabe('F', 'f');
      }
      else if (what.equals("G")) {
        this.str.tastatur_buchstabe('G', 'g');
      }
      else if (what.equals("H")) {
        this.str.tastatur_buchstabe('H', 'h');
      }
      else if (what.equals("I")) {
        this.str.tastatur_buchstabe('I', 'i');
      }
      else if (what.equals("J")) {
        this.str.tastatur_buchstabe('J', 'j');
      }
      else if (what.equals("K")) {
        this.str.tastatur_buchstabe('K', 'k');
      }
      else if (what.equals("L")) {
        this.str.tastatur_buchstabe('L', 'l');
      }
      else if (what.equals("M")) {
        this.str.tastatur_buchstabe('M', 'm');
      }
      else if (what.equals("N")) {
        this.str.tastatur_buchstabe('N', 'n');
      }
      else if (what.equals("O")) {
        this.str.tastatur_buchstabe('O', 'o');
      }
      else if (what.equals("P")) {
        this.str.tastatur_buchstabe('P', 'p');
      }
      else if (what.equals("Q")) {
        this.str.tastatur_buchstabe('Q', 'q');
      }
      else if (what.equals("R")) {
        this.str.tastatur_buchstabe('R', 'r');
      }
      else if (what.equals("S")) {
        this.str.tastatur_buchstabe('S', 's');
      }
      else if (what.equals("T")) {
        this.str.tastatur_buchstabe('T', 't');
      }
      else if (what.equals("U")) {
        this.str.tastatur_buchstabe('U', 'u');
      }
      else if (what.equals("V")) {
        this.str.tastatur_buchstabe('V', 'v');
      }
      else if (what.equals("W")) {
        this.str.tastatur_buchstabe('W', 'w');
      }
      else if (what.equals("X")) {
        this.str.tastatur_buchstabe('X', 'x');
      }
      else if (what.equals("Y")) {
        this.str.tastatur_buchstabe('Y', 'y');
      }
      else if (what.equals("Z")) {
        this.str.tastatur_buchstabe('Z', 'z');
      }
      else if (what.equals("ß")) {
        this.str.tastatur_buchstabe('ß', 'ß');
      }
      else if (what.equals("Ü")) {
        this.str.tastatur_buchstabe('Ü', 'ü');
      }
      else if (what.equals("Ä")) {
        this.str.tastatur_buchstabe('Ä', 'ä');
      }
      else if (what.equals("Ö")) {
        this.str.tastatur_buchstabe('Ö', 'ö');
      }
      else if (what.equals("Wort setzen")) {
        String n = this.gui.getInputWort();
        this.str.setwort(n);
        String m = this.gui.getInputBedeutung();
        this.str.setbedeutung(m);
        this.str.umwandeln();
        this.gui.setInputReset();
        this.gui_eingabe.setInputResetExtern();
      }
    }
    this.gui.setOutput(this.str.getgesucht());
    this.gui_zeichnung.repaint();
    this.str.pruefegewonnen();
  }
}