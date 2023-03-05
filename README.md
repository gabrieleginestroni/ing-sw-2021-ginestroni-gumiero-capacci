# Masters of Renaissance - IngSw 2021 final test

<img src="https://cf.geekdo-images.com/-zdSgCFfOGAsgZ6M-Rjw1w__opengraph/img/FEzUn1bObXKe0ajQ7m7U1dbJaVY=/fit-in/1200x630/filters:strip_icc()/pic4782992.jpg" width=216px height=288px align="right" />

Masters of Renaissance, a family strategy game for 1-4 players in the acclaimed world of Lorenzo il Magnifico, is the final test 
of "Software Engineering" course at Politecnico di Milano (2020/2021).

**Teacher**: Pierluigi San Pietro

**Final Score**: 30/30

<br />
<br />

## Group members:
- ###  [Gabriele Ginestroni](https://github.com/gabrieleginestroni)
- ###  [Giacomo Gumiero](https://github.com/giagum)
- ###  [Tommaso Capacci](https://github.com/TommasoCapacci)

## Implemented Functionalities
| Functionality | Status |
|:-----------------------|:------------------------------------:|
| Basic rules | [![GREEN](http://via.placeholder.com/15/44bb44/44bb44)]() |
| Complete rules | [![GREEN](http://via.placeholder.com/15/44bb44/44bb44)]() |
| Socket |[![GREEN](http://via.placeholder.com/15/44bb44/44bb44)]() |
| CLI | [![GREEN](http://via.placeholder.com/15/44bb44/44bb44)]() |
| GUI |[![GREEN](http://via.placeholder.com/15/44bb44/44bb44)]() |
| Multiple games | [![GREEN](http://via.placeholder.com/15/44bb44/44bb44)]()|
| Resilience to disconnections | [![GREEN](http://via.placeholder.com/15/44bb44/44bb44)]() |
| Parameters editor | [![RED](http://via.placeholder.com/15/f03c15/f03c15)]() |
| Local game | [![RED](http://via.placeholder.com/15/f03c15/f03c15)]() |
| Persistence | [![RED](http://via.placeholder.com/15/f03c15/f03c15)]() |

#### Legend
[![GREEN](http://via.placeholder.com/15/44bb44/44bb44)]() Implemented &nbsp;&nbsp;&nbsp;&nbsp;[![RED](http://via.placeholder.com/15/f03c15/f03c15)]() Not Implemented
## Screenshots
<p align="center">
  <img src="cli.png" class="center"/>
</p>
<p align="center">
  <img src="gui.png" class="center"/>
</p>
## Tools

Tool | Description
--------|------------
Intellij | IDE
Astah UML | UML Diagrams
Maven | Project Management
JavaFX | GUI
JUnit 5 | Testing


## Setup
To run the jars you need at least JRE version 11. Check Oracle's [download page](https://www.oracle.com/java/technologies/javase-downloads.html) for more info.

In [deliveries/jar](https://github.com/gabrieleginestroni/ing-sw-2021-ginestroni-gumiero-capacci/tree/master/deliverables/final/jar) folder there are three jar files. Here's how to run the provided jars:

- ##### server.jar
  ```shell
   > java -jar server.jar [-p PORT_NUMBER]
    ```
  * _port_number_ specifies the port where the server will be hosted (port 50000 used if no port has been specified) 
  
- ##### cli.jar
  ```shell
   > java -jar cli.jar [-ip SERVER_IP] [-p PORT_NUMBER]
  ```
  * _server_ip_ specifies the server ip address (localhost if no ip has been specified)
  * _port_number_ specifies the server port  (port 50000 used if no port has been specified)
  * It is recommended to use Linux shell to run the cli.jar due to incorrect Unicode printing in Windows' shell
  * For Windows users it is possible to run the jar from Intellij IDE. However, some special symbols won't be displayed
- ##### gui.jar
  ```shell
  > java -jar gui.jar
    ```
  * It is recommended to run the gui.jar from  Windows' shell with a FullHD screen resolution
 