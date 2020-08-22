package com.example.alcancia.views

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.alcancia.R
import com.example.alcancia.data.Goals
import com.example.alcancia.views.adapters.GoalsAdapter
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_goals.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.goal_grid_layout.*
import java.math.RoundingMode

class MainActivity : AppCompatActivity() {

    private var recyclerView: RecyclerView? = null
    private var goals= ArrayList<Goals>()
    private var gridLayoutManager: GridLayoutManager? = null
    private var goalsAdapters: GoalsAdapter? = null
    private lateinit var viewModel: GoalsViewModel
    private var doubleBackToExitPressedOnce = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(GoalsViewModel::class.java)
        setContentView(R.layout.activity_main)


        recyclerView = findViewById(R.id.goalsList)
        gridLayoutManager =
            GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false)
        recyclerView?.layoutManager = gridLayoutManager
        recyclerView?.setHasFixedSize(true)
        observeData()
        goalsAdapters = GoalsAdapter(applicationContext)
        recyclerView?.adapter = goalsAdapters

        btn_add.setOnClickListener {
            Toast.makeText(this, "This is for add a new goal", Toast. LENGTH_SHORT).show()
            startActivity(Intent(this, GoalsActivity::class.java))
            
        }
    }


    fun setTotal(mutableList: MutableList<Goals>){
        var total: Float = 0.0F
        for(i in 0..mutableList.size-1){
            total += mutableList[i].total
        }
        totalGlobal.amount = total
    }

    fun observeData(){
        shimmer_view_container.startShimmerAnimation()
        viewModel.fetchGoalData().observe(this, Observer {
            shimmer_view_container.stopShimmerAnimation()
            shimmer_view_container.visibility = View.GONE
            goalsAdapters!!.setListData(it)
            goalsAdapters!!.notifyDataSetChanged()
            setTotal(it)
        })
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            this.doubleBackToExitPressedOnce = true
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()
            return
        }
        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }

}