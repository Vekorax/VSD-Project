package qc.veko.easyswing.engine;

import javax.swing.JFrame;

public class EasyFrame extends JFrame{

	
	public static EasyPanel panel;
	private static EasyFrame instance;
	private static int width;
	private static int height;
	private static String name;
	
	public EasyFrame() {
		instance = this;
	}
	
	public void getJFrame() {
		setTitle(name);
    	setDefaultLookAndFeelDecorated(true);
    	setContentPane(panel);
    	//setIconImage(Utils.URLToImage("https://yt3.ggpht.com/a/AATXAJxW5KxqZGjjQAwPInex_b_yk1Oz7x-GKUzdGw=s900-c-k-c0xffffffff-no-rj-mo"));
    	setSize(width, height);
    	setDefaultCloseOperation(3);
    	setLocationRelativeTo(null);
    	this.setVisible(true);
    	this.setResizable(false);
    	this.setFocusable(true);
    	this.setLocationRelativeTo(null);
    	//this.setAlwaysOnTop(true);
    	setLayout(null);
	}

	@SuppressWarnings("static-access")
	public void setPanel(EasyPanel panel) {
		this.panel = panel;
		setContentPane(panel);
		this.repaint();
        this.revalidate();
	}
	@SuppressWarnings("static-access")
	public EasyFrame setDefaultPanel(EasyPanel panel) {
		this.panel = panel;
		return this;
	}
	
	public static EasyFrame getInstance() {
		return instance;
	}
	
	@SuppressWarnings("static-access")
	public EasyFrame setFrameResolution(int x, int y) {
		this.width = x;
		this.height = y;
		return this;
	}
	
	@SuppressWarnings("static-access")
	public EasyFrame setFrameTitle(String name) {
		this.name = name;
		return this;
	}

	
}
