package com.github.contactmanager.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.github.contactmanager.model.Contact
import com.github.contactmanager.viewmodel.ContactViewModel
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.NumberParseException
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactEditScreen(
    contactId: String? = null,
    viewModel: ContactViewModel,
    onSaveComplete: () -> Unit,
    onCancelClick: () -> Unit
) {
    val currentContact by viewModel.currentContact.collectAsState()
    val phoneNumberUtil = remember { PhoneNumberUtil.getInstance() }
    val defaultRegion = "US" // Default region code
    
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var isFavorite by remember { mutableStateOf(false) }
    
    var phoneNumberError by remember { mutableStateOf<String?>(null) }
    
    // Load existing contact data if editing
    LaunchedEffect(contactId, currentContact) {
        if (contactId != null) {
            viewModel.getContactById(contactId)
            currentContact?.let { contact ->
                firstName = contact.firstName
                lastName = contact.lastName
                phoneNumber = contact.phoneNumber
                email = contact.email
                notes = contact.notes
                isFavorite = contact.isFavorite
            }
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(if (contactId == null) "Add Contact" else "Edit Contact") 
                },
                navigationIcon = {
                    IconButton(onClick = onCancelClick) {
                        Icon(Icons.Default.Close, contentDescription = "Cancel")
                    }
                },
                actions = {
                    IconButton(onClick = { 
                        // Validate phone number
                        try {
                            // Try to parse with the default region code
                            val formattedNumber = if (!phoneNumber.startsWith("+")) {
                                phoneNumber // Keep as is if it doesn't have a country code
                            } else {
                                phoneNumber
                            }
                            
                            // More permissive validation - just check if it's not too short
                            if (formattedNumber.replace("[^0-9+]".toRegex(), "").length >= 7) {
                                phoneNumberError = null
                                
                                // Prepare contact object
                                val contact = if (contactId != null) {
                                    currentContact?.copy(
                                        firstName = firstName,
                                        lastName = lastName,
                                        phoneNumber = phoneNumber,
                                        email = email,
                                        notes = notes,
                                        isFavorite = isFavorite,
                                        updatedAt = System.currentTimeMillis()
                                    ) ?: Contact(
                                        id = UUID.randomUUID().toString(),
                                        firstName = firstName,
                                        lastName = lastName,
                                        phoneNumber = phoneNumber,
                                        email = email,
                                        notes = notes,
                                        isFavorite = isFavorite
                                    )
                                } else {
                                    Contact(
                                        firstName = firstName,
                                        lastName = lastName,
                                        phoneNumber = phoneNumber,
                                        email = email,
                                        notes = notes,
                                        isFavorite = isFavorite
                                    )
                                }
                                
                                // Save contact
                                viewModel.saveContact(contact)
                                onSaveComplete()
                            } else {
                                phoneNumberError = "Phone number is too short"
                            }
                        } catch (e: Exception) {
                            phoneNumberError = "Please enter a valid phone number"
                        }
                    }) {
                        Icon(Icons.Default.Check, contentDescription = "Save")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Contact avatar placeholder
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
                    .align(Alignment.CenterHorizontally),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(60.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // First Name
            OutlinedTextField(
                value = firstName,
                onValueChange = { firstName = it },
                label = { Text("First Name") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words
                ),
                modifier = Modifier.fillMaxWidth(),
                isError = firstName.isBlank()
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Last Name
            OutlinedTextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = { Text("Last Name") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words
                ),
                modifier = Modifier.fillMaxWidth(),
                isError = lastName.isBlank()
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Phone Number
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { 
                    phoneNumber = it
                    phoneNumberError = null  // Reset error on change
                },
                label = { Text("Phone Number") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth(),
                isError = phoneNumberError != null || phoneNumber.isBlank(),
                supportingText = {
                    if (phoneNumberError != null) {
                        Text(phoneNumberError!!, color = MaterialTheme.colorScheme.error)
                    } else {
                        Text("Format: 10 digits (e.g., 1234567890) or with country code (+1 234 567 890)")
                    }
                }
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Email
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email (Optional)") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Notes
            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("Notes (Optional)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Favorite switch
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Add to Favorites",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.align(Alignment.CenterStart)
                )
                
                Switch(
                    checked = isFavorite,
                    onCheckedChange = { isFavorite = it },
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Save button
            Button(
                onClick = {
                    // Validate phone number
                    try {
                        // Try to parse with the default region code
                        val formattedNumber = if (!phoneNumber.startsWith("+")) {
                            phoneNumber // Keep as is if it doesn't have a country code
                        } else {
                            phoneNumber
                        }
                        
                        // More permissive validation - just check if it's not too short
                        if (formattedNumber.replace("[^0-9+]".toRegex(), "").length >= 7) {
                            phoneNumberError = null
                            
                            // Prepare contact object
                            val contact = if (contactId != null) {
                                currentContact?.copy(
                                    firstName = firstName,
                                    lastName = lastName,
                                    phoneNumber = phoneNumber,
                                    email = email,
                                    notes = notes,
                                    isFavorite = isFavorite,
                                    updatedAt = System.currentTimeMillis()
                                ) ?: Contact(
                                    id = UUID.randomUUID().toString(),
                                    firstName = firstName,
                                    lastName = lastName,
                                    phoneNumber = phoneNumber,
                                    email = email,
                                    notes = notes,
                                    isFavorite = isFavorite
                                )
                            } else {
                                Contact(
                                    firstName = firstName,
                                    lastName = lastName,
                                    phoneNumber = phoneNumber,
                                    email = email,
                                    notes = notes,
                                    isFavorite = isFavorite
                                )
                            }
                            
                            // Save contact
                            viewModel.saveContact(contact)
                            onSaveComplete()
                        } else {
                            phoneNumberError = "Phone number is too short"
                        }
                    } catch (e: Exception) {
                        phoneNumberError = "Please enter a valid phone number"
                    }
                },
                enabled = firstName.isNotBlank() && lastName.isNotBlank() && phoneNumber.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("SAVE CONTACT")
            }
        }
    }
}