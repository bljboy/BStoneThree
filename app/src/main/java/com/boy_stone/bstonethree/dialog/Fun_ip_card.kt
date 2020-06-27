package com.boy_stone.bstonethree.dialog

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.boy_stone.bstonethree.R
import kotlinx.android.synthetic.main.activity_fun_ip_card.*
import org.jetbrains.anko.toast
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class Fun_ip_card : AppCompatActivity() {
    var result = ""
    @SuppressLint("HandlerLeak")
    private var handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            Log.d("result", result)
            if (msg.what == 1) {
                //Log.i("result>>>>>>>>>",result);
                try {
                    val json = JSONObject(result)
                    if (json.getInt("status") != 0) {
                        println(json.getString("msg"))
                    } else {
                        val resultarr = json.optJSONObject("result")
                        val area = resultarr.getString("area")
                        val type = resultarr.getString("type")
                        fun_ip_dialog_textview.text = area+"  "+type
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            } else if (msg.what == 2) {
                toast("服务器访问失败")
            }
            super.handleMessage(msg)
            result = ""
        }
    }.also { handler = it }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fun_ip_card)
        fun_ip_dialog_button.setOnClickListener(View.OnClickListener {
            Thread(Runnable {
                send(fun_ip_dialog_edittext.text.toString())
            }).start()
        })
    }
    fun send(ip:String) {
        val target =
            "https://api.jisuapi.com/ip/location?appkey=28484dd6dafeebd7&ip=${ip}" //要提交的服务器地址
        val url: URL
        try {
            url = URL(target) //创建URL对象
            val urlConn =
                url.openConnection() as HttpURLConnection // 创建一个HTTP连接
            urlConn.connectTimeout = 5000
            if (urlConn.responseCode == HttpURLConnection.HTTP_OK) {  //判断是否响应成功
                val `in` = InputStreamReader(
                    urlConn.inputStream
                ) // 获得读取的内容
                val buffer = BufferedReader(`in`) // 获取输入流对象
                var inputLine: String? = null
                while (buffer.readLine().also { inputLine = it } != null) {  //通过循环逐行读取输入流中的内容
                    result += inputLine
                }
                `in`.close() //关闭字符输入流
                handler.sendEmptyMessage(1)
            } else {
                handler.sendEmptyMessage(2)
            }
            urlConn.disconnect() //断开连接
        } catch (e: IOException) {
            e.printStackTrace()
            handler.sendEmptyMessage(2)
        }
    }
}