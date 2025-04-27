package com.github.contactmanager.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.github.contactmanager.data.database.ContactDatabase
import com.github.contactmanager.data.repository.ContactRepository
import com.github.contactmanager.model.Contact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * ViewModel for contacts-related UI components.
 */
class ContactViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ContactRepository
    
    // UI state
    private val _contacts = MutableStateFlow<List<Contact>>(emptyList())
    val contacts: StateFlow<List<Contact>> = _contacts.asStateFlow()
    
    private val _searchResults = MutableStateFlow<List<Contact>>(emptyList())
    val searchResults: StateFlow<List<Contact>> = _searchResults.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _currentContact = MutableStateFlow<Contact?>(null)
    val currentContact: StateFlow<Contact?> = _currentContact.asStateFlow()

    init {
        val contactDao = ContactDatabase.getDatabase(application).contactDao()
        repository = ContactRepository(contactDao, application)
        
        loadContacts()
    }

    private fun loadContacts() {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getAllContacts().collectLatest { contactsList ->
                _contacts.value = contactsList
                _isLoading.value = false
            }
        }
    }

    fun getContactById(id: String) {
        viewModelScope.launch {
            repository.getContactById(id).collectLatest { contact ->
                _currentContact.value = contact
            }
        }
    }

    fun searchContacts(query: String) {
        if (query.isBlank()) {
            _searchResults.value = emptyList()
            return
        }
        
        viewModelScope.launch {
            repository.searchContacts(query).collectLatest { results ->
                _searchResults.value = results
            }
        }
    }

    fun saveContact(contact: Contact) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveContact(contact)
        }
    }

    fun deleteContact(contact: Contact) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteContact(contact)
        }
    }

    fun toggleFavorite(contact: Contact) {
        val updatedContact = contact.copy(isFavorite = !contact.isFavorite)
        saveContact(updatedContact)
    }
}