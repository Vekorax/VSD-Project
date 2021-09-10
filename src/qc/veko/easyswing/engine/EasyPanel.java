package qc.veko.easyswing.engine;

import java.awt.*;

import javax.swing.JPanel;

import qc.veko.easyswing.guihelper.EasyButton;

import qc.veko.easyswing.guihelper.engine.BasicButton;
import qc.veko.easyswing.utils.Utils;

@SuppressWarnings("serial")
public class EasyPanel extends JPanel{

	private String backgroundPath = "https://www.litmus.com/wp-content/uploads/2020/04/ultimate-guide-to-background-images-in-email.png";

	private int widthOfImage = 0;
	private int heightOfImage = 0;

	private Color titleBarColor;
	private Color backgroundColor;
	private boolean useTitleBar;
	private String titleBarText;

	private BackGroundType backgroundType;
	private static EasyPanel instance;
	public EasyPanel() {
		instance = this;
		//setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

	}
	
	public EasyPanel setBackGroundImage(String path) {
		backgroundType = BackGroundType.IMAGE;
		this.backgroundPath = path;
		update(getGraphics());
		return this;
	}
	
	public EasyPanel setBackGroundScale(int width, int height) {
		backgroundType = BackGroundType.IMAGE;
		widthOfImage = width;
		heightOfImage = height;
		return this;
	}
	
	public EasyPanel resetBackGroundScale() {
		backgroundType = BackGroundType.IMAGE;
		widthOfImage = 0;
		heightOfImage = 0;
		return this;
	}
	public EasyPanel setTitleBar(boolean bool) {
		useTitleBar = bool;
		return this;
	}

	public EasyPanel setTitleBar(String title, Color color) {
		useTitleBar = true;
		backgroundType = BackGroundType.COLOR;
		titleBarColor = color;
		titleBarText = title;
		return this;
	}

	public EasyPanel setBakcgroundColor(Color color) {
		useTitleBar = true;
		backgroundType = BackGroundType.COLOR;
		backgroundColor = color;
		return this;
	}

	@Override
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
		switch (backgroundType){
			case COLOR:
				g.setColor(backgroundColor);
				g.fillRect(0,0,this.getWidth(), this.getHeight());
				if (useTitleBar) {
					g.setColor(titleBarColor);
					Rectangle rect = new Rectangle(0,0,this.getWidth(), 50);
					g.fillRect(0,0,this.getWidth(), 50);
					Font font = g.getFont().deriveFont(20f);
					Utils.drawCenteredString(g, titleBarText, rect, font);
				}
				break;
			default:
				Image image = Utils.getImage(backgroundPath);
				if (widthOfImage != 0 && heightOfImage != 0)
					image = Utils.getScaledImage(image, widthOfImage, heightOfImage);
				g.drawImage(image, 0, 0, null);
		}
	}
	public void onButtonClick(EasyButton button) {
		
	}
	
	public void onButtonClick(BasicButton button) {
		
	}
	
	
	public static EasyPanel getInstance() {
		return instance;
	}

	private enum BackGroundType {
		COLOR(),
		IMAGE();
	}
}
