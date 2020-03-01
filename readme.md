## Problem

Build a backend service that handles a (very simple) recruiting process. The process requires two types of objects: job offers and applications from candidates. minimum required fields for the objects are:

* Offer:
    * jobTitle (unique)
    * startDate
    * numberOfApplications

* Application:
    * related offer
    * candidate email (unique per Offer)
    * resume text
    * applicationStatus (APPLIED, INVITED, REJECTED, HIRED)
    
Not all of the fields have to be persisted. You may use ad hoc calculation, event sourcing, or whatever you see fit. These are the fields that must be returned by the API. You may add fields where necessary.

## Use cases
* user has to be able to create a job offer and read a single and list all offers.
* candidate has to be able to apply for an offer.
* user has to be able to read one and list all applications per offer.
* user has to be able to progress the status of an application.
* user has to be able to track the number of applications.
* status change triggers a notification (*)
* a log output will suffice as a notification here, but you should design it as if each status change triggers a completely different business case.


### Install
* run application (no pre-req)
```./mvnw spring-boot:run```
* if jdk 1.8+ and mvn 3.x+ is installed
```mvn spring-boot:run```
* Explore application through Swagger UI
http://localhost:8080/swagger-ui.html

### Software Architecture

Patterns used:
* Event Sourcing
* CQRS
* DDD

[![](https://mermaid.ink/img/eyJjb2RlIjoiY2xhc3NEaWFncmFtXG5cdEpvYk9mZmVyIFwiMVwiICotLSBcIjAuLm5cIiBKb2JBcHBsaWNhdGlvblxuXHRcblx0XHRcdFx0XHQiLCJtZXJtYWlkIjp7InRoZW1lIjoiZGVmYXVsdCJ9fQ)](https://mermaid-js.github.io/mermaid-live-editor/#/edit/eyJjb2RlIjoiY2xhc3NEaWFncmFtXG5cdEpvYk9mZmVyIFwiMVwiICotLSBcIjAuLm5cIiBKb2JBcHBsaWNhdGlvblxuXHRcblx0XHRcdFx0XHQiLCJtZXJtYWlkIjp7InRoZW1lIjoiZGVmYXVsdCJ9fQ)

JobOffer "1" *-- "0..n" JobApplication


