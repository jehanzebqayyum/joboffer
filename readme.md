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

[![](https://mermaid.ink/img/eyJjb2RlIjoiY2xhc3NEaWFncmFtXG5cdEpvYk9mZmVyIFwiMVwiICotLSBcIjAuLm5cIiBKb2JBcHBsaWNhdGlvblxuXHRKb2JBcHBsaWNhdGlvbiBcIjFcIiAqLS0gXCIxXCIgSm9iQXBwbGljYXRpb25TdGF0dXNcblx0XG5cdFx0XHRcdFx0IiwibWVybWFpZCI6eyJ0aGVtZSI6ImRlZmF1bHQifSwidXBkYXRlRWRpdG9yIjpmYWxzZX0)](https://mermaid-js.github.io/mermaid-live-editor/#/edit/eyJjb2RlIjoiY2xhc3NEaWFncmFtXG5cdEpvYk9mZmVyIFwiMVwiICotLSBcIjAuLm5cIiBKb2JBcHBsaWNhdGlvblxuXHRKb2JBcHBsaWNhdGlvbiBcIjFcIiAqLS0gXCIxXCIgSm9iQXBwbGljYXRpb25TdGF0dXNcblx0XG5cdFx0XHRcdFx0IiwibWVybWFpZCI6eyJ0aGVtZSI6ImRlZmF1bHQifSwidXBkYXRlRWRpdG9yIjpmYWxzZX0)

```
JobOffer "1" *-- "0..n" JobApplication
JobApplication "1" *-- "1" JobApplicationStatus
```

* Aggregate Root: Job
* Agregates: JobApplication
* Value Objects: JobApplicationStatus

#### Generic sequence diagram

[![](https://mermaid.ink/img/eyJjb2RlIjoic2VxdWVuY2VEaWFncmFtXG5cdENvbW1hbmRDb250cm9sbGVyLT4-K0NvbW1hbmRIYW5kbGVyOiBoYW5kbGUoY29tbWFuZClcblx0Q29tbWFuZEhhbmRsZXItPj4rRXZlbnRTdG9yZTogZXZlbnRzOiBsb2FkKGFnZ3JlZ2F0ZUlkKVxuXHRDb21tYW5kSGFuZGxlci0-PitBZ2dyZWdhdGU6IGFwcGx5KGV2ZW50cylcblx0Q29tbWFuZEhhbmRsZXItPj5BZ2dyZWdhdGU6IGV2ZW50czogaGFuZGxlKGNvbW1hbmQpXG5cdENvbW1hbmRIYW5kbGVyLT4-RXZlbnRTdG9yZTogc3RvcmUoZXZlbnRzKVxuXHRFdmVudFN0b3JlLT4-K0V2ZW50UHVibGlzaGVyOiBwdWJsaXNoKGV2ZW50cylcblx0RXZlbnRQdWJsaXNoZXIteFJlYWRIYW5kbGVyOiBoYW5kbGUoZXZlbnQpXG5cdFJlYWRIYW5kbGVyLT4-UmVwb3NpdG9yeTogc2F2ZShyZWFkRHRvKVxuXG5cblxuXG5cdFx0XHRcdFx0IiwibWVybWFpZCI6eyJ0aGVtZSI6ImRlZmF1bHQifSwidXBkYXRlRWRpdG9yIjpmYWxzZX0)](https://mermaid-js.github.io/mermaid-live-editor/#/edit/eyJjb2RlIjoic2VxdWVuY2VEaWFncmFtXG5cdENvbW1hbmRDb250cm9sbGVyLT4-K0NvbW1hbmRIYW5kbGVyOiBoYW5kbGUoY29tbWFuZClcblx0Q29tbWFuZEhhbmRsZXItPj4rRXZlbnRTdG9yZTogZXZlbnRzOiBsb2FkKGFnZ3JlZ2F0ZUlkKVxuXHRDb21tYW5kSGFuZGxlci0-PitBZ2dyZWdhdGU6IGFwcGx5KGV2ZW50cylcblx0Q29tbWFuZEhhbmRsZXItPj5BZ2dyZWdhdGU6IGV2ZW50czogaGFuZGxlKGNvbW1hbmQpXG5cdENvbW1hbmRIYW5kbGVyLT4-RXZlbnRTdG9yZTogc3RvcmUoZXZlbnRzKVxuXHRFdmVudFN0b3JlLT4-K0V2ZW50UHVibGlzaGVyOiBwdWJsaXNoKGV2ZW50cylcblx0RXZlbnRQdWJsaXNoZXIteFJlYWRIYW5kbGVyOiBoYW5kbGUoZXZlbnQpXG5cdFJlYWRIYW5kbGVyLT4-UmVwb3NpdG9yeTogc2F2ZShyZWFkRHRvKVxuXG5cblxuXG5cdFx0XHRcdFx0IiwibWVybWFpZCI6eyJ0aGVtZSI6ImRlZmF1bHQifSwidXBkYXRlRWRpdG9yIjpmYWxzZX0)

```
sequenceDiagram
	CommandController->>+CommandHandler: handle(command)
	CommandHandler->>+EventStore: events: load(aggregateId)
	CommandHandler->>+Aggregate: apply(events)
	CommandHandler->>Aggregate: events: handle(command)
	CommandHandler->>EventStore: store(events)
	EventStore->>+EventPublisher: publish(events)
	EventPublisher-xReadHandler: handle(event)
	ReadHandler->>Repository: save(readDto)
```
