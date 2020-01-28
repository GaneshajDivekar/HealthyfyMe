package com.example.me

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.me.core.network.RetrofitApiInterface
import com.example.me.core.network.RetrofitCallAPI
import com.example.me.core.network.WebServiceAPI
import com.example.me.data.api.SlotBookingModel
import com.example.me.presentation.ui.homeModule.HomeRepository
import com.example.me.presentation.ui.homeModule.HomeViewModel
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response
import retrofit2.Retrofit
import java.util.concurrent.CountDownLatch





@RunWith(MockitoJUnitRunner::class)
class UserRepositoryImplTest {

    @Mock
    internal var retrofitApiInterface: RetrofitApiInterface? = null
    @Mock
    private val homeRepository: HomeRepository? = null
    @Mock
    private var retrofit: Retrofit? = null
    @Mock
    private val mockWebService: WebServiceAPI? = null

    @Mock
    private var retrofitCall: RetrofitCallAPI? = null

    @Mock
    private val latch = CountDownLatch(1)

    @Mock
    internal var context: Application? = null
    var myViewModel: HomeViewModel? = null


    @get:Rule
    val instantTaskExecutorRule=InstantTaskExecutorRule()
    @get:Rule
    val testCoroutinsRule= TestCoroutineDispatcher()

    @Before
    fun setup() {
        myViewModel= HomeViewModel(context!!)
        MockitoAnnotations.initMocks(this)

    }


    @Test
    fun fetchValidDataShouldLoadIntoView() {
        val slotBookingModel = SlotBookingModel("",false,false,0,"")
      /*  `when`(charactersDataSource.getCharacters())
            .thenReturn(Observable.just(charactersResponseModel))*/
        testCoroutinsRule.runBlockingTest{
            val data = CompletableDeferred<Response<List<SlotBookingModel>>>()
            whenever(retrofitApiInterface!!.getSlotBookingResponse()).thenReturn(data)
        }

        myViewModel!!.callSlotBookingApi()






    }









/*
    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        */
/*RetrofitCallAPI!!.getInstance("https://www.x123healthifyme.com/api/v2/booking/")!!.getSlotBookingResponse()*//*


    }


    @Test
    fun displayUserName() {
        var displayUserName =  RetrofitCallAPI!!.getInstance("https://www.x123healthifyme.com/api/v2/booking/")!!.getSlotBookingResponse()
    }
*/

/*

    @Before
    fun beforeTest() {
        retrofitApiInterface = RetrofitCallAPI.getInstance("https://www.x123healthifyme.com/api/v2/booking/")
    }


    @Test
    @Throws(InterruptedException::class)
    fun test_login() {
        retrofitApiInterface!!.getSlotBookingResponse()
        latch.await()
    }

    fun getDataFromServer(): Deferred<Response<List<SlotBookingModel>>>{

        return RetrofitCallAPI.getInstance(WebServiceAPI.SERVERBASE_URL)!!.getSlotBookingResponse()
    }

*/


/*


    @Test
    @Throws(Exception::class)
    fun createMock() {
        val mockWebService = mock(RetrofitApiInterface::class.java)

        SlotBookingModel("Shital", false, false, 0, "Shital", "Shital")
    }


    @Test
    @Throws(Exception::class)
    fun verifyInteractionTimes() {
        val (_, _, _, slot_id) = SlotBookingModel("Shital", false, false, 0, "Shital", "Shital")

        slot_id

    }


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        retrofit = Retrofit.Builder().baseUrl("https://www.x123healthifyme.com/api/v2/booking/")
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    }


    @Test
    fun searchUsers_200OkResponse_InvokesCorrectApiCalls() {
        //Given
        `when`(retrofitApiInterface!!.getSlotBookingResponse()).thenReturn(getDataFromServer())


        //When
        val subscriber = TestSubscriber<List<SlotBookingModel>>()
        homeRepository!!.callSlotBookingApi()

        //Then
        subscriber.awaitTerminalEvent()
        subscriber.assertNoErrors()

        verify<RetrofitApiInterface>(retrofitApiInterface).getSlotBookingResponse()

    }

    fun getDataFromServer(): Deferred<Response<List<SlotBookingModel>>>{

        return RetrofitCallAPI.getInstance(WebServiceAPI.SERVERBASE_URL)!!.getSlotBookingResponse()
    }


    companion object {

        private val USER_LOGIN_RIGGAROO = "riggaroo"
        private val USER_LOGIN_2_REBECCA = "rebecca"
    }
*/

}

