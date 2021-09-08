package qc.veko.easyswing.utils;

import java.awt.Color;
import java.util.HashMap;

public enum EasyColor {

	Dark_Grey(new Color(75, 75, 75)),
	Orange(new Color(249, 91, 0)),
	;
	
	private Color color;
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static HashMap<EasyColor, Color> getColor = new HashMap();
	
	private EasyColor(Color color) {
		this.color = color;
	}
	
	public static Color getColor(EasyColor color) {
		return getColor.get(color);
	}
	
	private Color getColor() {
		return color;
	}
	
	static {
		for (EasyColor color : values()) {
			getColor.put(color, color.getColor());
		}
	}
	
}
