package ru.skoroxod.ui.search

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding3.appcompat.queryTextChangeEvents
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_search.*
import ru.skoroxod.R
import ru.skoroxod.domain.github.GithubUser
import timber.log.Timber
import java.util.concurrent.TimeUnit


class SearchFragment : Fragment() {

    private lateinit var userAdapter: GithubUsersListAdapter
    private var searchViewDisposable: Disposable? = null

    private val viewModel: SearchViewModel by lazy { ViewModelProviders.of(this).get(SearchViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return  inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
    }

    private fun initAdapter() {
        val linearLayoutManager = LinearLayoutManager(this.context,  RecyclerView.VERTICAL, false)

        userAdapter = GithubUsersListAdapter()

        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = userAdapter
        viewModel.userList?.observe(this, Observer<PagedList<GithubUser>> {
            userAdapter.submitList(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        Timber.d("onCreateOptionsMenu")
        menuInflater.inflate(R.menu.main, menu)
        initSearchView(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
    private fun initSearchView(menu: Menu) {
        val searchMenuItem = menu.findItem(R.id.action_search)

        val searchView = searchMenuItem?.actionView as SearchView

        searchViewDisposable?.let { if(!it.isDisposed) it.dispose() }

        searchViewDisposable = searchView.queryTextChangeEvents()
            .debounce(500, TimeUnit.MILLISECONDS)
            .doOnNext {
                Timber.tag("query").d("query text: ${it.queryText}")
            }
            .subscribe {
                viewModel.search(it.queryText.toString())
            }
    }

}

