# Quotes Management
This task aims to create an application for storing and viewing quotes. The main functionality of the system includes saving quotes and their authors, as well as displaying all saved entries. Additionally, the application will feature a daily quote display.

## Technical Requirements:
The system is divided into two web services - main and helper. They communicate via the HTTP and run on the local machine but listen on different ports. Communication between services is done using Java Socket, without using pre-made libraries for HTTP clients. Only libraries for JSON parsing are allowed.

Main Service:
- This service is responsible for entering, storing, and displaying quotes to users.
- The client communicates exclusively with this service.
- When a client wants to save a new quote, it sends a POST request to the /save-quote path. After that, the provided data is stored on the server, and the client is redirected to the /quotes location to view their saved quotes.

Helper Service:
- Has one functionality - returning the quote of the day.
- Internally communicates only with the main service.
- When the main service requests the quote of the day, the helper service randomly selects a quote from the existing set of quotes and returns it as a response in JSON format.