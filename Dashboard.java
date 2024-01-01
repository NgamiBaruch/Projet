package gestion;

import java.awt.Color;
//import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Dashboard extends JFrame {
	// Attributs
	JPanel container,container1;
	JLabel lText,lIndex;
	JTextField tText;
	JButton refreshButton;
	//constructeurs
	public Dashboard() {
	    this.setSize(600,500);
	    this.setResizable(false);
	    this.setLocationRelativeTo(null);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    //this.setUndecorated(true);
	    this.setLayout(null);
	    initComportement(); 
	    this.setContentPane(container);
}
	public void initComportement() {
		// conteneur des resultats de la requete demandant le nombre d'eleves en classe de terminal
		
		container = new JPanel();
		container.setBackground(Color.blue);
		container.setLayout(null);
		container1 = new JPanel();
		container1.setLayout(null); 
		container1.setBounds(40,50,150,60);
		
	    container.add(container1); 
	    // Button de stastitique
	    refreshButton = new JButton("NOS STATISTIQUE");
	    refreshButton.setBounds(20,60,55,30);
	    container1.add(refreshButton);
	   /* refreshButton.addActionListener(new ActionListener(){
	    	refresh.addActionListener()
	    });*/
		
	}
}
