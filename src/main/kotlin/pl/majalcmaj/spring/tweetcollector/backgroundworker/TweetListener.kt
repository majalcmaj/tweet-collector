package pl.majalcmaj.spring.tweetcollector.backgroundworker

import com.twitter.hbc.core.Client
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.majalcmaj.spring.tweetcollector.backgroundworker.converters.TweetConverter
import pl.majalcmaj.spring.tweetcollector.backgroundworker.entity.Tweet
import pl.majalcmaj.spring.tweetcollector.backgroundworker.factories.TwitterClientFactory
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.BlockingQueue

@Service
class TweetListener @Autowired constructor(
        val clientFactory: TwitterClientFactory,
        val tweetConverter: TweetConverter) {

    internal val messagesQueue = ArrayBlockingQueue<String>(100000)
    private val tweetSubject = BehaviorSubject.create<Tweet>()
    val observable: Observable<Tweet> get() = tweetSubject

    fun listenFor(terms: List<String>) {
        val client = getClient(terms)
        try {
            while (!client.isDone and !Thread.interrupted()) {
                val tweetJson = messagesQueue.take()
                val theTweet = tweetConverter.jsonToTweet(tweetJson)
                tweetSubject.onNext(theTweet)
            }
        } catch (ex: InterruptedException) {
            // Normal situation - do nothing
        } finally {
            client.stop()
        }
    }

    private fun getClient(terms: List<String>): Client {
        val client = clientFactory.getClient(terms, messagesQueue)
        client.connect()
        return client
    }
}