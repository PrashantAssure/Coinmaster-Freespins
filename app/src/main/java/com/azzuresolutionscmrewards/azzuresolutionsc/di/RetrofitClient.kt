package com.azzuresolutionscmrewards.azzuresolutionsc.di

import com.azzuresolutionscmrewards.azzuresolutionsc.retrofit.APIServices
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

const val BASE_URL = "http://www.fortuneinfoway.in/"

val networkModule = module {
    factory { getInterceptor() }
    factory { provideOkHttpClient(get(), get()) }
    factory { provideLoggingInterceptor() }
    factory { provideRetrofit(get()) }
    factory { provideApi(get()) }
}

private fun getInterceptor(): Interceptor {
    return Interceptor { chain ->
        val original = chain.request()
        val builder = original.newBuilder()
        val request = builder
            .header("Content-Type", "application/json")
            .method(original.method, original.body)
            .build()
        chain.proceed(request)
    }
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient)
        .addConverterFactory(ScalarsConverterFactory.create()).build()
}

fun provideOkHttpClient(
    interceptor: Interceptor,
    loggingInterceptor: HttpLoggingInterceptor
): OkHttpClient {
    return OkHttpClient().newBuilder().addInterceptor(interceptor)
        .addInterceptor(loggingInterceptor).build()
}

fun provideLoggingInterceptor(): HttpLoggingInterceptor {
    val logger = HttpLoggingInterceptor()
    logger.level = HttpLoggingInterceptor.Level.BODY
    return logger
}

fun provideApi(retrofit: Retrofit): APIServices = retrofit.create(APIServices::class.java)
