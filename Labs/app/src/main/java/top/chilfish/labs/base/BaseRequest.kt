package top.chilfish.labs.base

import android.util.Log
import com.drake.net.exception.NetConnectException
import com.drake.net.exception.NetSocketTimeoutException
import com.drake.net.exception.RequestParamsException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.withContext
import top.chilfish.labs.module.IODispatcher
import top.chilfish.labs.utils.showToast
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BaseRequest @Inject constructor(

    @IODispatcher
    private val ioDispatchers: CoroutineDispatcher,
) {
    suspend fun <T> request(request: suspend CoroutineScope.() -> Deferred<T>): T? {
        var res: T? = null
        withContext(ioDispatchers) {
            res = try {
                request().await()
            } catch (e: RequestParamsException) {
                e.printStackTrace()
                throw e
            } catch (e: NetSocketTimeoutException) {
                e.printStackTrace()
                Log.e("api", "net error: ${e.cause}")
                showToast("Server error")
                null
            } catch (e: NetConnectException) {
                e.printStackTrace()
                Log.e("api", "net error: ${e.cause}")
                showToast("Network error")
                null
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
        return res
    }
}
