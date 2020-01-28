package com.example.me.presentation.ui.homeModule

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.me.core.presentation.base.BaseViewModel
import com.example.me.data.api.SlotBookingModel

public class HomeViewModel(application: Application) : BaseViewModel(application) {
    fun callSlotBookingApi(): LiveData<List<SlotBookingModel> >{
        return HomeRepository.getInstance(getApplication()).callSlotBookingApi()
    }

}
