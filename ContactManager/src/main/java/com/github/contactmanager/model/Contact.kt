package com.github.contactmanager.model

import java.util.UUID

/**
 * Data class representing a contact.
 * This class follows the Single Responsibility Principle - it only represents contact data.
 */
data class Contact(
    val id: String = UUID.randomUUID().toString(),
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val email: String = "",
    val photoUri: String? = null,
    val isFavorite: Boolean = false,
    val notes: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)