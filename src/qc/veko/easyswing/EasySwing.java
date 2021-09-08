package qc.veko.easyswing;

import qc.veko.easyswing.engine.EasyFrame;

public class EasySwing {

	static EasyFrame jFrame = new EasyFrame();
	
	public static void launch() {
		jFrame.getJFrame();
	}
	
	public static EasyFrame getFrame() {
		return jFrame;
	}
	
}
