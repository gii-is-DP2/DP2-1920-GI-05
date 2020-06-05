package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class UserStory3 extends Simulation {

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
		.pause(6)
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
		.pause(6)
	}

	object Logged {
		val logged = exec(http("Logged")
			.post("/login")
			.headers(headers_5)
			.formParam("username", "admin1")
			.formParam("password", "4dm1n")
			.formParam("_csrf", "55ce1ed5-e904-47b8-b670-3413924de08c")
			.resources(http("request_6")
			.get("/")
			.headers(headers_1)))
		.pause(13)
	}

	object CategoriesList {
		val categoriesList = exec(http("CategoriesList")
			.get("/categories/all")
			.headers(headers_0)
			.resources(http("request_8")
			.get("/categories/all")
			.headers(headers_1)))
		.pause(11)
	}
		
	val okscn = scenario("AdminList").exec(Home.home, Login.login, Logged.logged, CategoriesList.categoriesList)

	val notOkscn = scenario("Owner403").exec(Home.home, Login.login, Logged.logged, CategoriesList.categoriesList)
		
	setUp(
		okscn.inject(rampUsers(4800) during (100 seconds)),
		notOkscn.inject(rampUsers(4800) during (100 seconds))
		).protocols(httpProtocol)
		.assertions(
			global.responseTime.max.lt(5000),
			global.responseTime.mean.lt(1000),
			 global.successfulRequests.percent.gt(95)
		)
}
