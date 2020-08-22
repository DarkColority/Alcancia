package com.example.alcancia.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.alcancia.data.Goals
import com.example.alcancia.data.NODE_GOALS
import com.google.firebase.database.*
import java.lang.Exception

class GoalsViewModel : ViewModel() {

    private val dbGoals = FirebaseDatabase.getInstance().getReference(NODE_GOALS)

    private val _goals = MutableLiveData<List<Goals>>()
    val goals: LiveData<List<Goals>>
        get() = _goals

    private val _author = MutableLiveData<Goals>()
    val goal: LiveData<Goals>
        get() = _author

    private val _result = MutableLiveData<Exception?>()
    val result: LiveData<Exception?>
        get() = _result

    fun addGoal(author: Goals) {
        author.id = dbGoals.push().key
        dbGoals.child(author.id!!).setValue(author)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    _result.value = null
                } else {
                    _result.value = it.exception
                }
            }
    }

    private val childEventListener = object : ChildEventListener {
        override fun onCancelled(error: DatabaseError) {}

        override fun onChildMoved(snapshot: DataSnapshot, p1: String?) {}

        override fun onChildChanged(snapshot: DataSnapshot, p1: String?) {
            val goal = snapshot.getValue(Goals::class.java)
            goal?.id = snapshot.key
            _author.value = goal
        }

        override fun onChildAdded(snapshot: DataSnapshot, p1: String?) {
            val author = snapshot.getValue(Goals::class.java)
            author?.id = snapshot.key
            _author.value = author
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {
            val author = snapshot.getValue(Goals::class.java)
            author?.id = snapshot.key
            //author?.isDeleted = true
            _author.value = author
        }
    }

    fun getRealtimeUpdates() {
        dbGoals.addChildEventListener(childEventListener)
    }

    fun fetchGoals() {
        dbGoals.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val authors = mutableListOf<Goals>()
                    for (authorSnapshot in snapshot.children) {
                        val author = authorSnapshot.getValue(Goals::class.java)
                        author?.id = authorSnapshot.key
                        author?.let { authors.add(it) }
                    }
                    _goals.value = authors
                }
            }
        })
    }

    fun updateGoal(author: Goals) {
        dbGoals.child(author.id!!).setValue(author)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    _result.value = null
                } else {
                    _result.value = it.exception
                }
            }
    }

    fun deleteGoal(author: Goals) {
        dbGoals.child(author.id!!).setValue(null)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    _result.value = null
                } else {
                    _result.value = it.exception
                }
            }
    }

    override fun onCleared() {
        super.onCleared()
        dbGoals.removeEventListener(childEventListener)
    }
}