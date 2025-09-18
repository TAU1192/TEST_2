package com.example.plantapp.comp

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicSecureTextField
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PasswordTextField(
    state: TextFieldState,
    modifier: Modifier = Modifier
) {
    var showPassword by remember { mutableStateOf(false) }

    BasicSecureTextField(
        state = state,
        textObfuscationMode = if (showPassword) {
            TextObfuscationMode.Visible
        } else {
            TextObfuscationMode.RevealLastTyped
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        decorator = { innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .border(1.dp, Color.Gray, RoundedCornerShape(6.dp))
                    .padding(horizontal = 16.dp),
            ) {
                Box(modifier = Modifier.align(Alignment.CenterStart)) {
                    innerTextField()
                }

                Icon(
                    imageVector = if (showPassword) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                    contentDescription = "Toggle password visibility",
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .clickable { showPassword = !showPassword }
                        .padding(8.dp)
                        .requiredSize(24.dp)
                )
            }
        }
    )
}