package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class UserStory12 extends Simulation {

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
			
	object LoginOwner {
		val login1 = exec(http("Login")
			.get("/login")
			.headers(headers_0)
		).pause(12)
		.exec(http("Logged")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "owner1")
			.formParam("password", "0wn3r")
			.formParam("_csrf", "dda05094-3fbd-4827-b380-557d9e1cadbe"))
		.pause(6)
	}	
	
	object ListTournaments {
		val listTournaments = exec(http("ListTournaments")
			.get("/tournaments/active")
			.headers(headers_0))
		.pause(31)
	}  
	
	object ShowTournament1 {
		val showTournament1 = exec(http("ShowTournament1")
			.get("/tournaments/6/show")
			.headers(headers_0))
		.pause(9)
	}  
	
	object ShowTournament2 {
		val showTournament2 = exec(http("ShowTournament2")
			.get("/tournaments/1/show")
			.headers(headers_0))
		.pause(9)
	}  
	
	object GetThrowApplication1 {
		val getThrowApplication1 = exec(http("GetThrowApplication1")
			.get("/applications/6/new")
			.headers(headers_0))
		.pause(39)
	}

	object GetThrowApplication2 {
		val getThrowApplication2 = exec(http("GetThrowApplication2")
			.get("/applications/1/new")
			.headers(headers_0))
		.pause(39)
	} 
	
	object PostThrowApplication1 {
		val postThrowApplication1 = exec(http("PostThrowApplication1")
			.post("/applications/6/new")
			.headers(headers_3)
			.formParam("pet", "Leo")
			.formParam("creditCard", "379254492621186")
			.formParam("_csrf", "2252a781-0808-41f4-904c-a9d5570268e4"))
		.pause(11)
	}

	object PostThrowApplication2 {
		val postThrowApplication2 = exec(http("PostThrowApplication2")
			.post("/applications/1/new")
			.headers(headers_3)
			.formParam("pet", "Leo")
			.formParam("creditCard", "363017956100486")
			.formParam("_csrf", "4659adab-c88d-443e-be38-4306ea9b9d7d"))
		.pause(13)
	} 	
	
	val positiveScn = scenario("ThrowApplication1").exec(Home.home, 	
													LoginOwner.login1, 
													ListTournaments.listTournaments,
													ShowTournament1.showTournament1,
													GetThrowApplication1.getThrowApplication1,
													PostThrowApplication1.postThrowApplication1)
	
	val negativeScn = scenario("ThrowApplication2").exec(Home.home, 
													LoginOwner.login1, 
													ListTournaments.listTournaments,
													ShowTournament2.showTournament2,
													GetThrowApplication2.getThrowApplication2,
													PostThrowApplication2.postThrowApplication2)	



	setUp(
		positiveScn.inject(rampUsers(8000) during (100 seconds)), 
		negativeScn.inject(rampUsers(8000) during (100 seconds))
		).protocols(httpProtocol).assertions(
        global.responseTime.max.lt(5000),    
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
     )
}