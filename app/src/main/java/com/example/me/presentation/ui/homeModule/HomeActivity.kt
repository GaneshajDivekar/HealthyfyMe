package com.example.me.presentation.ui.homeModule

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.example.me.R
import com.example.me.core.presentation.base.BaseActivity
import com.example.me.data.db.SlotBookingEntity
import com.example.me.databaseLocal.room.DatabaseHelper
import com.example.me.databinding.ActivityLoginBinding
import com.example.me.presentation.ui.homeModule.adapter.SlotBookingPagerAdapter
import com.example.me.presentation.ui.homeModule.fragment.FirstFragment.DynamicFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.DateFormatSymbols
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet


import java.text.SimpleDateFormat


class HomeActivity : BaseActivity<ActivityLoginBinding, HomeViewModel>(), HomeNavigator {
    private var activityLoginBinding: ActivityLoginBinding? = null
    private val activityModel: HomeViewModel by viewModel()
    var progressDoalog: ProgressDialog? = null


    companion object {
        var tabs = ArrayList<String>()
        var tabNameList = ArrayList<String>()

    }


    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun getViewModel(): HomeViewModel {
        return activityModel
    }

    override fun setUp(savedInstanceState: Bundle?) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityLoginBinding = getViewDataBinding()
        activityLoginBinding?.loginViewModel = this

        progressDoalog = ProgressDialog(this);
        progressDoalog!!.setMax(100);
        progressDoalog!!.setMessage("Loading....");
        progressDoalog!!.show();
        callLoginSlotData()
    }

    override fun callLoginSlotData() {
        Log.e("Current TimeStamp", "Current TimeStamp")
        activityModel.callSlotBookingApi().observe(this, Observer { slotBookingDetail ->
            if (slotBookingDetail != null) {
                progressDoalog!!.dismiss()

                DatabaseHelper.getDatabase(application).interfaceDao().getAllSlotBookingList()
                    .observe(this, object :
                        Observer<List<SlotBookingEntity>> {
                        override fun onChanged(t: List<SlotBookingEntity>?) {
                            for (i in t!!.indices) {
                                tabs.add(t.get(i).slotBookingStartDate.substring(0, 10))
                            }
                            var set = HashSet<String>()
                            set.addAll(tabs)
                            tabs.clear()
                            tabs.addAll(set)
                            // Sort in assending order
                            Collections.sort(tabs)
                            setupViewPager(activityLoginBinding!!.frameLayout);
                            activityLoginBinding!!.tabs.setupWithViewPager(activityLoginBinding!!.frameLayout);
                            var montName = getMonthForInt(tabs.get(0).substring(6, 7).toInt())
                            Log.e("MonthName", montName)
                            activityLoginBinding!!.txtMonthName.setText("" + montName)

                        }


                    })
            }
        })

    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = SlotBookingPagerAdapter(supportFragmentManager)
        for (i in tabs.indices) {
            try {


                val inFormat = SimpleDateFormat("yyyy-MM-dd")
                val date = inFormat.parse(tabs.get(i))
                val outFormat = SimpleDateFormat("EE")
                val day = outFormat.format(date)

                tabNameList.add(tabs.get(i).substring(9,10)+"\n"+day.toLowerCase())

                adapter.addFrag(DynamicFragment(application, i), tabs.get(i).substring(9,10)+"\n"+day.toLowerCase())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        viewPager.adapter = adapter
    }


    fun getMonthForInt(month: Int): String {
        return DateFormatSymbols().getMonths()[month - 1];

    }
}