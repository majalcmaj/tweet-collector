package pl.majalcmaj.spring.twitcollector.backgroundworker

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import pl.majalcmaj.spring.tweetcollector.backgroundworker.converters.TweetConverter
import pl.majalcmaj.spring.tweetcollector.backgroundworker.entity.Hashtag
import pl.majalcmaj.spring.tweetcollector.backgroundworker.entity.Tweet
import java.io.File

class TweetConverterTest {
    companion object {
        const val TWEET_FILE = "/tweet.json"
    }

    lateinit var tweetConverter: TweetConverter


    @Before
    fun setUp() {
        tweetConverter = TweetConverter(jacksonObjectMapper())
    }

    @Test
    fun givenTweetWhenConvertThenTweetReturned() {
        val tweet = readTweetFromFile()

        val actual = tweetConverter.jsonToTweet(tweet)
        val expected = Tweet(1234567L, "Some nice tweet #test",
                arrayListOf(Hashtag("test", 17, 21)))
        assertThat(actual, `is`(expected))
    }

    private fun readTweetFromFile(): String {
        return File(this::class.java.getResource(TWEET_FILE).file).readText()
    }

    private fun jacksonObjectMapper(): ObjectMapper {
        return ObjectMapper()
                .registerModule(KotlinModule())
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    }
}
