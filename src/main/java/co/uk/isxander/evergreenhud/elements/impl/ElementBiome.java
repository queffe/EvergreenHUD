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
import net.minecraft.util.BlockPos;
import net.minecraft.world.chunk.Chunk;

public class ElementBiome extends SimpleTextElement {

    @Override
    public ElementData metadata() {
        return new ElementData("Biome Display", "Displays the current biome you are standing in.", "Simple");
    }

    @Override
    protected String getValue() {
        String text = null;

        if (mc.theWorld != null && mc.thePlayer != null) {
            BlockPos playerPos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
            Chunk playerChunk = mc.theWorld.getChunkFromBlockCoords(playerPos);
            text = playerChunk.getBiome(playerPos, mc.theWorld.getWorldChunkManager()).biomeName;
        }

        if (text == null) text = "Unknown";

        return text;
    }

    @Override
    public String getDefaultDisplayTitle() {
        return "Biome";
    }

}
