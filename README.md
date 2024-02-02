# Customer UserData Application Using Spring Boot, Spring Security, JWT, JPA, React.Js and MySql

## Requirements

1. Java >= java 11

2. Maven - 3.x.x

3. Mysql - 5.x.x

4. lombok

5. VsCode

6. Vite-React App

## Steps to Setup

**1. Clone the application**

```bash
git clone https://github.com/DhanushIndrakumar/Sunbase.git
```
**2. Key points of the application**

+ The Backend files and Frontend files for the application are in 2 different folders.

+ The Frontend was done using Vite-React app through VSCode.

+ The Backend was implemented using Spring Boot through IntellijIdea.

+ The company folder contains backend files.

+ The frontend folder contains frontend files.


**3. Run the Backend application**

+ open `company/src/main/resources/application.properties`

+ change `spring.datasource.username` and `spring.datasource.password` as per your mysql installation

+ after changing both the username and password as per your mysql installation
  
+ we can run it from NetBeans or intellij IDEA.

+ The app will start running at http://localhost:2000/**
  
+ Since the port is set to localhost:2000 and the user is free to change the port if they wish.

**4. Explore Rest APIs**

+ Once the backend is working. Developer can go through the OpenApi documentation as it gives more understanding and working of the APIs.

 ![Capture](https://github.com/DhanushIndrakumar/Sunbase/assets/111871670/8d61a020-e1d0-423f-8148-3b61fb6c3406)

+ We can see the API Documentation at

```bash
    http://localhost:2000/swagger-ui.html
```
+ we need to first execute the register and login user APIs for the other APIs to work.

**5. Exploring the Api to insert or create User(Customer) in the database**

+ The register and Login API does not require any authentication as it forms the base for the user details being stored in the database and then authenticated when they attempt to login. 

+ The request body of the API with the response body is shown below:

  ![Capture2](https://github.com/DhanushIndrakumar/Sunbase/assets/111871670/22f803a8-880f-4c0e-a8b5-bd6336262841)

**6. JWT generation using Login API**

+ Api works in OpenApi documentation as well.The image below shows the request and the response body of the Login Api

  ![Capture3](https://github.com/DhanushIndrakumar/Sunbase/assets/111871670/a9e7a5cf-1110-4152-994f-49a2cddc7d14)

+ As seen in the above image JWT is being generated using which other APIs can be accessed without which 403 error will be shown.

**7. Testing the API to retrieve User(Customer) details without inserting token in the header**

![Capture4](https://github.com/DhanushIndrakumar/Sunbase/assets/111871670/a111ff6d-263b-4ea8-af75-6f7ff964ff3e)

+ It can be seen that without being authenticated the user cannot access other APIs which performs operations such Updating, Deleting and Retrieving Customer Details.

**8. Testing the API to retrive User(Customer) details with inserting JWT in the header**

![Capture5](https://github.com/DhanushIndrakumar/Sunbase/assets/111871670/93af5701-7196-4b93-a565-b58322a0e52d)

+ It can be seen that once the user is authenticated the user can access other APIs which performs operations such Updating, Deleting and Retrieving Customer Details.

+ The same works for all other APIs .JWT forms the most important aspect in any security systems.

**9. Running the Frontend application**

+ Frontend is being connected to backend APIs using Axios and it can also be performed using Fetch and Redux toolkit.

+ Open Frontend Folder through VSCode after cloning and give the command npm run dev

+ The first page that appears is as shown below:

  ![Capture6](https://github.com/DhanushIndrakumar/Sunbase/assets/111871670/0c40e0c5-0227-4e24-a113-dccfe85ac9a5)

+  The above figure is a blank image so the user must type /home in the search bar. eg:http://localhost:5173/home.

   ![Capture7](https://github.com/DhanushIndrakumar/Sunbase/assets/111871670/36f0fdb1-1aaa-4527-b2d3-a64dba2da9fc)

+ When the User clicks on next Button it is directed to login page as shown below and the user is asked to enter email and password post which JWT will be generated and the user will be authenticated.

  ![Capture8](https://github.com/DhanushIndrakumar/Sunbase/assets/111871670/f15d0425-a99c-4829-b077-e6bdb2083807)

+ After the user is being authenticated login page is directed to CustomerList page. where the user can view all the customers in the database. Search, delete, Edit and Add users to the database.

  ![Capture9](https://github.com/DhanushIndrakumar/Sunbase/assets/111871670/5998b985-1c83-4c5c-9128-39321f586231)

+ Once the user Clicks on Delete button and refresh the page, user will be removed from the database.

  

  






  



  

  

  

