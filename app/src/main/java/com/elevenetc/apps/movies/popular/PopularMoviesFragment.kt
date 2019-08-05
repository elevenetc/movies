package com.elevenetc.apps.movies.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.GridView
import com.elevenetc.apps.movies.R
import com.elevenetc.apps.movies.core.BaseFragment
import com.elevenetc.apps.movies.core.Movie
import com.elevenetc.apps.movies.core.utils.ChangedTextListener
import com.elevenetc.apps.movies.core.utils.EndScrollListener
import com.elevenetc.apps.movies.core.utils.ViewUtils
import com.elevenetc.apps.movies.popular.PopularMoviesFragment.Mode.POPULAR
import com.elevenetc.apps.movies.popular.PopularMoviesFragment.Mode.SEARCH


class PopularMoviesFragment : BaseFragment() {

    init {
        retainInstance = true
    }

    private var mode = POPULAR
    private var searchQuery = ""
    private var currentPage = 0
    private var allPagesLoaded = false
    private var itemWidth: Int = 0
    private var columnsCount: Int = 0
    private val viewModel by lazy { components.popularMovies().viewModel() }
    private lateinit var adapter: GridViewAdapter
    private lateinit var gridView: GridView
    private lateinit var textNoMovies: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_popular_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val editSearch = view.findViewById<EditText>(R.id.edit_search)

        val screenWidth = ViewUtils.getScreenWidth(activity!!)
        val minItemWidth = resources.getDimension(R.dimen.item_movie_width)

        textNoMovies = view.findViewById(R.id.text_no_movies)
        gridView = view.findViewById(R.id.grid_view)
        columnsCount = (screenWidth / minItemWidth).toInt()
        itemWidth = (screenWidth / columnsCount)
        gridView.setOnScrollListener(EndScrollListener { loadNextPage() })
        gridView.numColumns = columnsCount

        textNoMovies.visibility = View.GONE

        editSearch.addTextChangedListener(ChangedTextListener { onEditSearchChanged(it) })

        if (::adapter.isInitialized) {
            initAdapter(adapter.getMovies())
        } else {
            initAdapter()
            loadPage(currentPage)
        }
    }

    override fun onDestroyView() {
        subs.clear()
        super.onDestroyView()
    }

    private fun openDetails(movieId: String) {
        components.navigator().openMovieDetails(movieId, this)
    }

    private fun loadPage(page: Int) {
        if (mode == POPULAR) {
            subs.add(
                viewModel.getPopularMovies(page)
                    .subscribe({ movies ->
                        appendNewPage(movies)
                    }, {
                        components.logger().log(it)
                        appendNewPage(emptyList())
                    })
            )
        } else {
            subs.add(
                viewModel.searchMovies(searchQuery, page)
                    .subscribe({ movies ->
                        appendNewPage(movies)
                    }, {
                        components.logger().log(it)
                        appendNewPage(emptyList())
                    })
            )
        }

    }

    private fun onEditSearchChanged(query: String) {

        currentPage = 0
        searchQuery = query
        allPagesLoaded = false

        initAdapter()

        if (query.isEmpty()) {
            mode = POPULAR
            loadPage(currentPage)
        } else {
            mode = SEARCH
            loadPage(currentPage)
        }

    }

    private fun appendNewPage(movies: List<Movie>) {
        if (movies.isEmpty()) {
            textNoMovies.visibility = if (adapter.getMovies().isEmpty()) View.VISIBLE else View.GONE
            allPagesLoaded = true
        } else {
            textNoMovies.visibility = View.GONE
            adapter.append(movies)
        }
    }

    private fun loadNextPage() {
        if (allPagesLoaded) return
        loadPage(++currentPage)
    }

    private fun initAdapter(movies: List<Movie> = emptyList()) {
        adapter = GridViewAdapter(
            movies,
            itemWidth,
            components.bitmaps()
        ) { openDetails(it) }
        gridView.adapter = adapter
    }

    /**
     * [POPULAR] default request when fragment is created
     * [SEARCH] if something is typed in search field
     */
    enum class Mode {
        POPULAR, SEARCH
    }

}