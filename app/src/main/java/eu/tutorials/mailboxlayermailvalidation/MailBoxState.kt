package eu.tutorials.mailboxlayermailvalidation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception

/** Todo 6: We initialize the State class using remember function and pass in rememberCoroutineScope
 *  as argument. The remember function will call the class again when there is need for recomposition
 */
@Composable
fun rememberMailBoxState(
    coroutineScope: CoroutineScope = rememberCoroutineScope()
)
        =     remember(coroutineScope) {
    MailBoxState( coroutineScope = coroutineScope)
}


//Todo 1: Create a state class with coroutine scope as parameter for launching the suspend function
class MailBoxState(private val coroutineScope: CoroutineScope) {

    /** Todo 2: Create a MutableStateFlow variable for setting the response and StateFlow
     *   that is not mutable for exposing the response to the UI
     */
    private val _response  = MutableStateFlow(EmailResponse())
    val response:StateFlow<EmailResponse>
    get() = _response

    /** Todo 3: create a MutableStateFlow variable for setting the validity response and StateFlow
     *   that is not mutable for exposing  the validity to the UI. With StateFlow, UI elements can
     *   react to changes to data and recompose to update the value
     * */
    private val _valid  = MutableStateFlow("")
    val valid:StateFlow<String>
    get() = _valid

    /** Todo 4: A function that receives the email to be checked. Using the coroutineScope, we launch
     *   the suspend function from the service class within a try catch block to check the email state.
     *   We pass in emailState as argument for email and the api key as argument for key and set the response returned
     *   from the request to _response state variable
     *
     * */
    fun checkEmail(emailState: String){
        try {
            coroutineScope.launch {
                _response.value =
                    Api.service.checkEmail(email = emailState, key = Api.API_KEY)
                Log.d("email2","${response.value}")
                /** Todo 5: In the EmailResponse class we have a format_valid property that returns a boolean.
                 *  We check if this is true we know the format is valid but if it is false
                 *  which is the default that we have set, then it is invalid.
                 *  We set this result to _valid state variable
                 * */
                _valid.value = if (response.value.format_valid) "Email is valid" else "Invalid email"
            }
        }catch (e: Exception){

        }
    }
}