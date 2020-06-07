package com.hfad.parkingfinder.ui.findprovider

import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import com.hfad.parkingfinder.R
import com.hfad.parkingfinder.apicalls.provider.dto.ProviderDto
import com.hfad.parkingfinder.apicalls.provider.dto.ProviderType
import com.hfad.parkingfinder.ui.base.BaseActivity
import com.hfad.parkingfinder.ui.dialog.ProviderSearchFilterDialog
import com.hfad.parkingfinder.ui.findprovider.dagger.FindProviderInjector
import com.hfad.parkingfinder.utils.edittext.onSubmit
import kotlinx.android.synthetic.main.activity_find_provider.*
import kotlinx.android.synthetic.main.layout_normal_action_bar.view.*
import kotlinx.android.synthetic.main.layout_search_action_bar.view.*
import javax.inject.Inject

class FindProviderActivity : BaseActivity() {

    @Inject
    lateinit var presenter: FindProviderPresenter
    private lateinit var providerAdapter: ProviderAdapter
    private var handler: Handler? = null
    private var providerNameOrAddress: String? = null
    private var providerTypes = mutableSetOf<ProviderType>()
    private var maxDistance: Int? = null
    private var notScaledDistance: Int = 2100
    private var filtersDialog: ProviderSearchFilterDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_provider)
        FindProviderInjector().inject(this)
        initActionBar()
        initRV()
        initSwipeRefresh()
    }

    override fun onDestroy() {
        presenter.disposeRX()
        super.onDestroy()
    }

    fun setLoading(isLoading: Boolean) {
        swipeRefresh.isRefreshing = isLoading
    }

    fun updateProviderRV(providers: List<ProviderDto>) {
        providerAdapter.providers.clear()
        providerAdapter.providers.addAll(providers)
        providerAdapter.notifyDataSetChanged()
    }

    private fun initRV() {
        providerAdapter = ProviderAdapter()
        parkingRV.adapter = providerAdapter
        parkingRV.layoutManager = LinearLayoutManager(this)
        parkingRV.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(rv: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    val lastVisibleItem =
                            (rv.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    val totalItemCount = rv.layoutManager.itemCount
                    val visibleItemCount = (rv.layoutManager as LinearLayoutManager).childCount

                    if (totalItemCount <= (lastVisibleItem + visibleItemCount)) {
                        presenter.findProviders(providerAdapter.itemCount / PAGE_SIZE, PAGE_SIZE, maxDistance, providerNameOrAddress, providerTypes.toList(), true)
                    }
                }
            }
        })
        presenter.findProviders(0, PAGE_SIZE, maxDistance, providerNameOrAddress, providerTypes.toList())
    }

    private fun initActionBar() {
        initActionBarBasicBehavior(normalActionBar, searchActionBar, R.color.green)
        normalActionBar.titleTV.text = "Find shop"
        searchActionBar.searchET.hint = "Type name or address"
        searchActionBar.searchET.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                handler?.removeCallbacksAndMessages(null)
                handler = Handler()
                handler!!.postDelayed({
                    providerNameOrAddress = s.toString()
                    presenter.findProviders(0, PAGE_SIZE, maxDistance, providerNameOrAddress, providerTypes.toList())
                }, DEBOUNCE)
            }
        })
        searchActionBar.clearIV.setOnClickListener {
            searchActionBar.searchET.text.clear()
        }
        searchActionBar.adjustIV.setOnClickListener {
            filtersDialog?.dismiss()
            filtersDialog = ProviderSearchFilterDialog()
            filtersDialog?.onApplyListener = {
                maxDistance = filtersDialog?.maxDistance
                notScaledDistance = filtersDialog?.notScaledDistance ?: 2100
                providerTypes = filtersDialog?.providerTypes ?: mutableSetOf()
                presenter.findProviders(0, PAGE_SIZE, maxDistance, providerNameOrAddress, providerTypes.toList())
            }
            filtersDialog?.invalidateDialog(providerTypes, maxDistance, notScaledDistance)
            filtersDialog?.show(fragmentManager, null)
        }
        searchActionBar.searchET.onSubmit {
            presenter.findProviders(0, PAGE_SIZE, maxDistance, providerNameOrAddress, providerTypes.toList())
            hideKeyboard()
        }
    }

    private fun initSwipeRefresh() {
        swipeRefresh.setColorSchemeColors(ContextCompat.getColor(this, R.color.swipe_refresh_color_1),
                ContextCompat.getColor(this, R.color.swipe_refresh_color_2),
                ContextCompat.getColor(this, R.color.swipe_refresh_color_3),
                ContextCompat.getColor(this, R.color.swipe_refresh_color_4))
        swipeRefresh.setOnRefreshListener {
            presenter.findProviders(0, PAGE_SIZE, maxDistance, providerNameOrAddress, providerTypes.toList())
        }
    }

    fun appendToProviderRV(providers: List<ProviderDto>) {
        providerAdapter.providers.addAll(providers)
        providerAdapter.notifyDataSetChanged()
    }

    companion object {
        private const val DEBOUNCE = 1500L
        private const val PAGE_SIZE = 20
    }
}