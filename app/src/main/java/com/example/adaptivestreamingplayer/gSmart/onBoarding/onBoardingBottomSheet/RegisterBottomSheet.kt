package com.example.adaptivestreamingplayer.gSmart.onBoarding.onBoardingBottomSheet

import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Checkbox
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.ui.text.input.ImeAction

val BottomSheetBackgroundColor = Color(0xFFFFECAA) // Cream/yellow background
val FieldBackgroundColor = Color.Transparent
val FieldBorderColor = Color(0xFF1B1A40) // Dark blue border
val CloseButtonColor = Color(0xFFEF5858) // Red close button
val SheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)

enum class BottomSheetType {
    NONE, LOGIN, REGISTER
}

fun String.isValidMail(): Boolean = Patterns.EMAIL_ADDRESS.matcher(this).matches()

enum class FieldType {
    TEXT,
    EMAIL,
    PHONE,
    PASSWORD
}

data class FormField(
    val key: String,
    val label: String,
    val placeholder: String = "",
    val type: FieldType = FieldType.TEXT,
    val trailingIcon: ImageVector? = null,
    val isRequired: Boolean = true
)

data class BottomSheetConfig(
    val type: BottomSheetType,
    val heading: String,
    val title: String,
    val fields: List<FormField>,
    val buttonText: String,
    val footerText: String = "",
    val footerLinkText: String = "",
    val showRememberMe: Boolean = false,
    val showForgotPassword: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DynamicBottomSheet(
    config: BottomSheetConfig,
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(),
    sheetMaxWidth: Dp = BottomSheetDefaults.SheetMaxWidth,
    shape: Shape = BottomSheetDefaults.ExpandedShape,
    onDismiss: () -> Unit = {},
    onButtonClick: (Map<String, String>) -> Unit = { _ -> },
    onFooterLinkClick: () -> Unit = {}
) {
    var fieldValues by remember {
        mutableStateOf(config.fields.associate { it.key to "1234567890" })
    }
    var passwordVisibility by remember {
        mutableStateOf(config.fields.filter { it.type == FieldType.PASSWORD }
            .associate { it.key to false })
    }
    var rememberMe by remember { mutableStateOf(false) }

    ModalBottomSheet(
        sheetState = sheetState,
        sheetMaxWidth = sheetMaxWidth,
        modifier = modifier,
        onDismissRequest = onDismiss,
        containerColor = BottomSheetBackgroundColor,
        shape = shape,
        dragHandle = null
    ) {
        DynamicBottomSheetContent(
            config = config,
            fieldValues = fieldValues,
            passwordVisibility = passwordVisibility,
            rememberMe = rememberMe,
            onFieldValueChange = { key, value ->
                fieldValues = fieldValues + (key to value)
            },
            onPasswordVisibilityToggle = { key ->
                passwordVisibility =
                    passwordVisibility + (key to !(passwordVisibility[key] ?: false))
            },
            onRememberMeChange = { rememberMe = it },
            onDismiss = onDismiss,
            onButtonClick = { onButtonClick(fieldValues) },
            onFooterLinkClick = onFooterLinkClick
        )
    }
}

@Composable
private fun DynamicBottomSheetContent(
    config: BottomSheetConfig,
    fieldValues: Map<String, String>,
    passwordVisibility: Map<String, Boolean>,
    rememberMe: Boolean,
    onFieldValueChange: (String, String) -> Unit,
    onPasswordVisibilityToggle: (String) -> Unit,
    onRememberMeChange: (Boolean) -> Unit,
    onDismiss: () -> Unit,
    onButtonClick: () -> Unit,
    onFooterLinkClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header with close button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = config.heading,
                fontSize = 16.sp,
                color = Color(0xFF666666)
            )

            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.8f))
                    .clickable { onDismiss() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Close,
                    contentDescription = "Close",
                    tint = CloseButtonColor,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Title
        Text(
            text = config.title,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1B1A40), // Dark blue text color
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Dynamic Fields
        config.fields.forEach { field ->
            DynamicFormField(
                field = field,
                value = fieldValues[field.key] ?: "",
                isPasswordVisible = passwordVisibility[field.key] ?: false,
                onValueChange = { onFieldValueChange(field.key, it) },
                onPasswordVisibilityToggle = { onPasswordVisibilityToggle(field.key) }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Remember Me and Forgot Password (for login)
        if (config.showRememberMe || config.showForgotPassword) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (config.showRememberMe) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = rememberMe,
                            onCheckedChange = onRememberMeChange
                        )
                        Text(
                            text = "Remember me",
                            fontSize = 14.sp,
                            color = Color(0xFF1B1A40) // Dark blue text color
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.weight(1f))
                }

                if (config.showForgotPassword) {
                    Text(
                        text = "Forgot Password?",
                        fontSize = 14.sp,
                        color = CloseButtonColor,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.clickable { /* Handle forgot password */ }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Action Button
        Button(
            onClick = onButtonClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1B1A40), // Dark blue button background
                contentColor = Color(0xFFFFDE69) // Yellow button text
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = config.buttonText,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        }

        // Footer text with link
        if (config.footerText.isNotEmpty() && config.footerLinkText.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = config.footerText,
                    fontSize = 16.sp,
                    color = Color(0xFF1B1A40) // Dark blue text color
                )
                Text(
                    text = config.footerLinkText,
                    fontSize = 16.sp,
                    color = CloseButtonColor,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable { onFooterLinkClick() }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun DynamicFormField(
    field: FormField,
    value: String,
    isPasswordVisible: Boolean,
    onValueChange: (String) -> Unit,
    onPasswordVisibilityToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    val keyboardType = when (field.type) {
        FieldType.EMAIL -> KeyboardType.Email
        FieldType.PHONE -> KeyboardType.Phone
        FieldType.PASSWORD -> KeyboardType.Password
        FieldType.TEXT -> KeyboardType.Text
    }

    val imeAction = when (field.type) {
        FieldType.PHONE -> ImeAction.Done
        else -> ImeAction.Next
    }

    val visualTransformation = if (field.type == FieldType.PASSWORD && !isPasswordVisible) {
        PasswordVisualTransformation()
    } else {
        VisualTransformation.None
    }

    val trailingIcon: (@Composable () -> Unit)? = when {
        field.type == FieldType.PASSWORD -> {
            {
                IconButton(onClick = onPasswordVisibilityToggle) {
                    Icon(
                        imageVector = if (isPasswordVisible) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                        contentDescription = if (isPasswordVisible) "Hide password" else "Show password",
                        tint = Color(0xFF666666)
                    )
                }
            }
        }

        field.trailingIcon != null -> {
            {
                Icon(
                    imageVector = field.trailingIcon,
                    contentDescription = null,
                    tint = Color(0xFF666666)
                )
            }
        }

        else -> null
    }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(field.label, color = Color(0xFF666666)) },
        placeholder = if (field.placeholder.isNotEmpty()) {
            { Text(field.placeholder, color = Color(0xFF666666)) }
        } else null,
        trailingIcon = trailingIcon,
        visualTransformation = visualTransformation,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = FieldBackgroundColor,
            unfocusedContainerColor = FieldBackgroundColor,
            focusedBorderColor = FieldBorderColor,
            unfocusedBorderColor = FieldBorderColor,
            focusedTextColor = Color(0xFF1B1A40), // Dark blue text color
            unfocusedTextColor = Color(0xFF1B1A40) // Dark blue text color
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        )
    )
}

// Helper functions to create common configurations
object BottomSheetConfigs {
    fun loginConfig() = BottomSheetConfig(
        type = BottomSheetType.LOGIN,
        heading = "Welcome Back!!!",
        title = "Login",
        fields = listOf(
            FormField(
                key = "mobile",
                label = "Mobile Number",
                placeholder = "xxxxxxxxxx",
                type = FieldType.PHONE,
            )
        ),
        buttonText = "Login",
        footerText = "Don't have an account? ",
        footerLinkText = "Register",
        showRememberMe = false,
        showForgotPassword = false
    )

    fun registerConfig() = BottomSheetConfig(
        type = BottomSheetType.REGISTER,
        heading = "Hello...",
        title = "Register",
        fields = listOf(
            FormField(
                key = "fullName",
                label = "Full Name",
                placeholder = "Enter your full name",
                type = FieldType.TEXT
            ),
            FormField(
                key = "email",
                label = "Email Address",
                placeholder = "info@example.com",
                type = FieldType.EMAIL
            ),
            FormField(
                key = "mobile",
                label = "Mobile Number",
                placeholder = "xxxxxxxxxx",
                type = FieldType.PHONE
            )
        ),
        buttonText = "Register",
        footerText = "Already have account? ",
        footerLinkText = "Login"
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterBottomSheet(
    heading: String = "Hello...",
    title: String = "Register",
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(),
    sheetMaxWidth: Dp = BottomSheetDefaults.SheetMaxWidth,
    shape: Shape = BottomSheetDefaults.ExpandedShape,
    onDismiss: () -> Unit = {},
    onRegisterClick: (String, String, String, String) -> Unit = { _, _, _, _ -> },
    onLoginClick: () -> Unit = {}
) {
    DynamicBottomSheet(
        config = BottomSheetConfigs.registerConfig().copy(
            heading = heading,
            title = title
        ),
        modifier = modifier,
        sheetState = sheetState,
        sheetMaxWidth = sheetMaxWidth,
        shape = shape,
        onDismiss = onDismiss,
        onButtonClick = { values ->
            onRegisterClick(
                values["fullName"] ?: "",
                values["email"] ?: "",
                values["mobile"] ?: "",
                "" // password field removed from new register config
            )
        },
        onFooterLinkClick = onLoginClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun LoginBottomSheetPreview() {
    Box(
        modifier = Modifier.wrapContentSize()
    ) {

        val config = BottomSheetConfigs.loginConfig()
        var fieldValues by remember {
            mutableStateOf(config.fields.associate { it.key to "" })
        }
        var passwordVisibility by remember {
            mutableStateOf(config.fields.filter { it.type == FieldType.PASSWORD }
                .associate { it.key to false })
        }
        var rememberMe by remember { mutableStateOf(false) }

        DynamicBottomSheetContent(
            config = config,
            onDismiss = {},
            onButtonClick = { println("Login values: ") },
            onFooterLinkClick = {},
            fieldValues = fieldValues,
            passwordVisibility = passwordVisibility,
            rememberMe = rememberMe,
            onFieldValueChange = { key, value ->
                fieldValues = fieldValues + (key to value)
            },
            onPasswordVisibilityToggle = { key ->
                passwordVisibility =
                    passwordVisibility + (key to !(passwordVisibility[key] ?: false))
            },
            onRememberMeChange = { rememberMe = it },
            modifier = Modifier
                .clip(SheetShape)
                .background(BottomSheetBackgroundColor)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun RegisterBottomSheetPreview() {
    Box(
        modifier = Modifier.wrapContentSize()
    ) {

        val config = BottomSheetConfigs.registerConfig()
        var fieldValues by remember {
            mutableStateOf(config.fields.associate { it.key to "" })
        }
        var passwordVisibility by remember {
            mutableStateOf(config.fields.filter { it.type == FieldType.PASSWORD }
                .associate { it.key to false })
        }
        var rememberMe by remember { mutableStateOf(false) }

        DynamicBottomSheetContent(
            config = config,
            onDismiss = {},
            onButtonClick = { println("Login values: ") },
            onFooterLinkClick = {},
            fieldValues = fieldValues,
            passwordVisibility = passwordVisibility,
            rememberMe = rememberMe,
            onFieldValueChange = { key, value ->
                fieldValues = fieldValues + (key to value)
            },
            onPasswordVisibilityToggle = { key ->
                passwordVisibility =
                    passwordVisibility + (key to !(passwordVisibility[key] ?: false))
            },
            onRememberMeChange = { rememberMe = it },
            modifier = Modifier
                .clip(SheetShape)
                .background(BottomSheetBackgroundColor)
        )
    }
}
