package gestion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Timer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JFrame {
	
	// Attributs
	
	EleveConnection con = new EleveConnection();
	JPanel container,container1,container2;
	private JLabel lLogin,lNom,close,lcode,reduct,eye,message,nom,password;
	JTextField tNom,error,error1;
	JPasswordField pCode;
	JButton connection;
	JCheckBox check;
	
	// constructeurs
	
	public Login() {
		this.setSize(500,500);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setUndecorated(true);
		this.setShape(new RoundRectangle2D.Double(0,0,getWidth(),getHeight(),15,15));
		this.setLayout(null);
		initComportement();
		this.setContentPane(container);
		this.setVisible(true);
	}
	private void initComportement() {
		container = new JPanel();
		container.setBackground( Color.GRAY);
		container.setLayout(null); 
		container1 = new JPanel();
		
		// add container1
		
		container1.setLayout(null);
		container1.setBounds(20,110,445,290);
		container.add(container1);
		
		//container2
		
		container2 = new JPanel();
		container2.setLayout(null);
		container2.setBounds(20,50,445,58);
		lLogin = new JLabel("SIGN IN");
		lLogin.setFont(new Font("Arials",Font.BOLD,15));
		lLogin.setForeground(Color.red);
		lLogin.setBounds(200, 20, 80, 20);
		container2.add(lLogin);
		container2.setBackground(Color.PINK);
		container.add(container2);
		
		//Label nom
		
		nom = new JLabel("UERSNAME");
		nom.setBounds(85,40,80,10);
		nom.setFont(new Font("Arials",Font.ITALIC,8));
		container1.add(nom);
		
		// Label password
		
		password = new JLabel ("PASSWORD");
		password.setBounds(85,80,80,10);
		password.setFont(new Font("Arials",Font.ITALIC,8));
		container1.add(password);
		
		
		// icone nom
		
		lNom = new JLabel( new ImageIcon("C:/Users/Lenovo/Desktop/image/user.png"));
		//JPanel panIcon = new JPanel() ;
		//panIcon.setBackground( Color. red) ;
		//panIcon. setLayout( new BorderLayout());
		lNom.setBounds(60,50,20,20);
		container1.add(lNom);
		
		// creation du nouveau compte
		
		message = new JLabel(">>> CREATE YOUR ACCOUNT");
		message.setBounds(160,200,330,30);
		container1.add(message);
		message.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				message.setBackground(Color.blue);
				message.setForeground(Color.BLACK);
				dispose();
				Inscription inscrip = new Inscription();
				inscrip.setVisible(true);
			}
		});
		
		
		// JtextField nom
		
		tNom = new JTextField();
		tNom.setBounds(85,50,300,20);
		container1.add(tNom);
		//JLABEL POUR LA PASSWORD
		
		lcode = new JLabel(new ImageIcon("C:/Users/Lenovo/Desktop/image/lock.png"));
		lcode.setBounds(60,90,20,20);
		container1.add(lcode);
		
		//JPASSWORD
		
		pCode  = new JPasswordField();
		pCode.setBounds(85,90,300,20);
		container1.add(pCode);
		
		// les check box
		check = new JCheckBox();
		check.setBounds(385,92,35,10); 
		
		// checkbox to show and hide the password
		
		check.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(check.isSelected()) {
					pCode.setEchoChar((char)0);
			}else{
				pCode.setEchoChar('*');
			}
			}
		});
		
		container1.add(check);
		
		// message d'erreur
		error = new JTextField();
		error.setBounds(85,110  , 300, 30);
		error1 = new JTextField();
		error1.setBounds(60,200,330,30);
		
		// LE BOUTTON DE VISIBILITE DU MOT DE PASSE
		
		/*eye = new JLabel(new ImageIcon("C:/Users/Lenovo/Desktop/image/eye.PNG"));
		eye.setBounds(404,85,45,28);
		container1.add(eye);*/
		
		// BUTTON DE CONNEXION
		
		connection = new JButton("LOGIN");
		connection.setBounds(60,150,330,30);
		connection.setBackground(Color.BLUE);
		container1.add(connection);
		connection.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent et) {
				// TODO Auto-generated method stub
				String username =  tNom.getText().trim();
				String password = String.valueOf(pCode.getPassword()).trim();
				if(username.equals("")||pCode.equals("")) {
					JOptionPane.showMessageDialog(null, "Incorrect username and password !!",null, JOptionPane.ERROR_MESSAGE);
				}else {
					String sql = "select* from administrateur ";
					try {
						Statement pst = con.getConnection().createStatement();
						ResultSet rs = pst.executeQuery(sql);
						while (rs.next()) {
									rs.getString("password");
									rs.getString("username");
							}
						//fermeture de la connection
						dispose();
						Eleve eleve = new Eleve();
						eleve.setVisible(true);		
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null,"Error Incorrecte Informations",null,JOptionPane.ERROR_MESSAGE);
					}
				}
			}
			
		});
		dispose();
		//Fermeture
		
		close = new JLabel(new ImageIcon("C:/Users/Lenovo/Desktop/image/x.PNG"));
		close.setBounds(460,0,50, 20);
		container.add(close);
		close.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
				dispose();
			}
		});
		
	}
	public static void main(String[] args) {
		new Eleve();
	}
}
