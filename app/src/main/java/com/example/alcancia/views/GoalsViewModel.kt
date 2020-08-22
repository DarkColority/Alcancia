package com.example.alcancia.views

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.alcancia.data.Goals
import com.example.alcancia.data.Objective
import com.example.alcancia.data.Repo

class GoalsViewModel : ViewModel() {

    val repo = Repo()
    fun fetchGoalData(): LiveData<MutableList<Goals>>{
        val mutableData = MutableLiveData<MutableList<Goals>>()
        repo.getGoalData().observeForever{
            mutableData.value = it
        }

        return mutableData
    }

    fun saveGoalData(name: String, amount: Double, current: Double){

        repo.saveData(name, amount, current)
    }

    fun saveObjectives(objs: ArrayList<Objective>){
        repo.saveObjectives(objs)
    }

    fun updateGoals(goal: ArrayList<Goals>) {
        repo.updateGoal(goal)
    }


}
