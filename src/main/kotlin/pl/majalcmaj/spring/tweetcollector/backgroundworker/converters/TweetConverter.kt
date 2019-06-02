package pl.majalcmaj.spring.tweetcollector.backgroundworker.converters

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.majalcmaj.spring.tweetcollector.backgroundworker.entity.Hashtag
import pl.majalcmaj.spring.tweetcollector.backgroundworker.entity.JsonTweet
import pl.majalcmaj.spring.tweetcollector.backgroundworker.entity.Tweet
import java.util.stream.Collectors


@Service
class TweetConverter @Autowired constructor(val jacksonObjectMapper: ObjectMapper) {

    fun jsonToTweet(json: String): Tweet {
        val jsonTweet = jacksonObjectMapper.readValue(json, JsonTweet::class.java)
        return Tweet(jsonTweet.id, jsonTweet.text,
                jsonTweet.entities?.hashtags?.stream()?.map {
                    Hashtag(it.text, it.indices[0], it.indices[1])
                }?.collect(Collectors.toList()))
    }
}