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

4. Run the application and test the home endpoint.

```
http://localhost:9090/home
```
5. Click on the option to sign up and create an account (any simple password will work for now). You will be redirected to the login page.

6. Log in with the credentials you used to create an account in the previous step (email and password).

7. Now that you are authenticated, you can interact with the application (go to next step) or view the API documentation for users (go to Step 9).

8. At this stage, there are no reviews. Try posting a review!

9. To view the Swagger UI documentation for the users section of the API, visit 

```
http://localhost:9090/swagger-ui.html
```

## Deployment

If you need help with creating a locally persisted Postgres database for the application or deploying it to the cloud, please reach out to me.
(The API will be deployed to the cloud once it is completed so that the [Android client](https://github.com/israelopeters/rti-reviews-android) can easily access it.)


## Built With

* [Spring Boot](https://spring.io/projects/spring-boot) - Java framework used
* [Spring Security](https://spring.io/projects/spring-security) - Java-based authentication and authorization framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [Docker](https://www.docker.com/) - To be used to containerize the application for cloud deployment
* [AWS RDS](https://aws.amazon.com/rds/) - To be used to persist the database on the cloud
* [AWS Elastic Beanstalk](https://aws.amazon.com/elasticbeanstalk/) - To be used for hosting the application on the cloud
* [AWS S3](https://aws.amazon.com/s3/) - To be used for storing app images
* [SwaggerUI/Open API](https://swagger.io/tools/swagger-ui/) - Used to document the API

## Author

* **Israel Peters** - *Entire Project* - [israelopeters](https://github.com/israelopeters)
