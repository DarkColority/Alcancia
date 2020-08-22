package com.example.alcancia.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.alcancia.R
import kotlinx.android.synthetic.main.activity_goals.*
import kotlinx.android.synthetic.main.fragment_new_goal.*


private const val NAME = "name"
private const val AMOUNT = "amount"

class NewGoalFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private var name: String? = null
    private var total: String? = null
    private val viewModel = GoalsViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString("")
            param2 = it.getString("")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_new_goal, container, false)
    }

    companion object {

        fun newInstance(param1: String, param2: String) =
            NewGoalFragment().apply {
                arguments = Bundle().apply {
                    putString(NAME, name)
                    putString(AMOUNT, total)
                }
            }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }


}
