package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class UserStory10 extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.png""", """.*.ico""", """.*.js"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.9,en;q=0.8")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36")

	val headers_0 = Map(
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_2 = Map(
		"Accept" -> "image/webp,image/apng,image/*,*/*;q=0.8",
		"Proxy-Connection" -> "keep-alive")

	val headers_3 = Map(
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	object Home {
		val home = exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(8)
	}

	object LoginAdmin {
		val login1 = exec(http("Login")
			.get("/login")
			.headers(headers_0)
		).pause(12)
		.exec(http("Logged")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "admin1")
			.formParam("password", "4dm1n")
			.formParam("_csrf", "dda05094-3fbd-4827-b380-557d9e1cadbe"))
		.pause(5)
	}

	object LoginOwner {
		val login2 = exec(http("Login")
			.get("/login")
			.headers(headers_0)
		).pause(12)
		.exec(http("Logged")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "owner1")
			.formParam("password", "0wn3r")
			.formParam("_csrf", "dda05094-3fbd-4827-b380-557d9e1cadbe"))
		.pause(5)
	}		  
  
	object ListTournaments {
		val listTournaments = exec(http("ListTournaments")
			.get("/tournaments/all")
			.headers(headers_0))
		.pause(7)
	}  

	val positiveScn = scenario("AdminAllTournaments").exec(Home.home, 	
													LoginAdmin.login1, 
													ListTournaments.listTournaments)
	
	val negativeScn = scenario("OwnerAllTournaments").exec(Home.home, 
													LoginOwner.login2,
													ListTournaments.listTournaments)	



	setUp(
		positiveScn.inject(rampUsers(15000) during (100 seconds)), 
		negativeScn.inject(rampUsers(15000) during (100 seconds))
		).protocols(httpProtocol).assertions(
        global.responseTime.max.lt(5000),    
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
     )
}