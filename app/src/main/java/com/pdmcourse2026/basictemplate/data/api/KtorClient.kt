package com.pdmcourse2026.basictemplate.data.api

import android.util.Log
import com.pdmcourse2026.basictemplate.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object KtorClient {


  private const val API_KEY = BuildConfig.API_TOKEN

  val client = HttpClient(OkHttp) {
    // Parseo automático de JSON
    install(ContentNegotiation) {
      json(Json {
        ignoreUnknownKeys = true
      })
    }

    // Plugin de logging
    install(Logging) {
      logger = object : Logger {
        override fun log(message: String) {
          Log.d("KtorClient", message)
        }
      }
      level = LogLevel.ALL
    }

    // Configuración aplicada a todas las peticiones
    defaultRequest {
      url()
      header(HttpHeaders.Accept, "application/json")
      header(HttpHeaders.Authorization, "Bearer $API_KEY")
    }
  }
}