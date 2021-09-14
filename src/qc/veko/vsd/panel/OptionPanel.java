package qc.veko.vsd.panel;

import qc.veko.easyswing.engine.EasyFrame;
import qc.veko.easyswing.engine.EasyPanel;
import qc.veko.easyswing.guihelper.EasyRectangle;
import qc.veko.easyswing.guihelper.engine.BasicButton;
import qc.veko.easyswing.utils.EasyColor;
import qc.veko.vsd.utils.VSDUtils;

import java.awt.*;

public class OptionPanel extends EasyPanel {
    public OptionPanel() {
        setBakcgroundColor(EasyColor.getColor(EasyColor.DARK_GREY));
        setTitleBar("VSD Project V0.0.1 ALPHA", Color.orange);
        EasyRectangle rect = new EasyRectangle(this, "return to Main Menu",Color.darkGray)
                .setTitleRectangle("Go Back", Color.orange)
                .setFontSize(15F);

        new BasicButton(this,30,500 ,VSDUtils.WIDTH_OF_BUTTON, VSDUtils.HEIGHT_OF_BUTTON,"Back", 0)
                .setColored(Color.DARK_GRAY, Color.GRAY)
                .setFontSize(15F)
                .setCommentary(rect);
    }

    @Override
    public void onButtonClick(BasicButton button) {
        switch (button.getId()){
            case 0:
                EasyFrame.getInstance().setPanel(new BasicPanel());
                break;
        }
    }
}
