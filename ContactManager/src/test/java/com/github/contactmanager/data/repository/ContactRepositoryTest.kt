package com.github.contactmanager.data.repository

import android.content.Context
import com.github.contactmanager.data.dao.ContactDao
import com.github.contactmanager.data.provider.SystemContactProvider
import com.github.contactmanager.model.Contact
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import com.google.common.truth.Truth.assertThat

@ExperimentalCoroutinesApi
class ContactRepositoryTest {

    private lateinit var contactDao: ContactDao
    private lateinit var context: Context
    private lateinit var contactRepository: ContactRepository
    
    private val testContacts = listOf(
        Contact(id = "1", firstName = "John", lastName = "Doe", phoneNumber = "+1234567890"),
        Contact(id = "2", firstName = "Jane", lastName = "Smith", phoneNumber = "+1987654321", isFavorite = true),
        Contact(id = "3", firstName = "Bob", lastName = "Johnson", phoneNumber = "+1555123456")
    )
    
    private val favoriteContacts = listOf(
        testContacts[1] // Jane Smith is marked as favorite
    )

    @Before
    fun setUp() {
        // Create mocks
        contactDao = mock(ContactDao::class.java)
        context = mock(Context::class.java)
        
        // Configure mock behaviors
        `when`(contactDao.getAllContacts()).thenReturn(flowOf(testContacts))
        `when`(contactDao.getFavoriteContacts()).thenReturn(flowOf(favoriteContacts))
        `when`(contactDao.getContactByIdFlow("1")).thenReturn(flowOf(testContacts[0]))
        `when`(contactDao.getContactByIdFlow("2")).thenReturn(flowOf(testContacts[1]))
        `when`(contactDao.getContactByIdFlow("3")).thenReturn(flowOf(testContacts[2]))
        `when`(contactDao.getContactByIdFlow("4")).thenReturn(flowOf(null))
        `when`(contactDao.searchContacts("John")).thenReturn(flowOf(listOf(testContacts[0])))
        
        // Create repository with mock dao and context
        contactRepository = ContactRepository(contactDao, context)
    }

    @Test
    fun `getAllContacts returns all contacts from dao`() = runTest {
        // Given: repository with mocked dao
        `when`(contactDao.getAllContacts()).thenReturn(flowOf(testContacts))
        
        // When: accessing all contacts
        val result = contactRepository.getAllContacts(false).first()
        
        // Then: it should return the test contacts
        assertThat(result).isEqualTo(testContacts)
    }
    
    @Test
    fun `getContactById returns contact when exists`() = runTest {
        // When: getting an existing contact by ID
        val result = contactRepository.getContactById("1").first()
        
        // Then: it should return the correct contact
        assertThat(result).isEqualTo(testContacts[0])
    }
    
    @Test
    fun `getContactById returns null when contact doesn't exist`() = runTest {
        // When: getting a non-existent contact by ID
        val result = contactRepository.getContactById("4").first()
        
        // Then: it should return null
        assertThat(result).isNull()
    }
    
    @Test
    fun `searchContacts returns matching contacts`() = runTest {
        // When: searching for contacts
        val result = contactRepository.searchContacts("John").first()
        
        // Then: it should return matching contacts
        assertThat(result).isEqualTo(listOf(testContacts[0]))
    }
    
    @Test
    fun `saveContact delegates to dao`() = runTest {
        // Given: a new contact
        val newContact = Contact(firstName = "Alice", lastName = "Williams", phoneNumber = "+1234567891")
        
        // When: saving the contact
        contactRepository.saveContact(newContact)
        
        // Then: it should delegate to the dao
        verify(contactDao).insertOrUpdate(newContact)
    }
    
    @Test
    fun `deleteContact delegates to dao`() = runTest {
        // Given: a contact to delete
        val contactToDelete = testContacts[2]
        
        // When: deleting the contact
        contactRepository.deleteContact(contactToDelete)
        
        // Then: it should delegate to the dao
        verify(contactDao).delete(contactToDelete)
    }
}