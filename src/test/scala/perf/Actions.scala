package perf

import io.gatling.core.Predef._
import io.gatling.http.Predef._

object Actions {

  val webtours = http("webtours")
    .get("/webtours/")
    .check(status is 200)

  val welcome = http("welcome.pl")
    .get("/cgi-bin/welcome.pl")
    .queryParam("singOff","true")
    .check(status is 200)

  val nav = http("nav.pl")
    .get("/cgi-bin/nav.pl")
    .queryParam("in", "home")
    .check(status is 200)
    .check(regex("""name="userSession" value="(.+)"""").saveAs("userSession"))

  //login

  val loginPl = http("/cgi-bin/login.pl")
    .post("/cgi-bin/login.pl")
    .formParam("userSession","${userSession}")
    .formParam("username","${login}")
    .formParam("password","${password}")
    .formParam("login.x","0")
    .formParam("login.y","0")
    .formParam("JSFormSubmit","off")
    .check(status is 200)

  //tickets

  val reservations = http("/cgi-bin/reservations")
    .get("/cgi-bin/reservations.pl")
    .formParam("page", "welcome")
    .check(status is 200)

  val find_flight_step1 = http("find_flight_step1")
    .post("/cgi-bin/reservations.pl")
    .formParam("advanceDiscount", "0")
    .formParam("depart", "${depart}")
    .formParam("departDate", "02/12/2023")
    .formParam("arrive", "${arrive}")
    .formParam("returnDate", "02/13/2023")
    .formParam("numPassengers", "1")
    .formParam("seatPref", "None")
    .formParam("seatType", "Coach")
    .formParam("findFlights.x", "44")
    .formParam("findFlights.y", "9")
    .formParam(".cgifields", "roundtrip")
    .formParam(".cgifields", "seatType")
    .formParam(".cgifields", "seatPref")
    .check(status is 200)
    .check(regex("""name="outboundFlight" value="(.*?)"""").findAll.saveAs("outboundFlight1"))

  val find_flight_step2 = http("find_flight_step2")
    .post("/cgi-bin/reservations.pl")
    .queryParam("outboundFlight", "${outboundFlight1.random()}")
    .queryParam("numPassengers", "1")
    .queryParam("advanceDiscount", "0")
    .queryParam("seatType", "Coach")
    .queryParam("seatPref", "Window")
    .formParam("findFlights.x", "72")
    .formParam("findFlights.y", "18")
    .check(status is 200)
    .check(regex("""name="outboundFlight" value="(.*?)"""").saveAs("outboundFlight2"))

  val payment_details = http("payment_details")
    .post("/cgi-bin/reservations.pl")
    .queryParam("firstName", "Nikita")
    .queryParam("lastName", "Privalov")
    .queryParam("address2", "54")
    .queryParam("pass1", "Nikita Privalov")
    .queryParam("creditCard", "")
    .queryParam("expDate", "")
    .queryParam("oldCCOption", "")
    .queryParam("numPassengers", "1")
    .queryParam("seatType", "Coach")
    .queryParam("seatPref", "Window")
    .queryParam("outboundFlight", "${outboundFlight2}")
    .queryParam("advanceDiscount", "0")
    .queryParam("returnFlight", "")
    .queryParam("JSFormSubmit", "off")
    .queryParam("buyFlights.x:", "63")
    .queryParam("buyFlights.y:", "17")
    .queryParam("cgifields", "saveCC")
    .check(status is 200)

    val itinerary = http("itinerary")
      .get("/cgi-bin/itinerary.pl")
      .check(status is 200)

}
