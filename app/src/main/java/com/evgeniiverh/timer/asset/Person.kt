package com.evgeniiverh.timer.asset

class Person {
    var id:Int=0
    var name: String?=null
    var date: String?=null
    var time: String?=null

    constructor(){}
    constructor(id:Int,name:String,date:String,time:String)
    {
        this.id=id
        this.name=name
        this.date=date
        this.time=time
    }

}