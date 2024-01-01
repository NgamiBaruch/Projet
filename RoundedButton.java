package gestion;

import java.awt.Color;

import javax.swing.JButton;

public class RoundedButton extends JButton {
	public RoundedButton(String text) {
		super(text);
		setOpaque(false);
		setBackground (Color.BLUE);
		setBorder(new RoundedBorder(10));
		
	}

}
