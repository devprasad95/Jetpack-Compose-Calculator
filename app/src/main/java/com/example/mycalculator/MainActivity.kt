package com.example.mycalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mycalculator.ui.theme.MyCalculatorTheme
import com.example.mycalculator.ui.theme.Orange
import com.example.mycalculator.ui.theme.Purple200
import org.mariuszgromada.math.mxparser.Expression

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyCalculatorTheme {
                CalculatorScreen()

            }
        }
    }
}


@Composable
fun CalculatorScreen(modifier: Modifier = Modifier) {
    var expression by remember {
        mutableStateOf("")
    }

    var result by remember {
        mutableStateOf("")
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        Column(
            modifier = modifier
                .weight(1f)
                .padding(16.dp)
        ) {
            Text(
                modifier = modifier.fillMaxWidth(),
                text = expression,
                style = TextStyle(
                    fontSize = 40.sp,
                    textAlign = TextAlign.End
                ),
                maxLines = 3,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = modifier.fillMaxWidth(),
                text = result,
                style = TextStyle(
                    fontSize = 48.sp,
                    textAlign = TextAlign.End
                ),
            )
        }
        Column(modifier = modifier.fillMaxWidth()) {
            Row(modifier = modifier.fillMaxWidth()) {
                CalculatorButton(
                    text = "AC",
                    isFunction = true,
                    modifier = modifier.weight(2f),
                    onClick = {
                        expression = ""
                        result = ""
                    })
                CalculatorButton(
                    text = "⌫",
                    isFunction = true,
                    modifier = modifier.weight(1f),
                    onClick = { expression = delCharacter(expression) })
                CalculatorButton(
                    text = "÷",
                    isFunction = true,
                    modifier = modifier.weight(1f),
                    onClick = {
                        expression += it
                    })
            }
            Row(modifier = modifier.fillMaxWidth()) {
                CalculatorButton(text = "7", modifier = modifier.weight(1f), onClick = {
                    expression += it
                })
                CalculatorButton(text = "8", modifier = modifier.weight(1f), onClick = {
                    expression += it
                })
                CalculatorButton(text = "9", modifier = modifier.weight(1f), onClick = {
                    expression += it
                })
                CalculatorButton(
                    text = "×",
                    isFunction = true,
                    modifier = modifier.weight(1f),
                    onClick = {
                        expression += it
                    })
            }
            Row(modifier = modifier.fillMaxWidth()) {
                CalculatorButton(text = "4", modifier = modifier.weight(1f), onClick = {
                    expression += it
                })
                CalculatorButton(text = "5", modifier = modifier.weight(1f), onClick = {
                    expression += it
                })
                CalculatorButton(text = "6", modifier = modifier.weight(1f), onClick = {
                    expression += it
                })
                CalculatorButton(
                    text = "+",
                    isFunction = true,
                    modifier = modifier.weight(1f),
                    onClick = {
                        expression += it
                    })
            }
            Row(modifier = modifier.fillMaxWidth()) {
                CalculatorButton(text = "1", modifier = modifier.weight(1f), onClick = {
                    expression += it
                })
                CalculatorButton(text = "2", modifier = modifier.weight(1f), onClick = {
                    expression += it
                })
                CalculatorButton(text = "3", modifier = modifier.weight(1f), onClick = {
                    expression += it
                })
                CalculatorButton(
                    text = "-",
                    isFunction = true,
                    modifier = modifier.weight(1f),
                    onClick = {
                        expression += it
                    })
            }
            Row(modifier = modifier.fillMaxWidth()) {
                CalculatorButton(text = "0", modifier = modifier.weight(2f), onClick = {
                    expression += it
                })
                CalculatorButton(text = ".", modifier = modifier.weight(1f), onClick = {
                    expression += it
                })
                CalculatorButton(
                    text = "=",
                    isFunction = true,
                    modifier = modifier.weight(1f),
                    onClick = {
                        if (expression.isEmpty()) return@CalculatorButton
                        result = solveExpression(expression)
                    })
            }
        }

    }
}

fun solveExpression(expression: String): String {
    var answer = ""
    try {
        answer = Expression(expression.replace("÷","/")
            .replace("×","*")).calculate().toString()
    } catch (e:Exception){
        e.printStackTrace()
        return "Invalid Expression"
    }
    return answer.replace(".0","")
}

fun delCharacter(expression: String): String {
    return if (expression.isNotEmpty()) {
        expression.substring(0, expression.length - 1)
    } else {
        expression
    }
}

@Composable
fun CalculatorButton(
    modifier: Modifier = Modifier,
    text: String = "0",
    isFunction: Boolean = false,
    onClick: (String) -> Unit = {},
) {
    Button(
        shape = CircleShape,
        modifier = modifier
            .size(72.dp)
            .padding(4.dp),
        onClick = { onClick(text) }, colors = ButtonDefaults.buttonColors(
            backgroundColor = if (isFunction && text == "=" || text == "AC") {
                Color.Red
            } else if (isFunction) {
                Orange
            } else {
                Purple200
            }
        )
    ) {
        Text(text = text, style = TextStyle(fontSize = 24.sp))
    }
}

@Preview
@Composable
fun CalculatorButtonPreview() {
    CalculatorButton()
}

@Preview(showBackground = true)
@Composable
fun CalculatorScreenPreview() {
    CalculatorScreen()
}