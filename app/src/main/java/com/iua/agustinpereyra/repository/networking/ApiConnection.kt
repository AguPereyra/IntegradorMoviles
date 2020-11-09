package com.iua.agustinpereyra.repository.networking

import android.net.Uri
import com.iua.agustinpereyra.repository.database.entities.Cattle
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


// Base URL for Books API.
private const val BASE_URL = "https://www.googleapis.com/books/v1/volumes?"
private const val RESULTS_PARAM = "results"
private const val RESULTS_VALUE = "500"
private const val FIELDS_INC_PARAM = "inc"
private const val FIELDS_VALUE = "gender,cell,picture,email"

class ApiConnection {
    companion object {
        fun getRawData() : String {
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

        /*
        fun getListCattle() : List<Cattle> {
            jsonString = getRawData()
            // Pass to List<Cattle>

        }*/

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