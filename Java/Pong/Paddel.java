package arcTec.pong;


import java.util.Random;

import ledControl.BoardController;

/**
 * @author ArcTec
 * @version 0.01
 */

public class Paddel {
	private int x,y;
	private int laenge, pos;
	private int red,green,blue;
	private BoardController control;
	private Spielfeld feld;
	private boolean kiAktiv;
	private Random rand;


	/**
	 * @param x: x Koordinate des Paddels
	 * @param y: y Koordinate des Paddels
	 * @param laenge: Laenge des Paddels
	 * @param control: Der als Referenz übergebene BoardController 
	 * @param feld: Das als Referenz übergebene feld
	 * @param red: roter Farbwert des Paddels
	 * @param green: gruener Farbwert des Paddels
	 * @param blue: blauer Farbwert des Paddels
	 * @param pos: Position des Paddels: 1 = Links, 2  = Rechts
	 */
	public Paddel(int x, int y , int laenge, BoardController control, Spielfeld feld, int red, int green, int blue, int pos){
		this.x = x;
		this.y = y;
		this.laenge = laenge;
		this.control = control;
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.feld = feld;
		this.pos = pos;
		
		rand = new Random();


		for(int i = 0; i<laenge; i++){
			if(x < control.getHeight()-1)
				control.setColor(x, y+i, red, green, blue);
		}
		control.checkColors();
		control.updateLedStripe();

	}
	
	/**
	 * Zeichnet das Paddel ab der aktuellen y Kooridnate + laenge neu
	 * Wahlweise mit oder ohne update des LED Boards
	 * @param update Steuert, ob das Paddel neu gezeichnet werden soll 
	 */
	public void repaint (boolean update){
		for (int i = 1; i<11;i++){
			control.setColor(x, i, 0, 0 ,0);
		}
		for(int i = 0; i<laenge; i++){
			if(x < control.getHeight()-1)
				control.setColor(x, y+i, red, green, blue);
		}
		if(update)
			control.updateLedStripe();
	}
	/**
	 * Setzt das Paddel ein Pixel weiter nach oben
	 */
	public void nachOben(){
		if (y > feld.getMinHoehe()){
			if(feld.getBall().getX() != x){
			y--;
			control.setColor(x, y+laenge, 0, 0, 0);
			}
		}
	}


	/**
	 * Setzt das Paddel ein Pixel weiter nach unten
	 */
	public void nachUnten(){
		if (y+laenge < feld.getMaxHoehe()+1){
			if(feld.getBall().getX() != x){
			y++;
			control.setColor(x, y-1, 0, 0, 0);
			}
		}
	}



	/**
	 * Methode die für die Tätigkeit der COM-Spieler zuständig ist. Das Paddel richtet sich dabei ab
	 * einer bestimmten (1= 6 und 2 = 5) Stelle X auf die Koordinate des Balls aus. Um sicherzustellen, dass
	 * das Spiel nicht ewig läuft wird die Stelle bei jede Aufruf um einen zufallsgenerierten Wert in Richtung
	 * Paddel verschoben, sodass ab einer gewissen Stelle der Platz nicht ausreicht um sich nach dem Ball auszurichten
	 * Wenn der Ball hinter/Vor der Stelle ist bewegt sich das Paddel wieder in die Mitte
	 * @param ballX: Momentane x Koordinate des Balls
	 * @param ballY: Momentane y Koordinate des Balls
	 */
	public void ausrichten(int ballX,int ballY) {
		int aktivierungsStelle1 =  (feld.getSchwierigkeit() == 2) ? 6 - rand.nextInt(3) : 6;
		int aktivierungsStelle2 =  (feld.getSchwierigkeit() == 2) ? 5 + rand.nextInt(3): 5;
		if ( (pos == 1 && aktivierungsStelle1  > ballX) || (pos == 2 && aktivierungsStelle2 < ballX) ){
			if (y+1 > ballY){
				nachOben();
				repaint(false);
			}else if (y + 1 < ballY){
				nachUnten();
				repaint(false);
			}
		}else{
			if(y+1 > feld.getMaxHoehe()/2){
				nachOben();
				repaint(false);
			}else if (y+1 < feld.getMaxHoehe()/2){
				nachUnten();
				repaint(false);
			}
		}
	}


	//													<!------------------Getter und Setter-------------->
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getLaenge() {
		return laenge;
	}

	public int getPos() {
		return pos;
	}

	public boolean istAktiv() {
		return kiAktiv;
	}
	public void setAktiv( boolean aktiv) {
		this.kiAktiv = aktiv;
	}

}

