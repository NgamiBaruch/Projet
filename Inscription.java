package gestion;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Inscription extends JFrame {
	//ATTTRIBUTS
	JPanel container;
	private JLabel username,email,password,confirm,title,message,mes,phone,sex,lPhoto;
	private JTextField tUsername,tEmail,tphone;
	private JPasswordField pPassword,pConfirm;
	private JButton Enregister,Annuler,sortir;
	private JCheckBox check;
	private JComboBox  sexe;
	String path  = null;
	byte [] userimage = null;
	
	// CONSTRUCTEURS
	public Inscription() {
		this.setSize(500,600);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setUndecorated(true );
		initComportement();
		this.setContentPane(container);
	}
	public void initComportement() {
		EleveConnection con = new EleveConnection();
		container = new JPanel();
		container.setLayout(null);
		
		//Label pour le title
		
		title = new JLabel("CREATE YOUR ACCOUNT");
		title.setBounds(110, 40, 270, 30);
		title.setForeground(Color.ORANGE);
		title.setFont(new Font("Arial",Font.BOLD,20));
		container.add(title);
		
		
		// Label pour le username
		
		username = new JLabel("USERNAME");
		username.setFont(new Font("Arial",Font.BOLD,10));
		username.setBounds(30,90,70,30);
		container.add(username);
		tUsername = new JTextField();
		tUsername.setBounds(100, 95,200,30);
		tUsername.setBackground(Color.WHITE);
		container.add(tUsername);
		
		Image image = Toolkit.getDefaultToolkit().getImage("D:/PDF/5185871(0).png");
		ImageTextField textField = new ImageTextField("Entrez votre nom", image);
		textField.setBounds(100, 95,200,130);
		textField.setBackground(Color.WHITE);
		container.add(textField);
		
		
		//Label pour la password
		
		password = new JLabel("password");
		password.setBounds(30,150,70,30);
		container.add(password);
		
		// JTextField pour le password
		pPassword = new JPasswordField();
		pPassword.setBounds(100, 150,200,30);
		pPassword.setBackground(Color.WHITE);
		container.add(pPassword);
		
		check = new JCheckBox();
		check.setBounds(300,125,35,80); 
		// checkbox to showw and hide the password
		check.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(check.isSelected()) {
					pPassword.setEchoChar((char)0);
			}else{
				pPassword.setEchoChar('*');
			}
			}
		});
		container.add(check); 
		// Label pour le email
		email = new JLabel("EMAIL ");
		email.setFont(new Font("Arial",Font.BOLD,10));
		email.setBounds(30,250,70,30);
		container.add(email);
		// Email
		tEmail = new JTextField();
		tEmail.setBounds(100, 250,200,30);
		tEmail.setBackground(Color.WHITE);
		container.add(tEmail);
		
		//JLabel photo
		

		lPhoto = new JLabel();
		lPhoto.setBounds(380, 34, 90, 81);
		lPhoto.setFont(new Font("Arial",Font.BOLD,16));
		lPhoto.setBackground(new java.awt.Color(255,0,0));
		lPhoto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lPhoto.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		lPhoto.addMouseListener(new java.awt.event.MouseAdapter(){
		public void mouseClicked(java.awt.event.MouseEvent evt) {
		    	lPhotoMouseClicked(evt);
		}
		private void lPhotoMouseClicked(MouseEvent evt) {
		    	JFileChooser pic = new JFileChooser();
		    	pic.showOpenDialog(null);
		    	File picture = pic.getSelectedFile();
		    	path = picture.getAbsolutePath();
		    	BufferedImage img;
		    	try {
		    		img = ImageIO.read(pic.getSelectedFile());
		    		ImageIcon imageic = new ImageIcon(new ImageIcon(img).getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT));
		    		lPhoto.setIcon(imageic);
		    	 	File image = new File(path);
		    		FileInputStream fis = new FileInputStream(image);
		    		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		    		byte [] buff = new byte[2048];
		    		
		    		for(int i;(i=fis.read(buff)) != -1;){
		    			bos.write(buff, 0, i);
		    		}
		    		userimage = bos.toByteArray();
		    	}catch(Exception e) {
		    		e.printStackTrace();
		    	}
		}
		});
		container.add(lPhoto);
		
		// password  de confirmation
		
		confirm = new JLabel("CONFIRM");
		confirm.setFont(new Font("Arial",Font.BOLD,10));
		confirm.setBounds(30,200,70,30);
		container.add(confirm);
		
		//Label pour le sexe
		sex = new JLabel("SEXE");
		sex.setBounds(30,300,50,30);
		container.add(sex);
		
		// JTextField des JComboBox
		
		String[] str = {"","Male","Fenale"};
		sexe = new JComboBox(str);
		sexe.setBounds(100,300,200,30);
		container.add(sexe);
		
		//Label phone
		phone = new JLabel("Telephone");
		phone.setBounds(30,350,60,30);
		container.add(phone);
		
		//JTextField phone
		
		tphone = new JTextField();
		tphone.setBounds(100, 350, 200, 30);
		tphone.setBackground(Color.WHITE);
		container.add(tphone);
		
		
	
		// TextField
		
		pConfirm = new JPasswordField();
		pConfirm.setBounds(100, 200,200,30);
		pConfirm.setBackground(Color.WHITE);
		container.add(pConfirm); 
		
		// Button Enregister
		//Enregister = new JButton("REGISTER");
		RoundedButton button = new RoundedButton("REGISTER");
		button.setBounds(40, 500,100,30);
		//button.setBackground(Color.BLUE);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(pPassword.getText().equals(pConfirm.getText())) {
					String num,nom,mail,phone;
					
					num = pPassword.getText();
					nom = tUsername.getText();
					mail = tEmail.getText();
					phone = tphone.getText();
					String rq = "insert into administrateur(password,username,Email,phone,photo) values (?,?,?,?,?)";
					try {
						PreparedStatement ps = con.getConnection().prepareStatement(rq);
						ps.setString(1, num);
						ps.setString(2, nom); 
						ps.setString(3, mail);
						ps.setString(4,phone);
						ps.setBytes(5, userimage);
						ps.executeUpdate();
						JOptionPane.showMessageDialog(null, "students fine register",null, JOptionPane.INFORMATION_MESSAGE);
						
						dispose();
						Login elv = new Login();
						elv.setVisible(true);
					}catch(SQLException ex) {
						JOptionPane.showMessageDialog(null, "ERROR !"+ex.getMessage(),null, JOptionPane.ERROR_MESSAGE);
					}
				}else {
					JOptionPane.showMessageDialog(null, " Mot de passe incorrecte",null,JOptionPane.INFORMATION_MESSAGE);
				}
				
			}
		});
		//container.add(Enregister);
		container.add(button);
		
		Annuler = new JButton("BACK");
		Annuler.setBounds(330, 500,100,30);
		Annuler.setBackground(Color.GRAY);
		container.add(Annuler);
		Annuler.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				dispose();
				Login log = new Login();
				log.setVisible(true);
			}
		});
	
		sortir = new JButton("CANCEL");
		sortir.setBounds(180, 500,100,30);
		sortir.setBackground(Color.RED);
		container.add(sortir);
		//sortir.setBounds(460,0,50, 20);
		container.add(sortir);
		sortir.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				dispose();
				Login log = new Login();
				//log.setVisible(false);
				log.dispose();
			}
		});
		
		// message de securite
		
		message = new JLabel("MAKE SHOW YOU ARE REGISTER YOURS INFORMATIONS");
		message.setBounds(80, 400, 400, 60);
		message.setForeground(Color.PINK);
		message.setFont(new Font("Arials",Font.ITALIC,12));
		container.add(message);
		
	}
}
