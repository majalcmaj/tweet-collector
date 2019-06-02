package pl.majalcmaj.spring.twitcollector.backgroundworker

import com.twitter.hbc.core.Client
import io.reactivex.observers.TestObserver
import org.hamcrest.CoreMatchers.hasItems
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import pl.majalcmaj.spring.tweetcollector.backgroundworker.TweetListener
import pl.majalcmaj.spring.tweetcollector.backgroundworker.converters.TweetConverter
import pl.majalcmaj.spring.tweetcollector.backgroundworker.entity.Hashtag
import pl.majalcmaj.spring.tweetcollector.backgroundworker.entity.Tweet
import pl.majalcmaj.spring.tweetcollector.backgroundworker.factories.TwitterClientFactory
import java.util.stream.Collectors

@RunWith(MockitoJUnitRunner::class)
class TweetListenerTest {


    private lateinit var tweetListener: TweetListener

    @Mock
    private lateinit var client: Client

    @Mock
    private lateinit var clientFactory: TwitterClientFactory

    @Mock
    private lateinit var tweetConverter: TweetConverter

    @Before
    fun setUp() {
        tweetListener = TweetListener(clientFactory, tweetConverter)
    }

    @Test
    fun givenReceivingTweetsWhenSubscribeThenCorrectTweetsReceived() {
        val tweet1 = Tweet(1L, "Test1", arrayListOf(Hashtag("test", 1, 3)))
        val tweet2 = Tweet(1L, "Test2", arrayListOf(Hashtag("test2", 5, 6), Hashtag("test4", 12, 22)))
        val terms = listOf("asdf")

        tweetListener.messagesQueue.addAll(listOf(tweet1, tweet2).stream().map { it.text }.collect(Collectors.toSet()))
        `when`(clientFactory.getClient(terms, tweetListener.messagesQueue)).thenReturn(client)
        `when`(client.isDone)
                .thenReturn(false)
                .thenReturn(false)
                .thenReturn(true)

        `when`(tweetConverter.jsonToTweet(tweet1.text!!)).thenReturn(tweet1)
        `when`(tweetConverter.jsonToTweet(tweet2.text!!)).thenReturn(tweet2)

        val observable = tweetListener.observable
        val subscriber = TestObserver.create<Tweet>()
        observable.subscribe(subscriber)

        tweetListener.listenFor(terms)
        subscriber.assertNotComplete()
        subscriber.assertNoErrors()
        subscriber.assertValueCount(2)
        assertThat(subscriber.values(), hasItems(tweet1, tweet2))
        verify(client).connect()
        verify(client).stop()
    }
}
