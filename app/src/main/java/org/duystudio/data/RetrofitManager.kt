package org.duystudio.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.ArrayList
import java.util.concurrent.TimeUnit

class RetrofitManager {
    var apiInterface: ApiInterface? = null
        get() {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val builder: OkHttpClient.Builder = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
            val client: OkHttpClient = builder.build()
            if (field == null) {
                val retrofit: Retrofit = Retrofit.Builder()
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .baseUrl("http://api.airvisual.com/v2/")
//                    .baseUrl("https://api.jsonbin.io/")
                    .client(client)
                    .build()
                field = retrofit.create<ApiInterface>(ApiInterface::class.java)
            }
            return field
        }

    companion object {
        private fun genKey(): Map<String, String> {
            val hashMap = HashMap<String, String>()
            hashMap["key"] = "155b4444-dcd9-4e2d-99c2-214910c28c06"
            return hashMap
        }

        fun genParamCountry(): Map<String, String> {
            return genKey()
        }

        fun genParamState(country: Country): Map<String, String> {
            val hash = genKey().toMutableMap()
            hash["country"] = country.country
            return hash
        }

        fun genParamCity(country: Country, state: State): Map<String, String> {
            val hash = genKey().toMutableMap()
            hash["country"] = country.country
            hash["state"] = state.state
            return hash
        }

        fun genStation(country: Country, state: State, city: City): Map<String, String> {
            val hash = genKey().toMutableMap()
            hash["country"] = country.country
            hash["state"] = state.state
            hash["city"] = city.city
            return hash
        }

        var instance: RetrofitManager? = null
            get() {
                if (field == null) {
                    synchronized(RetrofitManager::class.java) {
                        if (null == field) {
                            field = RetrofitManager()
                        }
                    }
                }
                return field
            }
            private set
    }
}