package com.example.me.databaseLocal.room.dbHelper

class DBUtils {
    companion object {
        const val DATABASE_NAME = "slotBooking.db"
        const val DB_VERSION = 6
        const val SLOT_BOOKING_LIST = "slot_booking_list"


    }


    interface SlotBookingConstant {
        companion object {
            const val KEY_UNIQUE_ID_SLOT_BOOKING = "allitemsuniqueid"
            const val KEY_END_TIME = "end_time"
            const val KEY_IS_BOOKED = "is_booked"
            const val  KEY_IS_EXPIRED = "is_expired"
            const val KEY_SLOT_ID = "slot_id"
             const val KEY_START_TIME = "start_time"
            const val KEY_USERNAME= "username"
            const val KEY_SLOT_TIME_ZONE = "time_zone"
            const val KEY_AM_PM_START_TIME="am_pm_start_time"
            const val KEY_AM_PM_END_TIME = "am_pm_end_time"

        }

    }



}
