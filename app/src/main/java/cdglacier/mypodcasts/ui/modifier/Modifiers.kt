package cdglacier.mypodcasts.ui.modifier

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.calculateTargetValue
import androidx.compose.animation.splineBasedDecay
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.verticalDrag
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.input.pointer.util.VelocityTracker
import androidx.compose.ui.unit.IntOffset
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

fun Modifier.swipeDownToDismiss(
    onDismissed: () -> Unit
): Modifier = composed {
    val offsetY = remember { Animatable(0f) }
    pointerInput(Unit) {
        val decay = splineBasedDecay<Float>(this)
        coroutineScope {
            while (true) {
                val pointerId = awaitPointerEventScope { awaitFirstDown().id }
                offsetY.stop()
                val velocityTracker = VelocityTracker()
                awaitPointerEventScope {
                    verticalDrag(pointerId) { change ->
                        launch {
                            offsetY.snapTo(offsetY.value + change.positionChange().y)
                        }
                        velocityTracker.addPosition(change.uptimeMillis, change.position)
                    }
                }
                val velocity = velocityTracker.calculateVelocity().y
                val targetOffsetY = decay.calculateTargetValue(offsetY.value, velocity)
                offsetY.updateBounds(
                    lowerBound = -size.height.toFloat(),
                    upperBound = size.height.toFloat()
                )
                launch {
                    if (targetOffsetY.absoluteValue <= size.height) {
                        offsetY.animateTo(targetValue = 0f, initialVelocity = velocity)
                    } else {
                        offsetY.animateDecay(velocity, decay)
                        onDismissed()
                    }
                }
            }
        }
    }.offset { IntOffset(0, offsetY.value.roundToInt()) }
}
