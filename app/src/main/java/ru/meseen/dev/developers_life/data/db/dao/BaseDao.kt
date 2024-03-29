package ru.meseen.dev.developers_life.data.db.dao

import androidx.room.*

/**
 * @author Vyacheslav Doroshenko
 */
interface BaseDao <Type> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(type : Type)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(type : List<Type>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg type : Type)

    @Update
    fun update(type : Type)

    @Delete
    fun delete(type : Type)
}