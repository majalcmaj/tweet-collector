package pl.majalcmaj.spring.tweetcollector.backgroundworker

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "twitter")
@PropertySource("classpath:twitter-secrets.properties")
class TwitterConfigurationProperties {
    var consumerKey: String = ""
    var consumerSecret: String = ""
    var token: String = ""
    var tokenSecret: String = ""
}