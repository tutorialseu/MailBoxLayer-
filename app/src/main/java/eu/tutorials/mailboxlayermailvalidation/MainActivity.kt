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
import androidx.compose.ui.text.font.FontWeight
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
                   //Todo 2: Remove call to Greeting Function Greeting("Android")
                }
            }
        }
    }
}

/* Todo 1: remove Greeting Function and create a new composable
@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}
 */

//Todo 4: create EmailScreen composable function
@Composable
fun EmailScreen(
    //Todo 6: To track the entered characters for OutlineTextField we create a String to hold the value
    //and a lambda function with String parameter
    value: String,
                onValueChange: (String) -> Unit={},
    //Todo 10: Create a lambda function for the button Onclick which will be implemented later
    onButtonClicked:()->Unit={}) {
    /** Todo 5:Add a Column as the parent layout to align children elements
     *    vertically on the screen. Using the Modifier set the Column to fillMaxsize that is
     *    fill both horizontal and vertical sides of the screen. align the children to the center
     *    vertically using verticalArrangement and also horizontally using horizontalAlignment
     * */
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        /** Todo 7: Add the input field with value in EmailScreen parameter
         *    assigned as value argument and the onValueChange as onValueChange argument
         */
        OutlinedTextField(value = value, onValueChange = onValueChange)
        /**Todo 9: Add a column with three Text element for showing different Status of an email, whether it is valid or not,
         * the score strength, if the email is free, exits, disposable and also if the domain
         * is configured to receive email
         *  For now we set a default value to the text which will be updated later with result from
         *  the network call and set a vertical
         *  padding to add an 8dp of space above and below the Text.
         */
        Column(verticalArrangement = Arrangement.SpaceBetween, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Validity and score", modifier = Modifier.padding(vertical = 8.dp), fontWeight = FontWeight.ExtraBold)
            Text(
                text = " Free and Disposable",
                modifier = Modifier.padding(vertical = 8.dp), fontWeight = FontWeight.ExtraBold
            )

            Text(
                text = "Exists and Can Receive Email ",
                modifier = Modifier.padding(vertical = 8.dp), fontWeight = FontWeight.ExtraBold
            )
        }
        /**Todo 11: Add OutlinedButton to be click to check an email, set onButtonClicked which we created
         * as its onCLick parameter and within its block add a Text with tex set to "Check Email"
         * */
        OutlinedButton(onClick = onButtonClicked) {
            Text(text = "Check Email")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MailBoxLayerMailValidationTheme {
        //Todo 3: Remove call to Greeting Function Greeting("Android")

        EmailScreen(value = "")//Todo 8 add EmailScreen to DefaultPreview
    }
}