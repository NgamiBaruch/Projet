package gestion;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Eleve extends JFrame {
	EleveConnection con = new EleveConnection();
	String path  = null;
	byte [] userimage = null;
	Statement pst;
	JPanel  container = new JPanel();
	private JLabel lNumero,lNom,lClasse,lSexe,lTitle,lPhoto;
	private JTextField tNumero,tNom,tClasse;
	private JComboBox cSexe , cClasse;
	private JButton bRecherche, bRegister,bDelete;
	JTable table, table1;
	JScrollPane scroll,scrolll;
	public void init() {
		table1 = new JTable();
		scrolll = new JScrollPane();
		scrolll = new JScrollPane(table1,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrolll.setBounds(10,200,535,130);
		scrolll.setViewportView(table1);
		
	}
	// consructeurs
	public Eleve(){
		this.setSize(570,350);
		this.setTitle("Gestion des eleves");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(true);
		this.setLocationRelativeTo(null);
		this.setContentPane(container);
		//JScrollPane sn = new JScrollPane(container); 
		container.setLayout(null);
		//add(container);
		//container.setBackground(new Color(220,210,220));
		container.setBackground(new Color(220, 210, 220));
		initComportement();
	}
	public void initComportement() {
		container.setLayout(null);
		lTitle = new JLabel("PARTIE D' ENREGISTREMENT");
		lTitle.setBounds(180, 10, 250, 12);
		lTitle.setFont(new Font("Arial",Font.BOLD,15));
		lTitle.setForeground(new Color(0, 0,205));
		container.add(lTitle);
		lNumero = new JLabel("NUMERO ELEVE ");
		lNumero.setBounds(34,34,160,12);
		container.add(lNumero);
		
		// Bienvenue au JTextField
		
		tNumero = new JTextField();
		tNumero.setBounds(132,34,110,18);
		tNumero.setFont (new Font("Arial",Font.PLAIN,10));
		container.add(tNumero);
		lNom = new JLabel("NOM ET PRENOM");
		lNom.setBounds(33,50,180,20);
		container.add(lNom);
		tNom = new JTextField();
		tNom.setBounds(132, 56,230, 18);
		container.add(tNom);
		lSexe = new JLabel("SEXE");
		lSexe.setBounds(32, 77, 34, 15);
		container.add(lSexe);
		String [] str = {"","Masculin","feminin"};
		cSexe = new JComboBox(str);
		cSexe.setBounds(132, 79, 64, 17);
		container.add(cSexe);
		
		// definitions des classes
		
		lClasse = new JLabel("CLASSE");
		lClasse.setBounds(32, 95, 60, 19);
		container.add(lClasse);
		String [] ret = {"","6eime","5eime","4ieme","3 eime","2nde"," 1ere","Tle"};
		cClasse = new JComboBox(ret);
		cClasse.setBounds(132, 100, 64, 17);
		container.add(cClasse);
		
		// Menu recherche
		bRecherche = new JButton("RECHERCHE");
		bRecherche.setBounds(250, 34, 112, 18);
		bRecherche.setFont(new Font("Arial",Font.BOLD,10));
		bRecherche.setForeground(Color.blue);
		bRecherche.setBackground(new Color(123,34,80));
		
		// telechargement de l'individu
		bRecherche.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed (java.awt.event.ActionEvent evt) {
				bRechercheActionPerformed(evt);
			}
			private void bRechercheActionPerformed(ActionEvent evt) {
				String num;
				num = tNumero.getText();
				try {
					String rq = "select * from tb_eleve where numero = ?";
					PreparedStatement ps = con.getConnection().prepareStatement(rq);
					ps.setString(1, num);
					ResultSet rs = ps.executeQuery();
					if(rs.next() == false) {
						JOptionPane.showMessageDialog(null, "Matricule n'existe pas",null,JOptionPane.ERROR_MESSAGE);
						tNumero.setText("");
					}else {
						tNom.setText(rs.getString(2).trim());
						cSexe.setSelectedItem(rs.getString(3).trim());
						cClasse.setSelectedItem(rs.getString(4).trim());
						try { 
							Blob blob1 = rs.getBlob("photo");
							byte[] imagebyte = blob1.getBytes(1,(int)blob1.length());
							ImageIcon imag = new ImageIcon(new ImageIcon(imagebyte).getImage().getScaledInstance(150,150,150));
							//stractButton image1;
							//stractButton image1;
							lPhoto.setIcon(imag);
						}catch(Exception e) {
							JOptionPane.showMessageDialog(null,"erreur !"+e.getMessage(),null,JOptionPane.ERROR_MESSAGE);
						}						
					}
				}catch(SQLException e) {
					JOptionPane.showMessageDialog(null,"Numero incorrecte"+e.getMessage(),null,JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		container.add(bRecherche);
		
		// Menu enregistrement
		bRegister = new JButton("ENREGISTRER");
		bRegister.setBounds(132, 130, 110, 18);
		bRegister.setFont(new Font("Arial",Font.BOLD,10));
		bRegister.setForeground(Color.BLACK);
		bRegister.setBackground(new Color(173, 216,230));
		
		bRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				String num,nom,sexe,classe;
				
				num = tNumero.getText();
				nom = tNom.getText();
				sexe = cSexe.getSelectedItem().toString();
				classe = cClasse.getSelectedItem().toString();
				String rq = "insert into tb_eleve(numero,nom,sexe,classe,photo) values (?,?,?,?,?)";
				try {
					//Statement con;
					PreparedStatement ps = con.getConnection().prepareStatement(rq);
					ps.setString(1, num);
					ps.setString(2, nom); 
					ps.setString(3, sexe);
					ps.setString(4, classe);
					ps.setBytes(5, userimage);
					ps.executeUpdate();
					JOptionPane.showMessageDialog(null, "students fine register",null, JOptionPane.INFORMATION_MESSAGE);
					
				}catch(SQLException ex) {
					JOptionPane.showMessageDialog(null, "ERROR !"+ex.getMessage(),null, JOptionPane.ERROR_MESSAGE);
				}
				dispose();
				Eleve elv = new Eleve();
				elv.setVisible(true);
			}
		});
		container.add(bRegister);
		
		// Menu de suppression
		bDelete = new JButton("SUPPRIMER");
		bDelete.setFont(new Font ("Arial",Font.BOLD,10));
		bDelete.setForeground(Color.BLACK);
		bDelete.setBounds(250, 130, 110, 18);
		bDelete.setBackground(new Color(173,216,230));
		bDelete.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ev) {
				String num;
				num = tNumero.getText();
				String rq = "delete from tb_eleve where numero = '" +num + "'";
				try {
					pst = con.getConnection().createStatement();
					pst.executeUpdate(rq);
					JOptionPane.showMessageDialog(null,"Eleve Supprimer !",null,JOptionPane.INFORMATION_MESSAGE);
					
					//connection().close
				}catch(SQLException ex) {
					JOptionPane.showMessageDialog(null, "Erreur"+ex.getMessage(), null, JOptionPane.ERROR_MESSAGE);
				}
			dispose();
			Eleve elv = new Eleve();
			elv.setVisible(true);
			}
		});
		container.add(bDelete); 
		// Partie photo
		
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
		    	pic.setCurrentDirectory(new File("D:/Images"));
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
		
		//listes des eleves
		
		DefaultTableModel model = new DefaultTableModel();
		init();
		container.add(scrolll);
		model.addColumn("code");
		model.addColumn("Nom et Prenom");
		model.addColumn("sexe");
		model.addColumn("Classe");
		JButton JButtonDelete = new JButton("Delete");
		JButtonDelete.setBounds(0,0,78,20);
		JButtonDelete.setForeground(Color.BLUE);
		
		//table1.setBounds(10,450,350,250);
		table1.setBackground(Color.white);
		table1.setModel(model);
		String sql = "select* from tb_eleve order by numero asc";
		try {
			pst = con.getConnection().createStatement();
			ResultSet rs = pst.executeQuery(sql);
			while (rs.next()) {
				model.addRow(new Object[] {
						rs.getString("new JButtonDelete()"),
						rs.getString("numero"),
						rs.getString("nom"),
						rs.getString("sexe"),
						rs.getString("classe"),
				});
			}
			//fermeture de la connection
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,"Error!",null,JOptionPane.ERROR_MESSAGE);
		}
		table1.addMouseListener(new java.awt.event.MouseAdapter() {
		public void mouseReleased(java.awt.event.MouseEvent evt) {
			table1MouseReleased(evt);
		}
		private void table1MouseReleased(MouseEvent evt) {
			int selectionner = table1.getSelectedRow();
			DefaultTableModel model = (DefaultTableModel)table1.getModel();
			tNumero.setText(model.getValueAt(selectionner, 0).toString());
			tNom.setText(model.getValueAt(selectionner,1).toString());
			cSexe.setSelectedItem(model.getValueAt(selectionner, 2).toString());
			cClasse.setSelectedItem(model.getValueAt(selectionner, 3).toString());
		}
		
});
}
	public static void main(String[]args) {
		new Eleve();
	}
}
