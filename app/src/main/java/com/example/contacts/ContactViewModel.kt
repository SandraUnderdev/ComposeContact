package com.example.contacts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myproject.Contact

class ContactViewModel(
    private val repo: Repo
) : ViewModel() {

    val listOfContactLiveData = MutableLiveData<List<Contact>>()

    init {
        getAllData()
    }

    private fun getAllData() {
        val allContact = repo.getAllContact()
        listOfContactLiveData.value = allContact
    }

    fun addContact(contact: Contact) {
       repo.addDataToContactList(contact)
    }

}