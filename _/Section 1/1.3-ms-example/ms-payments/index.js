var restify = require('restify');
var errors = require('restify-errors');
var corsMiddleware = require('restify-cors-middleware')

var server = restify.createServer();

var allTransactions = [
    {
        "id": "59be8061-6ac5-403c-a989-aeeba266873a",
        "currency": "EUR",
        "amount": 50.25,
        "cardDetails": {
            "type": "VISA",
            "last4": "1234"
        }
    },
    {
        "id": "a9756bb5-e8cb-4e24-8b1f-378d4f60a0c8",
        "currency": "EUR",
        "amount": 5.10,
        "cardDetails": {
            "type": "MASTERCARD",
            "last4": "1234"
        }
    },
    {
        "id": "b16ad377-29c2-454f-a368-36236dcd0a73",
        "currency": "EUR",
        "amount": 23.99,
        "cardDetails": {
            "type": "AMEX",
            "last4": "1234"
        }
    },
    {
        "id": "963f3f9e-f846-457e-958b-18f6e27aaade",
        "currency": "EUR",
        "amount": 74.49,
        "cardDetails": {
            "type": "UNIONPAY",
            "last4": "1234"
        }
    }
];

const cors = corsMiddleware({origins: ['*']});

server.pre(cors.preflight)
server.use(cors.actual)
server.use(restify.plugins.acceptParser(server.acceptable));

server.get('/v1/transactions/', (req, res, next) => {

    res.send(allTransactions);
    next();
});

server.get('/v1/transactions/:id', (req, res, next) => {

    var transaction = allTransactions.find(t => t.id == req.params.id);

    if (transaction) {
        res.send(transaction);
    } else {
        res.send(new errors.ResourceNotFoundError());
    }

    next();
});

server.listen(process.env.PORT || 8080, function() {
  console.log('%s listening at %s', server.name, server.url);
});