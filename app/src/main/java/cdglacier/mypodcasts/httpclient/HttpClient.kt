package cdglacier.mypodcasts.httpclient

import okhttp3.OkHttpClient

class HttpClient {
    @Volatile
    private var INSTANCE: OkHttpClient? = null

    init {
        synchronized(this) {
            var instance = INSTANCE

            if (instance == null) {
                instance = OkHttpClient()

                INSTANCE = instance
            }
        }
    }
   
    fun getInstance(): OkHttpClient = INSTANCE!!
}