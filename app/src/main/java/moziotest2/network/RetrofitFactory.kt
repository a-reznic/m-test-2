package moziotest2.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit.SECONDS

class RetrofitFactory(
    private val endPoint: String,
    private var logLevel: Level = Level.NONE
) {
    private var debug = true
    private var logInOneMessage = false
    private var timeout = 10L

    enum class Level(val value: Int) {
        NONE(0),
        BASIC(1),
        HEADERS(2),
        BODY(3)
    }

    fun logInOneMessage(value: Boolean): RetrofitFactory {
        logInOneMessage = value
        return this
    }

    fun debug(value: Boolean): RetrofitFactory {
        debug = value
        return this
    }

    fun logLevel(value: Level): RetrofitFactory {
        logLevel = value
        return this
    }

    fun <T> createRetrofit(cl: Class<T>): T {
        val okHttpClient = OkHttpClient().newBuilder()
            .connectTimeout(timeout, SECONDS)
            .readTimeout(timeout, SECONDS)
            .writeTimeout(timeout, SECONDS)


        val client = okHttpClient.addNetworkInterceptor(HttpLoggingInterceptor { log ->
            Timber.tag("HTTP").d(log)
        })

        val retrofit = Retrofit.Builder()
            .baseUrl(endPoint)
            .client(client.build())
            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(ResultAdapterFactory())
            .build()

        return retrofit.create(cl)
    }

}