package com.example.me.presentation.ui.homeModule.fragment.FirstFragment


import android.app.Application
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.me.R
import com.example.me.core.presentation.base.BaseFragment
import com.example.me.data.db.SlotBookingEntity
import com.example.me.databaseLocal.room.DatabaseHelper
import com.example.me.databinding.FragmentDynamicBinding
import com.example.me.presentation.ui.homeModule.HomeActivity
import com.h6ah4i.android.widget.advrecyclerview.expandable.RecyclerViewExpandableItemManager
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractExpandableItemAdapter
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractExpandableItemViewHolder
import org.koin.androidx.viewmodel.ext.android.viewModel


class DynamicFragment(var application: Application, var i: Int) :
    BaseFragment<FragmentDynamicBinding, FirstViewModel>() {
    private var fragmentDynamicBinding: FragmentDynamicBinding? = null
    private val firstViewModel: FirstViewModel by viewModel()
    var perticularDateListFirst = ArrayList<SlotBookingEntity>()


    override fun getLayoutId(): Int {
        return R.layout.fragment_dynamic
    }

    override fun getViewModel(): FirstViewModel {
        return firstViewModel
    }

    override fun setUp(view: View, savedInstanceState: Bundle?) {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentDynamicBinding = getViewDataBinding()
        callData()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        perticularDateListFirst =
            DatabaseHelper.getDatabase(application).interfaceDao().getPerticularDateData(
                HomeActivity.tabs.get(i) + "%"
            ) as ArrayList<SlotBookingEntity>
        Log.e("data1", perticularDateListFirst.size.toString())

    }

    private fun callData() {

        val expMgr = RecyclerViewExpandableItemManager(null)

        fragmentDynamicBinding!!.recyclerView.setLayoutManager(LinearLayoutManager(activity))
        fragmentDynamicBinding!!.recyclerView.setAdapter(
            expMgr.createWrappedAdapter(
                MyAdapter(
                    perticularDateListFirst
                )
            )
        )
        (fragmentDynamicBinding!!.recyclerView.getItemAnimator() as SimpleItemAnimator).supportsChangeAnimations =
            false

        expMgr.attachRecyclerView(fragmentDynamicBinding!!.recyclerView)
    }

    internal abstract class MyBaseItem(
        val id: Long,
        val text: String,
        var isBooked: Boolean,
        var isExpired: Boolean,
        var timeZone: String
    )

    internal class MyGroupItem(
        id: Long,
        text: String,
        isBooked: Boolean,
        isExpired: Boolean,
        timeZone: String
    ) :
        MyBaseItem(id, text, isBooked, isExpired, timeZone) {
        val children: MutableList<MyChildItem>

        init {
            children = ArrayList()
        }
    }

    internal class MyChildItem(
        id: Long,
        text: String,
        isExpired: Boolean,
        isBooked: Boolean,
        timeZone: String
    ) :
        MyBaseItem(id, text, isExpired, isBooked, timeZone)

    internal abstract class MyBaseViewHolder(itemView: View) :
        AbstractExpandableItemViewHolder(itemView) {
        var textView: TextView
        var imgCollapse: ImageView? = null

        var txtAvaiableSlot: TextView? = null

        init {
            textView = itemView.findViewById(android.R.id.text1)
            imgCollapse=itemView.findViewById(R.id.imgCollapse)
            txtAvaiableSlot = itemView.findViewById(R.id.txtAvaiableSlot)
        }
    }

    internal class MyGroupViewHolder(itemView: View) : MyBaseViewHolder(itemView)

    internal class MyChildViewHolder(itemView: View) : MyBaseViewHolder(itemView)

    internal class MyAdapter(perticularDateListFirst: ArrayList<SlotBookingEntity>) :
        AbstractExpandableItemAdapter<MyGroupViewHolder, MyChildViewHolder>() {
        var mItems: MutableList<MyGroupItem>


        init {
            setHasStableIds(true) // this is required for expandable feature.

            mItems = ArrayList()

            for (i in perticularDateListFirst!!.indices) {

                val group = MyGroupItem(
                    i.toLong(),
                    perticularDateListFirst.get(i).slotTimeZone,
                    perticularDateListFirst.get(i).slotIsExpired,
                    perticularDateListFirst.get(i).slotIsBooked,
                    perticularDateListFirst.get(i).slotTimeZone
                )

                for (j in perticularDateListFirst.indices) {
                    if (perticularDateListFirst.get(i).slotTimeZone == perticularDateListFirst.get(j).slotTimeZone) {
                        group.children.add(
                            MyChildItem(i.toLong(), perticularDateListFirst.get(j).amPmStartTime + " - " + perticularDateListFirst.get(j).amPmEndtTime,
                                perticularDateListFirst.get(j).slotIsExpired,
                                perticularDateListFirst.get(j).slotIsBooked,
                                perticularDateListFirst.get(j).slotTimeZone
                            )
                        )
                    }

                }
                if (mItems.size == 0) {
                    mItems.add(group)
                } else if (!mItems.get(0).timeZone.equals(perticularDateListFirst.get(i).slotTimeZone)) {
                    mItems.add(group)
                }

            }

        }


        override fun getGroupCount(): Int {
            return mItems.size
        }

        override fun getChildCount(groupPosition: Int): Int {
            return mItems[groupPosition].children.size
        }

        override fun getGroupId(groupPosition: Int): Long {
            return mItems[groupPosition].id
        }

        override fun getChildId(groupPosition: Int, childPosition: Int): Long {
            // This method need to return unique value within the group.
            return mItems[groupPosition].children[childPosition].id
        }

        override fun onCreateGroupViewHolder(parent: ViewGroup, viewType: Int): MyGroupViewHolder {
            val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_group_item_for_expandable_minimal, parent, false)
            return MyGroupViewHolder(v)
        }

        override fun onCreateChildViewHolder(parent: ViewGroup, viewType: Int): MyChildViewHolder {
            val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_child_item_for_expandable_minimal, parent, false)
            return MyChildViewHolder(v)
        }

        override fun onBindGroupViewHolder(
            holder: MyGroupViewHolder,
            groupPosition: Int,
            viewType: Int
        ) {
            val group = mItems[groupPosition]

            var availableSlotLIst = java.util.ArrayList<DynamicFragment.MyChildItem>()
            for (i in mItems[groupPosition].children.indices)
            {
                if(!mItems[groupPosition].children.get(i).isExpired){
                    availableSlotLIst.add(mItems[groupPosition].children.get(i))
                }
            }


            holder.txtAvaiableSlot!!.setText(""+availableSlotLIst.size + " Slots available")
            holder.textView.text = group.text
        }

        override fun onBindChildViewHolder(
            holder: MyChildViewHolder,
            groupPosition: Int,
            childPosition: Int,
            viewType: Int
        ) {
            val child = mItems[groupPosition].children[childPosition]
            holder.textView.text = child.text
            if (child.isExpired) {
                holder.itemView.setBackgroundColor(Color.parseColor("#cccccc"))
            } else {
                holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"))
            }

        }

        override fun onCheckCanExpandOrCollapseGroup(
            holder: MyGroupViewHolder,
            groupPosition: Int,
            x: Int,
            y: Int,
            expand: Boolean
        ): Boolean {
            if(expand){
                holder.imgCollapse!!.setImageResource(R.drawable.ic_keyboard_arrow_up_red_24dp);
            }else{
                holder.imgCollapse!!.setImageResource(R.drawable.ic_keyboard_arrow_down_red_24dp);
            }
            return true
        }
    }


}
