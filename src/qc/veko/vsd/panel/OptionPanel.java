package qc.veko.vsd.panel;

import qc.veko.easyswing.engine.EasyPanel;
import qc.veko.easyswing.guihelper.EasyButton;
import qc.veko.easyswing.guihelper.EasyRectangle;
import qc.veko.easyswing.guihelper.engine.BasicButton;
import qc.veko.vsd.VSD;

import java.awt.*;

public class OptionPanel extends EasyPanel {
    public OptionPanel() {
        setBackGroundImage(VSD.getInstance().configManager.configInformations.get("Background")).setBackGroundScale(600, 600);

        BasicButton test = new BasicButton(this,100,100,100,100, "name", 1).setColored(Color.white, Color.GRAY);
        new EasyRectangle(this, 10, 200, 100, 100,Color.darkGray).setTitleRectangle("TEST", Color.orange).setText("Ceci est un très long test juste pour vérifier").setFontSize(15F);
    }

    @Override
    public void onButtonClick(BasicButton button) {

    }


}
