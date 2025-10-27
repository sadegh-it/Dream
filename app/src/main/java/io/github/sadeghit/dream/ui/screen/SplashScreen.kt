package io.github.sadeghit.dream.ui.screen


import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import io.github.sadeghit.dream.navigation.Screens
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(
    navController: NavController,

) {
    // برای انیمیشن متن
    var visible by remember { mutableStateOf(false) }
    val alpha: Float by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(durationMillis = 1500)
    )

    // نمایش انیمیشن و سپس هدایت به HomeScreen
    LaunchedEffect(Unit) {
        visible = true
        delay(2000) //  ۲ ثانیه
        navController.navigate(Screens.Home.route) {
            popUpTo(Screens.SplashScreen.route) { inclusive = true } // حذف Splash از استک
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(Color(0xFF4758B8), Color(0xFF00D4FF))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "CREATED by SADEGH IT",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.alpha(alpha)
        )
    }
}
