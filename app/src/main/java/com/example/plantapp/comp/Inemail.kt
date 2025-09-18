package com.example.plantapp.comp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

class EmailViewModel : ViewModel() {
    var email by mutableStateOf("")
        private set

    val emailHasErrors by derivedStateOf {
        if (email.isNotEmpty()) {
            // Email is considered erroneous until it completely matches EMAIL_ADDRESS.
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        } else {
            false
        }
    }

    fun updateEmail(input: String) {
        email = input
    }


}


@Composable
fun ValidatingInputTextField(
    email: String,
    updateState: (String) -> Unit,
    validatorHasErrors: Boolean
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        value = email,
        onValueChange = updateState,
        label = { Text("Email") },
        isError = validatorHasErrors,
        supportingText = {
            if (validatorHasErrors) {
                Text("Incorrect email format.")
            }
        }
    )
}

@Composable
fun ValidateInput() {
    Box(modifier = Modifier.fillMaxWidth().padding(top = 10.dp), contentAlignment = Alignment.TopCenter){
        val emailViewModel: EmailViewModel = viewModel<EmailViewModel>()
        ValidatingInputTextField(
            email = emailViewModel.email,
            updateState = { input -> emailViewModel.updateEmail(input) },
            validatorHasErrors = emailViewModel.emailHasErrors

        )
    }

}
