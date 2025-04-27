package com.github.contactmanager.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.github.contactmanager.model.Contact
import com.github.contactmanager.service.PhoneCallService
import com.github.contactmanager.viewmodel.ContactViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactDetailScreen(
    contactId: String,
    viewModel: ContactViewModel,
    phoneCallService: PhoneCallService,
    onBackClick: () -> Unit,
    onEditClick: (String) -> Unit,
    onDeleteClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val contact = uiState.detailState.contact
    val context = LocalContext.current
    
    // Load contact details when screen is shown
    LaunchedEffect(contactId) {
        viewModel.getContactById(contactId)
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Contact Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.Person, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { 
                        contact?.let { onEditClick(it.id) }
                    }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit contact")
                    }
                    IconButton(onClick = {
                        contact?.let { 
                            viewModel.deleteContact(it)
                            onDeleteClick()
                        }
                    }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete contact")
                    }
                }
            )
        },
        floatingActionButton = {
            contact?.let { currentContact ->
                FloatingActionButton (onClick = {
                    phoneCallService.openDialer(context, currentContact.phoneNumber)
                }) {
                    Icon(Icons.Default.Call, contentDescription = "Call")
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (uiState.detailState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else if (contact == null) {
                Text(
                    "Contact not found",
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                ContactDetails(
                    contact = contact,
                    onCallClick = { phoneCallService.openDialer(context, it) }
                )
            }
        }
    }
}

@Composable
fun ContactDetails(
    contact: Contact,
    onCallClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Contact photo
        if (contact.photoUri != null) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(contact.photoUri)
                    .crossfade(true)
                    .build(),
                contentDescription = "Contact photo",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
            )
        } else {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(60.dp)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Name
        Text(
            text = "${contact.firstName} ${contact.lastName}",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Phone
        ContactInfoItem(
            icon = Icons.Default.Phone,
            label = "Phone",
            value = contact.phoneNumber
        )
        
        Button(
            onClick = { onCallClick(contact.phoneNumber) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Icon(Icons.Default.Call, contentDescription = null)
            Spacer(modifier = Modifier.size(8.dp))
            Text("Call")
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
        
        // Email if available
        if (contact.email.isNotEmpty()) {
            ContactInfoItem(
                icon = Icons.Default.Email,
                label = "Email",
                value = contact.email
            )
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }
        
        // Notes if available
        if (contact.notes.isNotEmpty()) {
            Text(
                text = "Notes",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
            Text(
                text = contact.notes,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun ContactInfoItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        
        Column(
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}