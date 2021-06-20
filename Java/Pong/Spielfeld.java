package arcTec.pong;

import java.awt.event.KeyEvent;

import javax.swing.JOptionPane;

import ledControl.BoardController;
import ledControl.gui.KeyBuffer;

/**
 * @author ArcTec
 * @version 0.01
 */

public class Spielfeld{
	private int mitte, laenge;
	private static BoardController control;
	private static KeyBuffer keyBuffer;
	private int punkteLinks , punkteRechts;
	private int minBreite;
	private int maxBreite;
	private int minHoehe;
	private int maxHoehe;
	private int punkteZumGewinn;
	private int schwierigkeit;
	private boolean spielLaeuft;

	Spielball ball; // wird in der main-Methode erstmalig gezeichnet und danach ueber kollisionPruefen() verwaltet
	Paddel paddelLinks, paddelRechts;




	/**
	 * Initalisiert beim Start des Programms alle Komponenten. Begonnen mit dem BoardController.
	 * Folgend alle Variablen und Klassen!
	 */
	public Spielfeld(){
		control = BoardController.getBoardController(); //BoardController  anfordern
	//	control.addNetworkHost("132.252.253.16");  //Verbindung zum LEDBoard-Server aufbauen
		control.resetColors(); //Zur Sicherheit alle Farben auf 0 setzen

		//Masse des Spielfeldes setzen
		maxHoehe = 10;
		minHoehe = 1;
		maxBreite = 10; //gesamtes Board - rand von 1 -> also leds 1 bis 10!!!
		minBreite = 1;
		
		//Spielende definieren
		punkteZumGewinn = 4; // ?
		
		//Masse zur Positionierung setzen
		laenge = 3;
		mitte = maxHoehe/2;

		//Lauftext anzeigen
		animationStartGame();
		
		//Spielfeld zeichnen
		spielfeldZeichnen();
		
		//Paddel initialisieren
		paddelLinks = new Paddel(minBreite, mitte-1, laenge, control, this, 0,127,0, 1);
		paddelRechts = new Paddel(maxBreite,mitte-1,laenge,control,this,  127, 0 ,0, 2);
		
		//Ball initalisieren erzeugen 
		ball = new Spielball (maxBreite/2, mitte,control, this, 127, 127, 127);
		
		//Zu Beginn Computersteuerung aktiv schalten
		schwierigkeit = 1; //Computer kann nicht verlieren
		paddelRechts.setAktiv(true);
		paddelLinks.setAktiv(true);
	}

	/**
	 * Main Methode die, durch den Compiler, als erstes aufgerufen wird, 
	 * die Hauptklasse intialisiert und das Spiel startet
	 * @param args Die Verwendung von Befehlszeilenargumenten wird hier nicht unterstuetzt
	 */
	public static void main(String[] args) {
		//Hauptklasse intialisieren
		Spielfeld feld = new Spielfeld();
		//Spiel aktiv schalten
		feld.spielLaeuft = true;
		//KeyBuffer anfordern
		keyBuffer = control.getKeyBuffer();
		//Hauptschleife: Sorgt dafuer, dass selbst das Spiel laeuft
		while (true){
			feld.update();
		}
	}

	/**
	 * Methode die fuer die Funktionalitaet des Programms sorgt.
	 * Wertet zuerst die vorhandenen Tastatureingaben aus, dann folgt
	 * die Aktualisierung des Spielballs (im Falle eines COM-Spielers wird dies hier ebenfalls ausgefuehrt)
	 */
	private void update(){
		manageKeyEvents(); //Auswertung Tastatureingaben
		if (spielLaeuft){
			//Nur wenn spielLaeuft wahr ist wird das Siel fortgesetzt
			ball.moveBall();
		}
	}

	
	/**
	 * Wertet alle im Buffer vorhanden KeyEvents aus, um
	 * 		a) den (wirklichen) Spielern eine bessere Chance zu bieten und
	 * 		b) in jedem Falle die Eingaben zur Steuerung des Spiels abzuarbeiten
	 * 		    (die vielleicht tiefer im Buffer sind)
	 * Steuert die Bewegung der Paddel (nur wenn die jeweilgen COM nicht aktiv sind) und
	 * Wertet die  Eingaben zur Steuerung des Status der COMs und der Intialisierung des Neustarts aus
	 */
	public void manageKeyEvents(){
		while(keyBuffer.eventsInBuffer() > 0){
			KeyEvent event = keyBuffer.pop(); //KeyEvent vom Buffer abfragen
			//Sicherstellen dass mindestens ein Paddel bewegt werden darf und das Spiel laeuft
			if((!paddelLinks.istAktiv() || !paddelRechts.istAktiv()) && (spielLaeuft)){
				if (event != null){
					 //Das Event muss durch das druecke einer Taste ausgeloest werden
					if(event.getID() == java.awt.event.KeyEvent.KEY_PRESSED){
						switch (event.getKeyCode()){
						case java.awt.event.KeyEvent.VK_UP: //Wenn Pfeiltaste nach oben -> Rechts Paddel nach oben
							if(!paddelRechts.istAktiv()){
								paddelRechts.nachOben();
								paddelRechts.repaint(true);
							}
							break;
						case java.awt.event.KeyEvent.VK_DOWN: //Wenn Pfeiltaste nach unten -> Rechtes Paddel nach unten
							if(!paddelRechts.istAktiv()){
								paddelRechts.nachUnten();
								paddelRechts.repaint(true);
							}
							break;
						case java.awt.event.KeyEvent.VK_W: //Wenn Buchstabe W -> Linkes Paddel nach oben
							if(!paddelLinks.istAktiv()){
								paddelLinks.nachOben();
								paddelLinks.repaint(true);
							}
							break;
						case java.awt.event.KeyEvent.VK_S: //Wenn Buchstabe S -> Linkes Paddel nach unten
							if(!paddelLinks.istAktiv()){
								paddelLinks.nachUnten();
								paddelLinks.repaint(true);
							}
							break;
						default:
						}
					}
				}
			}
			//Abfrage der Eingaben zur Steuerung des Spiels. Wird auch ausgefuehrt wenn das Spiel nicht laeuft oder die Paddel nicht bewegt werden koennen
			if (event != null){
				if(event.getID() == java.awt.event.KeyEvent.KEY_PRESSED){
					switch (event.getKeyCode()){
					case java.awt.event.KeyEvent.VK_1: //Linkes Paddel COM Status wechseln
						paddelLinks.setAktiv(!paddelLinks.istAktiv());
						System.out.println("COM-Player 1 ist nun: "+ paddelLinks.istAktiv());
						break;
					case java.awt.event.KeyEvent.VK_2: //Rechtes Paddel COM Status wechseln
						paddelRechts.setAktiv(!paddelRechts.istAktiv());
						System.out.println("COM-Player 2 ist nun: "+ paddelRechts.istAktiv());
						break;
					case java.awt.event.KeyEvent.VK_R: //Spiel neustarten
						System.out.println("Initialisiere Neustart...");
						restart();
						break;
					case java.awt.event.KeyEvent.VK_SPACE:
						schwierigkeit = (schwierigkeit == 1) ? 2 : 1;
						String ausgabe = (schwierigkeit == 1) ? "Die Schwierigkeit der COMs ist nun: unbesiegbar": "Die Schwierigkeit der COMs ist nun: besiegbar" ;
						System.out.println(ausgabe);
						break;
					default:
					}
				}
			}
		}
		}
	
		
		/**
		 * Startet das Spiel neu!
		 * Setzt alle Punkte auf 0
		 * Laesst das Spiel weiter laufen (boolean Wert setzen)
		 * Paddel und Ball in die Mitte setzen
		 * update neu aufrufen (nur zur Sicherheit)
		 */
		public void restart(){
			System.out.println("Alle Farben im Spielfeld auf 0 setzten");
			 //Alle Farben manuell auf 0 setzen. Die Methode des BoardControllers hat nicht funktioniert
			for (int i = 0; i<12; i++){
				for (int j = 0; j<12; j++){
					control.setColor(i, j, 0,0,0);
				}
			}
			control.updateLedStripe();
			System.out.println("         Fertig!");
			
			System.out.println("Punkte der Spieler auf 0 setzen");
			punkteLinks = 0;
			punkteRechts = 0;
			
			System.out.println("Spielfeld neu zeichnen");
			spielfeldZeichnen();
			System.out.println("        Fertig!");

			System.out.println("Paddel in die Mitte setzen");
			paddelLinks.setY((maxHoehe/2)-(paddelLinks.getLaenge()/2));
			paddelLinks.repaint(true);
			paddelRechts.setY((maxHoehe/2)-(paddelRechts.getLaenge()/2));
			paddelRechts.repaint(true);
			System.out.println("Ball neu zeichen");
			ball.neuerBall(maxBreite/2, maxHoehe/2);
			
			System.out.println("-Alles fertig-");
			 
			System.out.println("Starte Spiel Neu!");
			spielLaeuft = true;
			update();

		}


		/**
		 * Zaehlt einen punkt zu der uebergebenen Seite hinzu,
		 * setzt den Ball neu in die Mitte und zentriert die Paddel
		 * Zeigt außerdem die Punkte an
		 * @param position Das Paddel (die Seite) dem ein Punkt hinzugefügt werden soll (1 = Links und 2= Rechts)
		 */
		public void treffer(int position){
			if (position == 1){
				punkteLinks += 1;
				System.out.println("Spieler 1 hat einen Punkt gemacht!");
				System.out.println(punkteLinks + " - "+punkteRechts);
				animationTreffer(position);
				if (punkteLinks == punkteZumGewinn){
					gameOver();
					return;
				}
				punkteAnzeigen(3000);
				paddelLinks.setY((maxHoehe/2)-(paddelLinks.getLaenge()/2));
				paddelLinks.repaint(false);
				paddelRechts.setY((maxHoehe/2)-(paddelRechts.getLaenge()/2));
				paddelRechts.repaint(false);
				ball.neuerBall(maxBreite/2,maxHoehe/2);
			}else if (position == 2){
				punkteRechts += 1;
				System.out.println("Spieler 2 hat einen Punkt gemacht!");
				System.out.println(punkteLinks + " - "+punkteRechts);
				animationTreffer(position);
				if (punkteRechts == punkteZumGewinn){
					gameOver();
					return;
				}
				punkteAnzeigen(3000);
				paddelLinks.setY((maxHoehe/2)-(paddelLinks.getLaenge()/2));
				paddelLinks.repaint(false);
				paddelRechts.setY((maxHoehe/2)-(paddelRechts.getLaenge()/2));
				paddelRechts.repaint(false);
				ball.neuerBall(maxBreite/2,maxHoehe/2);
			}else{
				JOptionPane.showMessageDialog(null, "Ooops...da ist ein Fehler passiert!", "Sorry!", JOptionPane.ERROR_MESSAGE);
				System.exit(-1);
			}
		}

		/**
		 * Zeigt die Punkte an, stoppt das Spiel und aktiviert den GameOver Bildschim
		 */
		public void gameOver (){
			control.resetColors();
			punkteAnzeigen(3000);
			spielLaeuft = false;
			System.out.println("Spiel ist vorbei! Der Punktestand war:");
			System.out.println("Spieler 1: "+punkteLinks);
			System.out.println("Spieler 2: "+punkteRechts);

			animationEndGame();

		}

		/**
		 * Zeichnet das Spielfeld
		 */
		public void spielfeldZeichnen(){
			spielLaeuft = false;
			for(int i = 0; i< 12; i++){
				control.setColor(i, 0, 127,120,0);
				control.setColor(11-i, 11,127, 120, 0);
				control.setColor(0, i, 127, 0, 0);
				control.setColor(11,11-i, 0, 127, 0);
				control.updateLedStripe();
				control.sleep(400);
			}
			spielLaeuft = true;
		}

		/**
		 * Zeigt die Punkte der beiden Parteien fuer die uebergebene Dauer an
		 * @param dauer: Dauer der Einblendung
		 */
		public void punkteAnzeigen(int dauer){
			spielLaeuft = false;
			int colors[][][] = this.getColors();

			control.resetColors();
			control.updateLedStripe();

			control.setColor(maxBreite/2, maxHoehe/2 -1, 127, 127, 127);
			control.setColor(maxBreite/2, maxHoehe/2 +1, 127, 127, 127);
			control.updateLedStripe();

			if (punkteRechts == 0){
				control.setColor(1, 3, 0, 127, 0);
				control.setColor(1, 4, 0, 127, 0);
				control.setColor(1, 5, 0, 127, 0);
				control.setColor(1, 6, 0, 127, 0);
				control.setColor(1, 7, 0, 127, 0);
				control.setColor(1, 8, 0, 127, 0);

				control.setColor(2, 2, 0, 127, 0);
				control.setColor(2, 3, 0, 127, 0);
				control.setColor(2, 8, 0, 127, 0);
				control.setColor(2, 9, 0, 127, 0);

				control.setColor(3, 3, 0, 127, 0);
				control.setColor(3, 4, 0, 127, 0);
				control.setColor(3, 5, 0, 127, 0);
				control.setColor(3, 6, 0, 127, 0);
				control.setColor(3, 7, 0, 127, 0);
				control.setColor(3, 8, 0, 127, 0);
				control.updateLedStripe();
			}else if (punkteRechts == 1){
				control.setColor(1, 2, 0, 127, 0);
				control.setColor(1, 3, 0, 127, 0);
				control.setColor(2, 2, 0, 127, 0);
				control.setColor(2, 3, 0, 127, 0);
				control.setColor(2, 4, 0, 127, 0);
				control.setColor(2, 5, 0, 127, 0);
				control.setColor(2, 6, 0, 127, 0);
				control.setColor(2, 7, 0, 127, 0);
				control.setColor(2, 8, 0, 127, 0);
				control.setColor(2, 9, 0, 127, 0);
				control.updateLedStripe();
			}else if (punkteRechts == 2){
				control.setColor(1, 2, 0, 127, 0);
				control.setColor(1, 3, 0, 127, 0);
				control.setColor(1, 7, 0, 127, 0);
				control.setColor(1, 8, 0, 127, 0);
				control.setColor(1, 9, 0, 127, 0);
				control.setColor(2, 1, 0, 127, 0);
				control.setColor(2, 2, 0, 127, 0);
				control.setColor(2, 6, 0, 127, 0);
				control.setColor(2, 7, 0, 127, 0);
				control.setColor(2, 9, 0, 127, 0);
				control.setColor(3, 1, 0, 127, 0);
				control.setColor(3, 2, 0, 127, 0);
				control.setColor(3, 6, 0, 127, 0);
				control.setColor(3, 7, 0, 127, 0);
				control.setColor(3, 9, 0, 127, 0);
				control.setColor(4, 2, 0, 127, 0);
				control.setColor(4, 3, 0, 127, 0);
				control.setColor(4, 4, 0, 127, 0);
				control.setColor(4, 5, 0, 127, 0);
				control.setColor(4, 9, 0, 127, 0);
				control.updateLedStripe();
			}else if (punkteRechts == 3){
				control.setColor(1, 1, 0, 127, 0);
				control.setColor(1, 2, 0, 127, 0);
				control.setColor(1, 5, 0, 127, 0);
				control.setColor(1, 6, 0, 127, 0);
				control.setColor(1, 9, 0, 127, 0);
				control.setColor(2, 1, 0, 127, 0);
				control.setColor(2, 2, 0, 127, 0);
				control.setColor(2, 5, 0, 127, 0);
				control.setColor(2, 6, 0, 127, 0);
				control.setColor(2, 9, 0, 127, 0);
				control.setColor(3, 1, 0, 127, 0);
				control.setColor(3, 2, 0, 127, 0);
				control.setColor(3, 3, 0, 127, 0);
				control.setColor(3, 4, 0, 127, 0);
				control.setColor(3, 5, 0, 127, 0);
				control.setColor(3, 6, 0, 127, 0);
				control.setColor(3, 7, 0, 127, 0);
				control.setColor(3, 8, 0, 127, 0);
				control.setColor(3, 9, 0, 127, 0);
				control.updateLedStripe();
			}else if (punkteRechts == 4){
				control.setColor(1, 1, 0, 127, 0);
				control.setColor(1, 2, 0, 127, 0);
				control.setColor(1, 3, 0, 127, 0);
				control.setColor(1, 4, 0, 127, 0);
				control.setColor(1, 5, 0, 127, 0);
				control.setColor(1, 6, 0, 127, 0);
				control.setColor(2, 5, 0, 127, 0);
				control.setColor(2, 6, 0, 127, 0);
				control.setColor(3, 1, 0, 127, 0);
				control.setColor(3, 2, 0, 127, 0);
				control.setColor(3, 3, 0, 127, 0);
				control.setColor(3, 4, 0, 127, 0);
				control.setColor(3, 5, 0, 127, 0);
				control.setColor(3, 6, 0, 127, 0);
				control.setColor(3, 7, 0, 127, 0);
				control.setColor(3, 8, 0, 127, 0);
				control.setColor(3, 9, 0, 127, 0);
				control.updateLedStripe();
			}

			if (punkteLinks == 0){
				control.setColor(7, 3, 127, 0, 0);
				control.setColor(7, 4, 127, 0, 0);
				control.setColor(7, 5, 127, 0, 0);
				control.setColor(7, 6, 127, 0, 0);
				control.setColor(7, 7, 127, 0, 0);
				control.setColor(7, 8, 127, 0, 0);

				control.setColor(8, 2, 127, 0, 0);
				control.setColor(8, 3, 127, 0, 0);
				control.setColor(8, 8, 127, 0, 0);
				control.setColor(8, 9, 127, 0, 0);

				control.setColor(9, 3, 127, 0, 0);
				control.setColor(9, 4, 127, 0, 0);
				control.setColor(9, 5, 127, 0, 0);
				control.setColor(9, 6, 127, 0, 0);
				control.setColor(9, 7, 127, 0, 0);
				control.setColor(9, 8, 127, 0, 0);
				control.updateLedStripe();
			}else if (punkteLinks == 1){
				control.setColor(7, 2, 127, 0, 0);
				control.setColor(7, 3, 127, 0, 0);
				control.setColor(8, 2, 127, 0, 0);
				control.setColor(8, 3, 127, 0, 0);
				control.setColor(8, 4, 127, 0, 0);
				control.setColor(8, 5, 127, 0, 0);
				control.setColor(8, 6, 127, 0, 0);
				control.setColor(8, 7, 127, 0, 0);
				control.setColor(8, 8, 127, 0, 0);
				control.setColor(8, 9, 127, 0, 0);
				control.updateLedStripe();
			}else if (punkteLinks == 2){
				control.setColor(7, 2, 127, 0, 0);
				control.setColor(7, 3, 127, 0, 0);
				control.setColor(7, 7, 127, 0, 0);
				control.setColor(7, 8, 127, 0, 0);
				control.setColor(7, 9, 127, 0, 0);
				control.setColor(8, 1, 127, 0, 0);
				control.setColor(8, 2, 127, 0, 0);
				control.setColor(8, 6, 127, 0, 0);
				control.setColor(8, 7, 127, 0, 0);
				control.setColor(8, 9, 127, 0, 0);
				control.setColor(9, 1, 127, 0, 0);
				control.setColor(9, 2, 127, 0, 0);
				control.setColor(9, 6, 127, 0, 0);
				control.setColor(9, 7, 127, 0, 0);
				control.setColor(9, 9, 127, 0, 0);
				control.setColor(10, 2, 127, 0, 0);
				control.setColor(10, 3, 127, 0, 0);
				control.setColor(10, 4, 127, 0, 0);
				control.setColor(10, 5, 127, 0, 0);
				control.setColor(10, 9, 127, 0, 0);
				control.updateLedStripe();
			}else if (punkteLinks == 3){
				control.setColor(7, 1, 127, 0, 0);
				control.setColor(7, 2, 127, 0, 0);
				control.setColor(7, 5, 127, 0, 0);
				control.setColor(7, 6, 127, 0, 0);
				control.setColor(7, 9, 127, 0, 0);
				control.setColor(8, 1, 127, 0, 0);
				control.setColor(8, 2, 127, 0, 0);
				control.setColor(8, 5, 127, 0, 0);
				control.setColor(8, 6, 127, 0, 0);
				control.setColor(8, 9, 127, 0, 0);
				control.setColor(9, 1, 127, 0, 0);
				control.setColor(9, 2, 127, 0, 0);
				control.setColor(9, 3, 127, 0, 0);
				control.setColor(9, 4, 127, 0, 0);
				control.setColor(9, 5, 127, 0, 0);
				control.setColor(9, 6, 127, 0, 0);
				control.setColor(9, 7, 127, 0, 0);
				control.setColor(9, 8, 127, 0, 0);
				control.setColor(9, 9, 127, 0, 0);
				control.updateLedStripe();
			}else if (punkteLinks == 4){
				control.setColor(7, 1, 127, 0, 0);
				control.setColor(7, 2, 127, 0, 0);
				control.setColor(7, 3, 127, 0, 0);
				control.setColor(7, 4, 127, 0, 0);
				control.setColor(7, 5, 127, 0, 0);
				control.setColor(7, 6, 127, 0, 0);
				control.setColor(8, 5, 127, 0, 0);
				control.setColor(8, 6, 127, 0, 0);
				control.setColor(9, 1, 127, 0, 0);
				control.setColor(9, 2, 127, 0, 0);
				control.setColor(9, 3, 127, 0, 0);
				control.setColor(9, 4, 127, 0, 0);
				control.setColor(9, 5, 127, 0, 0);
				control.setColor(9, 6, 127, 0, 0);
				control.setColor(9, 7, 127, 0, 0);
				control.setColor(9, 8, 127, 0, 0);
				control.setColor(9, 9, 127, 0, 0);
				control.updateLedStripe();
			}

			control.sleep(dauer);

			for (int i = 0; i<12; i++){
				for (int j = 0; j<12; j++){
					control.setColor(i, j, colors[i][j][0],colors[i][j][1],colors[i][j][2]);
				}
				control.updateLedStripe();
			}
			spielLaeuft = true;
		}


		
		/**
		 * Zeigt die Laufschrift "Pong" an
		 */
		public void animationStartGame(){
			spielLaeuft = false;
			for (int x = 0; x<2;x++){
				for (int i=0; i<37;i++){
					control.setBackgroundColor(20, 0, 0);
					control.resetColors();
					//P
					for (int j=0; j<8;j++){
						setRestrictedColor(10-i, 2+j, 127, 127, 127);
					}
					setRestrictedColor(10-i, 2, 127, 127, 127);
					setRestrictedColor(11-i, 2, 127, 127, 127);
					setRestrictedColor(12-i, 2, 127, 127, 127);
					setRestrictedColor(13-i, 3, 127, 127, 127);
					setRestrictedColor(13-i, 4, 127, 127, 127);
					setRestrictedColor(12-i, 5, 127, 127, 127);
					setRestrictedColor(11-i, 5, 127, 127, 127);

					//O
					for (int j=0; j<6;j++){
						setRestrictedColor(16-i, 3+j, 127, 127, 127);
						setRestrictedColor(20-i, 3+j, 127, 127, 127);
					}
					setRestrictedColor(17-i, 2, 127, 127, 127);
					setRestrictedColor(18-i, 2, 127, 127, 127);
					setRestrictedColor(19-i, 2, 127, 127, 127);
					setRestrictedColor(17-i, 9, 127, 127, 127);
					setRestrictedColor(18-i, 9, 127, 127, 127);
					setRestrictedColor(19-i, 9, 127, 127, 127);

					//N
					for (int j=0; j<7;j++){
						setRestrictedColor(23-i, 3+j, 127, 127, 127);
						setRestrictedColor(27-i, 3+j, 127, 127, 127);
					}
					setRestrictedColor(24-i, 4, 127, 127, 127);
					setRestrictedColor(25-i, 6, 127, 127, 127);
					setRestrictedColor(26-i, 8, 127, 127, 127);

					//G
					for (int j=0; j<6;j++){
						setRestrictedColor(31-i, 3+j, 127, 127, 127);
					}
					for (int j=0; j<4;j++){
						setRestrictedColor(35-i, 5+j, 127, 127, 127);
					}
					setRestrictedColor(32-i, 2, 127, 127, 127);
					setRestrictedColor(32-i, 9, 127, 127, 127);
					setRestrictedColor(33-i, 2, 127, 127, 127);
					setRestrictedColor(33-i, 5, 127, 127, 127);
					setRestrictedColor(33-i, 6, 127, 127, 127);
					setRestrictedColor(33-i, 9, 127, 127, 127);

					setRestrictedColor(34-i, 2, 127, 127, 127);
					setRestrictedColor(34-i, 5, 127, 127, 127);
					setRestrictedColor(34-i, 9, 127, 127, 127);

					setRestrictedColor(35-i, 2, 127, 127, 127);

					control.updateLedStripe();
					control.sleep(200);
				}
				control.setBackgroundColor(0, 0, 0);
				control.resetColors();
			}

			for (int i = 0; i<12; i++){
				for (int j = 0; j<12; j++){
					control.setColor(i, j, 127,127,127);
				}
			}
			control.updateLedStripe();

			control.resetColors();
			control.sleep(200);

			spielLaeuft = true;
		}
		
		
		/**
		 * Kurze Animation wenn ein Spieler einen Treffer erlangt
		 * @param pos Position des Paddels
		 */
		public void animationTreffer(int pos){
			spielLaeuft = false;
			int colors[][][] = getColors();

			if (pos == 1){
				for (int i = 0; i<12; i++){
					for (int j = 0; j<12; j++){
						control.setColor(i, j, 127,0,0);
					}
				}
				control.updateLedStripe();

				control.sleep(400);
			}else if (pos == 2){
				for (int i = 0; i<12; i++){
					for (int j = 0; j<12; j++){
						control.setColor(i, j, 0,127,0);
					}
				}
				control.updateLedStripe();

				control.sleep(400);
			}else{
				JOptionPane.showMessageDialog(null, "Ooops...da ist ein Fehler passiert!", "Sorry!", JOptionPane.ERROR_MESSAGE);
				System.exit(-1);
			}


			for (int i = 0; i<12; i++){
				for (int j = 0; j<12; j++){
					control.setColor(i, j, colors[i][j][0],colors[i][j][1],colors[i][j][2]);
				}
			}
			spielLaeuft = true;
		}



		/**
		 * Zeigt den Game Over Bildschirm an
		 */
		public void animationEndGame(){
			for (int i = 0; i<12; i++){
				for (int j = 0; j<12; j++){
					if (punkteLinks == 4){
						control.setColor(i, j, 0, 20, 0);
						ball.changeColor(0, 20, 0);
					}
					else if (punkteRechts == 4){
						control.setColor(i, j, 20, 0,0);
						ball.changeColor(20, 0, 0);
					}
				}
			}


			control.setColor(2, 2, 127, 127, 0);
			control.setColor(2, 4, 127, 127, 0);
			control.setColor(2, 9, 127, 127, 0);
			control.setColor(3, 3, 127, 127, 0);
			control.setColor(3, 8, 127, 127, 0);
			control.setColor(4, 2, 127, 127, 0);
			control.setColor(4, 4, 127, 127, 0);
			control.setColor(4, 7, 127, 127, 0);
			control.setColor(5, 7, 127, 127, 0);
			control.setColor(6, 7, 127, 127, 0);
			control.setColor(7, 2, 127, 127, 0);
			control.setColor(7, 4, 127, 127, 0);
			control.setColor(7, 7, 127, 127, 0);
			control.setColor(8, 3, 127, 127, 0);
			control.setColor(8, 8, 127, 127, 0);
			control.setColor(9, 2, 127, 127, 0);
			control.setColor(9, 4, 127, 127, 0);
			control.setColor(9, 9, 127, 127, 0);

			control.updateLedStripe();
		}
		
		
		/**
		 * Hilfsmethode für den Lauftext! Setzt Farbwerte nur dann wenn sie auf dem Board erscheinen würden
		 * @param x: x Koordinate
		 * @param y: y Koordinate
		 * @param red: Farbwert Rot
		 * @param green: Farbwert Grün
		 * @param blue: Farbwert Blau
		 */
		public void setRestrictedColor(int x,int y ,int red,int green,int blue){
			if(x >= 1 && x <= 10){
				if ( y >= 1 && y <= 10){
					control.setColor(x, y, red, green, blue);
				}
			}

		}



		//												<!------------- Getter und Setter--------------------->
		public Spielball getBall(){
			return ball;
		}
		public Paddel getPaddelLinks() {
			return paddelLinks;
		}

		public Paddel getPaddelRechts() {
			return paddelRechts;
		}

		public int getMinBreite() {
			return minBreite;
		}


		public int getMaxBreite() {
			return maxBreite;
		}


		public int getMinHoehe() {
			return minHoehe;
		}


		public int getMaxHoehe() {
			return maxHoehe;
		}


		public void setMaxHoehe(int maxHoehe) {
			this.maxHoehe = maxHoehe;
		}
		public int[][][] getColors(){
			int varColors[][][] = control.getColors();
			int colors[][][] = new int[control.getWidth()][control.getHeight()][3];
			for (int i = 0; i<12; i++){
				for (int j = 0; j<12; j++){
					colors[i][j][0] = varColors[i][j][0];
					colors[i][j][1] = varColors[i][j][1];
					colors[i][j][2] = varColors[i][j][2];
				}
			}
			return colors;
		}
		public boolean isSpielLaeuft() {
			return spielLaeuft;
		}

		public int getSchwierigkeit() {
			return schwierigkeit;
		}
	}
