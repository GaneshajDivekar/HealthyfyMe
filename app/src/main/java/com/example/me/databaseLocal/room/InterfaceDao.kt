package com.example.me.databaseLocal.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.me.data.db.SlotBookingEntity
import com.example.me.databaseLocal.room.dbHelper.DBUtils


@Dao
interface InterfaceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveLoginDetails(slotBookingEntity: SlotBookingEntity)

    @Query("SELECT * FROM " + DBUtils.SLOT_BOOKING_LIST)
    fun getAllSlotBookingList(): LiveData<List<SlotBookingEntity>>

    @Query("Delete from SLOT_BOOKING_LIST")
    fun deleteSlotBookingList()

    @Query("SELECT * from slot_booking_list WHERE start_time LIKE :search")
    fun getPerticularDateData(search: String): List<SlotBookingEntity>


}





