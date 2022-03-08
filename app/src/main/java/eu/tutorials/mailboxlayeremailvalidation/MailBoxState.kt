package eu.tutorials.mailboxlayeremailvalidation

import android.util.Log
import androidx.compose.runtime.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception


@Composable
fun rememberMailBoxState(
                         coroutineScope: CoroutineScope = rememberCoroutineScope())
        =     remember(coroutineScope) {
    MailBoxState( coroutineScope = coroutineScope)
}

class MailBoxState(val coroutineScope: CoroutineScope) {

    private val _response  = MutableStateFlow(EmailResponse())
    val response: StateFlow<EmailResponse>
        get() = _response
    private val _valid  = MutableStateFlow("")
    val valid: StateFlow<String>
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

    fun checkEmail(emailState: String){
        try {
            coroutineScope.launch {
                _response.value =
                    Api.service.checkEmail(email = emailState, key = Api.API_KEY)
                Log.d("email2","${response.value}")
                _valid.value = if (response.value.format_valid) "Email is valid" else "Invalid email"
                _smtpCheck.value = if (response.value.smtp_check) "Email exists" else "Email does not exist"
                _disposable.value = if (response.value.disposable) "Email is Disposable" else "Email is not disposable"
                _free.value = if (response.value.free) "Email is Free" else "Email is paid"
                _mxRecord.value = if (response.value.mx_found) "domain can receive email" else "domain cannot receive email"
                _score.value = "Score is ${response.value.score}"
            }
        }catch (e:Exception){

        }
    }
}