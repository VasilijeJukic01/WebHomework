# Public chat
Using Java sockets, create a chat application that enables public communication among all connected users. <br>
It is necessary to create a server that allows clients to connect. After establishing a connection, client identification is required, i.e., the server will request a username from each new client. The username must be unique, and it is not possible for more than one client to have the same username simultaneously. After defining the username, the connected client can view the message history and send new messages.

Upon successful connection and entry of the username, print a welcome message in the client's console, and inform all other connected clients about the connection of the new client. For the connected client, list the message history after the welcome message. Message history includes previously sent messages, their authors, and the date and time of sending. The message history is stored in the server's memory, with a maximum of 100 latest messages.

The server constantly awaits new messages from clients. Store each received message in the message history and then notify all other connected clients about the incoming message—update the history.

On the server, it is necessary to provide a predefined set of censored words. Check each message, and in case it contains any of the censored words, modify that word so that all letters except the first and last contain an asterisk ("*").

When receiving a new message, clients should display it in their console in the format: <br>
[Date and time of the received message] - [Username of the client who sent the message]: [Content of the message].