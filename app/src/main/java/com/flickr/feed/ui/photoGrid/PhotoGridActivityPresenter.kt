package com.flickr.feed.ui.photoGrid

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import com.flickr.feed.data.model.FlickrImage
import com.flickr.feed.data.repository.FlickrImagesRepository
import com.flickr.feed.utils.RunOn
import com.flickr.feed.utils.SchedulerContext
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

/**
 * Created by andrew on 26/10/2017.
 */
class PhotoGridActivityPresenter
@Inject constructor(private val view: PhotoGridContract.View,
                    private val repository: FlickrImagesRepository,
                    @RunOn(SchedulerContext.IO) private val ioScheduler: Scheduler,
                    @RunOn(SchedulerContext.UI) private val uiScheduler: Scheduler)
    : PhotoGridContract.Presenter, LifecycleObserver {

    init {
        if (view is LifecycleOwner) {
            (view as LifecycleOwner).lifecycle.addObserver(this)
        }
    }

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun getImages() {
        compositeDisposable.add(repository.getImages()
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribeWith(object : DisposableSingleObserver<List<FlickrImage>>() {
                    override fun onSuccess(images: List<FlickrImage>) {
                        view.displayImages(images)
                    }

                    override fun onError(e: Throwable) {
                        view.displayError()
                    }
                }))
    }

    override fun reloadImages() {
        compositeDisposable.add(repository.refreshImages()
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribeWith(object : DisposableSingleObserver<List<FlickrImage>>() {
                    override fun onSuccess(images: List<FlickrImage>) {
                        view.displayImages(images)
                    }

                    override fun onError(e: Throwable) {
                        view.displayError()
                    }
                }))
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onDetach() {
        compositeDisposable.clear()
    }
}