package ru.skoroxod

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.navigation.NavigationView
import com.jakewharton.rxbinding3.appcompat.queryTextChangeEvents
import com.squareup.picasso.Picasso
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*
import ru.skoroxod.backends.BackendFactory
import ru.skoroxod.client.AuthClient
import timber.log.Timber
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var authClient: AuthClient
    private val viewModel: MainViewModel by lazy { ViewModelProviders.of(this).get(MainViewModel::class.java) }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Timber.tag("Activity").d("onActivityResult: $requestCode")
        authClient.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        viewModel.viewState.observe(this, Observer<ViewState> { render(it) })
        viewModel.isLoggedIn()

        authClient = AuthClient(viewModel::onLoginComplete, viewModel::onLoginError)
    }

    private fun render(viewState: ViewState) {
        if (viewState is ViewState.NotLoggedIn) {
            renderNotLoggedIn()
        } else if (viewState is ViewState.LoggedIn) {
            renderLoggedIn(viewState)
        }
    }

    private fun renderNotLoggedIn() {
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container, LoginFragment(), "login")
            commitAllowingStateLoss()
        }
    }

    private fun renderLoggedIn(viewState: ViewState.LoggedIn) {
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)

        toolbar.navigationIcon?.setVisible(true, true)
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container, SearchFragment(), "login")
            commitAllowingStateLoss()
        }

        val h = nav_view.getHeaderView(0)
        h.user_name.text = viewState.userInfo.displayName
        h.add_info.text = viewState.userInfo.userId
        showUserAvatar(viewState.userInfo.imageUrl)
        Toast.makeText(this, viewState.users.size.toString(), Toast.LENGTH_LONG).show()
    }


    private fun showUserAvatar(url: String?) {

        if(avatar_image != null) {
            Picasso.get()
                .load(url)
                .resize(150, 150)
                .centerCrop()
                .transform(CircleTransform())
                .placeholder(R.drawable.ic_tag_faces_24dp)
                .error(R.drawable.ic_tag_faces_24dp)
                .into(avatar_image)
        }

    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                Timber.tag("Activity.logout").d("logout")
                viewModel.getCurrentBackend()?.let {
                    BackendFactory().getLogoutClient(it)
                        .logout(this) {
                            viewModel.logout()
                        }
                }
            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
