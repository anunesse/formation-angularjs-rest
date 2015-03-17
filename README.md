# AngularJS Training
This REST API has been designed for AngularJS trainings

## GET /tweets
Fetch the list of tweets.
The order is not guaranteed.

```
[
  {
    "id": 0,
    "authorName": "@Alex",
    "message": "Hello World!",
    "authorEmailHash": "265033d8a18c8d568ef2d676bda17f1d",
    "date": 1392989865956,
    "answers": 0|n,
    "answer": true|false
  },
  …
]
```

## POST /tweets
Add a tweet.

**Payload**
```
{
    "authorEmail": "anunesse@excilys.com"
    "authorName": "@Alex"
    "message": "Hello World !"
}
```

**Result**
```
{
    "id": 0,
    "authorName": "@Alex",
    "message": "Hello World!",
    "authorEmailHash": "265033d8a18c8d568aa2d676bda17f1d",
    "date": 1392989165956,
    "answers": 0,
    "answer": true|false
}
```

## POST /tweets/```{tweet_id}```/answers
Answer to a tweet.
Note that if you answer an answer, it is strictly equivalent
to answering the origin tweet.

> Same payload and result than **POST /tweets**

## GET /tweets/```{tweet_id}```/answers
Returns the given tweet plus all its answers. If the given is an answer,
returns the original tweet plus all its answers. Order is not guaranteed.

> Same result than **GET /tweets**