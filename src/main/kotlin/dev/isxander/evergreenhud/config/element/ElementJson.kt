/*
 * EvergreenHUD - A mod to improve your heads-up-display.
 * Copyright (c) isXander [2019 - 2021].
 *
 * This work is licensed under the CC BY-NC-SA 4.0 License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/4.0
 */

package dev.isxander.evergreenhud.config.element

import dev.isxander.evergreenhud.elements.Element
import kotlinx.serialization.Serializable

@Serializable
data class ElementJson(val schema: Int, val elements: List<Element>)
