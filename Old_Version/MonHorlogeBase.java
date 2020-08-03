import java.awt.*;
import java.util.*;
import java.applet.*;
import javax.swing.*;
import java.awt.Button;

import java.time.LocalDateTime;
import java.time.Month;

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
  int secondeplusmoins = 0 ;
  String nomVille = "" ;



  /* Méthode paint qui dessine l'horloge */
	public void paint(Graphics gsp) {
		
		super.paint(gsp);
		
		setBackground(Color.white);
		
		int haut = getHeight();
		int larg = getWidth();

		

		imgTmp = createImage(larg,haut);
		gTmp = imgTmp.getGraphics();

		
		/*API pour l'heure*/
		LocalDateTime currentTime = LocalDateTime.now();
		int heure = currentTime.getHour();
		int minute = currentTime.getMinute();
		int seconde = currentTime.getSecond();
		int milliseconde = currentTime.getNano()/1000000 ;

		Month mois = currentTime.getMonth();
		int jour = currentTime.getDayOfMonth();
				
/*
		  // create a clock 
		  ZoneId zid = ZoneId.of("Europe/Paris"); 
  
		  // create an LocalDateTime object using now(zoneId) 
		  LocalDateTime lt = LocalDateTime.now(zid); 
	
		  // print result 
		  System.out.println("LocalDateTime : "+ lt); 
*/

		if(menu){

			System.out.println("          *****MENU*****        ");
			System.out.println("*1- Choisir l'heure d'une ville*");
			System.out.println("*2-  Modifier l'heure courante *");
			System.out.println("*3-        Chronomètre         *");
			Scanner sc = new Scanner(System.in);
			int choix = sc.nextInt();
			
			if(choix == 1){
				System.out.println("* 1 - Los Angeles - 1 *") ;
				System.out.println("* 2 -    Japon    - 2 *") ;
				System.out.println("* 3 -    Paris    - 3 *") ;
				System.out.println("* 4 -   Londres   - 4 *") ;
				System.out.println("* 5 -   Sidney    - 5 *") ;

				Scanner scan = new Scanner(System.in);
				int choixVille = scan.nextInt();
				if(choixVille == 1){
					heureplusmoins = -9 ;
					nomVille = "Los Angeles" ;
				}else if(choixVille == 2){
					heureplusmoins = 7 ;
					nomVille = "Japon" ;
				}else if(choixVille == 3){
					heureplusmoins = 0 ;
					nomVille = "Paris" ;
				}else if(choixVille == 4){
					heureplusmoins = -1 ;
					nomVille = "Londres" ;
				}else if(choixVille == 5){
					heureplusmoins = 8 ;
					nomVille = "Sidney" ;
				}else{
					System.out.println("Tu as réussi à donner un nombre qui n'est pas inscrit... Donc je décide de prendre Paris.") ;
				}
			}else if(choix == 2){

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
				}if(choixSeconde > 60){
					choixSeconde = choixSeconde % 60 ;
				}
				
				/*Il faut trouver le nombre à ajouter ou retirer de l'heure actuelle*/
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
				if(seconde > choixSeconde){
					secondeplusmoins = -(seconde - choixSeconde) ;
				}else{
					secondeplusmoins = choixSeconde - seconde ;
				}
			}else if(choix == 3){

				heureplusmoins = -(heure) ;
				minuteplusmoins = -(minute) ;
				secondeplusmoins = -(seconde) ;
			}

			menu = false ;
		}

		heure = heure + heureplusmoins ;
		minute = minute + minuteplusmoins ; 
		seconde = seconde + secondeplusmoins ; 
		DessinHorloge.dessineHorloge(gTmp, haut, larg, heure, minute, seconde, milliseconde);
		gTmp.drawString(nomVille + ", ", 600, 100); //Trouver un moyen de s'afficher seulement pour l'option 1
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
			Thread.sleep(400);
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
