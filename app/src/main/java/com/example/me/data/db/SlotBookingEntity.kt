package com.example.me.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.me.databaseLocal.room.dbHelper.DBUtils

@Entity(tableName = DBUtils.SLOT_BOOKING_LIST)
public class SlotBookingEntity{
/*
    "end_time": "2019-12-17 01:45:00+00:00",
    "is_booked": false,
    "is_expired": false,
    "slot_id": 9537638,
    "start_time": "2019-12-17 01:30:00+00:00",
    "username": null*/

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DBUtils.SlotBookingConstant.KEY_UNIQUE_ID_SLOT_BOOKING)
     var slotBookingUniqueId: Int = 0


    @ColumnInfo(name = DBUtils.SlotBookingConstant.KEY_END_TIME)
     var slotBookingEndDate: String = ""

    @ColumnInfo(name = DBUtils.SlotBookingConstant.KEY_IS_BOOKED)
     var slotIsBooked: Boolean = false


    @ColumnInfo(name = DBUtils.SlotBookingConstant.KEY_IS_EXPIRED)
     var slotIsExpired: Boolean = false

    @ColumnInfo(name = DBUtils.SlotBookingConstant.KEY_SLOT_ID)
     var slotId : Int =0

    @ColumnInfo(name = DBUtils.SlotBookingConstant.KEY_START_TIME)
     var slotBookingStartDate: String = ""

    @ColumnInfo(name = DBUtils.SlotBookingConstant.KEY_USERNAME)
     var slotUserName: String = ""

    @ColumnInfo(name = DBUtils.SlotBookingConstant.KEY_SLOT_TIME_ZONE)
    var slotTimeZone: String = ""

    @ColumnInfo(name = DBUtils.SlotBookingConstant.KEY_AM_PM_START_TIME)
    var amPmStartTime:String=""

    @ColumnInfo(name = DBUtils.SlotBookingConstant.KEY_AM_PM_END_TIME)
    var amPmEndtTime:String=""





}
