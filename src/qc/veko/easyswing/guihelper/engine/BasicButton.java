package qc.veko.easyswing.guihelper.engine;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

import qc.veko.easyswing.engine.EasyPanel;
import qc.veko.easyswing.guihelper.EasyRectangle;
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

	private Color hoverColor;
	private Color normalColor;
	
	private Image hoverImage;
	private Image normalImage;

	private ButtonType buttonType;

	private EasyRectangle rect;
	
	
    public BasicButton(EasyPanel panel, int minX, int minY, int maxX, int maxY, String name, int id) {
        this.addMouseListener(this);
        this.panel = panel;
        x = minX;
        y = minY;
        width = maxX;
        height = maxY;
        text = name;
		setButtonType(ButtonType.BASIC);
		panel.add(this);
    }
    
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(1,1);
	}
	
	@Override
    public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBounds(x, y, width, height);
		switch(buttonType) {
			case TEXTURED:
				if (hover)
					Utils.drawFullsizedImage(g, this, hoverImage);
				else
					Utils.drawFullsizedImage(g, this, normalImage);
				break;
			case COLORED:
				if (hover)
					g.setColor(hoverColor);
				else
					g.setColor(normalColor);
				g.fillRect(0,0,this.getWidth(), this.getHeight());
				break;
			default:
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
		setButtonType(ButtonType.COLORED);
		return this;
	}

	public BasicButton setCommentary(EasyRectangle rect) {
		this.rect = rect;
		return this;
	}
	
	public BasicButton setTextured(Image normal, Image hover) {
		normalImage = normal;
		hoverImage = hover;
		this.hover = false;
		setButtonType(ButtonType.TEXTURED);
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
		if(rect != null) {
			rect.setPosition(x, y-50);
			rect.add();
			panel.repaint();
		}
		repaint();
	}
	@Override
	public void mouseExited(MouseEvent e) {
		hover = false;
		if(rect != null) {
			rect.remove();
			panel.repaint();
		}
		repaint();
		
	}

	public BasicButton setFontSize(float size) {
		this.setFont(this.getFont().deriveFont(size));
		return this;
	}

	private void setButtonType(ButtonType buttonType) {
		this.buttonType = buttonType;
	}

	public enum ButtonType {
		COLORED(),
		TEXTURED(),
		BASIC(),
	}
	
}
