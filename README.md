# global-chat

This is a sample chat application that uses the framework Netty. The server receives the chat messages and broadcasts
them to the connected clients.

To build:
`mvn package`

To start a server:
`java -jar target/global-server.jar server IP-OF-THE-SERVER-HERE`

To start a client:
`java -jar target/global-server.jar client IP-OF-THE-SERVER-HERE`