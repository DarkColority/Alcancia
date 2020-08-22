package com.example.alcancia.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.Toast
import com.example.alcancia.R
import com.example.alcancia.data.Goals
import com.example.alcancia.data.Objective
import com.example.alcancia.data.Objectives
import kotlinx.android.synthetic.main.activity_goal_detail.*
import kotlinx.android.synthetic.main.list_obejctives_detail.*

class GoalDetailActivity : AppCompatActivity() {
    private val goal = Goals()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goal_detail)



        tvGoalDetail.text = intent.getStringExtra("name")
        tvAmountDetail.text = intent.getStringExtra("total").toString()
        val goalId = intent.getStringExtra("id")

        val fruits: ArrayList<Objective> = ArrayList()
        fruits.add(Objective("",goalId,"Apple", 100.00F))
        fruits.add(Objective("",goalId,"Peach",100.00F))
        fruits.add(Objective("",goalId,"Banana",100.00F))
        fruits.add(Objective("",goalId,"Water Melon", 100.00F))
        fruits.add(Objective("",goalId,"Apple", 100.00F))

        val adapter = CustomAdapter(this, fruits)

        list_objectives_goal_detail.adapter = adapter

        list_objectives_goal_detail.onItemClickListener = AdapterView.OnItemClickListener{ parent, view, position, id ->
            Toast.makeText(this, fruits[position].name, Toast.LENGTH_SHORT).show()

        }

        btnEditGoal.setOnClickListener {
            EditGoalDialogFragment(goalId).show(supportFragmentManager, "")


        }
    }
}
