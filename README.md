Built off of https://github.com/denvazh/gatling

# Base Docker Image

* [java:8-jdk-alpine](https://registry.hub.docker.com/_/java/)

# Docker Tags

* 2.2.3 (latest)

# Required ENV variables
* USER_NAME = user name
* STRESS_PASSWORD = user password
* STRESS_URL = url
* STRESS_USERS = Total users

# Installation

* Install [Docker](https://www.docker.com/)

#  Build
docker build -t gatling .

# Usage

Use image to run container

Mount result location from host machine and run gatling in interactive mode

```
docker run -it --rm -v ~/local/folder/:/opt/gatling/results -e STRESS_USER=$STRESS_USER \
-e STRESS_URL=$STRESS_URL -e STRESS_PASSWORD=$STRESS_PASSWORD -e STRESS_USERS=$STRESS_USERS gatling
```