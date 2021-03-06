package com.flickr.feed.ui.photoGrid

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleRegistry
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.Parcelable
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewTreeObserver
import android.widget.ImageView
import butterknife.BindView
import butterknife.ButterKnife
import com.flickr.feed.R
import com.flickr.feed.application.App
import com.flickr.feed.application.session.SharedPrefs
import com.flickr.feed.application.session.SharedPrefsModule
import com.flickr.feed.data.model.FlickrImage
import com.flickr.feed.network.imageLoading.GlideApp
import com.flickr.feed.ui.adapter.PhotoGridAdapter
import com.wooplr.spotlight.SpotlightView
import javax.inject.Inject

/**
 * Created by andrew on 26/10/2017.
 *
 * Main Photo Grid activity. Main container for grid images representation
 */

class PhotoGridActivity : AppCompatActivity(), PhotoGridContract.View {

    @BindView(R.id.rvPhotos)
    lateinit var rvPhotos: RecyclerView

    @BindView(R.id.ivProgress)
    lateinit var ivProgress: ImageView

    @BindView(R.id.root)
    lateinit var rootView: View

    @BindView(R.id.swipeRefresh)
    lateinit var swipeRefresh: SwipeRefreshLayout

    @Inject
    lateinit var sharedPrefs: SharedPrefs

    @Inject
    lateinit var presenter: PhotoGridActivityPresenter

    private val lifecycleRegistry = LifecycleRegistry(this)
    private val adapter = PhotoGridAdapter(this)

    private val listStateKey = "list_state"
    private var listState : Parcelable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerPhotoGridComponent.builder()
                .photoGridActivityPresenterModule(PhotoGridActivityPresenterModule(this))
                .sharedPrefsModule(SharedPrefsModule(this))
                .flickrImagesRepositoryComponent((application as App).mFlickrImagesRepositoryComponent)
                .build().inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

        setupWidgets()

        presenter.getImages()
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(listStateKey, rvPhotos.layoutManager.onSaveInstanceState())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        listState = savedInstanceState.getParcelable(listStateKey)
    }

    override fun getLifecycle(): Lifecycle = lifecycleRegistry

    override fun displayImages(images: List<FlickrImage>) {
        ivProgress.visibility = View.GONE
        swipeRefresh.isRefreshing = false
        adapter.setImages(images)

        if (!images.isEmpty() && !sharedPrefs.isGridTipShowed()) {
            rvPhotos.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    showFTUE()
                    rvPhotos.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            })
            sharedPrefs.storeIsGridTipShowed(true)
        }
        
        rvPhotos.layoutManager.onRestoreInstanceState(listState)
    }

    override fun displayError() {
        Snackbar.make(rootView, getString(R.string.common_error), Snackbar.LENGTH_LONG).show()
    }

    private fun setupWidgets() {
        (ivProgress.background as AnimationDrawable).start()
        swipeRefresh.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorAccent))
        swipeRefresh.setOnRefreshListener { presenter.reloadImages() }

        rvPhotos.layoutManager = GridLayoutManager(this, 2)
        rvPhotos.adapter = adapter
        rvPhotos.itemAnimator = DefaultItemAnimator()
        rvPhotos.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_SETTLING) {
                    GlideApp.with(this@PhotoGridActivity).pauseRequestsRecursive()
                } else {
                    GlideApp.with(this@PhotoGridActivity).resumeRequestsRecursive()
                }
            }
        })
        rvPhotos.addItemDecoration(object : RecyclerView.ItemDecoration() {
            val padding = applicationContext.resources.getDimensionPixelSize(R.dimen.itm_padding)

            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                val position = parent.getChildLayoutPosition(view)

                outRect.left = if (position % 2 == 0) padding else padding / 2
                outRect.right = if (position % 2 == 1) padding else padding / 2
                outRect.bottom = padding

                if (position == 0 || position == 1) {
                    outRect.top = padding
                } else {
                    outRect.top = 0
                }
            }
        })
    }

    private fun showFTUE() {
        val layoutManager = rvPhotos.layoutManager as GridLayoutManager
        val firstItemPosition = layoutManager.findFirstVisibleItemPosition()
        val view = layoutManager.findViewByPosition(firstItemPosition)

        val animTime =
                resources.getInteger(android.R.integer.config_mediumAnimTime).toLong()
        val colorAccent =
                ContextCompat.getColor(applicationContext, R.color.colorAccent)

        SpotlightView.Builder(this@PhotoGridActivity)
                .introAnimationDuration(animTime)
                .enableRevealAnimation(true)
                .performClick(true)
                .fadeinTextDuration(animTime)
                .headingTvColor(colorAccent)
                .headingTvSize(32)
                .headingTvText(getString(R.string.ftue_title))
                .subHeadingTvColor(Color.WHITE)
                .subHeadingTvSize(16)
                .subHeadingTvText(getString(R.string.ftue_text))
                .maskColor(ContextCompat.getColor(applicationContext, R.color.bg_ftue))
                .target(view)
                .lineAnimDuration(animTime)
                .lineAndArcColor(colorAccent)
                .dismissOnTouch(true)
                .dismissOnBackPress(true)
                .enableDismissAfterShown(true)
                .show()

    }
}
