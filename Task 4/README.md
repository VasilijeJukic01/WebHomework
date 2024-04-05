# Meal Planner

This is a web application that allows users to select one meal from a set of predefined meals for each day of the week. Additionally, it provides an overview of all selected meals so that the user knows the quantity of meals to provide for the upcoming week.

## Technical Requirements
- The client requires that the web application be implemented using the Servlet API.
- All available meals should be loaded from files: monday.txt, tuesday.txt… Each meal is separated by a new line.
- Furthermore, ensure that a user can only choose a meal once in their session.
- It is not allowed for the client to revisit the meal selection page during the same session and after confirming the form. Instead, they should receive a message that the order has already been placed.
- Only after a "restart" (deleting all orders), should all users be allowed to access the meal selection page again.
- Access to the selected meals page should only be granted with a password provided as a query parameter (/order?password={password}). The valid password is obtained from a file (password.txt) during Servlet initialization.