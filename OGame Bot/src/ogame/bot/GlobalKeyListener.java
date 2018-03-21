package ogame.bot;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

/**
 * Created by Nikolaos Kouroumalos on 05/08/2017.
 *
 * @author Nikolaos Kouroumalos
 * @version 0.1
 */
public class GlobalKeyListener {

    public GlobalKeyListener() {
        try {
            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());

            System.exit(1);
        }

        GlobalScreen.addNativeKeyListener(new GlobalKeyListenerExample());
    }
}
