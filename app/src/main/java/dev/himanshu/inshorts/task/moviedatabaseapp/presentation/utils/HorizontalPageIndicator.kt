package dev.himanshu.inshorts.task.moviedatabaseapp.presentation.utils

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private const val WIDTH_ANIMATION_DURATION = 1000
private const val COLOR_ANIMATION_DURATION = 1500

private val DEFAULT_SPACING_BETWEEN_INDICATOR = 8.dp
private val DEFAULT_SELECTED_INDICATOR_WIDTH = 40.dp
private val DEFAULT_UNSELECTED_INDICATOR_WIDTH = 8.dp
private val DEFAULT_INDICATOR_SHAPE = RoundedCornerShape(10.dp)
private val DEFAULT_SELECTED_INDICATOR_HEIGHT = 5.dp
private val DEFAULT_UNSELECTED_INDICATOR_HEIGHT = 5.dp
private const val INDICATOR_ANIMATED_COLOR_LABEL = "INDICATOR_ANIMATED_COLOR_LABEL"
private const val INDICATOR_ANIMATED_WIDTH_LABEL = "INDICATOR_ANIMATED_WIDTH_LABEL"

@Composable
fun HorizontalPageIndicator(
    pagerState: PagerState,
    pageCount: Int,
    modifier: Modifier = Modifier,
    activeColor: Color,
    inactiveColor: Color,
    spacing: Dp = DEFAULT_SPACING_BETWEEN_INDICATOR,
    selectedWidth: Dp = DEFAULT_SELECTED_INDICATOR_WIDTH,
    unSelectedWidth: Dp = DEFAULT_UNSELECTED_INDICATOR_WIDTH,
    selectedIndicatorHeight: Dp = DEFAULT_SELECTED_INDICATOR_HEIGHT,
    unSelectedIndicatorHeight: Dp = DEFAULT_UNSELECTED_INDICATOR_HEIGHT,
    indicatorShape: Shape = DEFAULT_INDICATOR_SHAPE,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(spacing),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        for (pageIndex in 0 until pageCount) {
            val isSelected = pagerState.currentPage == pageIndex
            val animateWidth = animateDpAsState(
                targetValue = if (isSelected) selectedWidth else unSelectedWidth,
                animationSpec = tween(WIDTH_ANIMATION_DURATION),
                label = INDICATOR_ANIMATED_WIDTH_LABEL,
            )
            val color by animateColorAsState(
                targetValue = if (isSelected) {
                    activeColor
                } else {
                    inactiveColor
                },
                animationSpec = tween(COLOR_ANIMATION_DURATION),
                label = INDICATOR_ANIMATED_COLOR_LABEL,
            )
            Box(
                modifier = Modifier
                    .width(animateWidth.value)
                    .height(if (isSelected) selectedIndicatorHeight else unSelectedIndicatorHeight)
                    .clip(indicatorShape)
                    .background(color = color),
            )
        }
    }
}
