package com.github.contactmanager.data.repository

import android.content.Context
import com.github.contactmanager.data.provider.SystemContactProvider
import com.github.contactmanager.model.Contact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext

/**
 * Repository for managing contacts data from system contacts.
 * Following Single Responsibility Principle - this class is only responsible for contact data operations.
 */
class ContactRepository(
    private val context: Context
) {
    private val systemContactProvider = SystemContactProvider(context)

    /**
     * Loads all contacts from system contacts.
     * 
     * @param useSystemContacts Whether to load contacts from the system.
     * @return Flow of Contact list.
     */
    fun getAllContacts(useSystemContacts: Boolean = true): Flow<List<Contact>> {
        return if (useSystemContacts && systemContactProvider.hasReadContactsPermission()) {
            systemContactProvider.loadContacts()
        } else {
            flow { emit(emptyList<Contact>()) }
        }
    }

    /**
     * Gets a contact by ID.
     * 
     * @param id The contact ID.
     * @return Flow with the contact.
     */
    fun getContactById(id: String): Flow<Contact?> {
        return systemContactProvider.getContactById(id)
    }

    /**
     * Searches contacts by name or phone number.
     * 
     * @param query Search query string.
     * @return Flow with list of matching contacts.
     */
    fun searchContacts(query: String): Flow<List<Contact>> {
        return systemContactProvider.searchContacts(query)
    }

    /**
     * Inserts or updates a contact in system contacts.
     * 
     * @param contact The contact to save in system contacts.
     * @return The updated contact with system ID if it was added to system contacts.
     */
    suspend fun saveContact(contact: Contact): Contact {
        var updatedContact = contact
        
        // Save to system contacts
        if (systemContactProvider.hasWriteContactsPermission()) {
            withContext(Dispatchers.IO) {
                // Check if this is a new contact or an update
                val isSystemContact = try {
                    val id = contact.id.toLongOrNull()
                    id != null && id > 0
                } catch (e: Exception) {
                    false
                }
                
                if (isSystemContact) {
                    // Update existing system contact
                    systemContactProvider.updateSystemContact(contact)
                } else {
                    // Add new system contact
                    val systemId = systemContactProvider.addSystemContact(contact)
                    if (systemId != null) {
                        // Update the contact with the new system ID
                        updatedContact = contact.copy(id = systemId)
                    }
                }
            }
        }
        
        return updatedContact
    }

    /**
     * Deletes a contact from system contacts.
     * 
     * @param contact The contact to delete from system contacts.
     */
    suspend fun deleteContact(contact: Contact) {
        // Delete from system contacts
        if (systemContactProvider.hasWriteContactsPermission()) {
            withContext(Dispatchers.IO) {
                // Check if this is a system contact
                val isSystemContact = try {
                    val id = contact.id.toLongOrNull()
                    id != null && id > 0
                } catch (e: Exception) {
                    false
                }
                
                if (isSystemContact) {
                    systemContactProvider.deleteSystemContact(contact.id)
                }
            }
        }
    }

    /**
     * Determines if the app has permission to write system contacts.
     *
     * @return True if permission is granted, false otherwise.
     */
    fun hasWriteContactsPermission(): Boolean {
        return systemContactProvider.hasWriteContactsPermission()
    }

    /**
     * Determines if the app has permission to read system contacts.
     *
     * @return True if permission is granted, false otherwise.
     */
    fun hasReadContactsPermission(): Boolean {
        return systemContactProvider.hasReadContactsPermission()
    }
}