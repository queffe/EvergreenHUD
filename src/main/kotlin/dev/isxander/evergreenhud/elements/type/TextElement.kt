/*
 * EvergreenHUD - A mod to improve your heads-up-display.
 * Copyright (c) isXander [2019 - 2021].
 *
 * This work is licensed under the CC BY-NC-SA 4.0 License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/4.0
 */

package dev.isxander.evergreenhud.elements.type

import dev.isxander.settxi.impl.*
import dev.isxander.evergreenhud.utils.HitBox2D
import java.awt.Color

abstract class TextElement : BackgroundElement() {
    var brackets by boolean(false) {
        name = "Brackets"
        category = "Text"
        description = "Text is displayed within [square brackets]"
    }

    open var title by string("UNKNOWN") {
        name = "Title"
        category = "Text"
        description = "What is displayed before or after the actual value."
    }

    var textColor by color(Color.white) {
        name = "Color"
        category = "Text"
        description = "The color of the text."
    }

    var chroma by boolean(false) {
        name = "Chroma"
        category = "Text"
        description = "Makes the text rainbow barf."
    }

    var chromaSpeed by int(2000) {
        name = "Chroma Speed"
        category = "Text"
        description = "How fast should the chroma wave be?"
        range = 500..10_000

        depends { chroma }
    }

    var textStyle by option(TextStyle.SHADOW) {
        name = "Text Style"
        category = "Text"
        description = "What style text is rendered in."
    }

    var alignment by option(Alignment.LEFT) {
        name = "Alignment"
        category = "Text"
        description = "How the text is aligned."
    }

    open var cacheTime by int(5) {
        name = "Text Cache"
        category = "Text"
        description = "How many client ticks to wait before re-calculating the value. (20 ticks = 1 second)"
        range = 0..20
    }
    var clientTicks = 0

    override fun calculateHitBox(glScale: Float, drawScale: Float): HitBox2D {
        val width = hitboxWidth * drawScale
        val height = hitboxHeight * drawScale

        val top = paddingTop * drawScale
        val bottom = paddingBottom * drawScale
        val left = paddingLeft * drawScale
        val right = paddingRight * drawScale

        val x = position.rawX / glScale
        val y = position.rawY / glScale

        return when (alignment) {
            Alignment.LEFT -> HitBox2D(x - left, y - top, width + left + right, height + top + bottom)
            Alignment.RIGHT -> HitBox2D(x - (width / drawScale) - left, y - top, width + left + right, height + top + bottom)
            Alignment.CENTER -> HitBox2D(x - (width / 2f) - left, y - top, width + left + right, height + top + bottom)
            else -> throw IllegalStateException("Failed to parse alignment.")
        }
    }

    override fun onClientTick() {
        clientTicks++
        if (clientTicks > cacheTime)
            clientTicks = 0
    }

    object TextStyle : OptionContainer() {
        val NORMAL = option("Normal", "Just plain text.")
        val SHADOW = option("Shadow", "Text with Minecraft's shadow. This can be modified with mods like patcher.")
        val BORDER = option("Border", "Adds a black border around normal text. (Can cause increased amount of lag)")
    }

    object Alignment : OptionContainer() {
        val LEFT = option("Left", "When the length of text is increased, it expands to the right.")
        val RIGHT = option("Right", "When the length of text is increased, it expands to the left.")
        val CENTER = option("Center", "When the length of text is increased, it expands both left and right equally.")
    }
}
