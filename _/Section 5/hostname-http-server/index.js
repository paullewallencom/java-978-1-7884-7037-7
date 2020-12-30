const express = require('express')
const os = require("os")
const app = express()

app.get('/', function (req, res) {
  res.send({
      hostname: os.hostname()
  })
})

app.listen(8080, function () {
  console.log('Hostname app running on port 8080')
})