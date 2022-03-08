package eu.tutorials.mailboxlayermailvalidation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eu.tutorials.mailboxlayermailvalidation.ui.theme.MailBoxLayerMailValidationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //Todo 9: create access to the state class by assigning it to a variable
            val appState = rememberMailBoxState()

            //Todo 10: collect the value from the returned response and assign to a variable
            val response  =
                appState.response.collectAsState()

            //Todo 11: create a remember variable for the email
            var emailState by remember {
                mutableStateOf("")
            }
            MailBoxLayerMailValidationTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    /** Todo 12: Set emailState as argument for value, set a lambda block
                     * for onValueChange and receive the value from it and assign to emailState.
                     * In onButtonClicked, we check that emailState is not empty and call the
                     * checkEmail function with emailState assigned as parameter.
                     * Then we set appState as argument to state
                     * */
                    EmailScreen(value = emailState, onValueChange = {
                        emailState  = it
                    }, onButtonClicked = {
                        if (emailState.isNotEmpty()) {
                            appState.checkEmail(emailState = emailState)
                        }
                    }, state = appState)

                }
            }

            //Todo 13: set a Log to see the complete response on the logcat
            Log.d("email","${response.value}")
        }

    }
}


@Composable
fun EmailScreen(
   value: String,
                onValueChange: (String) -> Unit={},
    onButtonClicked:()->Unit={},
   //Todo 7: create a state variable
   state: MailBoxState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(value = value, onValueChange = onValueChange)
        Column(verticalArrangement = Arrangement.SpaceBetween, horizontalAlignment = Alignment.CenterHorizontally) {
            //Todo 8: update the value of each text according to the result from the State class
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
    MailBoxLayerMailValidationTheme {
        EmailScreen(value = "", state = rememberMailBoxState())
    }
}