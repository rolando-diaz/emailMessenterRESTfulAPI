Messaging Service (github) (RESTful API)

– This RESTful API is one of the backbones of my portfolio, it's running as an stand-alone microservice ready to receive and respond to http requests. Upon each HttpRequest from the Contact page, the form parameters are sent out to the RESTful API over the browser as a JSON object.

The RESTful API performs the following tasks;

1) It extracts the Cookies and JWT(jason web token) from the HttpRequest and checks previously stored history.
2) It checks whether the client's email is in our local Memcached or database(MySql) history.
3) It sends the message to my personal email.
4) It sends a confirmation message to the client's email, a JSON response message and a Cookie/JWT(jason web token) to the host client.
5) Post request pattern: send JSON {email : "*", name : "*", body : "*"} to www.rdiaz.com/email.