package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class UserStory19 extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.ico""", """.*.png"""), WhiteList())
		.acceptHeader("*/*")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.9,en;q=0.8,en-GB;q=0.7")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36")

	val headers_0 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_1 = Map("Proxy-Connection" -> "keep-alive")

	val headers_4 = Map(
		"Accept" -> "image/webp,image/apng,image/*,*/*;q=0.8",
		"Proxy-Connection" -> "keep-alive")

	val headers_5 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	object Home {
		val home = exec(http("Home")
			.get("/")
			.headers(headers_0)
			.resources(http("request_1")
			.get("/")
			.headers(headers_1)))
		.pause(15)
	}

	object Login {
		val login = exec(http("Login")
			.get("/login")
			.headers(headers_0)
			.resources(http("request_3")
			.get("/login")
			.headers(headers_1),
            http("request_4")
			.get("/login")
			.headers(headers_4)))
		.pause(14)
	}

	object Logged {
		val logged = exec(http("Logged")
			.post("/login")
			.headers(headers_5)
			.formParam("username", "judge1")
			.formParam("password", "jugd3")
			.formParam("_csrf", "a841c37f-5053-44f1-bf14-ee41e30c2914")
			.resources(http("request_6")
			.get("/")
			.headers(headers_1)))
		.pause(16)
	}

	object EndedList {
		val endedList = exec(http("EndedList")
			.get("/tournaments/endedList")
			.headers(headers_0)
			.resources(http("request_8")
			.get("/tournaments/endedList")
			.headers(headers_1)))
		.pause(17)
	}

	object RankingMade {
		val rankingMade = exec(http("RankingMade")
			.post("/tournaments/endedList")
			.headers(headers_5)
			.formParam("tournament", "Cats beauty contest 2019")
			.formParam("_csrf", "7954d280-0bd8-4d47-87db-551033fa7c4d")
			.resources(http("request_10")
			.get("/tournaments/endedList")
			.headers(headers_1)))
		.pause(18)
	}

	val okscn = scenario("RankingMadeOk").exec(Home.home, Login.login, Logged.logged, EndedList.endedList, RankingMade.rankingMade)

	val notOkscn = scenario("RankingMadeNotOk").exec(Home.home, Login.login, Logged.logged, EndedList.endedList)
		
setUp(
		okscn.inject(rampUsers(4000) during (100 seconds)),
		notOkscn.inject(rampUsers(4000) during (100 seconds))
		).protocols(httpProtocol)
		.assertions(
			global.responseTime.max.lt(5000),
			global.responseTime.mean.lt(1000),
			 global.successfulRequests.percent.gt(95)
		) 
}