package com.boy_stone.bstonethree

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_login_.*
import kotlinx.android.synthetic.main.fragment_login_.view.*
import kotlinx.android.synthetic.main.fragment_register_.*
import kotlinx.android.synthetic.main.navigationview_head.*
import org.jetbrains.anko.support.v4.toast
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class Login_Fragment : Fragment() {
    var sharedPreferences: SharedPreferences? = null
    var result = ""
    val handler: Handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            Log.d("result", result)
            when (true) {
                msg.what == 1 -> {
                    if ("ok" == result) { //如果服务器返回值为“ok”，证明用户名、密码输入正确
                        //跳转登录后界面
//                        toast("登录成功")
                        sharedPreferences = requireActivity().getSharedPreferences(
                            "login_user",
                            AppCompatActivity.MODE_PRIVATE
                        )
                        val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
                        editor.putString("user", login_userinput.text.toString())
                        editor.apply()
                        editor.commit()
                        requireActivity().finish()
                        val intent = Intent()
                        intent.setClass(requireContext(), MainActivity().javaClass)
                        startActivity(intent);
                    } else {
                        toast("账号或密码错误")
                    }
                }
                msg.what == 2 -> {
                    toast("请检查网络")
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
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_, container, false)
    }

    @SuppressLint("UseRequireInsteadOfGet", "CommitPrefEdits")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val s = sharedPreferences?.getString("user", "")
        if (s !=null) {
            requireActivity().finish()
            val intent = Intent()
            intent.setClass(requireContext(), MainActivity().javaClass)
            startActivity(intent);
        }

        view!!.login_registerbutton.setOnClickListener(View.OnClickListener {
            val findNavController = Navigation.findNavController(it)
            findNavController.navigate(R.id.action_login_Fragment_to_register_Fragment)
        })
        login_loginbutton.setOnClickListener(View.OnClickListener {
            when (true) {
                login_userinput.text.toString().equals("") -> {
                    toast("请输入用户名或手机号码")
                }
                login_passwordinput.text.toString().equals("") -> {
                    toast("请输入密码")
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
            val url: URL = URL("http://192.168.31.147:8080/BStone_war_exploded/login.jsp")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.connectTimeout = 2000
            connection.readTimeout = 2000
            connection.doInput = true
            connection.doOutput = true
            connection.useCaches = false
            connection.instanceFollowRedirects = true
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
            val out = DataOutputStream(connection.outputStream)
            val param = ("user="
                    + URLEncoder.encode(login_userinput.text.toString(), "utf-8")
                    + "&password="
                    + URLEncoder.encode(login_passwordinput.text.toString(), "utf-8")
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
            handler.sendEmptyMessage(2)
        }
    }
}