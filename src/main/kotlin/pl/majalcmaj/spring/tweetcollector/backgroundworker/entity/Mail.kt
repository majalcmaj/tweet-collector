package pl.majalcmaj.spring.tweetcollector.backgroundworker.entity

class Email {

    var to: String? = null
    var body: String? = null

    constructor() {}

    constructor(to: String, body: String) {
        this.to = to
        this.body = body
    }

    override fun toString(): String {
        return String.format("Email{to=%s, body=%s}", to, body)
    }

}