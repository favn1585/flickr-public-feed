package com.flickr.feed.repository

import com.flickr.feed.data.model.FlickrImage
import com.flickr.feed.data.repository.FlickrImagesDataSource
import com.flickr.feed.data.repository.FlickrImagesLocalDataSource
import com.flickr.feed.data.repository.FlickrImagesRepositoryImpl
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

/**
 * Created by andrew on 26/10/2017.
 */
class FlickrImagesRepositoryTest {

    private val images = arrayListOf(FlickrImage(), FlickrImage())

    @Mock
    lateinit var localDataSource: FlickrImagesLocalDataSource

    @Mock
    lateinit var remoteDataSource: FlickrImagesDataSource

    lateinit var repository: FlickrImagesRepositoryImpl

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        repository = FlickrImagesRepositoryImpl(localDataSource, remoteDataSource)
    }

    @Test
    fun repositoryShouldReturnLocalImages() {
        `when`(localDataSource.getImages()).thenReturn(Single.just(images))
        `when`(remoteDataSource.getImages()).thenReturn(Single.just(arrayListOf()))

        val testObserver = repository.getImages().test()

        testObserver.awaitTerminalEvent()
        testObserver.assertNoErrors()
        testObserver.assertValue { images -> images.size == images.size }
    }

    @Test
    fun repositoryShouldReturnRemoteImages() {
        `when`(localDataSource.getImages()).thenReturn(Single.just(arrayListOf()))
        `when`(remoteDataSource.getImages()).thenReturn(Single.just(images))

        val testObserver = repository.getImages().test()

        testObserver.awaitTerminalEvent()
        testObserver.assertNoErrors()
        testObserver.assertValue { images -> images.size == images.size }
    }
}