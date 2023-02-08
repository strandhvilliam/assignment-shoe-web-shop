# assignment-shoe-web-shop

This is an school-assignment to use JDBC and some functional programming practices to retrieve data from a MySQL database and display a web-shop for customers to use. I decided to further build the front-end using node.js and typescript. Since I did not want to use Spring for the Java backend, I made an TCP server that sends the requested data in JSON format. The node.js application sends requests and handles the received data and further uses the express framework to send data to browser application. We were also tasked with providing a report interface to see most sold products and similar statistics.

I have not included the necessary MySQL database or properties file in this project so it probably wont run as expected if forked.


### Technologies
- Java JDK 19
- node.js v18.12.1
- TypeScript 4.9.5
- Jackson for json converting
- Express 4.18
- HTML and CSS


### Screenshots

![image](https://user-images.githubusercontent.com/87245022/217496482-c13e48ae-5499-428f-acd6-10eb1c1e3043.png)
![image](https://user-images.githubusercontent.com/87245022/217496561-21f9968f-c665-4788-b6a1-b4873be1b88e.png)
![image](https://user-images.githubusercontent.com/87245022/217496673-c835e3e0-bfa0-4176-8935-1c6248c5e297.png)
