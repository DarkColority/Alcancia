package com.example.alcancia.views

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.alcancia.R
import com.example.alcancia.data.Objective
import com.example.alcancia.data.Objectives

class CustomAdapter(var context: Context, items:ArrayList<Objective>): BaseAdapter() {
    var items: ArrayList<Objective>? = null

    init{
        this.items = items
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var holder:ViewHolder? = null

        var view:View? = convertView

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.list_obejctives_detail, null)
            holder = ViewHolder(view)
            view.tag = holder
        }else{
            holder = view.tag as? ViewHolder
        }
        var item = getItem(position) as Objective
        holder?.name?.text = item.name
        holder?.amount?.text = item.total.toString()

        return view!!
    }

    override fun getItem(position: Int): Any {
        return items?.get(position)!!
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return items?.count()!!
    }


    private class ViewHolder(view:View){
        var name: TextView? = null
        var amount: TextView? = null
        init {
            this.name = view.findViewById(R.id.tvObjName)
            this.amount = view.findViewById(R.id.tvObjAmount)
        }
    }
}