package qc.veko.vsd.manager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import qc.veko.easyswing.utils.Utils;

public class ConfigManager {

	private final String OS = (System.getProperty("os.name")).toUpperCase();
	private String dataFolder = getOsDataFolder();
	private final String CONFIG_FILE_PATH = dataFolder + "config.yml";
	private final String BUTTONS_FILE_PATH = dataFolder + "/buttons//";
	private final String DEFAULT_WALLPAPER = "/qc/veko/vsd/background.png";
	private final int DEFAULT_NUMBER_BUTTONS = 9;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<Integer, Map<String, String>> buttonInformations = new HashMap();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, String> configInformations = new HashMap();

	private String getOsDataFolder() {
		String dataFolder;
		if (OS.contains("WIN")) {
			dataFolder = System.getenv("APPDATA") + "\\" + "VSD" + "//";
		}
		else {
			dataFolder = System.getProperty("user.home") + "/VSD//";
		}
		return dataFolder;
	}

	public void loadConfig() throws Exception {
		File mainDirectory = new File(dataFolder);
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
			mainDirectory.setWritable(true);
			buttonsDirectory.mkdir();
			createConfigFile();
		}

	}
	
	//Reading the .yml file for a button
	private void readButtonInformation(int fileNumber) throws IOException {
		BufferedReader file = null;
		if (OS.equals("WIN")) {
			file = new BufferedReader(new FileReader(new File(BUTTONS_FILE_PATH + "\\button" + fileNumber + ".yml")));
		} else {
			file = new BufferedReader(new FileReader(new File(BUTTONS_FILE_PATH + "button" + fileNumber + ".yml")));
		}
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
		File buttonFile = null;
		if (OS.equals("WIN"))
			buttonFile = new File(BUTTONS_FILE_PATH + "\\button" + id + ".yml");
		else
			buttonFile = new File(BUTTONS_FILE_PATH + "button" + id + ".yml");
		buttonFile.getParentFile().mkdir();
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
		File file = null;
		if (OS.equals("WIN"))
			file = new File(BUTTONS_FILE_PATH + "\\button" + id + ".yml");
		else
			file = new File(BUTTONS_FILE_PATH + "button" + id + ".yml");
		file.delete();
		buttonInformations.remove(id);
	}
	
	private void createConfigFile() throws IOException {
		File buttonFile = new File(CONFIG_FILE_PATH);
		System.out.println(CONFIG_FILE_PATH);

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
