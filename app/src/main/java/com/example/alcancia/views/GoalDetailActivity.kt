package com.example.alcancia.views

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.example.alcancia.R
import com.example.alcancia.data.Goals
import com.example.alcancia.data.Repo
import kotlinx.android.synthetic.main.activity_goal_detail.*

class GoalDetailActivity : AppCompatActivity() {
    private var goal = mutableListOf<Goals>()
    private val handler = Handler()
    val viewModel = GoalsViewModel()
    val repo = Repo()
    var goalId: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goal_detail)
        setSupportActionBar(toolbar)

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        goalId = intent.getStringExtra("id")

        observeData(goalId!!)

    }

    private fun observeData(goalId: String) {
        viewModel.fetchGoalById(goalId).observe(this, Observer {
            goal = it
            fillData(it)
        })
    }

    private fun fillData(goal: MutableList<Goals>) {
        val name = goal[0].name
        val amount = goal[0].total
        val current = goal[0].current
        btnConfirm.isClickable = false
        tvGoalDetail.text = name
        tvAmountDetail.text = amount.toString()
        tvCurrentDetail.text = current.toString()

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


        btnConfirm.setOnClickListener {
            var newName = etGoalDetail.text.toString()
            var newAmount = etAmountDetail.text.toString()
            var newCurrent = editCurrent.text.toString()
            var newCurrentF: Float
            newCurrentF = if (newCurrent.isEmpty()) {
                current
            } else {
                newCurrent.toFloat()
            }

            if (newAmount.isEmpty()) {
                newAmount = amount.toString()
            }
            if (newName.isEmpty()) {
                newName = name
            }
            if (current > newAmount.toFloat()) {
                Toast.makeText(
                    this,
                    "The new amount must be equals or higher than the current funds",
                    Toast.LENGTH_SHORT
                ).show()
            } else {

                val goals = ArrayList<Goals>()
                goals.add(Goals(goal[0].id, newName, newAmount.toFloat(), newCurrentF))

                Thread {
                    kotlin.run {
                        viewModel.updateGoals(goals)
                    }
                }.start()
                finish()
                startActivity(intent)
            }
        }
    }

    private fun showProgressBar(current: Double, amount: Double) {
        val progression = (current.toInt() * 100).div(amount)
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
                            Color.YELLOW, android.graphics.PorterDuff.Mode.SRC_IN
                        );
                    }
                    (progression > 75.1 && progression <= 100.0) -> {
                        progressBar.progressDrawable.setColorFilter(
                            Color.GREEN, android.graphics.PorterDuff.Mode.SRC_IN
                        );
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

    private fun editGoal() {
        etGoalDetail.visibility = View.VISIBLE
        etGoalDetail.setText(goal[0].name)
        etAmountDetail.visibility = View.VISIBLE
        etAmountDetail.setText(goal[0].total.toString())
        tvAmountDetail.visibility = View.GONE
        tvGoalDetail.visibility = View.GONE

        //EditGoalDialogFragment(goalId).show(supportFragmentManager, "")
        btnConfirm.isClickable = true
    }

    override fun onBackPressed() {
        super.onBackPressed()

        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_goal_detail, menu)

        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            android.R.id.home -> {
                finish()
                return true
            }
            R.id.id_edit -> {
                editGoal()
                //finish()
                return true

            }

            R.id.id_delete -> {
                showAlert()
                return true
            }
            R.id.menu_refresh -> {


                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Warning!")
        builder.setMessage("Are you sure you want to delete this goal?")
//builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

        builder.setPositiveButton("Yes, I'm sure") { dialog, which ->
            Toast.makeText(this,
                "Goal deleted", Toast.LENGTH_SHORT).show()
            viewModel.repo.deleteGoal(goalId!!)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        builder.setNegativeButton("Don't delete it") { dialog, which ->
            Toast.makeText(this,
                android.R.string.no, Toast.LENGTH_SHORT).show()

        }
        builder.show()
    }
}
