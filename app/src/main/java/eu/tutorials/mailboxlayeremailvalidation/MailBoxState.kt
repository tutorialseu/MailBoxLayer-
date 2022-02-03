package eu.tutorials.mailboxlayeremailvalidation

import android.util.Log
import androidx.compose.runtime.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception


@Composable
fun rememberMailBoxState(
                         coroutineScope: CoroutineScope = rememberCoroutineScope())
        =     remember(coroutineScope) {
    MailBoxState( coroutineScope = coroutineScope)
}
class MailBoxState(
                   val coroutineScope: CoroutineScope) {


     val response  = MutableStateFlow(EmailResponse())
    val valid  = MutableStateFlow("")
    fun checkEmail(emailState: String){
        try {
            coroutineScope.launch {
                response.value =
                    Api.authService.checkEmail(email = emailState, key = Api.API_KEY)
                Log.d("email2","${response.value}")
                valid.value = if (response.value.format_valid) "Email is valid" else "Invalid email"
            }
        }catch (e:Exception){

        }
    }
}