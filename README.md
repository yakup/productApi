# PRODUCT API

h1
Unit tests and integration test are written.
CriteriaBuilder is used for shorter code to filter query params

The app is dockerized.
You can start the docker image by these commands below:
    
    docker build -t productapi .
    docker run -p 8080:8080 productapi

You can start with the request below:
http://localhost:8080/product?city=Stockholm&type=subscription&min_price=10&max_price=100