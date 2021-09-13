package qc.veko.vsd.panel;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.awt.Desktop;

import javax.swing.JOptionPane;

import qc.veko.easyswing.engine.EasyFrame;
import qc.veko.easyswing.engine.EasyPanel;
import qc.veko.easyswing.guihelper.EasyButton;
import qc.veko.easyswing.utils.EasyColor;
import qc.veko.easyswing.utils.Utils;
import qc.veko.vsd.VSD;
import qc.veko.vsd.utils.VSDUtils;

public class BasicPanel extends EasyPanel {
	
	private int maxButton = 0;
	private static BasicPanel instance;
	public boolean showKeybind;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<Integer, EasyButton> buttonsMap = new HashMap();
	
	private int xOfButton (int xCounter) {
		if (xCounter == 0)
			return VSDUtils.BUTTONS_SEPARATION;
		else
			return VSDUtils.WIDTH_OF_BUTTON * xCounter + VSDUtils.BUTTONS_SEPARATION * xCounter + VSDUtils.BUTTONS_SEPARATION;
	}

	public BasicPanel() {
		instance = this;
		setBakcgroundColor(EasyColor.getColor(EasyColor.DARK_GREY));
		setTitleBar("VSD Project V0.0.1 ALPHA", Color.orange);
		setLayout(null);
		load();
	}
	
	public void load() {
		int xCounter = 0;
		int yCounter = 1;
		maxButton = Utils.convertStringToInteger(VSD.getInstance().configManager.configInformations.get("NumberOfButtons"));
		for (int i = 1; i <= maxButton ; ++i) {
			if (VSD.getInstance().configManager.buttonInformations.containsKey(i)) {
				String name = VSD.getInstance().configManager.buttonInformations.get(i).get("Name");
				String path = VSD.getInstance().configManager.buttonInformations.get(i).get("Path");
				buttonsMap.put(i, new EasyButton(this, xOfButton(xCounter), VSDUtils.NUMBER_OF_PIXEL_TO_SEPERATE_BUTTONS_HEIGHT * yCounter, VSDUtils.WIDTH_OF_BUTTON, VSDUtils.HEIGHT_OF_BUTTON, name, i, path).setFontSize(20F).setColored(Color.DARK_GRAY, Color.GRAY).setTextColor(Color.white).addButton());
			} else
				buttonsMap.put(i, new EasyButton(this, xOfButton(xCounter), VSDUtils.NUMBER_OF_PIXEL_TO_SEPERATE_BUTTONS_HEIGHT * yCounter, VSDUtils.WIDTH_OF_BUTTON, VSDUtils.HEIGHT_OF_BUTTON, "button" + i, i, null).setFontSize(20F).setColored(Color.DARK_GRAY, Color.GRAY).setTextColor(Color.white).addButton());
			xCounter++;
			if (xCounter == VSDUtils.NUMBER_OF_BUTTTONS_PER_ROW) {
				xCounter = 0;
				yCounter++;
			}
		}
		new EasyButton(this, VSDUtils.BUTTONS_SEPARATION, 500, VSDUtils.WIDTH_OF_BUTTON, VSDUtils.HEIGHT_OF_BUTTON, "Options", maxButton + 1, null).setColored(Color.DARK_GRAY, Color.GRAY).addButton();
		new EasyButton(this, xOfButton(1), 500, VSDUtils.WIDTH_OF_BUTTON, VSDUtils.HEIGHT_OF_BUTTON, "Show KeyBind", maxButton + 2, null).setFontSize(15F).setColored(deleteButtonsColors(showKeybind), deleteButtonsColors(showKeybind)).addButton();
	}

	public Color deleteButtonsColors(boolean buttonState) {
		if (buttonState)
			return Color.GREEN;
		else
			return Color.RED;
	}
	
	public EasyButton getButtonFromId(int id) {
		return buttonsMap.get(id);
	}
	
	@Override
	public void onButtonClick(EasyButton button) {
		if (maxButton < button.getId()) {
			switch (button.getText()) {
			case "Show KeyBind":
				showKeybind = !showKeybind;
				buttonsMap.forEach((id, ezButton) -> {
					if (VSD.getInstance().configManager.buttonInformations.containsKey(id)) {
						if (!showKeybind) {
							ezButton.setText(VSD.getInstance().configManager.buttonInformations.get(id).get("Name"));
							ezButton.setFontSize(20f);
						} else {
							if (VSD.getInstance().configManager.buttonInformations.get(id).containsKey("KeyBind"))
								ezButton.setText("(" + VSD.getInstance().configManager.buttonInformations.get(id).get("KeyBindText") + ")");
							ezButton.setFontSize(10f);
						}
					}
				});
				button.setColored(deleteButtonsColors(showKeybind), deleteButtonsColors(showKeybind));
				break;
			case "Options":
				EasyFrame.getInstance().setPanel(new OptionPanel());
				break;

			}
		} else {
			launchOrCreate(button);
		}
	}

	@Override
	public void onButtonRightClick(EasyButton button) {
		if (button.getText().equals("Show KeyBind"))
			EasyFrame.getInstance().setPanel(new OptionPanel());
		if (maxButton >= button.getId()) {
			if (button.getPath() != null)
				deleteOrEditButton(button);
			else
				JOptionPane.showMessageDialog(null, "This button is not used", "An error Occured", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private void deleteOrEditButton(EasyButton button) {
		Object[] options = { "Delete", "Edit"};
		int selection = JOptionPane.showOptionDialog(null, "The type of your button?", "Please select", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
		switch(selection) {
			case 1:
				EasyFrame.getInstance().setPanel(new ButtonSetPanel(button));
				break;
			case 0:
				try {
					VSD.getInstance().configManager.deleteButtonInformations(button.getId());
					button.setText("button" + button.getId());
					button.SetPath(null);
					JOptionPane.showMessageDialog(null, "Your button is now fresh", "Buttons info deleted", JOptionPane.INFORMATION_MESSAGE);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				break;
		}
	}

	private void launchOrCreate(EasyButton button) {
		if (button.getPath() == null) {
			EasyFrame.getInstance().setPanel(new ButtonSetPanel(button));
		} else {
			String type = VSD.getInstance().configManager.buttonInformations.get(button.getId()).get("Type");
			if (type.equals("internet")) {
				try {
	        		URI link = new URI(button.getPath());
	
	        		if (Desktop.isDesktopSupported())
	        		{
	        			Desktop.getDesktop().browse(link);
	        		}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				
				File selectedFile = new File(button.getPath());
				try {
					Desktop.getDesktop().open(selectedFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static BasicPanel getInstance() {
		return instance;
	}
	
}
