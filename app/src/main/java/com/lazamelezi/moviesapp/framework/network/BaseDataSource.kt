package com.lazamelezi.moviesapp.framework.network

import android.util.Log
import com.lazamelezi.moviesapp.domain.GeneralErrorResponse
import com.lazamelezi.moviesapp.domain.InvalidExceptionGeneral
import com.google.gson.Gson
import retrofit2.Response
import timber.log.Timber

abstract class BaseDataSource {

    //for testing force error
    protected suspend fun <T> getResult(call: suspend () -> Response<T>, forceError: Boolean = false): T {
        try {
            Timber.e("remoteDataSource")
            if(forceError){
                throw Exception("force error for testing purpose only -- BaseDataSource.kt")
            }
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return body
            }

            if(response.code() in 400..499){
                val errorResponse = Gson().fromJson(response.errorBody()?.string()?:"", GeneralErrorResponse::class.java)
                Log.e("BaseDataSource","Error 400 -- ${errorResponse.error.message}")
                throw InvalidExceptionGeneral(errorResponse.error.message?:"Error 400")
            }
            throw Exception(" not e ${response.code()} ${response.body()}")
        }catch (e:Throwable){
            error(e.message?:"")
        }
    }
}

