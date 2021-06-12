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

package co.uk.isxander.evergreenhud.elements.impl;

import co.uk.isxander.evergreenhud.elements.ElementData;
import co.uk.isxander.evergreenhud.elements.type.SimpleTextElement;
import net.minecraft.client.network.NetworkPlayerInfo;

public class ElementPing extends SimpleTextElement {

    private int ping = 0;

    @Override
    public ElementData metadata() {
        return new ElementData("Ping Display", "Shows the delay in ms for your actions to be sent to the server.", "Combat");
    }

    @Override
    protected String getValue() {
        if (mc.thePlayer == null)
            return "Unknown";

        NetworkPlayerInfo info = mc.getNetHandler().getPlayerInfo(mc.thePlayer.getGameProfile().getId());
        if (info != null && info.getResponseTime() != 1)
            ping = info.getResponseTime();

        return Integer.toString(ping);
    }


    @Override
    public String getDefaultDisplayTitle() {
        return "Ping";
    }

}
