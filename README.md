# AngularJS Training
This REST API has been designed for AngularJS trainings

## GET /tweets
Fetch the list of tweets.
The order is not guaranteed.

```json
[
  {
    "id": 0,
    "authorName": "@Alex",
    "message": "Hello World!",
    "authorEmailHash": "265033d8a18c8d568ef2d676bda17f1d",
    "date": 1392989865956,
    "answers": 0,
    "answer": false
  },
  ...
]
```

* answers: number of answers to a tweet
* answer: flag indicating whether a tweet is an answer or not

## GET /tweets/paged?page=&size=&search=
Fetch the list of tweets by page.

* page: index of the page (0 based, default value 0)
* size: number of tweets to retrieve by page (default value 20)
* search: search over authorName, authorEmail and message (default value "")

## POST /tweets
Add a tweet.

**Payload**
```json
{
    "authorEmail": "anunesse@excilys.com",
    "authorName": "@Alex",
    "message": "Hello World !"
}
```

**Result**
```json
{
    "id": 0,
    "authorName": "@Alex",
    "message": "Hello World!",
    "authorEmailHash": "265033d8a18c8d568aa2d676bda17f1d",
    "date": 1392989165956,
    "answers": 0,
    "answer": false
}
```

## POST /tweets/```{tweet_id}```/like
Like a tweet.

> Same result than **POST /tweets**

## POST /tweets/```{tweet_id}```/dislike
Dislike a tweet.

> Same result than **POST /tweets**

## POST /tweets/```{tweet_id}```/answers
Answer to a tweet.
Note that if you answer an answer, it is strictly equivalent
to answering the origin tweet.

> Same payload and result than **POST /tweets**

## GET /tweets/```{tweet_id}```/answers
Returns the given tweet plus all its answers. If the given is an answer,
returns the original tweet plus all its answers. Order is not guaranteed.

> Same result than **GET /tweets**