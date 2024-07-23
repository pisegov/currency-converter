package com.myaxa.ui.throttledClickListener

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.semantics.Role

/**
 * Designed to prevent multiple clicks on a button
 *
 * @see <a href="https://al-e-shevelev.medium.com/how-to-prevent-multiple-clicks-in-android-jetpack-compose-8e62224c9c5e">
 *          How to prevent multiple clicks in Android Jetpack Compose
 *      </a>
 */
internal class ThrottledClickListener(private val duration: Long = STANDARD_THROTTLE_DURATION_MS) {

    companion object {
        const val STANDARD_THROTTLE_DURATION_MS = 700L
    }

    private val now: Long
        get() = System.currentTimeMillis()

    private var lastEventTimeMs: Long = 0

    fun processEvent(event: () -> Unit) {
        if (now - lastEventTimeMs >= duration) {
            event.invoke()
        }
        lastEventTimeMs = now
    }
}

fun Modifier.throttledClickable(
    throttleDurationMs: Long = ThrottledClickListener.STANDARD_THROTTLE_DURATION_MS,
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit,
) = composed(
    inspectorInfo = debugInspectorInfo {
        name = "clickable"
        properties["enabled"] = enabled
        properties["onClickLabel"] = onClickLabel
        properties["role"] = role
        properties["onClick"] = onClick
    }
) {
    val multipleEventsCutter = remember { ThrottledClickListener(throttleDurationMs) }
    this.clickable(
        enabled = enabled,
        onClickLabel = onClickLabel,
        onClick = { multipleEventsCutter.processEvent { onClick() } },
        role = role,
        indication = LocalIndication.current,
        interactionSource = remember { MutableInteractionSource() }
    )
}
