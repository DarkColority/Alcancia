package com.example.alcancia.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.alcancia.R
import com.example.alcancia.data.Objective
import com.example.alcancia.data.Objectives

class NewObjectivesAdapter(var context: Context, items:ArrayList<Objective>): BaseAdapter() {
    var items: ArrayList<Objective>? = null

    init{
        this.items = items
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var holder:ViewHolder? = null

        var view: View? = convertView

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.add_objectives_list, null)
            holder = ViewHolder(view)
            view.tag = holder
        }else{
            holder = view.tag as? ViewHolder
        }
        var item = getItem(position) as Objective
        holder?.name?.text = item.name
        //holder?.image?.setImageResource(item.image)

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


    private class ViewHolder(view: View){
        var name: TextView? = null
        //var image: ImageView? = null
        init {
            this.name = view.findViewById(R.id.tvNOName)
            //this.image = view.findViewById(R.id.img_fruit)
        }
    }
}