package com.example.guessnumbergame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.guessnumbergame.ui.theme.GuessNumberGameTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GuessNumberGameTheme {
                GuessNumApp()
            }
        }
    }
}


@Composable
fun RandomNumWithButton(modifier: Modifier = Modifier) {
    var randomNumber by remember { mutableStateOf( (1..1000).random()) }
    var numberInput by remember { mutableStateOf("") }
    var numberDone by remember { mutableStateOf("") }
    var guessCount by remember { mutableStateOf(0) }
    var isGameEnd by remember { mutableStateOf(false) }
    val number = numberDone.toIntOrNull() ?: 0
    val hintText = hint(randomNumber, number, guessCount)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        Text(
            text = stringResource(R.string.topic),
            fontSize = 25.sp
        )

        Text(
            text = stringResource(R.string.detail),
            fontSize = 20.sp
        )

        Spacer(Modifier.height(100.dp))

//        check: random function, don't forget to remove!!!!!!!!(remove text here and string on strings.xml)
//        Text(
//            text = stringResource(R.string.number, randomNumber),
//            fontSize = 20.sp
//        )

        EditNumberField(
            value = numberInput,
            onValueChanged = { numberInput = it },
            onDone = {
                    value -> numberDone = value
                    if(numberDone != "") guessCount++
                    isGameEnd = randomNumber == (numberDone.toIntOrNull() ?: 0)
            },
            isGameEnd = isGameEnd
        )

        Spacer(Modifier.height(25.dp))

        Text(
            text = if (number != 0) hintText else "",
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.height(50.dp)
        )

        Button(onClick = {
            randomNumber = (1..1000).random()
            numberInput = ""
            numberDone = ""
            guessCount = 0
            isGameEnd = false
        }) {
            Text(
                text = stringResource(R.string.play_again),
                fontSize = 20.sp)
        }
    }
}

@Composable
fun EditNumberField(
    value: String,
    onValueChanged: (String) -> Unit,
    onDone: (String) -> Unit,
    isGameEnd: Boolean
) {
    TextField(
        value = value,
        onValueChange = onValueChanged,
        label = { Text(stringResource(R.string.answer)) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        keyboardActions = KeyboardActions(
            onDone = { if (! isGameEnd) onDone(value) },
        ),
    )
}

private fun hint(
    randomNumber: Int,
    number: Int,
    guessCount: Int
): String {
    if (randomNumber == number){
        return "Correct!\nYou guessed $guessCount time before you got it right!"
    }
    else if (randomNumber < number) {
        return "Hint: It's higher!"
    }
    return "Hint: It's lower!"
}

@Preview(showBackground = true)
@Composable
fun GuessNumApp() {
    RandomNumWithButton(modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.Center))
}