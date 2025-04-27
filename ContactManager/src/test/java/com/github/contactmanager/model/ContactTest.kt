package com.github.contactmanager.model

import org.junit.Test
import com.google.common.truth.Truth.assertThat
import java.util.UUID

class ContactTest {

    @Test
    fun `create contact with required parameters only`() {
        // When: creating a contact with only required parameters
        val contact = Contact(
            firstName = "John",
            lastName = "Doe",
            phoneNumber = "1234567890"
        )

        // Then: the contact should have default values for optional parameters
        assertThat(contact.firstName).isEqualTo("John")
        assertThat(contact.lastName).isEqualTo("Doe")
        assertThat(contact.phoneNumber).isEqualTo("1234567890")
        assertThat(contact.email).isEmpty()
        assertThat(contact.photoUri).isNull()
        assertThat(contact.notes).isEmpty()
        assertThat(contact.isFavorite).isFalse()
        assertThat(contact.id).isNotEmpty() // ID should be automatically generated
        assertThat(contact.createdAt).isGreaterThan(0L)
        assertThat(contact.updatedAt).isGreaterThan(0L)
    }
    
    @Test
    fun `create contact with all parameters`() {
        // Given: specific values for all parameters
        val id = UUID.randomUUID().toString()
        val createdAt = System.currentTimeMillis()
        val updatedAt = System.currentTimeMillis()

        // When: creating a contact with all parameters
        val contact = Contact(
            id = id,
            firstName = "Jane",
            lastName = "Smith",
            phoneNumber = "9876543210",
            email = "jane.smith@example.com",
            photoUri = "content://photos/1",
            isFavorite = true,
            notes = "Work colleague",
            createdAt = createdAt,
            updatedAt = updatedAt
        )

        // Then: the contact should have the specified values
        assertThat(contact.id).isEqualTo(id) // Primary key is correctly set
        assertThat(contact.firstName).isEqualTo("Jane")
        assertThat(contact.lastName).isEqualTo("Smith")
        assertThat(contact.phoneNumber).isEqualTo("9876543210")
        assertThat(contact.email).isEqualTo("jane.smith@example.com")
        assertThat(contact.photoUri).isEqualTo("content://photos/1")
        assertThat(contact.isFavorite).isTrue()
        assertThat(contact.notes).isEqualTo("Work colleague")
        assertThat(contact.createdAt).isEqualTo(createdAt)
        assertThat(contact.updatedAt).isEqualTo(updatedAt)
    }

    @Test
    fun `copy contact with updated properties`() {
        // Given: an existing contact
        val original = Contact(
            firstName = "John",
            lastName = "Doe",
            phoneNumber = "1234567890"
        )

        // When: creating a copy with updated properties
        val updated = original.copy(
            firstName = "Jonathan",
            photoUri = "content://photos/2",
            isFavorite = true,
            notes = "Friend from college",
            updatedAt = System.currentTimeMillis() + 1000
        )

        // Then: only specified properties should be updated
        assertThat(updated.id).isEqualTo(original.id) // Primary key remains the same
        assertThat(updated.firstName).isEqualTo("Jonathan")
        assertThat(updated.lastName).isEqualTo("Doe") // Unchanged
        assertThat(updated.phoneNumber).isEqualTo("1234567890") // Unchanged
        assertThat(updated.photoUri).isEqualTo("content://photos/2")
        assertThat(updated.isFavorite).isTrue()
        assertThat(updated.notes).isEqualTo("Friend from college")
        assertThat(updated.createdAt).isEqualTo(original.createdAt) // Created time should be unchanged
        assertThat(updated.updatedAt).isGreaterThan(original.updatedAt) // Updated time should change
    }
}