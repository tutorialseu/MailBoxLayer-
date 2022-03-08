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

    /** Todo 3: create a MutableStateFlow variable for setting each status response and StateFlow
     *   that is not mutable for exposing each status to the UI. With StateFlow, UI elements can
     *   react to changes to data and recompose to update the value
     * */
    //start
    private val _valid  = MutableStateFlow("")
    val valid:StateFlow<String>
    get() = _valid

    private val _smtpCheck  = MutableStateFlow("")
    val smtpCheck: StateFlow<String>
        get() = _smtpCheck

    private val _disposable  = MutableStateFlow("")
    val disposable: StateFlow<String>
        get() = _disposable

    private val _free = MutableStateFlow("")
    val free: StateFlow<String>
        get() = _free

    private val _score = MutableStateFlow("")
    val score: StateFlow<String>
        get() = _score

    private val _mxRecord = MutableStateFlow("")
    val mxRecord: StateFlow<String>
        get() = _mxRecord
//end
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
                /** Todo 5:
                 *  We check for each status from the response class and if
                 *  true we know that the email passed the check and if it is false
                 *  then the email did pass, We set a text to show a clear message for each each status
                 * */
                _valid.value = if (response.value.format_valid) "Email is valid" else "Invalid email"
                _smtpCheck.value = if (response.value.smtp_check) "Email exists" else "Email does not exist"
                _disposable.value = if (response.value.disposable) "Email is Disposable" else "Email is not disposable"
                _free.value = if (response.value.free) "Email is Free" else "Email is paid"
                _mxRecord.value = if (response.value.mx_found) "domain can receive email" else "domain cannot receive email"
                _score.value = "Score is ${response.value.score}"
            }
        }catch (e: Exception){

        }
    }
}