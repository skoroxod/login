package ru.skoroxod.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.navigation.NavigationView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*
import ru.skoroxod.R
import ru.skoroxod.domain.backends.BackendFactory
import ru.skoroxod.domain.backends.BackendType
import ru.skoroxod.domain.backends.LoginClient
import ru.skoroxod.ui.login.LoginFragment
import ru.skoroxod.ui.search.SearchFragment
import ru.skoroxod.ui.utils.CircleTransform
import timber.log.Timber

/**
 * Главная Activity приложения.
 * Приложение использует SingleActivity
 *
 */
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var backends: Map<BackendType, LoginClient>

    private val viewModel: MainViewModel by lazy { ViewModelProviders.of(this).get(MainViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        viewModel.viewState.observe(this, Observer<MainActivtiyViewState> { render(it) })
        viewModel.isLoggedIn()

        backends = BackendFactory().getLoginClients(viewModel::onLoginComplete, viewModel::onLoginError)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Timber.tag("Activity").d("onActivityResult: $requestCode")

        backends.values.forEach { it.processLogin(requestCode, resultCode, data) }

        super.onActivityResult(requestCode, resultCode, data)
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
                        .logout(this) { viewModel.logout() }
                }
            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun render(viewState: MainActivtiyViewState) {
        if (viewState is MainActivtiyViewState.NotLoggedIn) {
            renderNotLoggedIn()
        } else if (viewState is MainActivtiyViewState.LoggedIn) {
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

    private fun renderLoggedIn(viewState: MainActivtiyViewState.LoggedIn) {
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)

        toolbar.navigationIcon?.setVisible(true, true)
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container, SearchFragment(), "login")
            commitAllowingStateLoss()
        }
        nav_view.getHeaderView(0).apply {
            this.user_name.text = viewState.userInfo.displayName
            this.add_info.text = viewState.userInfo.userId
        }
        showUserAvatar(viewState.userInfo.imageUrl)
    }

    private fun showUserAvatar(url: String?) {

        if(avatar_image != null) {
            Picasso.get()
                .load(url)
                .resize(96,96)
                .centerCrop()
                .transform(CircleTransform())
                .placeholder(R.drawable.ic_tag_faces_24dp)
                .error(R.drawable.ic_tag_faces_24dp)
                .into(avatar_image)
        }
    }

}
