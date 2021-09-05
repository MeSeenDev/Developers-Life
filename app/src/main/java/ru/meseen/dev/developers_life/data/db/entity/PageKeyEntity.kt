package ru.meseen.dev.developers_life.data.db.entity

import android.provider.BaseColumns
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.meseen.dev.developers_life.data.db.DevLifeDataBase.Companion.PAGE_TABLE_NAME
import ru.meseen.dev.developers_life.ui.base.BaseKeyEntity

@Entity(tableName = PAGE_TABLE_NAME)
data class PageKeyEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = BaseColumns._ID)
    override val _id: Long? = null,
    @ColumnInfo(name = "prevPage")
    override val prevPage: Int?,
    @ColumnInfo(name = "nextPage")
    override val nextPage: Int?,
    @ColumnInfo(name = "section")
    val section: String
) : BaseKeyEntity