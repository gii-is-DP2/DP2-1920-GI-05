package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class UserStory9 extends Simulation {

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
	
	object ListApplications {
		val listApplications = exec(http("ListApplications")
			.get("/applications/all")
			.headers(headers_0))
		.pause(7)
	}  
	
	object ApplicationForm1 {
		val applicationForm1 = exec(http("ApplicationForm1")
			.get("/applications/4/edit")
			.headers(headers_0))
		.pause(7)
	}
	
	object ApplicationForm2 {
		val applicationForm2 = exec(http("ApplicationForm2")
			.get("/applications/1/edit")
			.headers(headers_0))
		.pause(7)
	}  
	
	object UpdateApplication1 {
		val applicationUpdate1 = exec(http("UpdateApplication1")
			.post("/applications/4/edit")
			.headers(headers_3)
			.formParam("tournament", "Lovebirds speed contest  2020")
			.formParam("owner", "Davis")
			.formParam("pet", "Jewel")
			.formParam("moment", "2020/11/02")
			.formParam("creditCard", "363017956100486")
			.formParam("status", "ACCEPTED")
			.formParam("_csrf", "885df097-e3ed-436b-8d7e-2758a178b429"))
		.pause(12)
	}  
	
	object UpdateApplication2 {
		val applicationUpdate2 = exec(http("UpdateApplication2")
			.post("/applications/1/edit")
			.headers(headers_3)
			.formParam("tournament", "Cats beauty contest 2019")
			.formParam("owner", "Franklin")
			.formParam("pet", "Leo")
			.formParam("moment", "2019/09/22")
			.formParam("creditCard", "352571631239294")
			.formParam("status", "PENDING")
			.formParam("_csrf", "640c27c1-f269-40bb-b4f6-19eef8ea655d"))
		.pause(23)
	}  
	

		

	val positiveScn = scenario("AdminUpdateApplication1").exec(Home.home, 	
													LoginAdmin.login1, 
													ListApplications.listApplications,
													ApplicationForm1.applicationForm1,
													UpdateApplication1.applicationUpdate1)
	
	val negativeScn = scenario("AdminUpdateApplication2").exec(Home.home, 
													LoginAdmin.login1,
													ListApplications.listApplications,
													ApplicationForm2.applicationForm2,
													UpdateApplication2.applicationUpdate2)



	setUp(
		positiveScn.inject(rampUsers(3000) during (100 seconds)), 
		negativeScn.inject(rampUsers(3000) during (100 seconds))
		).protocols(httpProtocol).assertions(
        global.responseTime.max.lt(5000),    
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
     )
}