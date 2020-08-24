package com.example.alcancia.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.alcancia.R
import kotlinx.android.synthetic.main.activity_goals.*
import kotlinx.android.synthetic.main.fragment_new_goal.*

class GoalsActivity : AppCompatActivity() {

    private var viewModel = GoalsViewModel()
    private val manager = supportFragmentManager
    private var fragmentLoaded: String? = null

    companion object{
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
                    fragment = ExtraInfo()
                    replaceFragment(fragment)
                    fragmentLoaded = "extra info"
                    saveGoal()
                }

                "extra info" -> {
                    //updateGoal()

                    startActivity(Intent(this, MainActivity::class.java))
                    this.finish()
                }
            }
        }
    }
    fun replaceFragment(fragment:Fragment){
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.fragmentGoal, fragment)
        transaction.addToBackStack(null)
        transaction.commit()

    }

    fun saveGoal() : String?{

        val name = inputGoalName.text.toString()
        val amount = inputGoalAmount.text.toString()
        val current: Double? =  0.0

        if(name.isEmpty()){
            inputGoalName.error = "This field is required"
            return "0"
        } else if(amount <= "0" || amount.isEmpty()){
            inputGoalAmount.error = "This field is required"
            return "0"
        }else{
            replaceFragment(ExtraInfo())
            btnContinue.setText("Finish")
            btnContinue.setIconResource(R.drawable.ic_save_black_24dp)
            Toast.makeText(applicationContext, "Goal added", Toast.LENGTH_LONG).show()
        }


        Thread(Runnable {
           goalId = viewModel.saveGoalData(name, amount.toDouble(), current!!).toString()
        }).start()

        return goalId
    }

    /*fun updateGoal(){
        Thread(Runnable {
            viewModel.updateGoals()
        }).start()
    }*/
}
