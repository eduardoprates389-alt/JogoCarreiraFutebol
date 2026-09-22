package com.jogocarreira.futebol.data
import android.content.Context
import com.jogocarreira.futebol.model.*
import org.json.JSONArray

class DataRepository(context: Context) {
    val clubs: List<Club>
    val competitions: List<Competition>
    init {
        val c=JSONArray(context.assets.open("clubs.json").bufferedReader().use{it.readText()})
        clubs=List(c.length()){i->val o=c.getJSONObject(i);Club(o.getString("name"),o.getString("country"),o.getString("league"),o.getInt("strength"))}
        val k=JSONArray(context.assets.open("competitions.json").bufferedReader().use{it.readText()})
        competitions=List(k.length()){i->val o=k.getJSONObject(i);Competition(o.getString("name"),o.getString("country"),o.getBoolean("continental"))}
    }
}
