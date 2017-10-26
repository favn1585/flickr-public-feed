package com.flickr.feed.network

import com.flickr.feed.data.api.FlickrApi
import com.flickr.feed.data.model.FlickrImages
import com.flickr.feed.di.DaggerTestNetworkComponent
import com.flickr.feed.utils.ServerTestHelper
import com.flickr.feed.utils.ServerTestHelper.Companion.testDate
import com.flickr.feed.utils.ServerTestHelper.Companion.testDescription
import com.flickr.feed.utils.ServerTestHelper.Companion.testMedia
import com.flickr.feed.utils.ServerTestHelper.Companion.testTitle
import com.flickr.feed.utils.getHost
import io.reactivex.observers.TestObserver
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.junit.MockitoJUnit
import retrofit2.HttpException
import retrofit2.Retrofit
import java.net.HttpURLConnection
import javax.inject.Inject


/**
 * Created by andrew on 24/10/2017.
 *
 * Test api requests mapping and errors
 */
class ApiTest {

    val server = MockWebServer()

    @Rule
    @JvmField
    var mockitoRule = MockitoJUnit.rule()

    @Inject
    lateinit var retrofit: Retrofit

    @Before
    fun setUp() {
        server.start()

        val component = DaggerTestNetworkComponent.builder()
                .networkModule(NetworkModule(server.getHost())).build()
        component.inject(this)
    }

    @After
    fun dispose() {
        server.shutdown()
    }

    @Test
    fun testError() {
        server.setDispatcher(object : Dispatcher() {
            override fun dispatch(request: RecordedRequest?): MockResponse =
                    MockResponse().setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)
        })
        val testObserver = TestObserver<FlickrImages>()
        val images = retrofit.create(FlickrApi::class.java).getImages()
        images.subscribe(testObserver)
        testObserver.awaitTerminalEvent()
        testObserver.assertError(HttpException::class.java)
    }

    @Test
    fun testMapping() {
        server.setDispatcher(ServerTestHelper.imagesDispatcher)
        val testObserver = retrofit.create(FlickrApi::class.java).getImages().test()
        testObserver.awaitTerminalEvent()
        testObserver.assertNoErrors().assertValueCount(1)

        testObserver.assertValue { images -> images.items.size == ServerTestHelper.testImagesSize }
        testObserver.assertValue { images -> images.items[0].title == testTitle }
        testObserver.assertValue { images -> images.items[0].media.m == testMedia }
        testObserver.assertValue { images -> images.items[0].date_taken == testDate }
        testObserver.assertValue { images -> images.items[0].description == testDescription }
        testObserver.assertValue { images -> images.items[0].published == testDate }
    }

}