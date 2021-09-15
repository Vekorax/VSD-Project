/*
Ce logiciel vous permet de créé des keybinds pour ouvrir des application, des sites web, de mute votre
micro, etc. Les possibilité sont grande et c'est principallement une alternative au fameux StreamDeck.

Author: Vekorax
Date: 23/08/2021
 */

package qc.veko.vsd;

import qc.veko.easyswing.EasySwing;
import qc.veko.vsd.manager.ConfigManager;
import qc.veko.vsd.manager.KeyboardManager;
import qc.veko.vsd.panel.BasicPanel;

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
		new KeyboardManager();
	}
	
	public static VSD getInstance() {
		return instance;
	}


}
