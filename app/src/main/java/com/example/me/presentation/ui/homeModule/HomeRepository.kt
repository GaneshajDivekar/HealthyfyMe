package com.example.me.presentation.ui.homeModule

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.me.core.network.RetrofitCallAPI
import com.example.me.data.api.SlotBookingModel
import com.example.me.core.network.WebServiceAPI
import com.example.me.data.db.SlotBookingEntity
import com.example.me.databaseLocal.room.DatabaseHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class HomeRepository{

    companion object {

        var homeRepository: HomeRepository? = null
        var mContext: Application? = null
        var databaseHelper: DatabaseHelper? = null
        @Synchronized
        @JvmStatic
        fun getInstance(context: Application): HomeRepository {
            mContext = context
            databaseHelper = DatabaseHelper.getDatabase(mContext!!)
            if (homeRepository == null) homeRepository = HomeRepository()
            return homeRepository!!


        }

    }
    var listCustomer = MutableLiveData<List<SlotBookingModel>>()

    fun callSlotBookingApi(): LiveData<List<SlotBookingModel>> {
        CoroutineScope(Dispatchers.Main).launch {

            val resultDef: Deferred<Response<List<SlotBookingModel>>> =
                getDataFromServer()
            try {
                val result: Response<List<SlotBookingModel>> = resultDef.await()
                if (result.isSuccessful) {
                    val resposne = result.body()
                    resposne?.let {
                        val list = resposne
                        databaseHelper!!.interfaceDao().deleteSlotBookingList()
                        for (i in list.indices) {
                            val slotBookingEntity = SlotBookingEntity()
                            slotBookingEntity.slotBookingEndDate = list.get(i).end_time
                            slotBookingEntity.slotIsBooked = list.get(i).is_booked
                            slotBookingEntity.slotIsExpired = list.get(i).is_expired
                            slotBookingEntity.slotId = list.get(i).slot_id
                            slotBookingEntity.slotBookingStartDate = list.get(i).start_time
                            slotBookingEntity.slotUserName = list.get(i).username ?: ""



                            var startDate = ""
                            var endDate = ""


                            var startTime =
                                list.get(i).start_time.substring(0, 16);
                            var endTime = list.get(i).end_time.substring(0, 16)
                            var tk = StringTokenizer(startTime);
                            var date = tk.nextToken();
                            var time = tk.nextToken();
                            startDate = Convert24to12(time)
                            slotBookingEntity.amPmStartTime =startDate ?: ""
                            //end Time
                            var tk1 = StringTokenizer(endTime);
                            var date1 = tk1.nextToken();
                            var time1 = tk1.nextToken();
                            endDate = Convert24to12(time1)
                            slotBookingEntity.amPmEndtTime =endDate ?: ""



                            val timeOfDay:Int =time.substring(0,2).toInt()

                            if (timeOfDay >= 0 && timeOfDay < 12) {
                                slotBookingEntity.slotTimeZone = "Morning"
                            } else if (timeOfDay >= 12 && timeOfDay < 16) {
                                slotBookingEntity.slotTimeZone = "Afternoon"
                            } else if (timeOfDay >= 16 && timeOfDay < 21) {
                                slotBookingEntity.slotTimeZone = "Evening"
                            } else if (timeOfDay >= 21 && timeOfDay < 24) {
                                slotBookingEntity.slotTimeZone = "Night"
                            }


                            databaseHelper!!.interfaceDao().saveLoginDetails(slotBookingEntity)




                        }

                        listCustomer.value = list
                    }
                } else {

                }
            } catch (ex: Exception) {
                resultDef.getCompletionExceptionOrNull()?.let {
                    println(resultDef.getCompletionExceptionOrNull()!!.message)
                }

            }
        }
        return listCustomer


    }


    private fun Convert24to12(time: String): String {
        var convertedTime = ""
        try {
            val displayFormat = SimpleDateFormat("HH:mm a", Locale.US)
            val parseFormat = SimpleDateFormat("HH:mm", Locale.US)


            val date = parseFormat.parse(time)
            convertedTime = displayFormat.format(date)

            println("convertedTime : $convertedTime")
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return convertedTime
        //Output will be 10:23 PM
    }




    fun getHoursValue( hours:Int):Int{
        return hours - 12;
    }


    /*  *
       * Heavy operation that cannot be done in the Main Thread*/

    fun getDataFromServer(): Deferred<Response<List<SlotBookingModel>>>{

        return RetrofitCallAPI.getInstance(WebServiceAPI.SERVERBASE_URL)!!.getSlotBookingResponse()
    }
}
