import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.*;

public class DessinHorloge{
	
	/* Dessin d'une aiguille 
   * Les paramétres sont l'angle (en fait la propotion d'angle de 0 à 1)
   * 1 correspond à un angle de 2*PI
   * la position x et y souhaitée pour le centre
   * la longueur et la largeur de l'aiguille
   * sa couleur */
  public static void dessinerAiguille(Graphics gTmp, double angle ,int x, int y, double longueur, double largeur , Color couleur)
  {
	   
	    double n = angle*200;
	    double z = n/100*Math.PI;	    
	    double u = (n+50)/100*Math.PI;

	    double x0 = Math.sin(z)*longueur;
	    double y0 = -Math.cos(z)*longueur;

	    double x1 = -Math.sin(z)*longueur/5.0;
	    double y1 = Math.cos(z)*longueur/5.0;

	    double x2 = Math.sin(u)*largeur;
	    double y2 = -Math.cos(u)*largeur;

	    double x3 = -Math.sin(u)*largeur;
	    double y3 = Math.cos(u)*largeur;

	    int[] tPolygonX = new int[4];
	    int[] tPolygonY = new int[4];
	    
	    tPolygonX[0] = (int)x1+x;
	    tPolygonX[1] = (int)x2+x;
	    tPolygonX[2] = (int)x0+x;
	    tPolygonX[3] = (int)x3+x;

	    tPolygonY[0] = (int)y1+y;
	    tPolygonY[1] = (int)y2+y;
	    tPolygonY[2] = (int)y0+y;
	    tPolygonY[3] = (int)y3+y;

	    gTmp.setColor(couleur);
	    gTmp.fillPolygon(tPolygonX, tPolygonY, 4);
	    gTmp.setColor(Color.white);
	    gTmp.drawPolygon(tPolygonX, tPolygonY, 4);
	  
  }
  
  
    /* dessin des numéros autours de l'horloge */
  public static void dessinerNumeros(Graphics gTmp, int pas, int rayon, Color couleur){
	
	  gTmp.setColor(couleur);
	  for (int i=1; i<13; i++){
		  if(i%pas == 0){
			  double angle = (i/12.0)*2.0*Math.PI;
			  int x = (int)(Math.sin(angle)*rayon*0.40+rayon*0.48);
			  int y = (int)(-Math.cos(angle)*rayon*0.40+rayon*0.52);
			  String chf = Integer.toString(i);
			  Font f = new Font("Courier", Font.BOLD, (int)(rayon*0.08));
			  gTmp.setFont(f);
			  gTmp.drawString(chf, x, y);
		  }
	  }	  
  }
  
  
  /* dessin de l'horloge */
  public static void dessineHorloge(Graphics gTmp, int haut, int larg, int heure, int minutes, int secondes , int millisecondes){
	  
	int taille = (haut < larg) ? haut : larg;
	    
    Color cFond = new Color(0,0,0);
	
    gTmp.setPaintMode();
    gTmp.setColor(cFond);
    gTmp.fillOval(2,2,taille,taille);
    gTmp.setColor(Color.white);
    gTmp.drawOval(2,2,taille,taille);
	
    DessinHorloge.dessinerNumeros(gTmp,1,taille,Color.cyan);
  
    int centreX = taille/2;
    int centreY = taille/2;
    
    //Aiguille des secondes
    DessinHorloge.dessinerAiguille(gTmp,(secondes+millisecondes/1000.0)/60.0, centreX, centreY, 0.45*taille,0.01*taille,Color.red);

    //Aiguille des minutes
    DessinHorloge.dessinerAiguille(gTmp,minutes/60.0, centreX, centreY, 0.50*taille,0.02*taille,Color.black);


    //Aiguille des heures
    DessinHorloge.dessinerAiguille(gTmp,heure/12.0+minutes/60.0/12.0, centreX, centreY, 0.25*taille,0.03*taille,Color.white);
    
  }

}

