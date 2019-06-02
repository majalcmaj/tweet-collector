package pl.majalcmaj.spring.tweetcollector.backgroundworker.factories

import com.twitter.hbc.ClientBuilder
import com.twitter.hbc.core.Client
import com.twitter.hbc.core.Constants
import com.twitter.hbc.core.HttpHosts
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint
import com.twitter.hbc.core.event.Event
import com.twitter.hbc.core.processor.StringDelimitedProcessor
import com.twitter.hbc.httpclient.auth.Authentication
import com.twitter.hbc.httpclient.auth.OAuth1
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.majalcmaj.spring.tweetcollector.backgroundworker.TwitterConfigurationProperties
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue

@Service
class TwitterClientFactory @Autowired constructor(val twitterConfigurationProperties: TwitterConfigurationProperties) {
    fun getClient(trackTerms: List<String>, msgQueue: BlockingQueue<String>): Client {
        val eventQueue = LinkedBlockingQueue<Event>(1000)
        val hosts = HttpHosts(Constants.STREAM_HOST)
        val statusesFilterEndpoint = StatusesFilterEndpoint()
        statusesFilterEndpoint.trackTerms(trackTerms)
        val auth: Authentication = OAuth1(
                twitterConfigurationProperties.consumerKey,
                twitterConfigurationProperties.consumerSecret,
                twitterConfigurationProperties.token,
                twitterConfigurationProperties.tokenSecret
        )
        return ClientBuilder()
                .name("Twitter-Collector")
                .hosts(hosts)
                .authentication(auth)
                .endpoint(statusesFilterEndpoint)
                .processor(StringDelimitedProcessor(msgQueue))
                .eventMessageQueue(eventQueue)
                .build()
    }
}