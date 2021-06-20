package paket;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class HangmanGUI extends JFrame
{
  private JLabel gesucht;
  private JPanel p;
  private JPanel pt;
  private JPanel ps;
  private JTextField wort;
  private JTextField bedeutung;
  private JButton a;
  private JButton b;
  private JButton c;
  private JButton d;
  private JButton e;
  private JButton f;
  private JButton g;
  private JButton h;
  private JButton i;
  private JButton j;
  private JButton k;
  private JButton l;
  private JButton m;
  private JButton n;
  private JButton o;
  private JButton _p;
  private JButton q;
  private JButton r;
  private JButton s;
  private JButton t;
  private JButton u;
  private JButton v;
  private JButton w;
  private JButton x;
  private JButton y;
  private JButton z;
  private JButton ae;
  private JButton oe;
  private JButton ue;
  private JButton sz;
  private HangmanData str;

  public HangmanGUI(ActionListener lstnr)
  {
    super("Hangman");
    super.setLocation(200, 200);
    super.setDefaultCloseOperation(3);

    JMenuBar mb = createMenue(lstnr);
    super.setJMenuBar(mb);

    this.gesucht = new JLabel();
    this.gesucht.setFont(new Font("Arial", 0, 21));
    this.gesucht.setBorder(new EmptyBorder(30, 30, 30, 30));
    super.add(this.gesucht, "Center");

    JPanel p = createControlPanel(lstnr);
    super.add(p, "South");

    super.pack();
  }

  private JMenuBar createMenue(ActionListener listen)
  {
    JMenuBar newMb = new JMenuBar();

    JMenu m = new JMenu("Programm");
    JMenuItem mi = new JMenuItem("Neu");
    mi.addActionListener(listen);
    m.add(mi);
    mi = new JMenuItem("Info");
    mi.addActionListener(listen);
    m.add(mi);
    mi = new JMenuItem("Beenden");
    mi.addActionListener(listen);
    m.add(mi);
    newMb.add(m);

    m = new JMenu("Aktion");
    mi = new JMenuItem("Hinweis");
    mi.addActionListener(listen);
    m.add(mi);
    mi = new JMenuItem("Neues Wort");
    mi.addActionListener(listen);
    m.add(mi);
    mi = new JMenuItem("Reset");
    mi.addActionListener(listen);
    m.add(mi);
    newMb.add(m);

    return newMb;
  }

  private JPanel createControlPanel(ActionListener listen)
  {
    JPanel p = new JPanel();
    JPanel pt = new JPanel();
    JPanel ps = new JPanel();

    p.setLayout(new GridLayout(2, 1));
    ps.setLayout(new GridLayout(1, 3));
    pt.setLayout(new GridLayout(3, 10));

    ps.setBorder(new TitledBorder("Steuerung"));
    pt.setBorder(new TitledBorder("Tastatur"));

    JButton a = new JButton("A");
    a.addActionListener(listen);
    pt.add(a);
    JButton b = new JButton("B");
    b.addActionListener(listen);
    pt.add(b);
    JButton c = new JButton("C");
    c.addActionListener(listen);
    pt.add(c);
    JButton d = new JButton("D");
    d.addActionListener(listen);
    pt.add(d);
    JButton e = new JButton("E");
    e.addActionListener(listen);
    pt.add(e);
    JButton f = new JButton("F");
    f.addActionListener(listen);
    pt.add(f);
    JButton g = new JButton("G");
    g.addActionListener(listen);
    pt.add(g);
    JButton h = new JButton("H");
    h.addActionListener(listen);
    pt.add(h);
    JButton i = new JButton("I");
    i.addActionListener(listen);
    pt.add(i);
    JButton j = new JButton("J");
    j.addActionListener(listen);
    pt.add(j);
    JButton k = new JButton("K");
    k.addActionListener(listen);
    pt.add(k);
    JButton l = new JButton("L");
    l.addActionListener(listen);
    pt.add(l);
    JButton m = new JButton("M");
    m.addActionListener(listen);
    pt.add(m);
    JButton n = new JButton("N");
    n.addActionListener(listen);
    pt.add(n);
    JButton o = new JButton("O");
    o.addActionListener(listen);
    pt.add(o);
    JButton _p = new JButton("P");
    _p.addActionListener(listen);
    pt.add(_p);
    JButton q = new JButton("Q");
    q.addActionListener(listen);
    pt.add(q);
    JButton r = new JButton("R");
    r.addActionListener(listen);
    pt.add(r);
    JButton s = new JButton("S");
    s.addActionListener(listen);
    pt.add(s);
    JButton t = new JButton("T");
    t.addActionListener(listen);
    pt.add(t);
    JButton u = new JButton("U");
    u.addActionListener(listen);
    pt.add(u);
    JButton v = new JButton("V");
    v.addActionListener(listen);
    pt.add(v);
    JButton w = new JButton("W");
    w.addActionListener(listen);
    pt.add(w);
    JButton x = new JButton("X");
    x.addActionListener(listen);
    pt.add(x);
    JButton y = new JButton("Y");
    y.addActionListener(listen);
    pt.add(y);
    JButton z = new JButton("Z");
    z.addActionListener(listen);
    pt.add(z);
    JButton ae = new JButton("Ä");
    ae.addActionListener(listen);
    pt.add(ae);
    JButton oe = new JButton("Ö");
    oe.addActionListener(listen);
    pt.add(oe);
    JButton ue = new JButton("Ü");
    ue.addActionListener(listen);
    pt.add(ue);
    JButton sz = new JButton("ß");
    sz.addActionListener(listen);
    pt.add(sz);

    this.wort = new JTextField("Geben sie hier das zu suchende Wort an");
    this.wort.addActionListener(listen);
    ps.add(this.wort);
    this.bedeutung = new JTextField("Nennen sie hier ggf. die Bedeutung des Wortes");
    this.bedeutung.addActionListener(listen);
    ps.add(this.bedeutung);
    JButton setzen = new JButton("Wort setzen");
    setzen.addActionListener(listen);
    ps.add(setzen);

    p.add(pt);
    p.add(ps);
    return p;
  }

  public String getOutput()
  {
    return this.gesucht.getText();
  }

  public String getInputWort()
  {
    String actual = this.wort.getText();

    return actual;
  }

  public String getInputBedeutung() {
    String actual = this.bedeutung.getText();

    return actual;
  }

  public void setOutput(String how)
  {
    this.gesucht.setText(how);
    super.pack();
  }

  public void setInputReset() {
    this.wort.setText("Geben sie hier das NEUE zu suchende Wort an");
    this.bedeutung.setText("Nennen sie hier die NEUE Bedeutung des Wortes");
    super.pack();
  }
}