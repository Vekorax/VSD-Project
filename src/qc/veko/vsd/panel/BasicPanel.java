package qc.veko.vsd.panel;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.awt.Desktop;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import qc.veko.easyswing.engine.EasyFrame;
import qc.veko.easyswing.engine.EasyPanel;
import qc.veko.easyswing.guihelper.EasyButton;
import qc.veko.easyswing.utils.Utils;
import qc.veko.vsd.VSD;

public class BasicPanel extends EasyPanel implements NativeKeyListener {
	
	private int maxButton = 0;
	
	private final int WIDTH_OF_BUTTON = 150;
	private final int HEIGHT_OF_BUTTON = 40;
	private final int NUMBER_OF_PIXEL_TO_SEPERATE_BUTTONS_HEIGHT = 70;
	private final int BUTTONS_SEPARATION = 35;
	private final int NUMBER_OF_BUTTTONS_PER_ROW = 3;
	
	private boolean deleteInformations = false;
	
	private boolean isKeyBindingMode = false;
	private int inKeyBindButtonId = 0;
	private boolean buttonSelectorForKeyBind = false;
	private EasyButton keybindButton;
	private EasyButton deleteButton;
	
	private boolean showKeybind = false;
	
	private JLabel informationsText = new JLabel("", 0);;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Map<Integer, EasyButton> buttonsMap = new HashMap();
	
	private int xOfButton (int xCounter) {
		if (xCounter == 0)
			return BUTTONS_SEPARATION;
		else
			return WIDTH_OF_BUTTON * xCounter + BUTTONS_SEPARATION * xCounter + BUTTONS_SEPARATION;
	}
	

	public BasicPanel() {
		setBackGround(VSD.getInstance().configManager.configInformations.get("Background")).setBackGroundScale(600, 600);
		setLayout(null);
		load();

		try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException e) {
			e.printStackTrace();
		}
		GlobalScreen.addNativeKeyListener(this);
	}
	
	public void load() {
		
		int xCounter = 0;
		int yCounter = 1;
		maxButton = Utils.convertStringToInteger(VSD.getInstance().configManager.configInformations.get("NumberOfButtons"));
		for (int i = 1; i <= maxButton ; ++i) {
			if (VSD.getInstance().configManager.buttonInformations.containsKey(i)) {
				String name = VSD.getInstance().configManager.buttonInformations.get(i).get("Name");
				String path = VSD.getInstance().configManager.buttonInformations.get(i).get("Path");
				buttonsMap.put(i, new EasyButton(this, xOfButton(xCounter), NUMBER_OF_PIXEL_TO_SEPERATE_BUTTONS_HEIGHT * yCounter, WIDTH_OF_BUTTON, HEIGHT_OF_BUTTON, name, i, path).setFontSize(20F).setColored(Color.DARK_GRAY, Color.GRAY).setTextColor(Color.white).addButton());
			} else
				buttonsMap.put(i, new EasyButton(this, xOfButton(xCounter), NUMBER_OF_PIXEL_TO_SEPERATE_BUTTONS_HEIGHT * yCounter, WIDTH_OF_BUTTON, HEIGHT_OF_BUTTON, "button" + i, i, null).setFontSize(20F).setColored(Color.DARK_GRAY, Color.GRAY).setTextColor(Color.white).addButton());
			xCounter++;
			if (xCounter == NUMBER_OF_BUTTTONS_PER_ROW) {
				xCounter = 0;
				yCounter++;
			}
		}
		deleteButton = new EasyButton(this, BUTTONS_SEPARATION, 500, WIDTH_OF_BUTTON, HEIGHT_OF_BUTTON, "Delete info", maxButton + 1, null).setColored(deleteButtonsColors(deleteInformations), deleteButtonsColors(deleteInformations)).addButton();
		keybindButton = new EasyButton(this, xOfButton(1), 500, WIDTH_OF_BUTTON, HEIGHT_OF_BUTTON, "KeyBind", maxButton + 2, null).setColored(deleteButtonsColors(isKeyBindingMode), deleteButtonsColors(isKeyBindingMode)).addButton();
		new EasyButton(this, xOfButton(2), 500, WIDTH_OF_BUTTON, HEIGHT_OF_BUTTON, "Show KeyBind", maxButton + 3, null).setFontSize(15F).setColored(deleteButtonsColors(showKeybind), deleteButtonsColors(showKeybind)).addButton();
		
		informationsText.setForeground(Color.WHITE);
		informationsText.setBounds(75, 300 , 450, 200);
		informationsText.setFont(informationsText.getFont().deriveFont(40.0F));
	    add(informationsText);
	}
	
	
	private Color deleteButtonsColors(boolean buttonState) {
		if (buttonState)
			return Color.GREEN;
		else
			return Color.RED;
	}
	
	private EasyButton getButtonFromId(int id) {
		return buttonsMap.get(id);
	}
	
	@Override
	public void onButtonClick(EasyButton button) {
		if (maxButton < button.getId()) {
			switch (button.getText()) {
			case "Delete info":
				deleteInformations = (deleteInformations) ? false : true;
				if (isKeyBindingMode)
					isKeyBindingMode = false;
				button.setColored(deleteButtonsColors(deleteInformations), deleteButtonsColors(deleteInformations));
				setText();

				//test line remove after
				EasyFrame.getInstance().setPanel(new OptionPanel());

				break;
			case "KeyBind":
				isKeyBindingMode = (isKeyBindingMode) ? false : true;
				button.setColored(deleteButtonsColors(isKeyBindingMode), deleteButtonsColors(isKeyBindingMode));
				setText();
				break;
			case "Show KeyBind":
				showKeybind = (showKeybind) ? false : true;
				if (deleteInformations)
					deleteInformations = false;
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
			}
		} else {
			if (deleteInformations) {
				if (button.getPath() != null) {
					try {
						VSD.getInstance().configManager.deleteButtonInformations(button.getId());
						button.setText("button" + button.getId());
						button.SetPath(null);
						JOptionPane.showMessageDialog(null, "Your button is now fresh", "Buttons info deleted", JOptionPane.INFORMATION_MESSAGE);
						deleteInformations = false;
						deleteButton.setColored(deleteButtonsColors(deleteInformations), deleteButtonsColors(deleteInformations));
						setText();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, e.getMessage(), "An error Occured", JOptionPane.INFORMATION_MESSAGE);
					}
				} else
					JOptionPane.showMessageDialog(null, "This button is not used", "An error Occured", JOptionPane.INFORMATION_MESSAGE);
			} else if(isKeyBindingMode) {
				inKeyBindButtonId = button.getId();
				buttonSelectorForKeyBind = true;
				setText();
			} else
				launchOrCreate(button);
		}
	}
	
	
	private void launchOrCreate(EasyButton button) {
		if (button.getPath() == null) {
			chooseDataAndName(button);
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
	
	private void launchOrCreate(int id) {
		EasyButton button = buttonsMap.get(id);
		if (button.getPath() == null) {
			chooseDataAndName(button);
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
	
	public void chooseDataAndName(EasyButton button) {
		String text = JOptionPane.showInputDialog(null, "Enter Name of Button", "Name of Button", JOptionPane.INFORMATION_MESSAGE);
		if (text == null)
			return;
		
		Object[] options = { "Internet", "File"};
    	int selection = JOptionPane.showOptionDialog(null, "The type of your button?", "Please select", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
    	switch(selection) {
    	case 1:
    		openFileSelector(button, text);
    		break;
    	case 0:
    		openLinkSelector(button, text);
    		break;
    	}
	}
	
	private void openFileSelector(EasyButton button, String text) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Choose a file to open");
		int returnValue = fileChooser.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			String keep;

			keep = selectedFile.getAbsolutePath();
			button.SetPath(keep);
			try {
				VSD.getInstance().configManager.createButtonInformation(text, keep, "file", button.getId());
			} catch (IOException e) {
				e.printStackTrace();
			}
			button.setText(text);
		}
	}
	
	private void openLinkSelector(EasyButton button, String text) {
		String link = JOptionPane.showInputDialog(null, "Enter Name of Button", "Name of Button", JOptionPane.INFORMATION_MESSAGE);
		if (link == null)
			return;
		button.SetPath(link);
		try {
			VSD.getInstance().configManager.createButtonInformation(text, link, "internet", button.getId());
		} catch (IOException e) {
			e.printStackTrace();
		}
		button.setText(text);
	}


	@Override
	public void nativeKeyPressed(NativeKeyEvent arg0) {
	}


	@Override
	public void nativeKeyReleased(NativeKeyEvent arg0) {
		if (buttonSelectorForKeyBind && isKeyBindingMode) {
			Map <String, String> m = VSD.getInstance().configManager.buttonInformations.get(inKeyBindButtonId);
			m.put("KeyBind", String.valueOf(arg0.getKeyCode()));
			m.put("KeyBindText", NativeKeyEvent.getKeyText(arg0.getKeyCode()));
			VSD.getInstance().configManager.buttonInformations.put(inKeyBindButtonId, m);
			
			try {
				VSD.getInstance().configManager.addKeybindToButtonsFile(inKeyBindButtonId, arg0.getKeyCode(), NativeKeyEvent.getKeyText(arg0.getKeyCode()));
			} catch (IOException e1) {
				e1.printStackTrace();
			}	
			
			if (showKeybind)
				getButtonFromId(inKeyBindButtonId).setText("(" + NativeKeyEvent.getKeyText(arg0.getKeyCode()) + ")");
			
			buttonSelectorForKeyBind = false;
			isKeyBindingMode = false;
			keybindButton.setColored(deleteButtonsColors(isKeyBindingMode), deleteButtonsColors(isKeyBindingMode));
			setText();
		} else {
			VSD.getInstance().configManager.buttonInformations.forEach((id, map) -> {
				if (map.containsKey("KeyBind")) {
					int buttonKeyBind = Utils.convertStringToInteger(map.get("KeyBind"));
					if (arg0.getKeyCode() == buttonKeyBind) 
						launchOrCreate(id);
				}
			});
		}
	}


	@Override
	public void nativeKeyTyped(NativeKeyEvent arg0) {
		
		
	}
	
	public void setText() {
		String text = "";
		if (buttonSelectorForKeyBind)
			text = "Press a key";
		else if (isKeyBindingMode || deleteInformations)
			text = "Select a button";
		else 
			text = "";
		this.informationsText.setText(text);
	}
	
}
