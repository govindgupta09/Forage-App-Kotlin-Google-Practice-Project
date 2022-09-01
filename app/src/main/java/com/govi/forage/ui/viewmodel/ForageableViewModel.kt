package com.govi.forage.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.govi.forage.data.ForageableDao
import com.govi.forage.model.Forageable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Shared [ViewModel] to provide data to the [ForageableListFragment], [ForageableDetailFragment],
 * and [AddForageableFragment] and allow for interaction the the [ForageableDao]
 */

// TODO: pass a ForageableDao value as a parameter to the view model constructor
class ForageableViewModel(
    // Pass dao here
    private val forageableDao: ForageableDao
): ViewModel() {
    // TODO: create a property to set to a list of all forageables from the DAO
    val forageables = forageableDao.getForagebles().asLiveData()

    // create method that takes id: Long as a parameter and retrieve a Forageable from the database by id via the DAO.
    fun getForageable(id: Long) = forageableDao.getForageable(id).asLiveData()

    fun addForageable(
        name: String,
        address: String,
        inSeason: Boolean,
        notes: String
    ) {
        val forageable = Forageable(
            name = name,
            address = address,
            inSeason = inSeason,
            notes = notes
        )
    // launch a coroutine and call the DAO method to add a Forageable to the database within it
        viewModelScope.launch(Dispatchers.IO) {
            forageableDao.insert(forageable)
        }
    }

    fun updateForageable(
        id: Long,
        name: String,
        address: String,
        inSeason: Boolean,
        notes: String
    ) {
        val forageable = Forageable(
            id = id,
            name = name,
            address = address,
            inSeason = inSeason,
            notes = notes
        )
        viewModelScope.launch(Dispatchers.IO) {
            // call the DAO method to update a forageable to the database here
            forageableDao.update(forageable)
        }
    }

    fun deleteForageable(forageable: Forageable) {
        viewModelScope.launch(Dispatchers.IO) {
            // call the DAO method to delete a forageable to the database here
            forageableDao.delete(forageable)
        }
    }

    fun isValidEntry(name: String, address: String): Boolean {
        return name.isNotBlank() && address.isNotBlank()
    }
}

// create a view model factory that takes a ForageableDao as a property and
//  creates a ForageableViewModel
class ForageableViewModelFactory(private val forageableDao: ForageableDao) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ForageableViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ForageableViewModel(forageableDao) as T
        }
        throw IllegalArgumentException("Unexpected class: $modelClass")
    }
}
