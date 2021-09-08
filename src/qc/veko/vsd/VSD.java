package qc.veko.vsd;

import qc.veko.easyswing.EasySwing;

public class VSD extends EasySwing {

	public ConfigManager configManager = new ConfigManager();
	public static VSD instance;
	
	public static void main(String[] args) {
		
		new VSD();
		getFrame().setDefaultPanel(new BasicPanel()).setFrameResolution(600, 600).setFrameTitle("VSD Project V0.0.1");
		launch();
		
	}
	
	public VSD() {
		instance = this;
		try {
			configManager.loadConfig();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static VSD getInstance() {
		return instance;
	}


}
