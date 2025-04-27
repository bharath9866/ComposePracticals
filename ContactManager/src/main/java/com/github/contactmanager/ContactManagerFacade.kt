package com.github.contactmanager

import android.content.Context
import com.github.contactmanager.model.Contact
import com.github.contactmanager.service.ContactService
import com.github.contactmanager.util.PhoneCallUtil

/**
 * Main facade class for the ContactManager module.
 * This class provides a simplified interface to the module's functionality.
 */
class ContactManagerFacade(private val context: Context) {

    private val contactService = ContactService()
    
    /**
     * Add a new contact to the contacts list
     * @return The newly created contact with auto-generated UUID
     */
    fun addContact(
        firstName: String,
        lastName: String,
        phoneNumber: String,
        email: String = "",
        photoUri: String? = null,
        isFavorite: Boolean = false,
        notes: String = ""
    ): Contact {
        return contactService.addContact(
            firstName, lastName, phoneNumber, email, photoUri, isFavorite, notes
        )
    }
    
    /**
     * Get all contacts
     */
    fun getAllContacts(): List<Contact> {
        return contactService.getAllContacts()
    }
    
    /**
     * Get favorite contacts
     */
    fun getFavoriteContacts(): List<Contact> {
        return contactService.getFavoriteContacts()
    }
    
    /**
     * Get a contact by ID
     */
    fun getContactById(id: String): Contact? {
        return contactService.getContactById(id)
    }
    
    /**
     * Update an existing contact
     */
    fun updateContact(
        id: String,
        firstName: String? = null,
        lastName: String? = null,
        phoneNumber: String? = null,
        email: String? = null,
        photoUri: String? = null,
        isFavorite: Boolean? = null,
        notes: String? = null
    ): Contact? {
        return contactService.updateContact(
            id, firstName, lastName, phoneNumber, email, photoUri, isFavorite, notes
        )
    }
    
    /**
     * Delete a contact by ID
     */
    fun deleteContact(id: String): Boolean {
        return contactService.deleteContact(id)
    }
    
    /**
     * Delete all contacts
     */
    fun deleteAllContacts() {
        contactService.deleteAllContacts()
    }
    
    /**
     * Open the dialer with a pre-filled number
     */
    fun openDialer(phoneNumber: String) {
        PhoneCallUtil.openDialer(context, phoneNumber)
    }
    
    /**
     * Make a phone call if permission is granted
     */
    fun makePhoneCall(phoneNumber: String): Boolean {
        return PhoneCallUtil.initiatePhoneCall(context, phoneNumber)
    }
    
    /**
     * Check if the app has call phone permission
     */
    fun hasCallPhonePermission(): Boolean {
        return PhoneCallUtil.hasCallPhonePermission(context)
    }
    
    /**
     * Format a phone number for display
     */
    fun formatPhoneNumber(phoneNumber: String): String {
        return PhoneCallUtil.formatPhoneNumber(phoneNumber)
    }
}