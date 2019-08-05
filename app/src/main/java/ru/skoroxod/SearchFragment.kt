package ru.skoroxod

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.jakewharton.rxbinding3.appcompat.queryTextChangeEvents
import io.reactivex.disposables.Disposable
import timber.log.Timber
import java.util.concurrent.TimeUnit


class SearchFragment : Fragment() {

    private var searchViewDisposable: Disposable? = null

    private val viewModel: SearchViewModel by lazy { ViewModelProviders.of(this).get(SearchViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_search, container, false)

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        Timber.d("onCreateOptionsMenu")
        menuInflater.inflate(ru.skoroxod.R.menu.main, menu)
        initSearchView(menu)


    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            ru.skoroxod.R.id.action_search -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
    private fun initSearchView(menu: Menu) {
        val searchMenuItem = menu.findItem(R.id.action_search)

        val searchView = searchMenuItem?.actionView as SearchView

        searchViewDisposable?.let { if(!it.isDisposed) it.dispose() }

        searchViewDisposable = searchView.queryTextChangeEvents()
            .debounce(300, TimeUnit.MILLISECONDS)
            .doOnNext {
                Timber.tag("query").d("query text: ${it.queryText}")
            }
            .subscribe {
                viewModel.search(it.queryText.toString())
            }
    }

}

