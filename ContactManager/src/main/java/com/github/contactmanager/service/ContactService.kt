package com.github.contactmanager.service

import com.github.contactmanager.model.Contact
import java.util.UUID

/**
 * A simple service for managing contacts.
 */
class ContactService {
    private val contacts = mutableListOf<Contact>()
    
    /**
     * Add a new contact to the list
     * @param firstName First name of the contact
     * @param lastName Last name of the contact
     * @param phoneNumber Phone number of the contact
     * @param email Optional email address
     * @param photoUri Optional photo URI
     * @param isFavorite Whether the contact is a favorite
     * @param notes Optional notes about the contact
     * @return The newly created contact
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
        val contact = Contact(
            id = UUID.randomUUID().toString(),
            firstName = firstName,
            lastName = lastName,
            phoneNumber = phoneNumber,
            email = email,
            photoUri = photoUri,
            isFavorite = isFavorite,
            notes = notes,
            createdAt = System.currentTimeMillis(),
            updatedAt = System.currentTimeMillis()
        )
        contacts.add(contact)
        return contact
    }
    
    /**
     * Get all contacts in the list
     * @return List of all contacts
     */
    fun getAllContacts(): List<Contact> = contacts.toList()
    
    /**
     * Get favorite contacts
     * @return List of contacts marked as favorites
     */
    fun getFavoriteContacts(): List<Contact> = 
        contacts.filter { it.isFavorite }
    
    /**
     * Get a contact by ID
     * @param id The ID of the contact to find
     * @return The contact if found, null otherwise
     */
    fun getContactById(id: String): Contact? =
        contacts.find { it.id == id }
    
    /**
     * Update an existing contact
     * @param id The ID of the contact to update
     * @param firstName New first name (optional)
     * @param lastName New last name (optional)
     * @param phoneNumber New phone number (optional)
     * @param email New email address (optional)
     * @param photoUri New photo URI (optional)
     * @param isFavorite New favorite status (optional)
     * @param notes New notes (optional)
     * @return The updated contact if found, null otherwise
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
        val index = contacts.indexOfFirst { it.id == id }
        if (index == -1) return null
        
        val contact = contacts[index]
        val updatedContact = contact.copy(
            firstName = firstName ?: contact.firstName,
            lastName = lastName ?: contact.lastName,
            phoneNumber = phoneNumber ?: contact.phoneNumber,
            email = email ?: contact.email,
            photoUri = photoUri ?: contact.photoUri,
            isFavorite = isFavorite ?: contact.isFavorite,
            notes = notes ?: contact.notes,
            updatedAt = System.currentTimeMillis()
        )
        
        contacts[index] = updatedContact
        return updatedContact
    }
    
    /**
     * Delete a contact by ID
     * @param id The ID of the contact to delete
     * @return true if contact was found and deleted, false otherwise
     */
    fun deleteContact(id: String): Boolean {
        val index = contacts.indexOfFirst { it.id == id }
        if (index == -1) return false
        
        contacts.removeAt(index)
        return true
    }
    
    /**
     * Delete all contacts
     */
    fun deleteAllContacts() {
        contacts.clear()
    }
}