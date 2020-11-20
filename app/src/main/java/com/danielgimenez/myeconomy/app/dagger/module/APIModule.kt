package com.danielgimenez.myeconomy.app.dagger.module

import android.os.Build
import com.danielgimenez.myeconomy.BuildConfig
import com.danielgimenez.myeconomy.Forbidden403ApiException
import com.danielgimenez.myeconomy.Response
import com.danielgimenez.myeconomy.Unauthorized401ApiException
import com.danielgimenez.myeconomy.data.source.network.MyEconomyApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Lazy
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

@Module
class APIModule{

    @Provides
    fun provideMyEconomyApi(client: Lazy<OkHttpClient>): MyEconomyApi{
        val retrofit = Retrofit.Builder().apply {
            baseUrl(BuildConfig.API_URL)
            client(client.get())
            addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            addCallAdapterFactory(CoroutineCallAdapterFactory())
        }.build()

        return retrofit.create(MyEconomyApi::class.java)


    }

    @Provides
    fun provideClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val userAgent = "TecTrack/${BuildConfig.VERSION_NAME} (${Build.BRAND} ${Build.MODEL}; Android/${Build.VERSION.RELEASE})"

        return OkHttpClient.Builder().readTimeout(30000, TimeUnit.MILLISECONDS).connectTimeout(30000, TimeUnit.MILLISECONDS)
            .addInterceptor(interceptor)
            .addInterceptor(object : Interceptor {
                @Throws(IOException::class)
                override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
                    val request = chain.request()
                    val requestWithVersion = request.newBuilder().header("User-Agent", userAgent).build()
                    val response = chain.proceed(requestWithVersion)

                    if (response.code == 401) {
                        var message = ""
                        if (response.body != null) {
                            val gson = Gson()
                            val error = gson.fromJson(response.body!!.string(), Response.Error::class.java)

                            if (!error.errors.isNullOrEmpty()) {
                                message = error.errors[0].message
                            }
                        }
                        throw Unauthorized401ApiException(message)
                    }

                    if (response.code == 403) {
                        var message = ""
                        if (response.body != null) {
                            val gson = Gson()
                            val error = gson.fromJson(response.body!!.string(), Response.Error::class.java)

                            if (!error.errors.isNullOrEmpty()) {
                                message = error.errors[0].message
                            }
                        }
                        throw Forbidden403ApiException(message)
                    }

                    return response
                }
            })
            .build()
    }


}