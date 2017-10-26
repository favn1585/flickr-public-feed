package com.flickr.feed.repository

import com.flickr.feed.data.db.FlickrImagesDao
import com.flickr.feed.data.model.FlickrImage
import com.flickr.feed.data.repository.FlickrImagesLocalDataSource
import com.flickr.feed.data.repository.FlickrImagesRemoteDataSource
import com.flickr.feed.data.repository.FlickrImagesRepository
import com.flickr.feed.di.DaggerTestNetworkComponent
import com.flickr.feed.network.NetworkModule
import com.flickr.feed.utils.ServerTestHelper
import com.flickr.feed.utils.getHost
import io.reactivex.Single
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.doNothing
import org.mockito.MockitoAnnotations
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * Created by andrew on 26/10/2017.
 */
class FlickrImagesRepositoryTest {

    private val LOCAL_IMAGES = arrayListOf(FlickrImage(), FlickrImage())

    lateinit var localDataSource: FlickrImagesLocalDataSource
    lateinit var remoteDataSource: FlickrImagesRemoteDataSource
    lateinit var repository: FlickrImagesRepository

    @Mock
    lateinit var dao: FlickrImagesDao

    @Inject
    lateinit var retrofit: Retrofit

    val server = MockWebServer()

    @Before
    fun setup() {
        server.start()

        val component = DaggerTestNetworkComponent.builder()
                .networkModule(NetworkModule(server.getHost())).build()
        component.inject(this)

        MockitoAnnotations.initMocks(this)

        localDataSource = FlickrImagesLocalDataSource(dao)
        remoteDataSource = FlickrImagesRemoteDataSource(retrofit)

        repository = FlickrImagesRepository(localDataSource, remoteDataSource)

        doNothing().`when`(dao).dropImages()
    }

    @After
    fun dispose() {
        server.shutdown()
    }

    @Test
    fun repository_shouldReturnLocalImages() {
        `when`(dao.getImages()).thenReturn(Single.just(LOCAL_IMAGES))

        val testObserver = repository.getImages().test()

        testObserver.awaitTerminalEvent()
        testObserver.assertNoErrors()
        testObserver.assertValue { images -> images.size == LOCAL_IMAGES.size }
    }

    @Test
    fun repository_shouldReturnRemoteImages() {
        server.setDispatcher(ServerTestHelper.imagesDispatcher)

        `when`(dao.getImages()).thenReturn(Single.just(arrayListOf()))

        val testObserver = repository.getImages().test()

        testObserver.awaitTerminalEvent()
        testObserver.assertNoErrors()
        testObserver.assertValue { images -> images.size == ServerTestHelper.testImagesSize }
    }
}