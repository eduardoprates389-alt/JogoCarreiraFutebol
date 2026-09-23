package com.jogocarreira.futebol.data

import android.content.Context
import com.jogocarreira.futebol.model.*
import org.json.JSONArray

class DataRepository(context: Context) {

    val clubs: List<Club>
    val competitions: List<Competition>

    init {
        clubs = try {
            val json = context.assets
                .open("clubs.json")
                .bufferedReader()
                .use { it.readText() }

            val array = JSONArray(json)

            List(array.length()) { i ->
                val obj = array.getJSONObject(i)

                Club(
                    name = obj.optString("name", "Clube"),
                    country = obj.optString("country", "Brasil"),
                    league = obj.optString("league", "Liga"),
                    strength = obj.optInt("strength", 50)
                )
            }
        } catch (e: Exception) {
            emptyList()
        }

        competitions = try {
            val json = context.assets
                .open("competitions.json")
                .bufferedReader()
                .use { it.readText() }

            val array = JSONArray(json)

            List(array.length()) { i ->
                val obj = array.getJSONObject(i)

                Competition(
                    name = obj.optString("name", "Competição"),
                    country = obj.optString("country", "Brasil"),
                    continental = obj.optBoolean("continental", false)
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
