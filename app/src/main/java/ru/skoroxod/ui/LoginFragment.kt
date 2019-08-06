package ru.skoroxod.ui

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_login.*
import ru.skoroxod.R


class LoginFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onResume() {
        super.onResume()

        (activity as MainActivity).authClient.initButton(fb_login_button, activity as Activity )
        (activity as MainActivity).authClient.initButton(google_login_button, activity as Activity )
        (activity as MainActivity).authClient.initButton(vk_login_button, activity as Activity)

    }
}

