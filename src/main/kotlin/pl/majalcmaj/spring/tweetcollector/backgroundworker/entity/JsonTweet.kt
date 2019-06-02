package pl.majalcmaj.spring.tweetcollector.backgroundworker.entity

data class JsonTweet(val id: Long, val text: String?, val entities: JsonEntities?)

data class JsonEntities(val hashtags: List<JsonHashtag>)

data class JsonHashtag(val text: String, val indices: Array<Int>)

