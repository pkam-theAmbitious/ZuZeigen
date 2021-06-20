package paket;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class HangmanGUIEingabe extends JFrame
{
  private JPasswordField wort2;
  private JTextField bedeutung2;
  private JButton neuesWort;
  private HangmanData str;

  public HangmanGUIEingabe(ActionListener lstnr2)
  {
    super("Wort eingabe");
    super.setLocation(400, 600);

    JPanel p = new JPanel();
    p.setLayout(new GridLayout(1, 3));

    this.wort2 = new JPasswordField("Wort");
    this.wort2.addActionListener(lstnr2);
    p.add(this.wort2);
    this.bedeutung2 = new JTextField("Nennen sie hier ggf. die Bedeutung des Wortes");
    this.bedeutung2.addActionListener(lstnr2);
    p.add(this.bedeutung2);
    JButton neuesWort = new JButton("Neues Wort");
    neuesWort.addActionListener(lstnr2);
    p.add(neuesWort);

    super.add(p, "Center");

    super.pack();
  }

  public String getInputWort()
  {
    String actual = this.wort2.getText();

    return actual;
  }

  public String getInputBedeutung() {
    String actual = this.bedeutung2.getText();

    return actual;
  }

  public void setInputResetExtern() {
    this.wort2.setText("Geben sie hier das NEUE zu suchende Wort an");
    this.bedeutung2.setText("Nennen sie hier die NEUE Bedeutung des Wortes");
    super.pack();
  }
}