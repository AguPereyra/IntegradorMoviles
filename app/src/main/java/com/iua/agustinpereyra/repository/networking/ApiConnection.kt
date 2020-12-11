package com.iua.agustinpereyra.repository.networking

import android.net.Uri
import com.iua.agustinpereyra.controller.RANDOMUSER_API_MALE
import com.iua.agustinpereyra.repository.database.entities.Cattle
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


// Base URL for API.
private const val BASE_URL = "https://randomuser.me/api/?"
private const val RESULTS_PARAM = "results"
private const val RESULTS_VALUE = "50"
private const val FIELDS_INC_PARAM = "inc"
private const val SEED_PARAM = "seed"
private const val RESULTS = "results"
private const val GENDER_FIELD = "gender"
private const val CELL_FIELD = "cell"
private const val PICTURE_FIELD = "picture"
private const val EMAIL_FIELD = "email"
private const val IMG_LARGE = "large"
private const val FIELDS_VALUE = "$GENDER_FIELD,$CELL_FIELD,$PICTURE_FIELD,$EMAIL_FIELD"
private const val SEED_VALUE = "cattle_app"


class ApiConnection {
    companion object {
        private fun getRawRandomuserData() : String? {
            lateinit var urlConnection : HttpURLConnection
            try {
                val builtURI = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter(RESULTS_PARAM, RESULTS_VALUE)
                    .appendQueryParameter(FIELDS_INC_PARAM, FIELDS_VALUE)
                    .appendQueryParameter(SEED_PARAM, SEED_VALUE)
                    .build()
                val requestURL = URL(builtURI.toString())
                urlConnection = requestURL.openConnection() as HttpURLConnection
                urlConnection.requestMethod = "GET"
                urlConnection.connect()

                // Read response
                return convertStreamToString(urlConnection.inputStream)
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                urlConnection.disconnect()
            }
            return null
        }

        /**
         * getCattleList returns an empty list if no response was matched
         */
        fun getCattleList() : List<Cattle> {
            val jsonString = getRawRandomuserData()
            // Pass to List<Cattle>
            val cattleList = mutableListOf<Cattle>()
            if (jsonString != null) {
                val jsonResponse = JSONObject(jsonString)
                val jsonArray = jsonResponse.getJSONArray(RESULTS)
                var jsonArrayItem : JSONObject
                for (i in 0 until jsonArray.length()) {
                    //TODO: Refactor
                    jsonArrayItem = jsonArray.getJSONObject(i)
                    // Special treatement to get a value usable as a weight from a cell number
                    var cell = jsonArrayItem.getString(CELL_FIELD).trim()
                    cell = cell.replace(" ", "").replace("-", "")
                    val weight = cell.slice(cell.length-3 until cell.length).toInt()
                    // Get sex as boolean
                    val sex = jsonArrayItem.getString(GENDER_FIELD) == RANDOMUSER_API_MALE
                    // Get image URL
                    val image = jsonArrayItem.getJSONObject(PICTURE_FIELD).getString(IMG_LARGE)
                    // Special treatment to get a value usable as a caravan (must be unique) from API
                    var caravan = jsonArrayItem.getString(EMAIL_FIELD).slice(0..3).toUpperCase() + jsonArrayItem.getString(EMAIL_FIELD).slice(1..2).toUpperCase() + weight.toString()

                    // Add to DB
                    cattleList.add(
                        Cattle(
                            caravan,
                            weight,
                            image,
                            sex
                        )
                    )
                }
                return cattleList
            }
            return listOf<Cattle>()
        }

        private fun convertStreamToString(inputStream: InputStream) : String {
            val buffReader = BufferedReader(InputStreamReader(inputStream))

            val stringBuilder = StringBuilder()
            try {
                var line: String? = buffReader.readLine()
                while (line != null) {
                    stringBuilder.append(line)
                    line = buffReader.readLine()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                if (buffReader != null) {
                    try {
                        buffReader.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }

            return stringBuilder.toString()
        }
    }

}