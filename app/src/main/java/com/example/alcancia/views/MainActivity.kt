package com.example.alcancia.views

import android.content.Intent
import android.graphics.Color
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
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.toolbar


class MainActivity : AppCompatActivity() {

    private var recyclerView: RecyclerView? = null
    private var goals= ArrayList<Goals>()
    private var gridLayoutManager: GridLayoutManager? = null
    private var goalsAdapters: GoalsAdapter? = null
    private lateinit var viewModel: GoalsViewModel
    private var doubleBackToExitPressedOnce = false
    private val handler = Handler()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(GoalsViewModel::class.java)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        swipe_layout.setOnRefreshListener {
            observeData()
            swipe_layout.isRefreshing = false
        }

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
        shimmer_view_container.startShimmer()
        viewModel.fetchGoalData().observe(this, Observer {
            shimmer_view_container.stopShimmer()
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

    /*private fun showProgressBar(current: Double, amount: Double){
        val progression = (current.toInt()*100).div(amount)
        Thread(Runnable {
            // Update the progress bar and display the current value
            handler.post(Runnable {
                progressBar!!.progress = progression.toInt()

                when {
                    progression <= 30.0 -> {
                        progressBar.progressDrawable.setColorFilter(
                            Color.RED, android.graphics.PorterDuff.Mode.SRC_IN
                        );
                    }
                    (progression > 30.1 && progression <= 75.0) -> {
                        progressBar.progressDrawable.setColorFilter(
                            Color.YELLOW, android.graphics.PorterDuff.Mode.SRC_IN);
                    }
                    (progression > 75.1 && progression <= 100.0) -> {
                        progressBar.progressDrawable.setColorFilter(
                            Color.GREEN, android.graphics.PorterDuff.Mode.SRC_IN);
                    }
                }
            })
            try {
                Thread.sleep(100)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

        }).start()
    }*/


}