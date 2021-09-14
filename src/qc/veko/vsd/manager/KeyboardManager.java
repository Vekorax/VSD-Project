package qc.veko.vsd.manager;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.SwingDispatchService;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import qc.veko.easyswing.utils.Utils;
import qc.veko.vsd.VSD;
import qc.veko.vsd.panel.BasicPanel;
import qc.veko.vsd.panel.ButtonSetPanel;
import qc.veko.vsd.utils.VSDUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class KeyboardManager implements NativeKeyListener {

    public KeyboardManager(){
        GlobalScreen.setEventDispatcher(new SwingDispatchService());
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            e.printStackTrace();
        }
        GlobalScreen.addNativeKeyListener(this);
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent arg0) {
    }


    @Override
    public void nativeKeyReleased(NativeKeyEvent arg0) {
        if (ButtonSetPanel.getInstance().isKeyBindingMode) {
            ButtonSetPanel.getInstance().keybind = arg0.getKeyCode();
            ButtonSetPanel.getInstance().keybindText = NativeKeyEvent.getKeyText(arg0.getKeyCode());

            if (BasicPanel.getInstance().showKeybind)
                BasicPanel.getInstance().getButtonFromId(ButtonSetPanel.getInstance().button.getId()).setText("(" + NativeKeyEvent.getKeyText(arg0.getKeyCode()) + ")");

            ButtonSetPanel.getInstance().isKeyBindingMode = false;
            ButtonSetPanel.getInstance().keybindButton
                    .setColored(ButtonSetPanel.getInstance().deleteButtonsColors(false),
                            ButtonSetPanel.getInstance().deleteButtonsColors(ButtonSetPanel.getInstance().isKeyBindingMode));
        } else {
            VSD.getInstance().configManager.buttonInformations.forEach((id, map) -> {
                if (map.containsKey("KeyBind")) {
                    int buttonKeyBind = Utils.convertStringToInteger(map.get("KeyBind"));
                    if (arg0.getKeyCode() == buttonKeyBind)
                        VSDUtils.launch(id);
                }
            });
        }
    }


    @Override
    public void nativeKeyTyped(NativeKeyEvent arg0) {


    }

}
