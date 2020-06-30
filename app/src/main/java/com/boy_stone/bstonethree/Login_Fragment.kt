package com.boy_stone.bstonethree

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.tencent.connect.UserInfo
import com.tencent.connect.common.Constants
import com.tencent.tauth.IUiListener
import com.tencent.tauth.Tencent
import com.tencent.tauth.UiError
import kotlinx.android.synthetic.main.fragment_login_.*
import kotlinx.android.synthetic.main.fragment_login_.view.*
import org.jetbrains.anko.support.v4.toast
import org.json.JSONObject
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class Login_Fragment : Fragment() {
    //
    var mUserInfo: UserInfo? = null
    var mIuiListener: BaseUiListener? = null
    val APP_ID = "101879204"
    var mTencent: Tencent? = null

    var ip = "http://192.168.1.103:8080/BStone/login.jsp"
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
                        val sharedPreferences: SharedPreferences
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
                    toast("网络错误或服务器失去连接")
                }
                msg.what == 3 -> {


                }
                else -> {
                }
            }
            super.handleMessage(msg)
            result = ""
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.i("sokeokfoeofef>>>>>>>>>", "sssssssssssss")
        if (requestCode == Constants.REQUEST_LOGIN) {
            //QQ登录回调
            Tencent.onActivityResultData(requestCode, resultCode, data, mIuiListener)
            Log.i("qqqqqqqqq", "swwwwwwwwwwwwwwwwwwwwwwww")
        }


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
        mTencent = Tencent.createInstance(APP_ID, context)
        //QQ登录
        login_qq_button.setOnClickListener(View.OnClickListener {

            // QQ登录
            //if (!mTencent.isSessionValid()) {
            mIuiListener = BaseUiListener()
            mTencent!!.login(requireActivity(), "all", mIuiListener)
        })

        //存储登录用户名的数据，存在就直接登录
        val sharedPreferences = requireActivity().getSharedPreferences(
            "login_user",
            AppCompatActivity.MODE_PRIVATE
        )
        val s = sharedPreferences.getString("user", "")
        if (!s.equals("")) {
            requireActivity().finish()
            val intent = Intent()
            intent.setClass(requireContext(), MainActivity().javaClass)
            startActivity(intent);
        }
        //注册按钮跳转
        view!!.login_registerbutton.setOnClickListener(View.OnClickListener {
            val findNavController = Navigation.findNavController(it)
            findNavController.navigate(R.id.action_login_Fragment_to_register_Fragment)
        })

        //登录按钮
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

    //    @SuppressLint("HandlerLeak")
    //    private Handler handler = new Handler() {
    //        @Override
    //        public void handleMessage(@NonNull Message msg) {
    //            super.handleMessage(msg);
    //            if (msg.what == 1) {//执行的登录操作
    //                login_result = login_result.trim();
    //                Log.i("POST返回结果", login_result);
    //                try {
    //                    JsonParser parser = new JsonParser();
    //                    JsonObject object = (JsonObject) parser.parse(login_result);
    //                    String rescode = object.get("rescode").getAsString();
    //                    String phone = object.get("phone").getAsString();
    //                    String username = object.get("username").getAsString();
    //                    Log.i("code返回码>>>>>>>>>>>", rescode);
    //                    if (rescode.equals("1")) {
    //                        //用户信息输入正确，跳转到主页面
    //                        SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
    //                        SharedPreferences.Editor editor = sp.edit();
    //                        editor.putString("type", "1");// 1是常规登录
    //                        editor.putString("phone", phone);
    //                        editor.putString("username", username);
    //                        editor.commit();
    //                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
    //                        startActivity(intent);
    //                        LoginActivity.this.finish();
    //                    } else if (rescode.equals("2")) {
    //                        Toast.makeText(LoginActivity.this, "手机号或密码错误！", Toast.LENGTH_SHORT).show();
    //                    }
    //                } catch (Exception e) {
    //                    Toast.makeText(LoginActivity.this, "手机号或密码错误！", Toast.LENGTH_SHORT).show();
    //
    //                }
    //
    //            } else if (msg.what == 2) {//2网络或服务端问题
    //                Toast.makeText(LoginActivity.this, "网络或服务器故障！", Toast.LENGTH_SHORT).show();
    //
    //            }
    //            login_result = "";
    //
    //
    //        }
    //    };


    /**
     * 自定义监听器实现IUiListener接口后，需要实现的3个方法
     * <p>
     * onComplete完成 onError错误 onCancel取消
     */
    /***
     * {
    "ret":0,
    "msg":"",
    "nickname":"Peter",
    "figureurl":"http://qzapp.qlogo.cn/qzapp/111111/942FEA70050EEAFBD4DCE2C1FC775E56/30",
    "figureurl_1":"http://qzapp.qlogo.cn/qzapp/111111/942FEA70050EEAFBD4DCE2C1FC775E56/50",
    "figureurl_2":"http://qzapp.qlogo.cn/qzapp/111111/942FEA70050EEAFBD4DCE2C1FC775E56/100",
    "figureurl_qq_1":"http://q.qlogo.cn/qqapp/100312990/DE1931D5330620DBD07FB4A5422917B6/40",
    "figureurl_qq_2":"http://q.qlogo.cn/qqapp/100312990/DE1931D5330620DBD07FB4A5422917B6/100",
    "gender":"男",
    "is_yellow_vip":"1",
    "vip":"1",
    "yellow_vip_level":"7",
    "level":"7",
    "is_yellow_year_vip":"1"
    }
     */
    inner class BaseUiListener : IUiListener {
        override fun onComplete(p0: Any?) {
            toast("授权成功")
            Log.i("po>>>>>>>>>>>>", "" + p0.toString())
            val jsonObject = p0 as JSONObject
            val openID = jsonObject.getString("openid")
            val accessToken = jsonObject.getString("access_token")
            val expires = jsonObject.getString("expires_in")
            mTencent!!.openId = openID
            mTencent!!.setAccessToken(accessToken, expires)
            val qqToken = mTencent!!.qqToken
            mUserInfo = UserInfo(requireContext(), qqToken)
            mUserInfo!!.getOpenId(object : IUiListener {
                override fun onComplete(p0: Any?) {
                    val name = jsonObject.getString("nickname")
                    val sex = jsonObject.getString("gender")
                    val fivefig = jsonObject.getString("figureurl_1")
                    val hunderfig = jsonObject.getString("figureurl_2")
                    Log.i("name>>>>>>>>>", name + "")
                    Log.i("sex>>>>>>>>>", sex + "")
                    Log.i("fivefig>>>>>>>>>", fivefig + "")
                    Log.i("hunderfig>>>>>>>>>", hunderfig + "")
                }

                override fun onCancel() {
                    TODO("Not yet implemented")
                }

                override fun onError(p0: UiError?) {
                    TODO("Not yet implemented")
                }

            })


        }

        override fun onCancel() {
        }

        override fun onError(p0: UiError?) {

        }

    }

    fun send() {
        try {
            val url: URL = URL(ip)
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
                    + login_userinput.text.toString()
                    + "&password="
                    + login_passwordinput.text.toString()
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