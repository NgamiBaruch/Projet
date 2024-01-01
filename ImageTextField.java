package gestion;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JTextField;

public class ImageTextField extends JTextField implements FocusListener {
	private Image image;
	private Icon icon;
	private String labelText;
	
	public ImageTextField(String labelText,Image image) {
		this.labelText= labelText;
		this.image = image;
		setOpaque(false);
		setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		addFocusListener(this);
	}
	public ImageTextField(String laabelText, Icon icon) {
		this.labelText = labelText;
		this.icon = icon;
		setOpaque(false);
		setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		
	}
	
	protected void paintCompoment(Graphics g) {
		super.paintComponent(g);
		if(getText().isEmpty() && ! hasFocus()) {
			Graphics2D g2d =  (Graphics2D)g.create();
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			if(image != null) {
				int x = getWidth()-image.getWidth(null) - 5;
				int y = (getHeight()-image.getHeight(null)/2);
				g2d.drawImage(image, x , y , null);
			}else if(icon!=null){
				int x = getWidth()-image.getWidth(null) - 5;
				int y = (getHeight()-image.getHeight(null)/2);
				icon.paintIcon(this, g2d, x, y);
			}
			g2d.setColor(getDisabledTextColor());
			g2d.drawString(labelText, 5, (getHeight()+g2d.getFontMetrics().getAscent())/2);
			g2d.dispose();
		}
		
	}

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		repaint();
	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		repaint();
	}
	

}
