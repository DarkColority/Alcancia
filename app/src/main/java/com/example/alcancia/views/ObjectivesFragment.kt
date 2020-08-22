package com.example.alcancia.views

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast

import com.example.alcancia.R
import com.example.alcancia.data.Goals
import com.example.alcancia.data.Objective
import com.example.alcancia.views.GoalsActivity.Companion.fruit
import kotlinx.android.synthetic.main.fragment_objectives.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class ObjectivesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var viewModel : GoalsViewModel? = null
    val fruits: ArrayList<Objective> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_objectives, container, false)
    }
    companion object {
        fun newInstance(param1: String, param2: String) =
            ObjectivesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = CustomAdapter(requireContext(), fruits)

        addObjsList.adapter = adapter

        addObjsList.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                Toast.makeText(context, fruit!!.get(position).name, Toast.LENGTH_SHORT)
                    .show()

            }
        btnAddObjective.setOnClickListener{
            val name = inputObjName.text.toString()
            val amount = inputObjAmount.text.toString()
            val current: Double = 0.0

            if(name.isEmpty()){
                inputObjName.error = "This field is required"
            } else if(amount <= "0" || amount.isEmpty()){
                inputObjAmount.error = "This field is required"
            }

            fruits.add(Objective("", GoalsActivity.goalId, name, amount.toFloat() ))
            inputObjAmount.setText("")
            inputObjName.setText("")
            fruit = fruits
        }

    }

    fun saveObjectives(){
        Thread(Runnable {
            viewModel = GoalsViewModel()
            viewModel!!.saveObjectives(fruit!!)
        }).start()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }


}




