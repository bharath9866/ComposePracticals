package com.github.contactmanager.service

class ContactServiceTest {

    private lateinit var contactService: ContactService
    
    @Before
    fun setUp() {
        contactService = ContactService()
    }
    
    @Test
    fun `add contact adds to contacts list`() {
        // When: adding a new contact
        val contact = contactService.addContact(
            firstName = "John",
            lastName = "Doe",
            phoneNumber = "1234567890",
            email = "john.doe@example.com"
        )
        
        // Then: the contact is in the list
        val allContacts = contactService.getAllContacts()
        assertEquals(1, allContacts.size)
        assertEquals(contact, allContacts[0])
    }
    
    @Test
    fun `get contact by id returns correct contact`() {
        // Given: a contact is added
        val addedContact = contactService.addContact(
            firstName = "Jane",
            lastName = "Smith",
            phoneNumber = "9876543210"
        )
        
        // When: getting the contact by its ID
        val retrievedContact = contactService.getContactById(addedContact.id)
        
        // Then: the retrieved contact matches the added contact
        assertEquals(addedContact, retrievedContact)
    }
    
    @Test
    fun `update contact updates contact details`() {
        // Given: a contact is added
        val addedContact = contactService.addContact(
            firstName = "Original",
            lastName = "Name",
            phoneNumber = "1234567890"
        )
        
        // When: updating the contact
        val updatedContact = contactService.updateContact(
            id = addedContact.id,
            firstName = "Updated",
            phoneNumber = "9876543210"
        )
        
        // Then: the contact is updated correctly
        assertNotNull(updatedContact)
        assertEquals(addedContact.id, updatedContact!!.id)
        assertEquals("Updated", updatedContact.firstName)
        assertEquals("Name", updatedContact.lastName)  // Unchanged
        assertEquals("9876543210", updatedContact.phoneNumber)
    }
    
    @Test
    fun `get favorite contacts returns only favorites`() {
        // Given: multiple contacts with some favorites
        contactService.addContact(
            firstName = "John",
            lastName = "Doe",
            phoneNumber = "1111111111",
            isFavorite = true
        )
        
        contactService.addContact(
            firstName = "Jane",
            lastName = "Smith",
            phoneNumber = "2222222222",
            isFavorite = false
        )
        
        contactService.addContact(
            firstName = "Bob",
            lastName = "Johnson",
            phoneNumber = "3333333333",
            isFavorite = true
        )
        
        // When: getting favorite contacts
        val favoriteContacts = contactService.getFavoriteContacts()
        
        // Then: only favorites are returned
        assertEquals(2, favoriteContacts.size)
        assertTrue(favoriteContacts.all { it.isFavorite })
    }
    
    @Test
    fun `delete contact removes contact from list`() {
        // Given: a contact is added
        val addedContact = contactService.addContact(
            firstName = "ToDelete",
            lastName = "User",
            phoneNumber = "1231231234"
        )
        
        // When: deleting the contact
        val result = contactService.deleteContact(addedContact.id)
        
        // Then: the contact is removed
        assertTrue(result)
        assertTrue(contactService.getAllContacts().isEmpty())
    }
    
    @Test
    fun `delete all contacts clears the contact list`() {
        // Given: multiple contacts
        contactService.addContact("User", "One", "1111111111")
        contactService.addContact("User", "Two", "2222222222")
        contactService.addContact("User", "Three", "3333333333")
        
        // Verify contacts were added
        assertEquals(3, contactService.getAllContacts().size)
        
        // When: deleting all contacts
        contactService.deleteAllContacts()
        
        // Then: the list is empty
        assertTrue(contactService.getAllContacts().isEmpty())
    }
}