package pl.majalcmaj.spring.tweetcollector.backgroundworker.entity

data class Tweet(val id: Long, val text: String?, val hashtags: List<Hashtag>?)

data class Hashtag(val text: String, val startIdx: Int, val stopIdx: Int)
