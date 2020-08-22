package com.example.alcancia.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.alcancia.R
import com.example.alcancia.data.Goals
import com.example.alcancia.data.Objective
import com.example.alcancia.views.adapters.NewObjectivesAdapter
import kotlinx.android.synthetic.main.activity_goal_detail.*
import kotlinx.android.synthetic.main.activity_goals.*
import kotlinx.android.synthetic.main.fragment_new_goal.*
import kotlinx.android.synthetic.main.fragment_objectives.*

class GoalsActivity : AppCompatActivity() {

    private var viewModel = GoalsViewModel()
    private val manager = supportFragmentManager
    private var fragmentLoaded: String? = null
    var goal: Goals? = null

    companion object{

        var fruit: ArrayList<Objective>? = null
        var goalId: String? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goals)
        var fragment: Fragment


        replaceFragment(NewGoalFragment())
        fragmentLoaded = "goal"

        btnContinue.setOnClickListener {
            when (fragmentLoaded) {
                "goal" -> {
                    val name = inputGoalName.text.toString()
                    goal?.name = name
                    val total = inputGoalAmount.text.toString()
                    goal?.total = total.toDouble().toFloat()
                    if (chkObjs.isChecked) {
                        fragmentLoaded = "objective"
                        fragment = ObjectivesFragment()
                        replaceFragment(fragment)
                    } else {
                        fragment = ExtraInfo()
                        replaceFragment(fragment)
                        fragmentLoaded = "extra info"
                    }


                }
                "objective" -> {
                    ObjectivesFragment().saveObjectives()
                    fragment = ExtraInfo()
                    btnContinue.text = "Finish"
                    btnContinue.setIconResource(R.drawable.ic_save_black_24dp)
                    replaceFragment(fragment)
                    fragmentLoaded = "extra info"

                }
                "extra info" -> {
                    saveGoal(goal)
                    startActivity(Intent(this, MainActivity::class.java))
                }
            }
        }
    }


    fun replaceFragment(fragment:Fragment){
        var bundle: Bundle? = null
        bundle?.putString("Name", goal!!.name)
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.fragmentGoal, fragment)
        transaction.addToBackStack(null)
        transaction.commit()

    }

    fun saveGoal(goal: Goals?) {

        val name = goal?.name
        val amount = goal?.total.toString()
        val current: Double? = goal?.current?.toDouble()

        if(name!!.isEmpty()){
            inputGoalName.error = "This field is required"
            return
        } else if(amount <= "0" || amount.isEmpty()){
            inputGoalAmount.error = "This field is required"
            return
        }else{
            if(chkObjs.isChecked){
                replaceFragment(ObjectivesFragment())
            }else{
                replaceFragment(ExtraInfo())
                btnContinue.setText("Finish")
                btnContinue.setIconResource(R.drawable.ic_save_black_24dp)
                Toast.makeText(applicationContext, "Goal added", Toast.LENGTH_LONG).show()
            }

        }
        Thread(Runnable {
            viewModel.saveGoalData(name!!, amount.toDouble(), current!!)
        }).start()


    }
}
