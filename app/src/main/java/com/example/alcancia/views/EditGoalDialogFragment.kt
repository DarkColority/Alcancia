package com.example.alcancia.views

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment

import com.example.alcancia.R
import com.example.alcancia.data.Goals
import kotlinx.android.synthetic.main.fragment_edit_goal_dialog.*
import kotlinx.android.synthetic.main.fragment_edit_goal_dialog.addObjsList
import kotlinx.android.synthetic.main.fragment_edit_goal_dialog.btnNewObj
import kotlinx.android.synthetic.main.fragment_edit_goal_dialog.chkObjs


    class EditGoalDialogFragment(goalId: String) : DialogFragment() {
        val goalId = goalId
        val viewModel = GoalsViewModel()
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setStyle(STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth)
        }

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            return inflater.inflate(R.layout.fragment_edit_goal_dialog, container, false)
        }

        override fun onActivityCreated(savedInstanceState: Bundle?) {
            super.onActivityCreated(savedInstanceState)

            chkObjs.setOnClickListener{
                if(!chkObjs.isChecked){
                    addObjsList.visibility = View.GONE
                    btnNewObj.visibility = View.GONE
                }else{
                    addObjsList.visibility = View.VISIBLE
                    btnNewObj.visibility = View.VISIBLE
                }
            }
            val goal = ArrayList<Goals>()

            /*objs.add(0, Objective(goal[0].id,"Parte 1",5000.0F))
            objs.add(1, Objective( "","Parte 2", 5000.0F))
            goal.add(0, Goals("aCS6NRewi0rYNO5ScbE1", "nueva", 10000.0F, 2500.0F))*/

            btnSaveGoalEdit.setOnClickListener{
                Thread{ kotlin.run {
                    viewModel.updateGoals(goal)
                }}.start()

            }

            btnDeleteGoal.setOnClickListener{
                showAlert()
            }

        }

        private fun showAlert(){
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Warning!")
            builder.setMessage("Are you sure you want to delete this goal?")
//builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

            builder.setPositiveButton("Yes, I'm sure") { dialog, which ->
                Toast.makeText(context,
                    "Goal deleted", Toast.LENGTH_SHORT).show()
                viewModel.repo.deleteGoal(goalId)
                startActivity(Intent(context, MainActivity::class.java))
                activity?.finish()
            }

            builder.setNegativeButton("Don't delete it") { dialog, which ->
                Toast.makeText(context,
                    android.R.string.no, Toast.LENGTH_SHORT).show()

            }
            builder.show()
        }
    }
