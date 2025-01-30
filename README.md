## RTI Reviews app backend specification
Backend API service to power the RTI Reviews Android app

# RTI Reviews App backend
The Android app lets users keep and share their book reviews. A user can post a review, view all the reviews on the app, filter reviews by book genre, and like and comment on other users' reviews. Also, a user can delete their own posted reviews. This documentation is for the backend API that will serve the [Android client](https://github.com/israelopeters/rti-reviews-android).

## Getting Started

For comprehensive documentation on the API and its endpoints, please see the SwaggerUI page here: (coming soon). 
Also see "Running Locally" below for notes on how to run the application on your local machine for testing purposes.


## Features

### Sign-up/Login
A user can sign up by entering basic details (email, name, country, bio) or using their Google account. The user can log in using an email and a password or through their Google account.

### Reviews List
A signed-in user can view a list of posted reviews.

### Review
Each review item contains the following: image URI, review title, body text, time stamp of original posting, likes count, comment list, reviewed book genre, and book rating.

### Profile
Each user has a profile containing the following details: profile picture, name, country, bio, date created, and list of reviews posted. A profile can be edited: profile picture, name, country, bio.

### Add/Edit/Delete Review
A new review can be added, and an existing one can be edited or deleted. 

## Using the API

### Running Locally
You can run the application quickly on your local machine with your web browser and an in-memory database. Follow these steps:


1. Fork and clone the repo.

2. Open the application.properties file and change the active profile from "dev" to "h2".

```
spring.profiles.active=h2
```

3. Clear the username and password from the application-h2.properties file.

```
spring.datasource.username=
spring.datasource.password=
```

4. Run the application and create an account (use [Postman](https://www.postman.com/downloads/)).

```
http://localhost:9090/api/v1/users/add
```
5. Log in with the credentials you used to create an account in the previous step (email and password).

6. Now that you are authenticated, you can interact with the API. But first, view the API documentation to see how (next step).

7. To view the Swagger UI/OpenAPI documentation, visit the URL below in your browser.

```
http://localhost:9090/api/v1/docs
```

8. At this stage, there are no reviews (you are using an in-memory database (H2)). Try posting a review and trying the other endpoints!


## Deployment

If you need help with creating a locally persisted Postgres database for the application or deploying it to the cloud, please reach out to me.
The API has been deployed to the cloud for easy access by the [Android client](https://github.com/israelopeters/rti-reviews-android). To interact 
with the cloud instance, please use the endpoint below:

```
http://rtireviews-api-env.eba-wp43m9p3.eu-west-2.elasticbeanstalk.com
```
For example, type the URL below in any browser (yes, without running the app locally) to view the Swagger UI/OpenAPI documentation.

```
http://rtireviews-api-env.eba-wp43m9p3.eu-west-2.elasticbeanstalk.com/api/v1/docs
```

To create an account that you can use to interact with the API endpoints, visit the following URL, using the JSON body 
format sampled underneath (see more details in the API docs above):

```
http://rtireviews-api-env.eba-wp43m9p3.eu-west-2.elasticbeanstalk.com/api/v1/users/add
```
Modify the field values accordingly (i.e., replace the given personal details with any of your choice).
```
{
    "firstName": "Israel",
    "lastName": "Peters",
    "country": "United Kingdom",
    "bio": "Well, I love reading.",
    "email": "israel@email.com",
    "password": "israel_password"
}
```

Note that email and password validation have not been implemented.


## Built With

* [Spring Boot](https://spring.io/projects/spring-boot) - Java framework used
* [Spring Security](https://spring.io/projects/spring-security) - Java-based authentication and authorization framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [Docker](https://www.docker.com/) - Used to containerize the application for cloud deployment
* [AWS RDS](https://aws.amazon.com/rds/) - Used to host the database on the cloud
* [AWS Elastic Beanstalk](https://aws.amazon.com/elasticbeanstalk/) - Used for hosting the application on the cloud
* [AWS S3](https://aws.amazon.com/s3/) - To be used for storing app images
* [SwaggerUI/Open API](https://swagger.io/tools/swagger-ui/) - Used to document the API

## Author

* **Israel Peters** - *Entire Project* - [israelopeters](https://github.com/israelopeters)
