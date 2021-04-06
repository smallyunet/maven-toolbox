var express = require('express');
var app = express();
var bodyParser = require('body-parser')

app.use(bodyParser.json());

app.get('/get', function (req, res) {
    res.send({
        "key": "value"
    });
})

app.post('/post', function (req, res) {
    if (JSON.stringify(req.body) == JSON.stringify({ key: 'value' })) {
        res.send(true);
    } else {
        res.send(false)
    }
})

var server = app.listen(20250, function () {
    var port = server.address().port
    console.log("http://localhost:%s", port)
})
