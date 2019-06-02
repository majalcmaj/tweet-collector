package pl.majalcmaj.spring.tweetcollector

import org.springframework.jms.annotation.JmsListener
import org.springframework.stereotype.Component
import pl.majalcmaj.spring.tweetcollector.backgroundworker.entity.Tweet

@Component
class TweetReceiver {
    @JmsListener(destination = "tweets", containerFactory = "myFactory")
    fun receiveTweetMessage(tweet: Tweet) {
        println(tweet)
    }
}

