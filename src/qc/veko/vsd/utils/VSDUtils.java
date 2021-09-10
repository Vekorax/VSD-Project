package qc.veko.vsd.utils;

import qc.veko.easyswing.guihelper.EasyButton;
import qc.veko.vsd.VSD;
import qc.veko.vsd.panel.BasicPanel;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;

public class VSDUtils {

    public static final int WIDTH_OF_BUTTON = 150;
    public static final int HEIGHT_OF_BUTTON = 40;
    public static final int NUMBER_OF_PIXEL_TO_SEPERATE_BUTTONS_HEIGHT = 70;
    public static final int BUTTONS_SEPARATION = 35;
    public static final int NUMBER_OF_BUTTTONS_PER_ROW = 3;

    public static void launch(int id) {
        EasyButton button = BasicPanel.getInstance().buttonsMap.get(id);
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
