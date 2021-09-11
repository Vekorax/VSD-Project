package qc.veko.vsd.panel;

import qc.veko.easyswing.engine.EasyFrame;
import qc.veko.easyswing.engine.EasyPanel;
import qc.veko.easyswing.guihelper.EasyButton;
import qc.veko.easyswing.utils.EasyColor;
import qc.veko.vsd.VSD;
import qc.veko.vsd.utils.VSDUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ButtonSetPanel extends EasyPanel {

    private static ButtonSetPanel instance;

    public EasyButton button;
    public EasyButton keybindButton;
    public EasyButton pathButton;
    public boolean isKeyBindingMode = false;

    public ButtonSetPanel(EasyButton button){
        setBakcgroundColor(EasyColor.getColor(EasyColor.DARK_GREY));
        setTitleBar("VSD Project V0.0.1 ALPHA", Color.orange);
        instance = this;
        setLayout(null);
        this.button = button;
        loadButton();
    }

    private void loadButton() {
        String buttonName = (button.getPath() != null) ? button.getText() : "Edit Name";
        new EasyButton(this,  xOfButton(1), 140, VSDUtils.WIDTH_OF_BUTTON, VSDUtils.HEIGHT_OF_BUTTON, buttonName, 1, null).setColored(Color.darkGray, Color.gray).setTextColor(Color.white).addButton();;

        pathButton = new EasyButton(this, VSDUtils.BUTTONS_SEPARATION, 500, VSDUtils.WIDTH_OF_BUTTON, VSDUtils.HEIGHT_OF_BUTTON, "Set Path", 2, null).setTextColor(Color.white).setColored(Color.darkGray, Color.gray).addButton();
        keybindButton = new EasyButton(this, xOfButton(1), 500, VSDUtils.WIDTH_OF_BUTTON, VSDUtils.HEIGHT_OF_BUTTON, "KeyBind", 3, null).setColored(deleteButtonsColors(isKeyBindingMode), deleteButtonsColors(isKeyBindingMode)).addButton();
        new EasyButton(this, xOfButton(2), 500, VSDUtils.WIDTH_OF_BUTTON, VSDUtils.HEIGHT_OF_BUTTON, "Finish", 4, null).setTextColor(Color.white).setColored(Color.darkGray, Color.gray).addButton();
        new EasyButton(this, xOfButton(2), 430, VSDUtils.WIDTH_OF_BUTTON, VSDUtils.HEIGHT_OF_BUTTON, "Back", 5, null).setTextColor(Color.white).setColored(Color.darkGray, Color.gray).addButton();
    }

    @Override
    public void onButtonClick(EasyButton button){
        switch (button.getId()) {
            case 1:
                chooseName(button);
                break;
            case 2:
                chooseData(this.button.getText());
                break;
            case 3:
                isKeyBindingMode = !isKeyBindingMode;
                button.setColored(deleteButtonsColors(isKeyBindingMode), deleteButtonsColors(isKeyBindingMode));
                break;
            case 4:
                if (this.button.getText() != null && this.button.getPath() != null)
                    EasyFrame.getInstance().setPanel(new BasicPanel());
                else
                    JOptionPane.showMessageDialog(null, "The name and the path must be set before finishing", "Empty informations", JOptionPane.INFORMATION_MESSAGE);
                break;
            default:
                EasyFrame.getInstance().setPanel(new BasicPanel());
        }
    }

    public Color deleteButtonsColors(boolean buttonState) {
        if (buttonState)
            return Color.GREEN;
        else
            return Color.RED;
    }

    private int xOfButton (int xCounter) {
        if (xCounter == 0)
            return VSDUtils.BUTTONS_SEPARATION;
        else
            return VSDUtils.WIDTH_OF_BUTTON * xCounter + VSDUtils.BUTTONS_SEPARATION * xCounter + VSDUtils.BUTTONS_SEPARATION;
    }

    public void chooseName(EasyButton button) {
        String text = JOptionPane.showInputDialog(null, "Enter Name of Button", "Name of Button", JOptionPane.INFORMATION_MESSAGE);
        if (text == null)
            return;
        button.setText(text);
        this.button.setText(text);
        try {
            VSD.getInstance().configManager.createButtonInformation(text, null, null, this.button.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void chooseData(String name) {
        Object[] options = { "Internet", "File"};
        int selection = JOptionPane.showOptionDialog(null, "The type of your button?", "Please select", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        switch(selection) {
            case 1:
                openFileSelector(name);
                break;
            case 0:
                openLinkSelector(name);
                break;
        }
    }
    private void openFileSelector(String text) {
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
        }
    }

    private void openLinkSelector(String text) {
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

    public static ButtonSetPanel getInstance(){
        return instance;
    }
}
