package com.iua.agustinpereyra.repository.networking

import android.net.Uri
import androidx.core.net.toUri
import com.iua.agustinpereyra.repository.database.entities.Cattle
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


// Base URL for Books API.
private const val BASE_URL = "https://randomuser.me/api/?"
private const val RESULTS_PARAM = "results"
private const val RESULTS_VALUE = "500"
private const val FIELDS_INC_PARAM = "inc"
private const val RESULTS = "results"
private const val GENDER_FIELD = "gender"
private const val CELL_FIELD = "cell"
private const val PICTURE_FIELD = "picture"
private const val EMAIL_FIELD = "email"
private const val FIELDS_VALUE = "$GENDER_FIELD,$CELL_FIELD,$PICTURE_FIELD,$EMAIL_FIELD"


class ApiConnection {
    companion object {
        fun getRawRandomuserData() : String {
            lateinit var cattleListJsonString : String
            lateinit var urlConnection : HttpURLConnection
            try {
                val builtURI = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter(RESULTS_PARAM, RESULTS_VALUE)
                    .appendQueryParameter(FIELDS_INC_PARAM, FIELDS_VALUE)
                    .build()
                val requestURL = URL(builtURI.toString())
                urlConnection = requestURL.openConnection() as HttpURLConnection
                urlConnection.requestMethod = "GET"
                urlConnection.connect()

                // Read response
                cattleListJsonString = convertStreamToString(urlConnection.inputStream)
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                urlConnection.disconnect()
            }

            return cattleListJsonString
        }


        fun getCattleList() : List<Cattle> {
            val jsonString = getRawRandomuserData()
            // Pass to List<Cattle>
            val cattleList = mutableListOf<Cattle>()
            val jsonResponse = JSONObject(jsonString)
            val jsonArray = jsonResponse.getJSONArray(RESULTS)
            var jsonArrayItem : JSONObject
            for (i in 0..jsonArray.length()) {
                jsonArrayItem = jsonArray.getJSONObject(i)
                cattleList.add(
                    Cattle(
                        jsonArrayItem.getString(EMAIL_FIELD).slice(0..3) + (i % 5).toString(),
                        jsonArrayItem.getString(CELL_FIELD).slice(0..3).toInt(),
                        jsonArrayItem.getString(PICTURE_FIELD),
                        jsonArrayItem.getBoolean(GENDER_FIELD)
                    )
                )
            }
            return cattleList
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