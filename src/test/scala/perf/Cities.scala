package perf

import io.gatling.core.Predef._

object Cities {

  val cities = csv (fileName="cities.csv").random

}
