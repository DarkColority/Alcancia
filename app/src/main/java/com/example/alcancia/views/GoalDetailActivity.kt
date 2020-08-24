package com.example.alcancia.views

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.example.alcancia.R
import com.example.alcancia.data.Goals
import kotlinx.android.synthetic.main.activity_goal_detail.*

class GoalDetailActivity : AppCompatActivity() {
    private val goal = Goals()
    private val handler = Handler()
    val viewModel = GoalsViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goal_detail)

        val goalId = intent.getStringExtra("id")
        observeData(goalId)
       
    }

    private fun observeData(goalId: String){
        viewModel.fetchGoalById(goalId).observe(this, Observer {
            fillData(it)
        })
    }

    private fun fillData(goal:MutableList<Goals>){
        val name = goal[0].name
        val amount = goal[0].total
        val current = goal[0].current
        btnConfirm.isClickable = false
        tvGoalDetail.text = name
        tvAmountDetail.text = amount.toString()

        showProgressBar(current.toDouble(), amount.toDouble())
        btnEditGoal.setOnClickListener {
            etGoalDetail.visibility = View.VISIBLE
            etGoalDetail.setText(name)
            etAmountDetail.visibility = View.VISIBLE
            etAmountDetail.setText(amount.toString())
            tvAmountDetail.visibility = View.GONE
            tvGoalDetail.visibility = View.GONE

            //EditGoalDialogFragment(goalId).show(supportFragmentManager, "")
            btnConfirm.isClickable = true
        }


        btnConfirm.setOnClickListener{
            var newName = etGoalDetail.text.toString()
            var newAmount = etAmountDetail.text.toString()
            var newCurrent = editCurrent.text.toString()

            if(newAmount.isEmpty()){
                newAmount = amount.toString()
            }
            if(newName.isEmpty()){
                newName = name
            }

            val goals= ArrayList<Goals>()
            goals.add(Goals(goal[0].id, newName, newAmount.toFloat(), newCurrent.toFloat()))

            Thread{ kotlin.run {
                viewModel.updateGoals(goals)
            }}.start()
        }
    }

    private fun showProgressBar(current: Double, amount: Double){
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
    }

}
