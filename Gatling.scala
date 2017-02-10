import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class Gatling extends Simulation {

    var mngrName = sys.env("STRESS_USER")
    var password = sys.env("STRESS_PASSWORD")
    var url = sys.env("STRESS_URL")
    var users = sys.env("STRESS_USERS").toDouble

    val Users = (users * 0.1).toInt

    val httpProtocol = http
		.baseURL(url)
		.inferHtmlResources()
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate, br")
		.acceptLanguageHeader("en-US,en;q=0.5")
		.userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.11; rv:44.0) Gecko/20100101 Firefox/44.0")

    val token1 = regex("""name="authenticity_token" value="([^"]*)"""").saveAs("authToken")

	val scnMngr = scenario("ManagerSim")
		.exec(http("mngr_request_0")
			.get("/sign_in")
            .check(token1))
		.pause(10)
//        .exec(
//            session => {
//            println("authenticity_token:" + session("authToken"))
//            session
//            })
		.exec(http("mngr_request_1")
			.post("/sign_in")
			.formParam("utf8", "✓")
			.formParam("authenticity_token", """${authToken}""")
			.formParam("employee[login]", mngrName)
			.formParam("employee[password]", password)
			.formParam("commit", "Sign in"))
		.pause(15)
		.exec(http("mngr_request_2")
			.get("sign_out"))

    setUp(
          scnCadm.inject(atOnceUsers(Users)).protocols(httpProtocol))
}