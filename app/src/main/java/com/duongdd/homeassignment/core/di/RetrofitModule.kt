package com.duongdd.homeassignment.core.di

import com.duongdd.homeassignment.datasource.remoteDatasource.api.UserAPI
import com.duongdd.homeassignment.datasource.remoteDatasource.interceptors.TokenHeaderInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {
    @Provides
    fun provideUserAPI(retrofit: Retrofit): UserAPI{
        return retrofit.create(UserAPI::class.java)
    }

    @Provides
    fun providesGson(): Gson {
        return GsonBuilder()
            .create()
    }

    @Provides
    fun provideRestRetrofit(
        httpClient: OkHttpClient,
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl("https://api.github.com/")
            .build()
    }

    @Provides
    fun provideHttpRestClient(
        tokenHeaderInterceptor: TokenHeaderInterceptor
    ): OkHttpClient {
        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder.addInterceptor {chain ->
            val url = chain
                .request()
                .url
                .newBuilder()
                .build()
            chain.proceed(chain.request().newBuilder().url(url).build())
        }
        httpClientBuilder.addInterceptor(tokenHeaderInterceptor)
        httpClientBuilder.readTimeout(0, TimeUnit.SECONDS)
        httpClientBuilder.writeTimeout(0, TimeUnit.SECONDS)
        val loggerInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        httpClientBuilder.addInterceptor(loggerInterceptor)
        return httpClientBuilder.build()
    }
}