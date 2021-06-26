# Masters of Renaissance - IngSw 2021 final test

Masters of Renaissance, a family strategy game for 1-4 players in the acclaimed world of Lorenzo il Magnifico, is the final test 
of "Software Engineering" course at Politecnico di Milano (2020/2021).

![Logo](src/main/resources/images/login_image.jpg)

#Group members:
- ##  [Gabriele Ginestroni](https://github.com/gabrieleginestroni)
- ##  [Giacomo Gumiero](https://github.com/giagum)
- ##  [Tommaso Capacci](https://github.com/TommasoCapacci)

## Implemented Functionalities
| Functionality | Status |
|:-----------------------|:------------------------------------:|
| Basic rules | [![GREEN](http://placehold.it/15/44bb44/44bb44)]() |
| Complete rules | [![GREEN](http://placehold.it/15/44bb44/44bb44)]() |
| Socket |[![GREEN](http://placehold.it/15/44bb44/44bb44)]() |
| CLI | [![GREEN](http://placehold.it/15/44bb44/44bb44)]() |
| GUI |[![GREEN](http://placehold.it/15/44bb44/44bb44)]() |
| Multiple games | [![GREEN](http://placehold.it/15/44bb44/44bb44)]()|
| Resilience to disconnections | [![GREEN](http://placehold.it/15/44bb44/44bb44)]() |
| Parameters editor | [![RED](http://placehold.it/15/f03c15/f03c15)]() |
| Local game | [![RED](http://placehold.it/15/f03c15/f03c15)]() |
| Persistence | [![RED](http://placehold.it/15/f03c15/f03c15)]() |

#### Legend
[![GREEN](http://placehold.it/15/44bb44/44bb44)]() Implemented &nbsp;&nbsp;&nbsp;&nbsp;[![RED](http://placehold.it/15/f03c15/f03c15)]() Not Implemented 

## Setup
In [deliveries/jar](https://github.com/gabrieleginestroni/ing-sw-2021-ginestroni-gumiero-capacci/tree/master/deliverables/final/jar) folder there are three jar files. Here's how to run the provided jars:

- #####server.jar
  ```shell
   > java -jar server.jar [-p PORT_NUMBER]
    ```
  * _port_number_ specifies the port where the server will be hosted (port 50000 used if no port has been specified). 
  
- #####cli.jar
  ```shell
   > java -jar cli.jar [-ip SERVER_IP] [-p PORT_NUMBER]
  ```
  * _server_ip_ specifies the server ip address (localhost if no ip has been specified)\
  * _port_number_ specifies the server port  (port 50000 used if no port has been specified).
- #####gui.jar
  ```shell
  > java -jar gui.jar
    ```
 