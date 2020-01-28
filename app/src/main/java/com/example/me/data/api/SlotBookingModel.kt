package com.example.me.data.api


data class SlotBookingModel(
    var end_time: String="",
    var is_booked: Boolean=false,
    var is_expired: Boolean=false,
    var slot_id: Int=0,
    var start_time: String="",
    var username: String=""
)




