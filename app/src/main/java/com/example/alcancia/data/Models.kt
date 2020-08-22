package com.example.alcancia.data

import com.google.firebase.database.Exclude

class User(userId: Int, name: String, lastName: String, mail: String, password: String, currency: String, goals : Goals){
    var userId: Int? = null
    var name : String = ""
    var lastName : String = ""
    var mail : String = ""
    var password : String = ""
    var currency : String = ""

    init {
        this.userId = userId
        this.name = name
        this.lastName = lastName
        this.mail = mail
        this.password = password
        this.currency = currency
    }
}

data class Goals(
    @get:Exclude
    var id: String? = null,
    var name: String = "",
    var total: Float = 0.0F,
    var current: Float = 0.0F,
    @get:Exclude
    var objectives: Objectives? = null,
    @get:Exclude
    var isDeleted: Boolean = false
)



class Objectives(objective: ArrayList<Objective>){
    var objective : ArrayList<Objective>? = null
    init {
        this.objective = objective
    }
}

data class Objective(
    @get:Exclude
    var id: String? = null,
    var goalId: String? = null,
    var name: String = "",
    var total: Float = 0.0F,
    var current: Float = 0.0F
)