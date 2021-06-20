package paket;

import javax.swing.JOptionPane;

public class HangmanData
{
  private String content_inhalt;
  private String wort_inhalt;
  private String bedeutung_inhalt;
  private String eingaben;
  private int fehler;

  public HangmanData()
  {
    reset();
    this.fehler = 0;
  }

  public void reset()
  {
    this.wort_inhalt = "Kein Wort gew�hlt";
    this.eingaben = " ";
    this.fehler = 0;
    umwandeln();
  }
  public void umwandeln() {
    this.content_inhalt = "";
    int word_length = this.wort_inhalt.length();

    for (int i = 0; i < word_length; i++) {
      char c1 = this.wort_inhalt.charAt(i);
      if (this.eingaben.indexOf(c1) == -1) {
        this.content_inhalt += "_";
      }
      else
        this.content_inhalt += c1;
    }
  }

  public void pruefegewonnen() {
    if (this.wort_inhalt.equals(this.content_inhalt)) {
      JOptionPane.showMessageDialog(null, "Herzlichen Gl�ckwunsch! Sie haben das Wort " + this.wort_inhalt + " herausgefunden! Die Bedeutung des Wortes ist: " + this.bedeutung_inhalt);
      reset();
    }
    else if (this.fehler >= 11) {
      JOptionPane.showMessageDialog(null, "Herzlichen Gl�ckwunsch! Sie haben das Spiel verloren (W�hlen sie ein neues)");
      reset();
    }
  }

  public void tastatur_buchstabe(char c1, char c2) { int word_length = this.wort_inhalt.length();
    int anzahl = 0;
    for (int i = 0; i < word_length; i++) {
      if (this.wort_inhalt.charAt(i) == c1) {
        anzahl++;
      }
      if (this.wort_inhalt.charAt(i) == c2) {
        anzahl++;
      }
    }
    if (anzahl == 0) {
      this.fehler += 1;
    }
    else {
      this.eingaben = (this.eingaben + c1 + c2);
      umwandeln();
    } }

  public void setgesucht(String content_inhalt)
  {
    this.content_inhalt = content_inhalt;
  }
  public String getgesucht() {
    return this.content_inhalt;
  }

  public void setwort(String wort_inhaltneu) {
    this.wort_inhalt = wort_inhaltneu;
    this.eingaben = " ";
    umwandeln();
  }
  public String getWort() {
    return this.wort_inhalt;
  }

  public void setbedeutung(String bedeutung_inhalt) {
    this.bedeutung_inhalt = bedeutung_inhalt;
  }
  public int getFehler() {
    return this.fehler;
  }
}