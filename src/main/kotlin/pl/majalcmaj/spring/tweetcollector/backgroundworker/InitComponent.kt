package pl.majalcmaj.spring.tweetcollector.backgroundworker

import org.springframework.beans.factory.InitializingBean
import org.springframework.core.task.AsyncListenableTaskExecutor
import org.springframework.core.task.TaskExecutor
import org.springframework.stereotype.Component

@Component
class InitComponent(val tweetService: TweetService) : InitializingBean {
    override fun afterPropertiesSet() {
        tweetService.run()
    }
}