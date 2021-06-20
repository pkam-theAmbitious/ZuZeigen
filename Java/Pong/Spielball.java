package arcTec.pong;

import java.util.Random;

import ledControl.BoardController;

/**
 * @author ArcTec
 * @version 0.01
 */

public class Spielball {
	private int x, y;
	public int xa, ya;
	private int red, green, blue;
	private int bevoreRed, bevoreGreen, bevoreBlue;

	private BoardController control;
	private Spielfeld feld;
	private Paddel paddelLinks, paddelRechts;
	private Random rand;


	/**
	 * Intialisiert alle Variablen und Objekte und ruft eine Methode auf die den Ball zeichnet;
	 * @param x: x Koordinate des Paddels
	 * @param y: y Koordinate des Paddels
	 * @param control: Der als Referenz übergebene BoardController 
	 * @param feld: Das als Referenz übergebene Spielfeld
	 * @param red: roter Farbwert des Paddels
	 * @param green: gruener Farbwert des Paddels
	 * @param blue: blauer Farbwert des Paddels
	 */
	public Spielball(int x, int y, BoardController control, Spielfeld feld, int red, int green, int blue){
		rand = new Random();
		this.x = x;
		this.y = y;
		this.control = control;
		this.feld = feld;
		this.red = red;
		bevoreRed = red;
		this.green = green;
		bevoreGreen = green;
		this.blue = blue;
		bevoreBlue = blue;
		xa = 1;
		ya = 1;

		paddelLinks = feld.getPaddelLinks();
		paddelRechts = feld.getPaddelRechts();

		this.neuerBall(x, y);
	}

	
	/**
	 * Methode die denn Ball bewegt und dabei auf eine Kollision mit den Rändern prüft und notfalls
	 * die Richtung des Balls aendert oder einen treffer ausloest.
	 * Zu Beginn wird die Methode kollisionPruefen() aufgerufen!
	 */
	public void moveBall (){
		kollisionPruefen();

		int colors[][][] = control.getColors();
		if (colors[x][y][0] == red && colors[x][y][1] == green && colors[x][y][2] == blue){
			control.setColor(x, y, 0, 0, 0);
		}
		if (colors[x][y][0] == bevoreRed && colors[x][y][1] == bevoreGreen && colors[x][y][2] == bevoreBlue){
			control.setColor(x, y, 0, 0, 0);
		}
		x+= xa;
		y+= ya;

		if (y+ya >= 11 || y+ya<= 0){
			ya = -ya;
		}if (x<= 0){
			feld.treffer(1);
		}
		if(x>= 11){
			feld.treffer(2);
		}

		if (paddelLinks.istAktiv() && feld.isSpielLaeuft()){
			paddelLinks.ausrichten(x,y);
		}
		if (paddelRechts.istAktiv() && feld.isSpielLaeuft()){
			paddelRechts.ausrichten(x,y);

		}

		if (feld.isSpielLaeuft())
			control.setColor(x, y, red, green, blue);
		control.updateLedStripe();
		control.sleep(300);



	}	

	/**
	 * Ueberprueft ob der Ball mit einem Paddel kollidieren wird und aendert im entsprechenden Fall
	 * die Richtung des Balls.
	 * Dabei wird der Ball je nach auftreffen des Balls unterschiedlich "stark" abgelenkt (Damit sich die
	 * Bewegung nicht (allzu) schnell wiederholt)
	 */
	public void kollisionPruefen(){
		Paddel paddelLinks = feld.getPaddelLinks();
		Paddel paddelRechts = feld.getPaddelRechts();
		int maxBreite = feld.getMaxBreite();
		int minBreite = feld.getMinBreite();

		if (xa >  0){
			int paddelY = paddelRechts.getY();

			if(x + xa == maxBreite){
				if (y + ya == paddelY ){
					control.setColor(x, y, 0, 0, 0);
					int varYa = (Integer.compare(ya, 0) < 0) ? -1 + ya : 1 + ya;
					if (y + varYa >= 10){
						y = 9;
					}
					else if (y + varYa <= 1){
						y = 2;
					}else{
						y += varYa;
					}
					xa = -xa;
					x += -1;
					changeColor(127, 0, 0);
				}else if(y + ya == paddelY+1){
					xa = -xa;
					changeColor(127, 0, 0);
				}else if(y + ya == paddelY+2){
					control.setColor(x, y, 0, 0, 0);
					int varYa = (Integer.compare(ya, 0) < 0) ? -1 + ya : 1 + ya;
					if (y + varYa >= 10){
						y = 9;
					}
					else if (y + varYa <= 1){
						y = 2;
					}else{
						y += varYa;
					}

					xa = -xa;
					x += -1;
					changeColor(127, 0, 0);
				}
			}
		}
		if (xa <  0){
			int paddelY = paddelLinks.getY();

			if(x + xa == minBreite){
				if (y + ya == paddelY){
					control.setColor(x, y, 0, 0, 0);
					int varYa = (Integer.compare(ya, 0) < 0) ? -1 + ya : 1 + ya;
					if (y + varYa >= 10){
						y = 9;
					}
					else if (y + varYa <= 1){
						y = 2;
					}else{
						y += varYa;
					}

					xa = -xa;
					x += 1;
					changeColor(0, 127, 0);
				}else if(y + ya == paddelY+1){
					xa = -xa;
					changeColor(0, 127, 0);
				}else if(y + ya == paddelY+2){
					control.setColor(x, y, 0, 0, 0);
					int varYa = (Integer.compare(ya, 0) < 0) ? -1 + ya : 1 + ya;
					if (y + varYa >= 10){
						y = 9;
					}
					else if (y + varYa <= 1){
						y = 2;
					}else{
						y += varYa;
					}

					xa = -xa;
					x += 1;
					changeColor(0, 127, 0);
				}
			}
		}
	}



	/**
	 * Erzeugt einen neuen Ball an der uebergebenen Stelle + 3 Felder nach oben oder unten
	 * Der Ball blinkt ein paar Mal auf bevor das Spiel startet.
	 * @param x: x Koordinate
	 * @param y: y Koordinate
	 */
	public void neuerBall(int x, int y) {
		int colors[][][] = control.getColors();
		if (colors[x][y][0] == red && colors[x][y][1] == green && colors[x][y][2] == blue){
			control.setColor(x, y, 0, 0, 0);
		}
		if (colors[x][y][0] == bevoreRed && colors[x][y][1] == bevoreGreen && colors[x][y][2] == bevoreBlue){
			control.setColor(x, y, 0, 0, 0);
		}

		this.x = x+rand.nextInt(1);
		this.y = 3+rand.nextInt(3);
		changeColor(127, 127, 127);
		for (int i = 0; i<5; i++){
			control.setColor(this.x, this.y,rand.nextInt(127), rand.nextInt(127), rand.nextInt(127));
			control.updateLedStripe();
			control.sleep(500);
		}
		control.setColor(this.x, this.y, red, green, blue);
		control.updateLedStripe();


	}

	/**
	 * Zeichnet den Spielball an der aktuellen Position, mit einer anderen Farbe, neu
	 * und spreichert den vorherigen Wert in einer anderen Variable.
	 * @param red Teil des RGB-Farbwertes
	 * @param green Teil des RGB-Farbwertes
	 * @param blue Teil des RGB-Farbwertes
	 */
	public void changeColor (int red, int green, int blue){
		bevoreRed = this.red;
		bevoreGreen  = this.green;
		bevoreBlue = this.blue;

		this.red = red; 
		this.green = green;
		this.blue = blue;
	}

	//													<!-------------------Getter und Setter---------------->

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getRed() {
		return red;
	}

	public int getGreen() {
		return green;
	}

	public int getBlue() {
		return blue;
	}

	public int getXa() {
		return xa;
	}

	public void setXa(int xa) {
		this.xa = xa;
	}
	public void setX(int x){
		this.x = x;
	}

	public int getYa() {
		return ya;
	}

	public void setYa(int ya) {
		this.ya = ya;
	}


}
