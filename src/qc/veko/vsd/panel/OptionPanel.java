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
        EasyRectangle rect = new EasyRectangle(this, "comment ça va blg",Color.darkGray).setTitleRectangle("TEST", Color.orange).setFontSize(15F);
        test.setCommentary(rect);
    }

    @Override
    public void onButtonClick(BasicButton button) {

    }


}
