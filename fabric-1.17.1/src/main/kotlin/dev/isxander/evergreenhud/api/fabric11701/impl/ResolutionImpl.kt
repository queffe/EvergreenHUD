/*
 * EvergreenHUD - A mod to improve on your heads-up-display.
 * Copyright (C) isXander [2019 - 2021]
 *
 * This program comes with ABSOLUTELY NO WARRANTY
 * This is free software, and you are welcome to redistribute it
 * under the certain conditions that can be found here
 * https://www.gnu.org/licenses/lgpl-2.1.en.html
 *
 * If you have any questions or concerns, please create
 * an issue on the github page that can be found here
 * https://github.com/isXander/EvergreenHUD
 *
 * If you have a private concern, please contact
 * isXander @ business.isxander@gmail.com
 */

package dev.isxander.evergreenhud.api.fabric11701.impl

import dev.isxander.evergreenhud.api.impl.UResolution
import dev.isxander.evergreenhud.api.fabric11701.mc

class ResolutionImpl : UResolution() {

    override val displayWidth: Int get() = mc.window.width
    override val displayHeight: Int get() = mc.window.height

    override val scaledWidth: Int get() = mc.window.scaledWidth
    override val scaledHeight: Int get() = mc.window.scaledHeight

    override val scaleFactor: Double get() = mc.window.scaleFactor

}