package qc.veko.easyswing.utils;

import java.awt.Color;
import java.util.HashMap;

public enum EasyColor {

	DARK_GREY(new Color(75, 75, 75)),
	ORANGE(new Color(249, 91, 0)),
	DARK_GRAY(new Color(23, 23, 23)),
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
