# Video rental store

## What assumptions/decisions were made?

The solution is generic and could be potentially used for any kind of **rentable** items, so in code the term video is not used in favor to rentable. 

The API was split into following operations:
1. Calculating rental prices for specified rentables and rental length.  
2. Renting specified rentables for specified periods of time.
3. Calculating surcharges for specified rentals.
4. Browsing rentables inventory.
5. Opening customer account.
6. Browsing rentables inventory.

Each of the above operations is exposed by a REST endpoint as described below.
The flow of these operations is specified in BDD style acceptance tests (src/test/resources/rental.feature).

## What architecture/design patterns were used?

1. Hexagonal architecture
2. Command/handler style API design
3. Rich domain model with DDD building blocks (aggregates, vo, entities, repositories)
4. CQRS (invetory search split from the write model)

## What technologies were used?

1. Java 8
2. Maven
3. Spring Framework
* Spring Boot to wire up the app
* Spring Web MVC for REST API
* Spring Test for acceptance test support
4. Junit 4 for unit tests
5. Cucumber for BDD acceptance testing. 
6. No real DB was used. Only fake inmemory repositories.

## How to run the program?

The following launches the web application on localhost:8080

```
mvn spring-boot:run  
```

After that it's possible to execute the API calls.

## How to use the program API?

### Getting the available videos

```
curl -X GET http://localhost:8080/rentables
```

Sample response:

```javascript
[
    {
        "id": "8acf807d-f3fb-401a-bd7a-58128a60cfa9",
        "name": "The Millers 2",
        "category": "NEW_RELEASE"
    },
    {
        "id": "68307056-c89c-4d8a-9ccf-ba15591d391b",
        "name": "Batman",
        "category": "OLD_FILM"
    },
    {
        "id": "215ae3f6-b3d7-49f2-9cd9-15d82746d2f1",
        "name": "The Millers",
        "category": "REGULAR"
    }
]
```

The video ids received here should be used in the calls to rent/return videos.

### Opening customer account

To rent the videos, it's necessary to create customer account by following API call:

```
curl -X POST \
  http://localhost:8080/customers \
  -H 'content-type: application/json' \
  -d '{"name": "Customer name"}'
```

Sample response:
````
{
    "customerId": "b6e44e6a-4b39-401b-8726-f741d6d05a30"
}
````

The received customerId should be used in the call to rent videos. 

### Calculating price

To calculate rental price (w/o actualy renting the videos) use the following call:

````
curl -X PUT \
  http://localhost:8080/rentals/prices \
  -H 'content-type: application/json' \
  -d '{
    "toRent": {
        "7ddf3d26-1821-4c44-82d5-b72a69f1307b": 3,
        "cf793e99-9355-4ceb-8401-588a253ad615": 4
    }
}'
````

toRent is a map where keys are video ids and values are rental length for each video.

In response the system returns price calculation as follows:

````
{
    "items": [
        {
            "rentableId": "7ddf3d26-1821-4c44-82d5-b72a69f1307b",
            "name": "The Millers",
            "price": {
                "currencyCode": "SEK",
                "currency": "SEK",
                "amount": 30
            },
            "days": 3
        },
        {
            "rentableId": "cf793e99-9355-4ceb-8401-588a253ad615",
            "name": "The Millers 2",
            "price": {
                "currencyCode": "SEK",
                "currency": "SEK",
                "amount": 160
            },
            "days": 4
        }
    ],
    "total": {
        "currencyCode": "SEK",
        "currency": "SEK",
        "amount": 190
    }
}
````

The calculation consists of line items and the total cost of rental.

### Renting videos

To rent one or more videos, use the following api call:

````
curl -X POST \
  http://localhost:8080/rentals \
  -H 'content-type: application/json' \
  -d '{
    "customerId": "5b5faab1-2e14-4f8e-ae38-63df1ebe2ad5",
    "toRent": {
        "7ddf3d26-1821-4c44-82d5-b72a69f1307b": 1	
    }
}'
````

toRent is a map where keys are video ids and values are numbers of days for which the customer wants to rent a particular video.

In response the application returns the id of the rental, which should be used to calculate surcharges:

```
{
    "rentalId": "11bc985a-8bca-4f83-b368-bbadf43b1385"
}
```

The videos are then marked as rent and cannot be rent again unless they are returned.

### Calculating surcharges

When videos are kept longer than declared, calculate surcharges as follows:

```
curl -X GET http://localhost:8080/rentals/a9dd6971-26a1-4640-9286-0f6fcd8886c1/surcharges
```

In the url, provide the rental id obtained in a call to rent endpoint.

For each not-returned item, the system will calculate the surcharge. Sample response:

```
{
    "items": [
        {
            "rentableId": "7ddf3d26-1821-4c44-82d5-b72a69f1307b",
            "name": "The Millers",
            "price": {
                "currencyCode": "SEK",
                "currency": "SEK",
                "amount": 0
            },
            "days": 0
        }
    ],
    "total": {
        "currencyCode": "SEK",
        "currency": "SEK",
        "amount": 0
    }
}
```

### Returning videos

To return one or more videos call the following:

```
curl -X PUT \
  http://localhost:8080/rentals/return \
  -H 'content-type: application/json' \
  -d '{
    "toReturn": ["8acf807d-f3fb-401a-bd7a-58128a60cfa9"]
}'
```

In toReturn array specify ids of the videos to return.

As a result of the call videos will be marked as returned and can be rent again.

## How to run tests?

The following executes both unit (junit 4) and acceptance tests (Cucumber):

```
mvn test
```