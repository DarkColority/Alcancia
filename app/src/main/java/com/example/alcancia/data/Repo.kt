package com.example.alcancia.data

import android.content.ContentValues.TAG
import android.util.Log
import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.alcancia.views.GoalsActivity
import com.example.alcancia.views.GoalsViewModel
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import java.util.ArrayList

class Repo {
    val db = FirebaseFirestore.getInstance()

    fun getGoalData(): LiveData<MutableList<Goals>>{
        val mutableData = MutableLiveData<MutableList<Goals>>()
        db.collection("Goals").orderBy("name").get().addOnSuccessListener {result ->

            val listData = mutableListOf<Goals>()
            for(document in result){
                val id = document.id
                val name = document.getString("name")
                val amount = document.getDouble("amount")?.toFloat()
                val current = document.getDouble("current")?.toFloat()
                val goal = Goals(id, name!!, amount!!, current!!)
                listData.add(goal)
            }

            mutableData.value = listData
        }
        return mutableData
    }
    fun getGoalById(id: String): LiveData<MutableList<Goals>>{

        val mutableData = MutableLiveData<MutableList<Goals>>()
        db.collection("Goals").document(id).get().addOnSuccessListener {result ->

            val listData = mutableListOf<Goals>()
                val name = result.getString("name")
                val amount = result.getDouble("amount")?.toFloat()
                val current = result.getDouble("current")?.toFloat()
                val goal = Goals(id, name!!, amount!!, current!!)
                listData.add(goal)


            mutableData.value = listData
        }
        return mutableData
    }

    fun saveData(name: String, amount: Double, current: Double): String{
        var id = ""
        val dispatch = HashMap<String, Any>()


        dispatch.put("name", name)
        dispatch.put("amount", amount)
        dispatch.put("current", current)


        db.collection("Goals")
            .add(dispatch)
            .addOnSuccessListener(object: OnSuccessListener<DocumentReference> {
                override fun onSuccess(documentReference:DocumentReference) {
                    id = documentReference.id
                    GoalsActivity.goalId = documentReference.id
                }
            })
            .addOnFailureListener(object: OnFailureListener {
                override fun onFailure(@NonNull e:Exception) {
                    Log.w("error", "Error adding document", e)
                }
            })
        return id
    }


    fun updateGoal(goal: ArrayList<Goals>){


        val goals = db.collection("Goals")

        val data1 = hashMapOf(
            "name" to goal[0].name,
            "amount" to goal[0].total,
            "current" to goal[0].current
        )
        goals.document(goal[0].id!!).set(data1)



        /*val dispatch = HashMap<String, Any>()

        val id =  goal[0].id!!
        dispatch["name"] = goal[0].name
        dispatch["amount"] = goal[0].total
        dispatch["current"] = goal[0].current
        dispatch["objectives"] = goal[0].objectives

        db.collection("Goals").document(id)
            .set(dispatch)*/
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully updated!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
        // [END set_document]
    }

    fun deleteGoal(goalId: String){
        db.collection("Goals").document(goalId)
            .delete()
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully deleted!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
    }
}