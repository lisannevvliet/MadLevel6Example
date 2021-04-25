package com.example.madlevel6example.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TriviaApi {
    companion object {
        // The base URL off the API.
        private const val baseUrl = "http://numbersapi.com/"

        /**
         * @return [TriviaApiService] The service class off the retrofit client.
         */
        fun createApi(): TriviaApiService {
            // Create an OkHttpClient to be able to make a log of the network traffic.
            val okHttpClient = OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).build()

            // Create the Retrofit instance.
            val triviaApi = Retrofit.Builder().baseUrl(baseUrl).client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build()

            // Return the Retrofit NumbersApiService.
            return triviaApi.create(TriviaApiService::class.java)
        }
    }
}