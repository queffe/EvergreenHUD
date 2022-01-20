/*
 * EvergreenHUD - A mod to improve your heads-up-display.
 * Copyright (c) isXander [2019 - 2022].
 *
 * This work is licensed under the GPL-3 License.
 * To view a copy of this license, visit https://www.gnu.org/licenses/gpl-3.0.en.html
 */

package dev.isxander.evergreenhud.gui.screens

import dev.isxander.evergreenhud.EvergreenHUD
import dev.isxander.evergreenhud.gui.screens.components.ElementComponent
import dev.isxander.evergreenhud.gui.screens.components.SidebarComponent
import gg.essential.elementa.ElementaVersion
import gg.essential.elementa.WindowScreen
import gg.essential.elementa.components.inspector.Inspector
import gg.essential.elementa.dsl.*

class ElementConfigurationMenu : WindowScreen(ElementaVersion.V1) {

    lateinit var selectedElement: ElementComponent
    val sidebar = SidebarComponent() childOf window

    init {
        Inspector(window) childOf window
        for (element in EvergreenHUD.elementManager) {
            ElementComponent(element) {
                this::selectedElement.isInitialized && selectedElement == it
            }.onMouseClick {
                selectedElement = this as ElementComponent
            } childOf window
        }
    }

}
