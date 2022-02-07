package eu.tutorials.mailboxlayermailvalidation

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//Todo 4: Create the service interface
interface MailBoxService{
    /** Todo 5: We create a suspend function with Get annotation having the patch check within it.
     * In the Function parameter we add the 2 queries, one for access_key and the other for email.
     * The function returns Email Response which is the data class representing the Json response
     * resulting from the request
     * */
    @GET("check")
    suspend fun checkEmail(@Query("access_key") key:String,
                           @Query("email")email:String):EmailResponse

}

object Api {
    const val API_KEY = BuildConfig.API_KEY

    //Todo 7: create the base url
    private val BASE_URL = "http://apilayer.net/api/"

    //Todo 8: set up Moshi for Json to Kotlin converter
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    //Todo 9: create the logging interceptor and add to HttpClient
    private val logging = HttpLoggingInterceptor()
    private val httpClient = OkHttpClient.Builder().apply {
        logging.level = HttpLoggingInterceptor.Level.BODY
        addNetworkInterceptor(logging)
    }.build()

    //Todo 10: attach baseurl, moshi and httpclient to retrofit and build
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .client(httpClient)
        .build()

    //Todo 11: create the service class using the lazy delegate
    val service: MailBoxService by lazy { retrofit.create(MailBoxService::class.java) }
}