package com.example.mylibrarydemo

import com.google.gson.Gson
import com.neovisionaries.ws.client.WebSocketFactory
import jp.co.soramitsu.fearless_utils.wsrpc.SocketService
import jp.co.soramitsu.fearless_utils.wsrpc.executeAsync
import jp.co.soramitsu.fearless_utils.wsrpc.interceptor.WebSocketResponseInterceptor
import jp.co.soramitsu.fearless_utils.wsrpc.recovery.Reconnector
import jp.co.soramitsu.fearless_utils.wsrpc.request.RequestExecutor
import jp.co.soramitsu.fearless_utils.wsrpc.request.runtime.RuntimeRequest
import jp.co.soramitsu.fearless_utils.wsrpc.response.RpcResponse

class DemoClass(private val name : String) {
    fun getName() : String {
        return name
    }
   private var socketService: SocketService? = null
    init {
        val reconnector = Reconnector()
        val requestExecutor = RequestExecutor()
        socketService = SocketService(
            Gson(),
            LoggerImpl(),
            WebSocketFactory(),
            reconnector,
            requestExecutor
        )
        socketService?.setInterceptor(object : WebSocketResponseInterceptor {
            override fun onRpcResponseReceived(rpcResponse: RpcResponse): WebSocketResponseInterceptor.ResponseDelivery {
                return WebSocketResponseInterceptor.ResponseDelivery.DELIVER_TO_SENDER
            }
        })
        socketService?.start("wss://wsspc1-qa.agung.peaq.network/")
    }
    suspend fun getRuntimeVersion() : String {

        val resultRuntimeVersion = socketService?.executeAsync(
            RuntimeRequest(
                method = "chain_getRuntimeVersion",
                params = listOf()
            )
        )
        return resultRuntimeVersion?.result.toString()
    }

}