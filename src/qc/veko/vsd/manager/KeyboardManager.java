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
            Map<String, String> m = new HashMap<>();
            if (VSD.getInstance().configManager.buttonInformations.containsKey(ButtonSetPanel.getInstance().button.getId()))
                m = VSD.getInstance().configManager.buttonInformations.get(ButtonSetPanel.getInstance().button.getId());
            m.put("KeyBind", String.valueOf(arg0.getKeyCode()));
            m.put("KeyBindText", NativeKeyEvent.getKeyText(arg0.getKeyCode()));
            VSD.getInstance().configManager.buttonInformations.put(ButtonSetPanel.getInstance().button.getId(), m);

            try {
                VSD.getInstance().configManager.addKeybindToButtonsFile(ButtonSetPanel.getInstance().button.getId(), arg0.getKeyCode(), NativeKeyEvent.getKeyText(arg0.getKeyCode()));
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            if (BasicPanel.getInstance().showKeybind)
                BasicPanel.getInstance().getButtonFromId(ButtonSetPanel.getInstance().button.getId()).setText("(" + NativeKeyEvent.getKeyText(arg0.getKeyCode()) + ")");

            ButtonSetPanel.getInstance().isKeyBindingMode = false;
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
