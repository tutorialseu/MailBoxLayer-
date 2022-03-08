package eu.tutorials.mailboxlayeremailvalidation

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eu.tutorials.mailboxlayeremailvalidation.ui.theme.MailBoxLayerEmailValidationTheme
import kotlinx.coroutines.flow.MutableStateFlow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val appState = rememberMailBoxState()

            var emailState by remember {
                mutableStateOf("")
            }
            MailBoxLayerEmailValidationTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    EmailUI(value = emailState, onValueChange = {
                   emailState  = it
               }, onButtonClicked = {
                   if (emailState.isNotEmpty()) {
                       appState.checkEmail(emailState = emailState)
                   }
               }, state = appState)

                }
            }

        }
    }
}

@Composable
fun EmailUI(value: String,
            onValueChange: (String) -> Unit={},
            state: MailBoxState, onButtonClicked:()->Unit={}) {
    Log.d("emailvalid2",state.valid.collectAsState().value)
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

        OutlinedTextField(value = value, onValueChange = onValueChange)
        Column(verticalArrangement = Arrangement.SpaceBetween, horizontalAlignment = Alignment.CenterHorizontally) {

            Text(
                text = "${state.valid.collectAsState().value}  ${state.score.collectAsState().value}",
                modifier = Modifier.padding(vertical = 8.dp),fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = "${state.free.collectAsState().value}  ${state.disposable.collectAsState().value}",
                modifier = Modifier.padding(vertical = 8.dp),fontWeight = FontWeight.ExtraBold
            )

            Text(
                text = "${state.smtpCheck.collectAsState().value} ${state.mxRecord.collectAsState().value} ",
                modifier = Modifier.padding(vertical = 8.dp), fontWeight = FontWeight.ExtraBold
            )


        }
        OutlinedButton(onClick = onButtonClicked) {
        Text(text = "Check Email")
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MailBoxLayerEmailValidationTheme {
      EmailUI(value = "", state = rememberMailBoxState())
    }
}