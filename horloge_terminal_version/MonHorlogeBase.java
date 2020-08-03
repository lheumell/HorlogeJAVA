import java.awt.*;
import java.util.*;
import java.applet.*;
import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.math.*;

public class MonHorlogeBase extends JPanel implements Runnable {


	Image imgTmp;
	Graphics gTmp;
	boolean menu = false;
	static int coef = 0;
	static int heure_base;
	static int minute_base ;
	static int seconde_base ;
	static int milli_base ;
	static int heure_chx ;
	static int minute_chx ;
	static int seconde_chx ;
	static int milli_chx ;
	static boolean premier_heure = true ;
	static boolean premier_heure_v2 = true ;
	
	/* Méthode paint qui dessine l'horloge */
	public void paint(Graphics gsp) {

		super.paint(gsp);

		setBackground(Color.white);

		int haut = getHeight(); 
		int larg = getWidth();

		imgTmp = createImage(larg, haut);
		gTmp = imgTmp.getGraphics();
		LocalDateTime currentTime = LocalDateTime.now();
		int heure = currentTime.getHour();
		int minute = currentTime.getMinute();
		int seconde = currentTime.getSecond();
		int milliseconde = currentTime.getNano()/1000000 ;
		if ( menu == false ){
			System.out.println("Menu :");
			System.out.println(" - Changement de creneau horaire : 1 ");
			System.out.println(" - Modifier l'heure courante : 2 ");
			System.out.println(" - Démarre un chronometre : 3 ");
			Scanner sc = new Scanner(System.in);
			int choix = 0;
			int choixgmt = 0;
			
			System.out.print("  Veuillez entrer le choix du mode que vous voulez : ");
			choix = sc.nextInt();
			if (choix == 1 ){
				System.out.println("    - Vous voulez quelle changement d'horaire ? ");
				System.out.println("            - 1 : GMT+1 ");
				System.out.println("            - 2 : GMT+2 ");
				System.out.println("            - 3 : GMT+3 ");
				System.out.println("            - 4 : GMT ");
				System.out.println("            - 5 : GMT-1 ");
				System.out.println("            - 6 : GMT-2 ");
				System.out.print("  Veuillez entrer le choix du creneau horaire : ");
				choixgmt = sc.nextInt();
				switch (choixgmt) {
					case 1:
						System.out.println("heure de base ");
						break;
					case 2:
						coef = 1 ;
						break;
					case 3:
						coef = 2 ;
						break;
					case 4:
						coef = -1;
						break;
					case 5:
						coef = -2;
						break;
					case 6:
						coef = -3;
						break;
				
					default:
						System.out.println("Vous avez rentré un mauvais chiffre , on prends le gmt+1 c'est a dire paris ");
						break;
				}
			}else if (choix == 3){
					/* il faut faire heure - heure de base pour avoir l'heure a zero et apres tu scan f toute les heures minutes ect  */
				/*  faire un boolean pour gérer le premier cas pour avoir un truc de base  */
				if ( premier_heure == true ){
					heure_base = heure ;
					minute_base = minute ;
					seconde_base = seconde ;
					milli_base = milliseconde ;
					premier_heure = false ;
				}
				
			}else if (choix == 2){
					/* il faut faire heure - heure de base pour avoir l'heure a zero */

					if ( premier_heure == true ){
						heure_base = heure ;
						minute_base = minute ;
						seconde_base = seconde ;
						milli_base = milliseconde ;
						premier_heure = false ;
					}

				if ( premier_heure_v2 == true ){
					System.out.print("Veulliez choisir l'heure : ");
					heure_chx = sc.nextInt();
					System.out.println(" ");
					System.out.print("Veulliez choisir les minutes : ");
					minute_chx = sc.nextInt();
					System.out.println(" ");
					System.out.print("Veulliez choisir les secondes : ");
					seconde_chx = sc.nextInt();
					System.out.println(" ");
					System.out.print("Veulliez choisir les millisecondes : ");
					milli_chx = sc.nextInt();
					System.out.println(" ");


					premier_heure_v2 = false ;
				}
			}
			menu = true ;
		}
		/* cette partie sert a faire un chrono ou bien meme une horloge avec des trucs definit  */
		if (premier_heure == false ) {
		heure = heure - heure_base ;
		minute = minute - minute_base ;
		seconde = seconde - seconde_base ;
		milliseconde =  milliseconde - milli_base ;
		}

		if (premier_heure_v2 == false ) {
			heure = heure + heure_chx ;
			minute = minute + minute_chx ; 
			seconde = seconde + seconde_chx ;
			milliseconde = milliseconde + milli_chx ;

			}


		heure = heure + coef;
		DessinHorloge.dessineHorloge(gTmp, haut, larg, heure, minute, seconde , milliseconde);
		gsp.drawImage(imgTmp, 0, 0, this);

	}

	public MonHorlogeBase(){
		Thread tr = new Thread(this);
        tr.start();
	}

	static public void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new MonHorlogeBase());
		frame.setBounds(100, 100, 800, 600);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}


	@Override
	public void run() {
		while(true){
		repaint();
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	}
}
