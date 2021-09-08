package qc.veko.easyswing.engine;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import qc.veko.easyswing.guihelper.EasyButton;

import qc.veko.easyswing.guihelper.engine.BasicButton;
import qc.veko.easyswing.utils.Utils;

@SuppressWarnings("serial")
public class EasyPanel extends JPanel{

	private String backgroundPath = "https://www.litmus.com/wp-content/uploads/2020/04/ultimate-guide-to-background-images-in-email.png";

	private int widthOfImage = 0;
	private int heightOfImage = 0;
	
	private static EasyPanel instance;
	public EasyPanel() {
		instance = this;
		//setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

	}
	
	public EasyPanel setBackGround(String path) {
		this.backgroundPath = path;
		update(getGraphics());
		return this;
	}
	
	public EasyPanel setBackGroundScale(int width, int height) {
		widthOfImage = width;
		heightOfImage = height;
		return this;
	}
	
	public EasyPanel resetBackGroundScale() {
		widthOfImage = 0;
		heightOfImage = 0;
		return this;
	}

	@Override
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    Image image = Utils.getImage(backgroundPath);
	    if (widthOfImage != 0 && heightOfImage != 0)
	    	image = Utils.getScaledImage(image, widthOfImage, heightOfImage);
	    g.drawImage(image, 0, 0, null);
	}
	public void onButtonClick(EasyButton button) {
		
	}
	
	public void onButtonClick(BasicButton button) {
		
	}
	
	
	public static EasyPanel getInstance() {
		return instance;
	}
}
