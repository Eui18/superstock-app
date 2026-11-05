package com.superstock.app.data.datasource.remote

import com.superstock.app.data.dto.ErrorResponseDto
import kotlinx.coroutines.CancellationException
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

val apiJson = Json {
    ignoreUnknownKeys = true
}

sealed class ApiException(message: String) : Exception(message) {
    class NoConnection : ApiException("No hay conexión con el servidor")
    class Timeout : ApiException("El servidor tardó demasiado en responder")
    class Http(val code: Int, message: String) : ApiException(message)
    class Serialization : ApiException("La respuesta del servidor no tiene el formato esperado")
    class Unknown : ApiException("Ocurrió un error inesperado")
}

suspend fun <T> safeApiCall(call: suspend () -> T): Result<T> {
    return try {
        Result.success(call())
    } catch (e: CancellationException) {
        throw e
    } catch (e: HttpException) {
        Result.failure(ApiException.Http(e.code(), readErrorMessage(e)))
    } catch (e: SocketTimeoutException) {
        Result.failure(ApiException.Timeout())
    } catch (e: IOException) {
        Result.failure(ApiException.NoConnection())
    } catch (e: SerializationException) {
        Result.failure(ApiException.Serialization())
    } catch (e: Exception) {
        Result.failure(ApiException.Unknown())
    }
}

private fun readErrorMessage(exception: HttpException): String {
    val body = exception.response()?.errorBody()?.string()
    val message = body?.let {
        runCatching { apiJson.decodeFromString(ErrorResponseDto.serializer(), it).message }.getOrNull()
    }
    return message ?: ("Error del servidor (" + exception.code() + ")")
}