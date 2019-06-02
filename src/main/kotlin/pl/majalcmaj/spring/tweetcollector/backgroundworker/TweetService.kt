package pl.majalcmaj.spring.tweetcollector.backgroundworker

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.jms.annotation.JmsListener
import org.springframework.jms.core.JmsTemplate
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.stereotype.Service
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.Future

@Service
@EnableConfigurationProperties
class TweetService @Autowired constructor(
        val tweetListener: TweetListener,
        val jmsTemplate: JmsTemplate,
        val threadPoolTaskExecutor: ThreadPoolTaskExecutor) {
    private var tweetListenerFuture: Future<*>? = null

    fun run() {
        val observable = tweetListener.observable
        observable.subscribe {
            jmsTemplate.convertAndSend("tweets", it)
        }
    }

    @JmsListener(destination = "tweetTerms", containerFactory = "myFactory")
    fun startListeningForTerms(terms: List<String>) {
        tweetListenerFuture?.cancel(true)
        tweetListenerFuture = threadPoolTaskExecutor.submit { tweetListener.listenFor(terms) }
    }
}