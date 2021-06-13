/*
 * Copyright (C) isXander [2019 - 2021]
 * This program comes with ABSOLUTELY NO WARRANTY
 * This is free software, and you are welcome to redistribute it
 * under the certain conditions that can be found here
 * https://www.gnu.org/licenses/gpl-3.0.en.html
 *
 * If you have any questions or concerns, please create
 * an issue on the github page that can be found here
 * https://github.com/isXander/EvergreenHUD
 *
 * If you have a private concern, please contact
 * isXander @ business.isxander@gmail.com
 */

package co.uk.isxander.evergreenhud.config;

import co.uk.isxander.evergreenhud.elements.ElementManager;
import co.uk.isxander.xanderlib.utils.json.BetterJsonObject;
import co.uk.isxander.evergreenhud.EvergreenHUD;

import java.io.File;

public class MainConfig {

    private static final int VERSION = 1;
    public static final File CONFIG_FILE = new File(EvergreenHUD.DATA_DIR, "config.json");

    private final ElementManager manager;

    public MainConfig(ElementManager manager) {
        this.manager = manager;
    }

    public void load() {
        BetterJsonObject root;
        try {
            root = BetterJsonObject.getFromFile(CONFIG_FILE);
        } catch (Exception e) {
            save();
            return;
        }

        // new config version so we need to reset the config
        if (root.optInt("version") != VERSION) {
            EvergreenHUD.LOGGER.warn("Resetting configuration! Older or newer config version detected.");
            EvergreenHUD.getInstance().notifyConfigReset();
            save();
            return;
        }
        manager.setEnabled(root.optBoolean("enabled", true));
        manager.setUseAlternateLook(root.optBoolean("alternate_look", true));
    }

    public BetterJsonObject generateJson() {
        BetterJsonObject root = new BetterJsonObject();
        root.addProperty("version", VERSION);
        root.addProperty("enabled", manager.isEnabled());
        root.addProperty("alternate_look", manager.isUseAlternateLook());

        return root;
    }

    public void save() {
        generateJson().writeToFile(CONFIG_FILE);
    }

}
