package com.jogocarreira.futebol.model
data class Club(val name:String,val country:String,val league:String,val strength:Int)
data class Competition(val name:String,val country:String,val continental:Boolean)
data class Player(val name:String,val nationality:String,val position:String,val club:String,val age:Int,val marketValue:Long,val overall:Int,val goals:Int,val assists:Int,val trophies:Int)
