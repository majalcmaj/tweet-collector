package pl.majalcmaj.spring.twitcollector.backgroundworker

import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import pl.majalcmaj.spring.tweetcollector.backgroundworker.TweetService

@Ignore
@RunWith(SpringRunner::class)
@SpringBootTest(classes = [TweetService::class])
class TweetServiceTest {

//	@Autowired
//	lateinit var tweetService: TweetService

	@Test
	fun runTest() {
//		tweetService.run()
	}

}
