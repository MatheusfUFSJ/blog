package br.com.in8.coins.servives.restapi

import br.com.sigga.api.BuildConfig
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.core.KoinComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.DateFormat
import java.util.concurrent.TimeUnit

object NetworkModule : KoinComponent {

    private const val DEFAULT_TIMEOUT_SECONDS = 30L

    fun <T> createService(service: Class<T>): T =
            makeRetrofit(BuildConfig.API).create(service)


    private fun makeRetrofit(url: String, timeout: Long = DEFAULT_TIMEOUT_SECONDS,
                             vararg interceptors: Interceptor): Retrofit =
            Retrofit.Builder()
                    .baseUrl(url)
                    .client(makeHttpClient(interceptors, timeout))
                    .addConverterFactory(GsonConverterFactory.create(makeConverter()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()

    private fun makeHttpClient(interceptors: Array<out Interceptor>, timeout: Long): OkHttpClient =
            OkHttpClient.Builder()
                    .connectTimeout(timeout, TimeUnit.SECONDS)
                    .readTimeout(timeout, TimeUnit.SECONDS)
                    .addInterceptor(headersInterceptor())
                    .apply { interceptors().addAll(interceptors) }
                    .build()

    private fun makeConverter(): Gson =
            GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .setPrettyPrinting()
                    .setDateFormat(DateFormat.FULL)
                    .create()

    private fun headersInterceptor(): Interceptor =
            Interceptor { chain ->
                chain.proceed(chain.request().newBuilder()
                        .addHeader("Accept", "application/json")
                        .addHeader("Accept-Language", "pt-br")
                        .addHeader("Content-Type", "application/json")
                        .build())
            }

}
