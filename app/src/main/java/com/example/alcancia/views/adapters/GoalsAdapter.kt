package com.example.alcancia.views.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.alcancia.R
import com.example.alcancia.data.Goals
import com.example.alcancia.views.GoalDetailActivity
import com.example.alcancia.views.GoalsActivity
import com.example.alcancia.views.MainActivity
import kotlinx.android.synthetic.main.goal_grid_layout.*

class GoalsAdapter(context: Context) :
    RecyclerView.Adapter<GoalsAdapter.ItemHolder>() {

    private var dataList = mutableListOf<Goals>()

    var activityContext = context


    fun setListData(data:MutableList<Goals>){
        dataList = data
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val viewHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.goal_grid_layout, parent, false)


        return ItemHolder(viewHolder)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {

        val goal: Goals = dataList[position]


        holder.icons.setImageResource(R.drawable.ic_attach_money_black_24dp)
        holder.titles.text = goal.name
        holder.amount.text = goal.total.toString()

        //holder.progress.text = goal.progress.toString()


        holder.itemView.setOnClickListener{
            Toast.makeText(activityContext, goal.name, Toast.LENGTH_LONG).show()
            val intent = Intent(activityContext, GoalDetailActivity::class.java)
            intent.putExtra("name", goal.name)
            intent.putExtra("total", goal.total.toString())
            intent.putExtra("id", goal.id)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activityContext.startActivity(intent)
        }

    }

    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var icons = itemView.findViewById<ImageView>(R.id.imgGoal)
        var titles = itemView.findViewById<TextView>(R.id.tvGoal)
        var amount = itemView.findViewById<TextView>(R.id.tvAmount)
        var progress = itemView.findViewById<TextView>(R.id.tvProgress)

    }
}