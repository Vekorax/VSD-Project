package qc.veko.easyswing.guihelper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;

import qc.veko.easyswing.engine.EasyPanel;

@SuppressWarnings("serial")
public class EasyRectangle extends JComponent{
	private int x;
	private int y;
	private int width;
	private int height;
	
	private Color background;
	private Color title;
	private boolean isTitle = false;
	private String titleText;
	private String[] text;
	private EasyPanel panel;
	
	public static Font font = new Font("Arial", Font.PLAIN, 10);
	
	
	public EasyRectangle (EasyPanel panel, int x , int y, int width, int height, Color background) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.background = background;
		this.panel = panel;
		panel.add(this);
	}

	public EasyRectangle remove() {
		panel.remove(this);
		return this;
	}

	public EasyRectangle setTitleRectangle(String text, Color title) {
		isTitle = true;
		titleText = text;
		this.title = title;
		return this;
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(1,1);
	}
	
	@Override
    public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if (text != null) 
			calculateText(g, text);
		else
			setBounds(x, y, width, height);
		
		g.setColor(background);
		g.fillRect(0,0,this.getWidth(), this.getHeight());
		Rectangle rect = new Rectangle(0,0,this.getWidth(), 30);
		System.out.println( this.getHeight());
		if (text != null)
			drawText(g, text, this.getBounds());
		if (isTitle) {
			drawTitleRectangle(g);
			drawCenteredString(g, titleText, rect);
		}
    }
	
	public EasyRectangle setFontSize(float size) {
		font = font.deriveFont(size);
		this.setFont(font);
		return this;
	}
	public EasyRectangle setText(String text) {
		this.text = text.split("\n");
		return this;
	}
	
	
	/*.............................................
	.................From Litarvan.................
	...............................................*/
	
	private void calculateText(Graphics g, String[] text) {
		int num = text.length;
		int maxLenght = 0;
		for (int i = 0; i < num; ++i) {
			if (maxLenght < Math.max(g.getFontMetrics().stringWidth(text[i]), 0)) {
				maxLenght = Math.max(g.getFontMetrics().stringWidth(text[i]), 0);
			}
		}
		
		setBounds(x, y, maxLenght +15, 38 + (font.getSize() * num));
		//SIZE = new Dimension(maxLenght, heightBox * num);
		//setBounds(x, y, maxLenght, heightBox * num);
		
	}
	private void drawText(Graphics g, String[] text, Rectangle rect) {
		FontMetrics fm = g.getFontMetrics();

        // Drawing the text, centered
        g.setColor(Color.BLACK);
        g.setFont(this.getFont());
		for (int i = 0; i < text.length; ++i) {
			g.drawString(text[i], (int) getStringCenterPos(rect, text[i], fm, g).getX(), (int) (getStringCenterPos(rect, text[i], fm, g).getY() +(14 / text.length) + i*font.getSize()));
		}
	}
	
	private void drawTitleRectangle(Graphics g) {
		g.setColor(title);
		g.fillRect(0,0,this.getWidth(), 30);
	}
	
    public void drawCenteredString(Graphics g, String str, Rectangle parent)
    {
        // Getting the Font Metrics
        FontMetrics fm = g.getFontMetrics();

        // Getting the center pos for this rectangle
        Point centerPos = getStringCenterPos(parent, str, fm, g);

        // Drawing the text, centered
        g.setColor(Color.BLACK);
        g.setFont(this.getFont());
        g.drawString(str, (int) centerPos.getX(), (int) centerPos.getY());
    }
    public Point getStringCenterPos(Rectangle parent, String str, FontMetrics fontMetrics, Graphics g)
    {
        // Getting the string bounds
        Rectangle2D stringBounds = fontMetrics.getStringBounds(str, g);

        // Getting the center pos for this rectangle
        double x = ((parent.getWidth() - stringBounds.getWidth()) / 2);
        double y = ((parent.getHeight() - stringBounds.getHeight()) / 2 + fontMetrics.getAscent());
        return new Point((int) x, (int) y);
    }


}
