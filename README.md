Game Score Backend Service
=======================
----------

Description
===========

Write a HTTP-based mini game back-end in Java which registers game scores for different users and levels, with the capability to return high score lists per level. There shall also be a simple login system in place (without any authentication).

Services
=========
The functions are described in detail below and the notation <value> means a call parameter value or a return value. All calls shall result in the HTTP status code 200, unless when something goes wrong, where anything but 200 must be returned. Numbers parameters and return values are sent in decimal ASCII representation as expected (ie no binary format).

Login
-----

This function returns a session key in the form of a string (without spaces or “strange” characters) which shall be valid for use with the other functions for 10 minutes. The session keys should be “reasonably unique”.

    Request: GET http://{HOST}:{PORT}/<sessionkey>/login
     Response: <sessionkey>
    <userid> : 31 bit unsigned integer number
    <sessionkey> : A string representing session (valid for 10 minutes).

Example:

    GET http://localhost:8081/4711/login --> UICSNDK

Score
-----

This method can be called several times per user and level and does not return anything. Only requests with valid session keys shall be processed.


    Request: POST http://{HOST}:{PORT}/<levelid>/score?sessionkey=<sessionkey>
    Request body: <score>
    Response: (nothing)
    <levelid> : 31 bit unsigned integer number
    <sessionkey> : A session key string retrieved from the login function.
    <score> : 31 bit unsigned integer number


Example:

    POST http://localhost:8081/2/score?sessionkey=UICSNDK
    raw: 1500


High Score List
---------------
Retrieves the high scores for a specific level. The result is a comma separated list in descending score order. Because of memory reasons no more than 15 scores are to be returned for each level. Only the highest score counts. ie: an user id can only appear at most once in the list. If a user hasn't submitted a score for the level, no score is present for that user. A request for a high score list of a level without any scores submitted shall be an empty string.


    Request: GET http://{HOST}:{PORT}/<levelid>/highscorelist
    Response: CSV of <userid>=<score>
    <levelid> : 31 bit unsigned integer number
    <score> : 31 bit unsigned integer number
    <userid> : 31 bit unsigned integer number



Example:

    http://localhost:8081/2/highscorelist - > 4711=1500,131=1220



Requirements
============

+ Java JDK 17 used.
+ JUnit 4.12 for testing proposes


Execution
=========

>       java -jar Game.jar
