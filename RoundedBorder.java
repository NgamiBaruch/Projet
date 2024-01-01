package gestion;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;

import javax.swing.border.AbstractBorder;

public class RoundedBorder extends AbstractBorder {
	private int raduis;
	public  RoundedBorder(int raduis) {
		this.raduis = raduis;
		
	}

	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		
		Graphics2D g2d = (Graphics2D)g;
		g2d.drawRoundRect(x,y,width - 1, height - 1, raduis, raduis);

	}

}
