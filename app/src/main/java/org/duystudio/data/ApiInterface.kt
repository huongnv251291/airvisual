package org.duystudio.data

import org.duystudio.entity.Data
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiInterface {

    @GET("countries")
    suspend fun getCountries(@QueryMap params: Map<String, String>): String

    @GET("states")
    suspend fun getStateSupportInCountries(@QueryMap params: Map<String, String>): String

    @GET("cities")
    suspend fun getCitySupportInState(@QueryMap params: Map<String, String>): String
    @GET("stations")
    suspend fun getStationSupportInCity(@QueryMap params: Map<String, String>): String




}