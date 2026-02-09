This si the project to learn microservices using java springboot.

First Service:
Order Service[8081]
We are going to use mongodb for database.
Structure:
id
name
description

Controller[four API endpoints]:
1. Create
2. Update[name, description]
3. Delete
4. Get One
5. Get All

Four mysql in repo we extend JPA and with mongo we extend mongorepository.
We extend these repos with our model and finding id datatype.

Then in service we import these repos

Inventory Service[8082]
Then we develop the inventory Service for maintaning inventory for products in mysql db.
but we will also counter update and delete the parts from inventory if product is deleted.
Inventory Model
1. amount
2. price
3. product id

Inventory Service will contain
1. Create
2. Update amount
3. Update Price
4. GetAll
5. Get One

Discovery Service is running on port 8084.

Order Service[8083]
Order Model:
1. productid[List]
2. Amount for each productId
3. Base Price of each productId
4. Total
5. UserId

We are goinf to make orderservice in mongodb

API Gateway appplication is running on port 8080

User Service: 8085
Login and registeration of user servcies are runnig fine and all routes except login and register are protected.

Implement the system on frontend.

Need to overhaul the frontend part completely on the same model as https://github.com/Abhisheksharmaatri/Collaborative-Code-Editor/blob/master/frontend/src/pages/Home.js


