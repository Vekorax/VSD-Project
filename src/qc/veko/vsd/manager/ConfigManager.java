package qc.veko.vsd.manager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import qc.veko.easyswing.utils.Utils;
import qc.veko.vsd.VSD;
import qc.veko.vsd.panel.ButtonSetPanel;

public class ConfigManager {
	
	private String DATA_FOLDER = System.getenv("APPDATA") + "\\" + "VSD";
	private String CONFIG_FILE_PATH = DATA_FOLDER + "\\config.yml";
	private String BUTTONS_FILE_PATH = DATA_FOLDER + "\\buttons";
	private String DEFAULT_WALLPAPER = "/qc/veko/vsd/background.png";
	private int DEFAULT_NUMBER_BUTTONS = 9;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<Integer, Map<String, String>> buttonInformations = new HashMap();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, String> configInformations = new HashMap();

	public void loadConfig() throws Exception {
		
		File mainDirectory = new File(DATA_FOLDER);
		File buttonsDirectory = new File(BUTTONS_FILE_PATH);
		if(mainDirectory.exists()) {
			File[] directoryListing = buttonsDirectory.listFiles();
			if (directoryListing != null) {
				for (File child : directoryListing) {
					String removed = child.getName().replace("button", "");
					String id = removed.replace(".yml", "");
					readButtonInformation(Utils.convertStringToInteger(id));
				}
			}
			readConfigFile();
		} else {
			mainDirectory.mkdir();
			buttonsDirectory.mkdir();
			createConfigFile();
		}

	}
	
	//Reading the .yml file for a button
	private void readButtonInformation(int fileNumber) throws IOException {
		BufferedReader file = new BufferedReader(new FileReader(new File(BUTTONS_FILE_PATH + "\\button" + fileNumber + ".yml")));
    	String name = file.readLine().replace("Name : ", "").trim();
    	String path = file.readLine().replace("Path : ", "").trim();
    	String type = file.readLine().replace("Type : ", "").trim();
    	String keybind = null;
    	String keybindText = null;
    	try {
    		keybind = file.readLine().replace("KeyBind : ", "").trim();
    		keybindText = file.readLine().replace("KeyBindText : ", "").trim();
    	} catch (Exception e) {
    		
    	}
    	@SuppressWarnings({ "rawtypes", "unchecked" })
		Map<String, String> m = new HashMap();
    	m.put("Name", name);
    	m.put("Path", path);
    	m.put("Type", type);
    	if (keybind != null) {
    		m.put("KeyBind", keybind);
    		m.put("KeyBindText", keybindText);
    	}
    	buttonInformations.put(fileNumber, m);
    	file.close();
	}
	
	//create a .yml file for a button
	public void createButtonInformation(String name, String path, String type, int id) throws IOException {
		File buttonFile = new File(BUTTONS_FILE_PATH + "\\button" + id + ".yml");
		buttonFile.createNewFile();
		FileWriter file = new FileWriter(buttonFile);
		file.write("Name : " + name);
		file.write("\nPath : " + path);
		file.write("\nType : " + type);
		file.close();
		Map<String, String> m = new HashMap();
		if (buttonInformations.containsKey(id))
			m = buttonInformations.get(id);
    	m.put("Name", name);
    	m.put("Path", path);
    	m.put("Type", type);
    	buttonInformations.put(id, m);
		
	}
	
	public void deleteButtonInformations(int id) throws FileNotFoundException {
		File file = new File(BUTTONS_FILE_PATH + "\\button" + id + ".yml");
		file.delete();
		buttonInformations.remove(id);
	}
	
	private void createConfigFile() throws IOException {
		File buttonFile = new File(CONFIG_FILE_PATH);
		buttonFile.createNewFile();
		FileWriter file = new FileWriter(buttonFile);
		file.write("Background : " + DEFAULT_WALLPAPER);
		file.write("\nNumberOfButtons : " + DEFAULT_NUMBER_BUTTONS);
		configInformations.put("Background", DEFAULT_WALLPAPER);
		configInformations.put("NumberOfButtons", Utils.convertIntegerToString(DEFAULT_NUMBER_BUTTONS));
		file.close();
	}
	
	private void readConfigFile() throws IOException {
		BufferedReader file = new BufferedReader(new FileReader(new File(CONFIG_FILE_PATH)));
    	String background = file.readLine().replace("Background : ", "").trim();
    	String numberOfButtons = file.readLine().replace("NumberOfButtons : ", "").trim();
		configInformations.put("Background", background);
		configInformations.put("NumberOfButtons", numberOfButtons);
    	file.close();
	}
	
	public void addKeybindToButtonsFile(int id, int keybind, String string) throws IOException {
		File buttonFile = new File(BUTTONS_FILE_PATH + "\\button" + id + ".yml");
		buttonFile.createNewFile();
		FileWriter file = new FileWriter(buttonFile);
		String name = buttonInformations.get(id).get("Name");
		String path = buttonInformations.get(id).get("Path");
		String type = buttonInformations.get(id).get("Type");
		file.write("Name : " + name);
		file.write("\nPath : " + path);
		file.write("\nType : " + type);
		file.write("\nKeyBind : " + keybind);
		file.write("\nKeyBindText : " + string);
		file.close();
		
		
	}
	
}
