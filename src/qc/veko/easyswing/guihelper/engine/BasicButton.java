package qc.veko.easyswing.guihelper.engine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

import qc.veko.easyswing.engine.EasyPanel;
import qc.veko.easyswing.utils.Utils;

@SuppressWarnings("serial")
public class BasicButton extends JComponent implements MouseListener{
	
	public String text;
	public int id;
	public boolean hover = false;
	
	private EasyPanel panel;
	
	private int x;
	private int y;
	private int width;
	private int height;
	
	public boolean colored = false;
	private Color hoverColor;
	private Color normalColor;
	
	private Image hoverImage;
	private Image normalImage;
	
	
    public BasicButton(EasyPanel panel, int minX, int minY, int maxX, int maxY, String name, int id) {
        this.addMouseListener(this);
        this.panel = panel;
        x = minX;
        y = minY;
        width = maxX;
        height = maxY;
        text = name;
    }
    
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(1,1);
	}
	
	@Override
    public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBounds(x, y, width, height);
		if (colored) {
			if (hover)
				g.setColor(hoverColor);
			else 
				g.setColor(normalColor);
			g.fillRect(0,0,this.getWidth(), this.getHeight());
		} else {
			if (hover)
				Utils.drawFullsizedImage(g, this, hoverImage);
			else
				Utils.drawFullsizedImage(g, this, normalImage);
		}
		Utils.drawCenteredString(g, text, this.getBounds(), this.getFont());
    }
	
	public int getId() {
		return id;
	}
	
	public String getText() {
		return text;
	}
	
	public BasicButton setColored(Color normal, Color hover) {
		normalColor = normal;
		hoverColor = hover;
		colored = true;
		return this;
	}
	
	public BasicButton setTextured(Image normal, Image hover) {
		normalImage = normal;
		hoverImage = hover;
		this.hover = false;
		//button.setIcon(Utils.convertImageToIcon(normal));
		//button.setRolloverIcon(Utils.convertImageToIcon(hover));
		//button.setPressedIcon(Utils.convertImageToIcon(hover));
		return this;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		panel.onButtonClick(this);
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		hover = true;
		
		repaint();
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		hover = false;
		
		repaint();
		
	}
	
}
