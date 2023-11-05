package io.github.arcticpot.jublockly.features

import gg.essential.elementa.constraints.animation.Animations
import net.minecraft.util.Util


class AnimatedValue(defaultValue: Number) {
    private var oldValue = defaultValue.toDouble()
    private var transitionDuration = -1
    private var startTransition = 0L
    private var transitionValue = defaultValue.toDouble()

    val value: Double get() {
        assert(timeDelta > 0)
        if (transitionDuration < 0 || timeDelta > transitionDuration) return transitionValue
        val delta = timeDelta.toFloat() / transitionDuration.toFloat()
        // Just use Elementa's! I'm lazy lmao
        val interpolationDelta = Animations.OUT_EXP.getValue(delta)
        return oldValue + ((transitionValue - oldValue) * interpolationDelta)
    }

    val intValue: Int get() = value.toInt()

    fun animate(target: Number, duration: Int) {
        oldValue = value
        startTransition = getNow()
        transitionValue = target.toDouble()
        transitionDuration = duration
    }

    /** Stop the animation then set the value */
    fun set(value: Double) {
        transitionDuration = -1
        transitionValue = value
    }

    private fun getNow(): Long {
        return Util.getMeasuringTimeMs()
    }

    private val timeDelta: Int get() {
        return (getNow() - startTransition).toInt()
    }
}