package org.d3if3156.staroom.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.d3if3156.staroom.model.Sky
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://unspoken.my.id/"

//private val retrofit = Retrofit.Builder()
//    .addConverterFactory(ScalarsConverterFactory.create())
//    .baseUrl(BASE_URL)
//    .build()
interface StarApiService {
    @GET("api_andrepp.php")
    suspend fun getStar(): List<Sky>
}
object StarApi {
//    val service: StarApiService by lazy {
//        retrofit.create(StarApiService::class.java)
//    }
    fun getStarUrl(imageId: String): String {
        return "${BASE_URL}api_andrepp.php?id=$imageId"
    }

}
enum class ApiStatus { LOADING, SUCCESS, FAILED}