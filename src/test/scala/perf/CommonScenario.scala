package perf

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import perf.Actions._

object CommonScenario{
  def apply(): ScenarioBuilder = new CommonScenario().mainScenario
}

class CommonScenario {

  val open = group("open") {
      exec(webtours)
        .exec(welcome)
        .exec(nav)
  }

  val login = group("login") {
    exec(loginPl)
  }

  val buying_tickets = group("buying_tickets") {
    exec(reservations)
      .feed(Cities.cities)
      .exec(find_flight_step1)
      .exec(find_flight_step2)
      .exec(payment_details)
      .exec(itinerary)
      .exec(logOut)
  }

  val mainScenario = scenario("mainScenario")
    .feed(Feeders.users)
    .exec(open)
    .exec(login)
    .exec(buying_tickets)

}
