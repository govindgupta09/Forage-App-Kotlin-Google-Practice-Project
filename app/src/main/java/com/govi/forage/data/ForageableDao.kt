package com.govi.forage.data

import androidx.room.*
import com.govi.forage.model.Forageable
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for database interaction.
 */
@Dao
interface ForageableDao {

    // implement a method to retrieve all Forageables from the database
    @Query("SELECT * FROM forageable")
    fun getForagebles(): Flow<List<Forageable>>

    //implement a method to retrieve a Forageable from the database by id
    @Query("SELECT * FROM forageable WHERE id = :id")
    fun getForageable(id: Long): Flow<Forageable>

    // implement a method to insert a Forageable into the database
    //  (use OnConflictStrategy.REPLACE)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(forageable: Forageable)


    // implement a method to update a Forageable that is already in the database
    @Update
    fun update(forageable: Forageable)


    // implement a method to delete a Forageable from the database.
    @Delete
    fun delete(forageable: Forageable)
}
