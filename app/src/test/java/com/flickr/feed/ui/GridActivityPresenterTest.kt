package com.flickr.feed.ui

import com.flickr.feed.data.model.FlickrImage
import com.flickr.feed.data.repository.FlickrImagesRepositoryImpl
import com.flickr.feed.ui.photoGrid.PhotoGridActivityPresenter
import com.flickr.feed.ui.photoGrid.PhotoGridContract
import com.flickr.feed.utils.RunOn
import com.flickr.feed.utils.SchedulerContext
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by andrew on 26/10/2017.
 */
@RunWith(MockitoJUnitRunner::class)
class GridActivityPresenterTest {

    private val LOCAL_IMAGES = arrayListOf(FlickrImage(), FlickrImage(), FlickrImage())

    lateinit var presenter: PhotoGridActivityPresenter

    @Mock
    lateinit var view: PhotoGridContract.View

    @Mock
    lateinit var repository: FlickrImagesRepositoryImpl

    @RunOn(SchedulerContext.IO) lateinit var ioScheduler: Scheduler
    @RunOn(SchedulerContext.UI) lateinit var uiScheduler: Scheduler

    lateinit var testScheduler: TestScheduler

    @Before
    fun setUp() {
        testScheduler = TestScheduler()
        uiScheduler = testScheduler
        ioScheduler = testScheduler

        presenter = PhotoGridActivityPresenter(view, repository, ioScheduler, uiScheduler)
    }

    @Test
    fun loadQuestions_ShouldShowMessage_WhenNoDataReturned() {
        whenever(repository.getImages()).thenReturn(Single.just(LOCAL_IMAGES))

        presenter.getImages()
        testScheduler.triggerActions()

        verify(view).displayImages(LOCAL_IMAGES)
    }
}