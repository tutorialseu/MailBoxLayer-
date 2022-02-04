package eu.tutorials.mailboxlayermailvalidation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eu.tutorials.mailboxlayermailvalidation.ui.theme.MailBoxLayerMailValidationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MailBoxLayerMailValidationTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {

                }
            }
        }
    }
}


@Composable
fun EmailScreen(
   value: String,
                onValueChange: (String) -> Unit={},
    onButtonClicked:()->Unit={}) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(value = value, onValueChange = onValueChange)
        Text(text = "Invalid Email", modifier = Modifier.padding(vertical = 8.dp))
        OutlinedButton(onClick = onButtonClicked) {
            Text(text = "Check Email")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MailBoxLayerMailValidationTheme {
        EmailScreen(value = "")
    }
}