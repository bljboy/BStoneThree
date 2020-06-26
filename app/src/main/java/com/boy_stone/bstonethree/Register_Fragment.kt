package com.boy_stone.bstonethree

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
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
    var handler: Handler? = null
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
            toast("ssssss")
            val controller = Navigation.findNavController(requireActivity(), R.id.login_fragment)
            controller.navigateUp()

        })
//        register_button.setOnClickListener(View.OnClickListener {
//            if (TextUtils.isEmpty(register_userinput.text.toString()) && TextUtils.isEmpty(
//                    register_iphoneinput.text.toString()
//                ) && TextUtils.isEmpty(register_passwordinput.text.toString()) && TextUtils.isEmpty(
//                    register_passwordinputagain.text.toString()
//                )
//            ) {
//                toast("空")
//            } else {
//                handler = object : Handler() {
//                    override fun handleMessage(msg: Message) {
//                        super.handleMessage(msg)
//                        if ("ok".equals(result)) {
//                            requireActivity().finish()
//                        }
//                    }
//
//                }
//                thread {
//                    send()
//                    val obtainMessage = (handler as Handler).obtainMessage()
//                    (handler as Handler).sendMessage(obtainMessage)
//                }.start()
//
//            }
//        })

    }

    fun send() {
        val url: URL = URL("http://localhost:8080/BStone_war_exploded/register.jsp")
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "POST"
        connection.doInput = true
        connection.doInput = true
        connection.useCaches = false
        connection.instanceFollowRedirects = true
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
        val out = DataOutputStream(connection.outputStream)
        val param = "user=" + URLEncoder.encode(register_userinput.text.toString(), "UTF-8")
        "&iphone_number=" + URLEncoder.encode(register_iphoneinput.text.toString(), "UTF-8")
        "password=" + URLEncoder.encode(
            register_passwordinput.text.toString(),
            "UTF-8"
        )
        out.writeBytes(param)
        out.flush()
        out.close()
        if (connection.responseCode == HttpURLConnection.HTTP_OK) {
            val inputStreamReader = InputStreamReader(connection.inputStream)
            val buffer = BufferedReader(inputStreamReader)
            var inputline: String? = null

            do {
                inputline = buffer.readLine()
                if (inputline != null) {
                    result += inputline
                }
            } while (true)
        }
    }

    fun onSupportNavigateUp(): Boolean {
        val controller = Navigation.findNavController(requireActivity(), R.id.login_fragment)
        return controller.navigateUp()
    }


}