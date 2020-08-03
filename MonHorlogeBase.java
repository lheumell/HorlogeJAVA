//https://www.codeflow.site/fr/article/java8__java-display-all-zoneid-and-its-utc-offset pour connaitre les ZoneId

import java.awt.*;
import java.util.*;
import java.applet.*;
import javax.swing.*;


import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;

//Ceci importe la classe Scanner du package java.util
import java.util.Scanner; 
//Ceci importe toutes les classes du package java.util
import java.util.*;


public class MonHorlogeBase extends JPanel implements Runnable{


	Image imgTmp;
	Graphics gTmp;
	boolean menu = true ;
	int heureplusmoins = 0 ;
	int minuteplusmoins = 0 ;
	double secondeplusmoins = 0 ;
	String nomVille = "" ;
	String localisation = "Europe/Paris" ; //Lieu par defaut

	/*API pour les secondes continues et la date*/
	LocalDateTime currentTime = LocalDateTime.now();
	double seconde = currentTime.getSecond();

  /* Méthode paint qui dessine l'horloge */
	public void paint(Graphics gsp) {
		
		super.paint(gsp);
		
		setBackground(Color.white);
		
		int haut = getHeight();
		int larg = getWidth();

		

		imgTmp = createImage(larg,haut);
		gTmp = imgTmp.getGraphics();

	/*API pour l'heure*/
	ZoneId zid = ZoneId.of(localisation);
	LocalDateTime currentTime = LocalDateTime.now(zid);
	int heure = currentTime.getHour();
	int minute = currentTime.getMinute();
	Month mois = currentTime.getMonth();
	int jour = currentTime.getDayOfMonth();


		if(menu){

			System.out.println("          *****MENU*****         ");
			System.out.println("*1-  Choisir le fuseau horaire  *");
			System.out.println("*2- Choisir l'heure d'une ville *");
			System.out.println("*3-  Modifier l'heure courante  *");
			System.out.println("*4-        Chronomètre          *");
			Scanner sc = new Scanner(System.in);
			int choix = sc.nextInt();
			
			if(choix == 1){
				System.out.println("* 1 -    -9    - 1 *") ;
				System.out.println("* 2 -    +7    - 2 *") ;
				System.out.println("* 3 -    +0    - 3 *") ;
				System.out.println("* 4 -    -1    - 4 *") ;
				System.out.println("* 5 -    +8    - 5 *") ;

				Scanner scan = new Scanner(System.in);
				int choixFuseau = scan.nextInt();
				if(choixFuseau == 1){
					heureplusmoins = -9 ;
				}else if(choixFuseau == 2){
					heureplusmoins = 7 ;
				}else if(choixFuseau == 3){
					heureplusmoins = 0 ;
				}else if(choixFuseau == 4){
					heureplusmoins = -1 ;
				}else if(choixFuseau == 5){
					heureplusmoins = 8 ;
				}else{
					System.out.println("Tu as réussi à donner un nombre qui n'est pas inscrit... Donc je décide de prendre Paris.") ;
				}
			}else if(choix == 2){
				System.out.println("* 1 - Kiritimati(opposé +) - 1 *") ;
				System.out.println("* 2 -        Tokyo         - 2 *") ;
				System.out.println("* 3 -        Paris         - 3 *") ;
				System.out.println("* 4 -     Los Angeles      - 4 *") ;
				System.out.println("* 5 - Kamchatka(opposé -)  - 5 *") ;

				Scanner scan = new Scanner(System.in);
				int choixVille = scan.nextInt();

				if(choixVille == 1){
					localisation = "Pacific/Kiritimati" ;
				}else if(choixVille == 2){
					localisation = "Asia/Tokyo" ;
				}else if(choixVille == 3){
				}else if(choixVille == 4){
					localisation = "America/Los_Angeles" ;
				}else if(choixVille == 5){
					localisation = "Pacific/Pago_Pago" ;
				}else{
					System.out.println("Tu as réussi à donner un nombre qui n'est pas inscrit... Donc je décide de prendre Paris.") ;
				}
			}else if(choix == 3){

				System.out.println("Choisir l'heure :") ;
				Scanner sch = new Scanner(System.in);
				int choixHeure = sch.nextInt();
				System.out.println("Choisir les minutes :") ;
				Scanner scm = new Scanner(System.in);
				int choixMinute = scm.nextInt();
				System.out.println("Choisir les secondes :") ;
				Scanner scs = new Scanner(System.in);
				int choixSeconde = scs.nextInt();
			
				//Cohérence des heures
				if(choixHeure > 24){
					choixHeure = choixHeure % 24 ;
				}if(choixMinute > 60){
					choixMinute = choixMinute % 60 ;
				}
				
				//Il faut trouver le nombre à ajouter ou retirer de l'heure actuelle
				
				if(heure > choixHeure){
					heureplusmoins = -(heure - choixHeure) ;
				}else{
					heureplusmoins = choixHeure - heure ; 
				}
				if(minute > choixMinute){
					minuteplusmoins = -(minute - choixMinute) ;
				}else{
					minuteplusmoins = choixMinute - minute ;
				}
				seconde = choixSeconde ; // La valeur "seconde" n'est pas inclue dans le refresh "paint", il faut juste lui donner sa valeur 
			}else if(choix == 4){

				heureplusmoins = -(heure) ;
				minuteplusmoins = -(minute) ;
				seconde -= seconde ;
			}

			menu = false ;
		}

		heure = heure + heureplusmoins ;
		minute = minute + minuteplusmoins ; 
		DessinHorloge.dessineHorloge(gTmp, haut, larg, heure, minute, seconde);
		gTmp.drawString(localisation + ", ", 600, 100); 
		gTmp.drawString(jour + " " + mois , 600, 200); 
		gsp.drawImage(imgTmp,0,0,this);
    }
  

	/*Attribut tr*/
	public MonHorlogeBase(){
		Thread tr = new Thread(this);
        tr.start();
	}
  
  	/*Methode RUN*/
	@Override
	public void run() {
		while(true){
		repaint();
		try {
			Thread.sleep(100);
			seconde += 0.1 ;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	}

	/*Main*/
   static public void main(String[] args){
		JFrame frame = new JFrame();
		frame.getContentPane().add(new MonHorlogeBase());	
		frame.setBounds(100,100,1200,600);  //Placement sur le bureau + dimensions fenetre
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 
  }
}
