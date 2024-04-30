package com.example.contacts

import com.example.myproject.Contact

class Repo {
    private val listOfContact = mutableListOf<Contact>()

    fun getAllContact() = listOfContact

    fun addDataToContactList(contact: Contact) = listOfContact.add(contact)
}