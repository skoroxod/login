package ru.skoroxod.ui.login

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.facebook.login.widget.LoginButton
import com.google.android.gms.common.SignInButton
import kotlinx.android.synthetic.main.fragment_login.*
import ru.skoroxod.R
import ru.skoroxod.domain.backends.BackendType
import ru.skoroxod.ui.MainActivity


class LoginFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onResume() {
        super.onResume()

        initButton(fb_login_button)
        initButton(google_login_button)
        initButton(vk_login_button)

    }

    private fun initButton(button: View) {
        val mainActivity = (activity as MainActivity)
        when (button) {
            is SignInButton -> { //google
                mainActivity.backends[BackendType.GOOGLE]?.initButton(button, mainActivity)
            }
            is LoginButton -> { // FB
                mainActivity.backends[BackendType.FACEBOOK]?.initButton(button, mainActivity)
            }
            else -> {  //VK
                mainActivity.backends[BackendType.VK]?.initButton(button, mainActivity)
            }
        }

    }

}

