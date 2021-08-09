
# POST
post https://deofrqlgq9.execute-api.us-east-2.amazonaws.com/Prod/books/

{
    "title-1": "test title 1",
    "author-1": "author name"
}

response:

{
    "title-1": "test title 1",
    "author-1": "author name",
    "_id": {
        "timestamp": 1628530126,
        "counter": 8169902,
        "randomValue1": 12671854,
        "randomValue2": 28279
    }
}


# GET   177ms
get https://deofrqlgq9.execute-api.us-east-2.amazonaws.com/Prod/books/

[
    {
        "_id": {
            "timestamp": 1628530126,
            "counter": 8169902,
            "randomValue1": 12671854,
            "randomValue2": 28279
        },
        "title-1": "test title 1",
        "author-1": "author name"
    },
    {
        "_id": {
            "timestamp": 1628530332,
            "counter": 8169903,
            "randomValue1": 12671854,
            "randomValue2": 28279
        },
        "title-2": "test title 2",
        "author-2": "author name"
    }
]

