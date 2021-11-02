package com.example.codelabweek1

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.graphics.drawable.shapes.OvalShape
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.codelabweek1.ui.theme.CodelabWeek1Theme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CodelabWeek1Theme {
                // A surface container using the 'background' color from the theme
                MyApp()
            }
        }
    }
}

@Composable
private fun MyApp() {
    var shouldShowOnboarding by rememberSaveable {
        mutableStateOf(true)
    }

    if (shouldShowOnboarding) {
        OnboardingScreen { shouldShowOnboarding = false }
    } else {
        Greetings()
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun Greetings(names: List<String> = List(10000) { "$it" }) {
    LazyColumn(Modifier.padding(vertical = 4.dp)) {
        items(names.size) { idx ->
            Greeting(name = names[idx])
        }
    }
}

@Composable
fun Greeting(name: String) {
    var randomFloat by rememberSaveable {
        mutableStateOf(Random.nextFloat() * 360)
    }
    val color by animateColorAsState(
        Color(
            android.graphics.Color.HSVToColor(floatArrayOf(randomFloat, 0.5f, 1f))
        )
    )
    Card(
        backgroundColor = color,
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .clip(RoundedCornerShape(30.dp))
            .shadow(10.dp)
    ) {
        CardContent(name) {
            randomFloat = Random.nextFloat() * 360
        }
    }
}

@Composable
fun CardContent(name: String, onIconClicked: () -> Unit) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    Row(
        Modifier
            .padding(12.dp)
            .animateContentSize(
                spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(
            Modifier
                .weight(1f)
                .padding(12.dp)
        ) {
            Text("Hello,")
            Text(name, style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.ExtraBold))
            if (expanded) {
                Text(
                    ("Composem ipsum color sit lazy, padding theme elit, sed do bouncy. ").repeat(4)
                )
            }
        }
        IconButton(onClick = {
            expanded = !expanded
            onIconClicked()
        }) {
            Icon(
                imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                contentDescription = if (expanded) {
                    stringResource(id = R.string.show_less)
                } else {
                    stringResource(id = R.string.show_more)
                }
            )
        }
    }
}

//@Preview(showBackground = true, name = "Text Preview", widthDp = 320)
@Composable
fun DefaultPreview() {
    CodelabWeek1Theme {
        MyApp()
    }
}

@Composable
fun OnboardingScreen(onContinueClicked: () -> Unit) {
    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Welcome to the basics Codelab!")
            Button(
                modifier = Modifier.padding(vertical = 24.dp),
                onClick = onContinueClicked
            ) {
                Text(text = "Continue")
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    CodelabWeek1Theme {
        OnboardingScreen({})
    }
}