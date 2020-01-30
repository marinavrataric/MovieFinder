package com.example.moviefinder.networking

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BackendFactory {

    private var retrofit: Retrofit? = null
    private val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    const val BASE_URL = "https://api.themoviedb.org/3/"

    private val httpClient =
            OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()

    private val  client: Retrofit? = if (retrofit == null) createRetrofit() else retrofit

    private fun createRetrofit(): Retrofit? {
        retrofit = Retrofit.Builder()
            .client(httpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit
    }

    private fun getService(): tmdbApiService {
        return client!!.create(tmdbApiService::class.java)
    }

    fun getInteractor(): Interactor {
        return InteractorImpl(getService())
    }





    val showSearchService: tmdbApiService = Retrofit.Builder()
        .addConverterFactory(ConverterFactory.converterFactory)
        .client(HttpClient.client)
        .baseUrl(BASE_URL)
        .build()
        .create(tmdbApiService::class.java)
}

object ConverterFactory{
    val converterFactory = GsonConverterFactory.create()
}
object HttpClient{
    val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()
}