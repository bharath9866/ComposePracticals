# Contact Manager Module

A lightweight module for Android applications that provides contact management and phone call
functionality.

## Features

- **Contact Management**: Create, view, edit, and delete contacts
- **Favorites Support**: Mark contacts as favorites for quick access
- **Phone Call Utilities**: Open dialer or initiate calls from the app

## Components

### Data Model

The module uses a simple `Contact` data class with the following properties:
- `id`: Unique identifier for the contact
- `firstName`: First name of the contact
- `lastName`: Last name of the contact
- `phoneNumber`: Phone number of the contact
- `email`: Email address (optional)
- `isFavorite`: Flag indicating if the contact is marked as a favorite

### Services

#### ContactManagerFacade

The main entry point for the module, providing a simplified interface to all functionality:
- Contact management (add, get, update, delete)
- Phone call operations
- Phone number formatting

#### ContactService

The `ContactService` class provides methods for managing contacts:
- `addContact`: Add a new contact with the provided details
- `getAllContacts`: Get all contacts in the list
- `getFavoriteContacts`: Get contacts marked as favorites
- `getContactById`: Get a specific contact by its ID
- `updateContact`: Update an existing contact's details
- `deleteContact`: Delete a contact by its ID
- `deleteAllContacts`: Clear all contacts from the list

#### PhoneCallUtil

The `PhoneCallUtil` object provides utilities for making phone calls:
- `openDialer`: Opens the phone dialer with a pre-filled number
- `initiatePhoneCall`: Directly initiates a call (requires permission)
- `hasCallPhonePermission`: Checks if the app has permission to make calls
- `formatPhoneNumber`: Formats phone numbers for display

## Usage Examples

### Using the Facade

The simplest way to use the module is through the `ContactManagerFacade`:
