package com.github.contactmanager.data.provider

import android.Manifest
import android.content.ContentProviderOperation
import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.ContactsContract
import android.util.Log
import androidx.core.content.ContextCompat
import com.github.contactmanager.model.Contact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.util.*

/**
 * Provider to access system contacts.
 * This class follows the Interface Segregation Principle - it has specific methods for specific needs.
 */
class SystemContactProvider(private val context: Context) {

    /**
     * Checks if the app has permission to read contacts.
     *
     * @return True if permission is granted, false otherwise.
     */
    fun hasReadContactsPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_CONTACTS
        ) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * Checks if the app has permission to write contacts.
     *
     * @return True if permission is granted, false otherwise.
     */
    fun hasWriteContactsPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.WRITE_CONTACTS
        ) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * Loads all contacts from the system's contacts provider.
     *
     * @return Flow of Contact list.
     */
    fun loadContacts(): Flow<List<Contact>> = flow {
        if (!hasReadContactsPermission()) {
            Log.e(TAG, "No permission to read contacts")
            emit(emptyList())
            return@flow
        }
        
        val contacts = mutableListOf<Contact>()
        
        val projection = arrayOf(
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
            ContactsContract.Contacts.PHOTO_URI,
            ContactsContract.Contacts.HAS_PHONE_NUMBER,
            ContactsContract.Contacts.STARRED
        )
        
        val contentResolver = context.contentResolver
        val cursor = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            projection,
            null,
            null,
            "${ContactsContract.Contacts.DISPLAY_NAME_PRIMARY} ASC"
        )
        
        cursor?.use { c ->
            val idIdx = c.getColumnIndex(ContactsContract.Contacts._ID)
            val nameIdx = c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)
            val photoIdx = c.getColumnIndex(ContactsContract.Contacts.PHOTO_URI)
            val hasPhoneIdx = c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)
            val starredIdx = c.getColumnIndex(ContactsContract.Contacts.STARRED)
            
            while (c.moveToNext()) {
                val id = c.getString(idIdx)
                val name = c.getString(nameIdx) ?: "Unknown"
                val photoUri = c.getString(photoIdx)
                val hasPhone = c.getInt(hasPhoneIdx) > 0
                val starred = c.getInt(starredIdx) > 0
                
                // Split name to first and last name
                val nameParts = name.trim().split(" ")
                val firstName = nameParts.firstOrNull() ?: ""
                val lastName = if (nameParts.size > 1) nameParts.subList(1, nameParts.size).joinToString(" ") else ""
                
                // Get phone numbers
                val phoneNumber = if (hasPhone) {
                    getPhoneNumber(contentResolver, id)
                } else ""
                
                // Get emails
                val email = getEmail(contentResolver, id)
                
                val contact = Contact(
                    id = id,
                    firstName = firstName,
                    lastName = lastName,
                    phoneNumber = phoneNumber,
                    email = email,
                    photoUri = photoUri,
                    isFavorite = starred,
                    notes = "",
                    createdAt = System.currentTimeMillis(),
                    updatedAt = System.currentTimeMillis()
                )
                
                contacts.add(contact)
            }
        }
        
        emit(contacts)
    }.flowOn(Dispatchers.IO)
    
    /**
     * Gets a specific contact by ID from the system.
     *
     * @param contactId The ID of the contact to retrieve.
     * @return Flow with the contact.
     */
    fun getContactById(contactId: String): Flow<Contact?> = flow {
        if (!hasReadContactsPermission()) {
            Log.e(TAG, "No permission to read contacts")
            emit(null)
            return@flow
        }
        
        val contentResolver = context.contentResolver
        val uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactId.toLongOrNull() ?: -1)
        
        val projection = arrayOf(
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
            ContactsContract.Contacts.PHOTO_URI,
            ContactsContract.Contacts.HAS_PHONE_NUMBER,
            ContactsContract.Contacts.STARRED
        )
        
        val cursor = contentResolver.query(
            uri,
            projection,
            null,
            null,
            null
        )
        
        var contact: Contact? = null
        
        cursor?.use { c ->
            if (c.moveToFirst()) {
                val idIdx = c.getColumnIndex(ContactsContract.Contacts._ID)
                val nameIdx = c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)
                val photoIdx = c.getColumnIndex(ContactsContract.Contacts.PHOTO_URI)
                val hasPhoneIdx = c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)
                val starredIdx = c.getColumnIndex(ContactsContract.Contacts.STARRED)
                
                val id = c.getString(idIdx)
                val name = c.getString(nameIdx) ?: "Unknown"
                val photoUri = c.getString(photoIdx)
                val hasPhone = c.getInt(hasPhoneIdx) > 0
                val starred = c.getInt(starredIdx) > 0
                
                // Split name to first and last name
                val nameParts = name.trim().split(" ")
                val firstName = nameParts.firstOrNull() ?: ""
                val lastName = if (nameParts.size > 1) nameParts.subList(1, nameParts.size).joinToString(" ") else ""
                
                // Get phone numbers
                val phoneNumber = if (hasPhone) {
                    getPhoneNumber(contentResolver, id)
                } else ""
                
                // Get emails
                val email = getEmail(contentResolver, id)
                
                contact = Contact(
                    id = id,
                    firstName = firstName,
                    lastName = lastName,
                    phoneNumber = phoneNumber,
                    email = email,
                    photoUri = photoUri,
                    isFavorite = starred,
                    notes = "",
                    createdAt = System.currentTimeMillis(),
                    updatedAt = System.currentTimeMillis()
                )
            }
        }
        
        emit(contact)
    }.flowOn(Dispatchers.IO)
    
    /**
     * Searches contacts by name or phone number.
     *
     * @param query Search query string.
     * @return Flow with list of matching contacts.
     */
    fun searchContacts(query: String): Flow<List<Contact>> {
        return loadContacts().map { contacts ->
            contacts.filter { contact ->
                val fullName = "${contact.firstName} ${contact.lastName}".lowercase()
                fullName.contains(query.lowercase()) || contact.phoneNumber.contains(query)
            }
        }
    }
    
    /**
     * Gets the first phone number for a contact.
     *
     * @param contentResolver ContentResolver instance.
     * @param contactId Contact ID.
     * @return Phone number or empty string if none found.
     */
    private fun getPhoneNumber(contentResolver: ContentResolver, contactId: String): String {
        val phoneCursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER),
            "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} = ?",
            arrayOf(contactId),
            null
        )
        
        var phoneNumber = ""
        
        phoneCursor?.use { pc ->
            if (pc.moveToFirst()) {
                val phoneIdx = pc.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                if (phoneIdx >= 0) {
                    phoneNumber = pc.getString(phoneIdx) ?: ""
                }
            }
        }
        
        return phoneNumber
    }
    
    /**
     * Gets the first email address for a contact.
     *
     * @param contentResolver ContentResolver instance.
     * @param contactId Contact ID.
     * @return Email or empty string if none found.
     */
    private fun getEmail(contentResolver: ContentResolver, contactId: String): String {
        val emailCursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Email.CONTENT_URI,
            arrayOf(ContactsContract.CommonDataKinds.Email.ADDRESS),
            "${ContactsContract.CommonDataKinds.Email.CONTACT_ID} = ?",
            arrayOf(contactId),
            null
        )
        
        var email = ""
        
        emailCursor?.use { ec ->
            if (ec.moveToFirst()) {
                val emailIdx = ec.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS)
                if (emailIdx >= 0) {
                    email = ec.getString(emailIdx) ?: ""
                }
            }
        }

        return email
    }

    /**
     * Adds a new contact to the system contacts.
     *
     * @param contact The contact to add.
     * @return The ID of the created contact, or null if creation failed.
     */
    fun addSystemContact(contact: Contact): String? {
        if (!hasWriteContactsPermission()) {
            Log.e(TAG, "No permission to write contacts")
            return null
        }

        val operations = ArrayList<ContentProviderOperation>()

        // Create raw contact
        operations.add(
            ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build()
        )

        // Add name
        operations.add(
            ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, contact.firstName)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME, contact.lastName)
                .build()
        )

        // Add phone number
        operations.add(
            ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, contact.phoneNumber)
                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                .build()
        )

        // Add email if available
        if (contact.email.isNotEmpty()) {
            operations.add(
                ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Email.ADDRESS, contact.email)
                    .withValue(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_HOME)
                    .build()
            )
        }

        // Add notes if available
        if (contact.notes.isNotEmpty()) {
            operations.add(
                ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Note.NOTE, contact.notes)
                    .build()
            )
        }

        // Set favorite status
        if (contact.isFavorite) {
            operations.add(
                ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE, "vnd.android.cursor.item/starred")
                    .withValue("data1", 1)
                    .build()
            )
        }

        try {
            val results = context.contentResolver.applyBatch(ContactsContract.AUTHORITY, operations)
            if (results.isNotEmpty() && results[0].uri != null) {
                // Extract the ID from the URI
                val contactUri = results[0].uri!!
                val contactId = ContentUris.parseId(contactUri).toString()
                return contactId
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error adding contact: ${e.message}")
        }

        return null
    }

    /**
     * Updates an existing system contact.
     *
     * @param contact The contact with updated information.
     * @return True if the update was successful, false otherwise.
     */
    fun updateSystemContact(contact: Contact): Boolean {
        if (!hasWriteContactsPermission()) {
            Log.e(TAG, "No permission to write contacts")
            return false
        }

        try {
            val operations = ArrayList<ContentProviderOperation>()
            val contactId = contact.id
            val rawContactId = getRawContactId(contactId)

            if (rawContactId == null) {
                Log.e(TAG, "Could not find raw contact ID for contact ID: $contactId")
                return false
            }

            // Update name
            operations.add(
                ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                    .withSelection(
                        "${ContactsContract.Data.RAW_CONTACT_ID} = ? AND ${ContactsContract.Data.MIMETYPE} = ?",
                        arrayOf(rawContactId.toString(), ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                    )
                    .withValue(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, contact.firstName)
                    .withValue(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME, contact.lastName)
                    .build()
            )

            // Update phone number - first delete existing phone numbers
            operations.add(
                ContentProviderOperation.newDelete(ContactsContract.Data.CONTENT_URI)
                    .withSelection(
                        "${ContactsContract.Data.RAW_CONTACT_ID} = ? AND ${ContactsContract.Data.MIMETYPE} = ?",
                        arrayOf(rawContactId.toString(), ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    )
                    .build()
            )

            // Add the updated phone number
            operations.add(
                ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValue(ContactsContract.Data.RAW_CONTACT_ID, rawContactId)
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, contact.phoneNumber)
                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                    .build()
            )

            // Update email - first delete existing emails
            operations.add(
                ContentProviderOperation.newDelete(ContactsContract.Data.CONTENT_URI)
                    .withSelection(
                        "${ContactsContract.Data.RAW_CONTACT_ID} = ? AND ${ContactsContract.Data.MIMETYPE} = ?",
                        arrayOf(rawContactId.toString(), ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                    )
                    .build()
            )

            // Add the updated email if available
            if (contact.email.isNotEmpty()) {
                operations.add(
                    ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValue(ContactsContract.Data.RAW_CONTACT_ID, rawContactId)
                        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.Email.ADDRESS, contact.email)
                        .withValue(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_HOME)
                        .build()
                )
            }

            // Update notes - first delete existing notes
            operations.add(
                ContentProviderOperation.newDelete(ContactsContract.Data.CONTENT_URI)
                    .withSelection(
                        "${ContactsContract.Data.RAW_CONTACT_ID} = ? AND ${ContactsContract.Data.MIMETYPE} = ?",
                        arrayOf(rawContactId.toString(), ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE)
                    )
                    .build()
            )

            // Add the updated notes if available
            if (contact.notes.isNotEmpty()) {
                operations.add(
                    ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValue(ContactsContract.Data.RAW_CONTACT_ID, rawContactId)
                        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.Note.NOTE, contact.notes)
                        .build()
                )
            }

            // Update favorite status
            val values = ContentValues()
            values.put(ContactsContract.Contacts.STARRED, if (contact.isFavorite) 1 else 0)
            context.contentResolver.update(
                ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactId.toLong()),
                values,
                null,
                null
            )

            // Apply all operations
            context.contentResolver.applyBatch(ContactsContract.AUTHORITY, operations)
            return true
        } catch (e: Exception) {
            Log.e(TAG, "Error updating contact: ${e.message}")
            return false
        }
    }

    /**
     * Deletes a contact from the system contacts.
     *
     * @param contactId The ID of the contact to delete.
     * @return True if deletion was successful, false otherwise.
     */
    fun deleteSystemContact(contactId: String): Boolean {
        if (!hasWriteContactsPermission()) {
            Log.e(TAG, "No permission to write contacts")
            return false
        }

        try {
            val contactUri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, contactId)
            val deleteCount = context.contentResolver.delete(contactUri, null, null)
            return deleteCount > 0
        } catch (e: Exception) {
            Log.e(TAG, "Error deleting contact: ${e.message}")
            return false
        }
    }

    /**
     * Gets the raw contact ID for a given contact ID.
     *
     * @param contactId The contact ID.
     * @return The raw contact ID, or null if not found.
     */
    private fun getRawContactId(contactId: String): Long? {
        val contentResolver = context.contentResolver
        val cursor = contentResolver.query(
            ContactsContract.RawContacts.CONTENT_URI,
            arrayOf(ContactsContract.RawContacts._ID),
            "${ContactsContract.RawContacts.CONTACT_ID} = ?",
            arrayOf(contactId),
            null
        )

        var rawContactId: Long? = null

        cursor?.use { c ->
            if (c.moveToFirst()) {
                val idIdx = c.getColumnIndex(ContactsContract.RawContacts._ID)
                if (idIdx >= 0) {
                    rawContactId = c.getLong(idIdx)
                }
            }
        }

        return rawContactId
    }

    companion object {
        private const val TAG = "SystemContactProvider"
    }
}