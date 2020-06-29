package com.boy_stone.bstonethree

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_register_.*
import org.jetbrains.anko.support.v4.toast
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class Register_Fragment : Fragment() {
    var result: String = ""

    @SuppressLint("HandlerLeak")
    private var handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            Log.d("result", result)
            when (true) {
                msg.what == 1 -> {
                    if ("ok".equals(result)) { //如果服务器返回值为“ok”，证明用户名、密码输入正确
                        //跳转登录后界面
                        toast("注册成功")
                        requireActivity().finish()
                        val intent = Intent()
                        intent.setClass(requireContext(),Login_Activity().javaClass)
                        startActivity(intent);
                    } else {
                        toast("账号或电话号码已被注册")
                    }
                }
                msg.what == 2 -> {
                    toast("服务器异常")
                }
                msg.what == 3 -> {
                    toast("请检查网络问题")
                }
                else -> {
                }
            }
            super.handleMessage(msg)
            result = ""
        }
    }.also { handler = it }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register_, container, false)
    }


    @SuppressLint("HandlerLeak")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        register_toolbar_title.setOnClickListener(View.OnClickListener {
            val controller = Navigation.findNavController(requireActivity(), R.id.login_fragment)
            controller.navigateUp()
        })
        register_button.setOnClickListener(View.OnClickListener {
            when(true) {
                register_userinput.text.toString().equals("") -> {
                    toast("请输入用户名")
                }
                register_iphoneinput.text.toString().equals("") -> {
                    toast("请输入电话号码")
                }
                register_capinput.text.toString().equals("") -> {
                    toast("请输入验证码")
                }
                register_passwordinput.text.toString().equals("") -> {
                    toast("请输入密码")
                }
                !register_passwordinputagain.text.toString()
                    .equals(register_passwordinput.text.toString()) -> {
                    toast("两次密码不一致")
                }
                else -> {
                    Thread(Runnable
                    // 创建一个新线程，用于从网络上获取文件
                    {
                        send() //调用send()方法，用于发送用户名、密码到Web服务器
                        val m = handler.obtainMessage() // 获取一个Message
                        handler.sendMessage(m) // 发送消息
                    }).start()

                }
            }
        })

    }

    fun send() {
        try {
            val url: URL = URL("http://120.77.249.243/BStone/register.jsp")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.doInput = true
            connection.doOutput = true
            connection.useCaches = false
            connection.instanceFollowRedirects = true
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
            val out = DataOutputStream(connection.outputStream)
            val param = ("user="
                    + register_userinput.text.toString()
                    + "&iphone_number="
                    + register_iphoneinput.text.toString()
                    + "&password="
                    + register_passwordinput.text.toString()
            )
            out.writeBytes(param)
            out.flush()
            out.close()
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {  //判断是否响应成功
                val isn = InputStreamReader(
                    connection.getInputStream()
                ) // 获得读取的内容
                val buffer = BufferedReader(isn) // 获取输入流对象
                var inputLine: String? = null
                while (buffer.readLine().also { inputLine = it } != null) {  //通过循环逐行读取输入流中的内容
                    result += inputLine
                }
                isn.close() //关闭字符输入流
                handler.sendEmptyMessage(1)

            } else {
                Log.d("服务器", "连接失败")
                handler.sendEmptyMessage(2)
            }
            connection.disconnect()
        } catch (e: Exception) {
            handler.sendEmptyMessage(3)
        }

    }

    fun onSupportNavigateUp(): Boolean {
        val controller = Navigation.findNavController(requireActivity(), R.id.login_fragment)
        return controller.navigateUp()
    }


}