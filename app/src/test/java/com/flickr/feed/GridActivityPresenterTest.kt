package com.flickr.feed

import com.flickr.feed.data.db.FlickrImagesDao
import com.flickr.feed.data.model.FlickrImage
import com.flickr.feed.data.repository.FlickrImagesLocalDataSource
import com.flickr.feed.data.repository.FlickrImagesRemoteDataSource
import com.flickr.feed.data.repository.FlickrImagesRepository
import com.flickr.feed.di.DaggerTestNetworkComponent
import com.flickr.feed.network.NetworkModule
import com.flickr.feed.utils.getHost
import io.reactivex.Single
import okhttp3.mockwebserver.MockWebServer
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
class GridActivityPresenterTest {

    private val LOCAL_IMAGES = arrayListOf(FlickrImage(), FlickrImage())
    private val REMOTE_IMAGES = arrayListOf(FlickrImage(), FlickrImage(), FlickrImage())


    lateinit var localDataSource: FlickrImagesLocalDataSource
    lateinit var remoteDataSource: FlickrImagesRemoteDataSource
    lateinit var repository: FlickrImagesRepository

    @Mock
    lateinit var dao: FlickrImagesDao

    @Inject
    lateinit var retrofit: Retrofit

    @Before
    fun setup() {
        val component = DaggerTestNetworkComponent.builder()
                .networkModule(NetworkModule(MockWebServer().getHost())).build()
        component.inject(this)

        MockitoAnnotations.initMocks(this)

        localDataSource = FlickrImagesLocalDataSource(dao)
        remoteDataSource = FlickrImagesRemoteDataSource(retrofit)

        repository = FlickrImagesRepository(localDataSource, remoteDataSource)

        doNothing().`when`(dao).dropImages()
    }

    @Test
    fun repository_shouldReturnLocalImages() {
        `when`(dao.getImages()).thenReturn(Single.just(LOCAL_IMAGES))
        //`when`(localDataSource.getImages()).thenReturn(Single.just(arrayListOf()))

        val testObserver = repository.getImages().test()
        //Mockito.verify(localDataSource).getImages()

        testObserver.awaitTerminalEvent()
        testObserver.assertNoErrors()
        testObserver.assertValue { images -> images.size == LOCAL_IMAGES.size }
    }

   /* @Test
    fun repository_shouldReturnRemoteImages() {
        `when`(localDataSource.getImages()).thenReturn(Single.just(arrayListOf()))
        `when`(localDataSource.deleteAllImages()).then { doNothing() }

        Mockito.doNothing().`when`(localDataSource).deleteAllImages()

        `when`(remoteDataSource.getImages()).thenReturn(Single.just(REMOTE_IMAGES))

        val testObserver = repository.getImages().test()
        Mockito.verify(localDataSource).getImages()
        Mockito.verify(remoteDataSource).getImages()

        testObserver.awaitTerminalEvent()
        testObserver.assertNoErrors()
        testObserver.assertValue { images -> images.size == REMOTE_IMAGES.size }
    }*/
}