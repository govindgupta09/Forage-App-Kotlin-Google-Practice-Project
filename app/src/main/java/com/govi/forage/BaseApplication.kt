package com.govi.forage

import android.app.Application
import com.govi.forage.data.ForageDatabase

/**
 * An application class that inherits from [Application], allows for the creation of a singleton
 * instance of the [ForageDatabase]
 */
class BaseApplication : Application() {
    // provide a ForageDatabase value by lazy here
    val database: ForageDatabase by lazy {
        ForageDatabase.getDatabase(this)
    }
}