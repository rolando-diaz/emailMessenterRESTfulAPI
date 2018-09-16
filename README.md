Messaging Service (github) (RESTful API)

– This RESTful API is one of the backbones of my portfolio, it's running as an stand-alone microservice ready to receive and respond to http requests. Upon each HttpRequest from the Contact page, the form parameters are sent out to the RESTful API over the browser as a JSON object.

The RESTful API performs the following tasks;

>It extracts the Cookies and JWT(jason web token) from the HttpRequest, and checks if the remote client(ip address) has sent me more than 50 email messages today.

>It checks whether the client's email is in our local Memcached or database(MySql) history, each email is only allowed to send me 10 messages per day.

>It sends the message to my personal email and a confirmation message to the client's email.

>Finally, it sends back a JSON response message to the host client. The HttpResponse also contains a Cookie and a JWT token with the host messages count.

Note: Send a post http request {email : "*", name : "*", body : "*"} to www.rdiaz.com/email or go to the Contact page in order to test it.