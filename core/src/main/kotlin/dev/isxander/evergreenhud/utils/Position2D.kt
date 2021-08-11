/*
 | EvergreenHUD - A mod to improve on your heads-up-display.
 | Copyright (C) isXander [2019 - 2021]
 |
 | This program comes with ABSOLUTELY NO WARRANTY
 | This is free software, and you are welcome to redistribute it
 | under the certain conditions that can be found here
 | https://www.gnu.org/licenses/lgpl-3.0.en.html
 |
 | If you have any questions or concerns, please create
 | an issue on the github page that can be found here
 | https://github.com/isXander/EvergreenHUD
 |
 | If you have a private concern, please contact
 | isXander @ business.isxander@gmail.com
 */

package dev.isxander.evergreenhud.utils

import dev.isxander.evergreenhud.compatibility.universal.RESOLUTION

class Position2D private constructor(var scaledX: Float, var scaledY: Float, var scale: Float) {

    var rawX: Float
        get() = RESOLUTION.scaledWidth.toFloat() * scaledX
        set(x) { scaledX = MathUtils.getPercent(x, 0f, RESOLUTION.scaledWidth.toFloat()) }
    var rawY: Float
        get() = RESOLUTION.scaledHeight.toFloat() * scaledY
        set(y) { scaledY = MathUtils.getPercent(y, 0f, RESOLUTION.scaledHeight.toFloat()) }

    companion object {
        fun rawPositioning(x: Float, y: Float, scale: Float = 1f): Position2D =
            Position2D(MathUtils.getPercent(x, 0f, RESOLUTION.scaledWidth.toFloat()), MathUtils.getPercent(y, 0f, RESOLUTION.scaledHeight.toFloat()), scale)

        fun scaledPositioning(x: Float, y: Float, scale: Float = 1f): Position2D =
            Position2D(x, y, scale)
    }

}

fun rawPosition(lambda: PositionBuilder.() -> Unit): Position2D =
    with(PositionBuilder().apply(lambda)) {
        Position2D.rawPositioning(x, y, scale)
    }

fun scaledPosition(lambda: PositionBuilder.() -> Unit): Position2D =
    with(PositionBuilder().apply(lambda)) {
        Position2D.scaledPositioning(x, y, scale)
    }

class PositionBuilder {

    var x: Float = 0f
    var y: Float = 0f
    var scale: Float = 1f

}