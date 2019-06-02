package pl.majalcmaj.spring.tweetcollector

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jms.core.JmsTemplate
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("terms")
class TermsController @Autowired constructor(val jmsTemplate: JmsTemplate){

    @PostMapping
    fun termsToListenFor(@RequestBody terms: List<String>) {
        jmsTemplate.convertAndSend("tweetTerms", terms)
    }
}