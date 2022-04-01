package com.lsroar.data.di

import android.content.Context
import com.lsroar.data.R
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.IOException
import java.io.ByteArrayOutputStream
import java.io.InputStream

class MockInterceptor(private val context: Context) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val uri = chain.request().url.toString()
        val jsonMock = mockUrl(uri) // It's var to change while debugging
        val responseCode = 200 // It's var to change while debugging
        return if (jsonMock != null) {
            mockResponse(chain, jsonMock, responseCode)
        } else {
            chain.proceed(chain.request())
        }
    }

    private fun mockResponse(chain: Interceptor.Chain, jsonMock: String, responseCode: Int = 200): Response {
        return Response.Builder()
            .code(responseCode)
            .message(jsonMock)
            .request(chain.request())
            .protocol(Protocol.HTTP_1_0)
            .body(jsonMock.toByteArray().toResponseBody("application/json".toMediaTypeOrNull()))
            .addHeader("content-type", "application/json")
            .build()
    }

    private fun mockUrl(uri: String?): String? {
        val resource = when (uri) {
            // "someurl" -> R.raw.some_raw
            else -> when {
                uri.isNullOrEmpty() -> -1
                uri.isBlank() -> -1
                uri.startsWith(
                    "https://gateway.marvel.com/v1/public/characters/"
                ) -> R.raw.character_detail
                // uri.contentEquals("http://someurl.com") -> R.raw.mock_fake
                else -> -1
            }
        }
        return readFromFile(resource)
    }
    private fun readFromFile(raw: Int): String? {
        return if (raw > 0) {
            readFully(context.resources.openRawResource(raw))
        } else {
            null
        }
    }

    private fun readFully(entityResponse: InputStream): String? {

        val baos = ByteArrayOutputStream()
        val buffer = ByteArray(1024)
        var length = 0
        try {
            while (length != -1) {
                baos.write(buffer, 0, length)
                length = entityResponse.read(buffer)
            }
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }

        return baos.toString()
    }
}
