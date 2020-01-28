package com.example.me.core.network


import com.example.me.data.api.SlotBookingModel
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

public interface RetrofitApiInterface {
    @GET(WebServiceAPI.Slot_booking)
    fun getSlotBookingResponse(): Deferred<Response<List<SlotBookingModel>>>


}


