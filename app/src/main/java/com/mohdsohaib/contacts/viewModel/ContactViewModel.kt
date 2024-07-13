package com.mohdsohaib.contacts.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.mohdsohaib.contacts.model.Contact
import com.mohdsohaib.contacts.model.repository.ContactRepository
import kotlinx.coroutines.launch


class ContactViewModel(private val repository: ContactRepository): ViewModel() {
    val allContacts: LiveData<List<Contact>> = repository.allContacts.asLiveData()

    fun addContacts(image: String, name: String, phoneNumber: String, email: String){
        viewModelScope.launch {
            val contact = Contact(0, image = image, name = name, phoneNumber = phoneNumber, email = email)
            repository.insert(contact)
        }
    }
    fun updateContact(contact: Contact){
        viewModelScope.launch {
            repository.update(contact)
        }
    }

    fun deleteContact(contact: Contact){
        viewModelScope.launch {
            repository.delete(contact)
        }
    }
}

@Suppress("UNCHECKED_CAST")
class ContactViewModelFactory(private val repository: ContactRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ContactViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return ContactViewModel(repository) as T
        }else{
            throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }
}