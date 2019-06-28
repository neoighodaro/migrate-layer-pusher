var express = require('express');
var bodyParser = require('body-parser')

var fs = require('fs')
var jsrsasign = require('jsrsasign');

var app = express();

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: false}));

app.get('/', function(req, res) {
  res.send('Welcome to the Sample Backend for Layer Authentication');  
});

var layerProviderID = process.env.LAYER_PROVIDER_ID;
var layerKeyID = process.env.LAYER_KEY_ID;
var privateKey = process.env.PRIVATE_KEY;
if (!privateKey) {
  try {
    privateKey = fs.readFileSync('layer-key.pem').toString();
  } catch(e) {
    console.error('Couldn\'t find Private Key file: layer-key.pem');
  }
}

app.post('/authenticate', function(req, res) {
  var userId = req.body.user_id;
  var nonce = req.body.nonce;
  
  if (!userId) return res.status(400).send('Missing `user_id` body parameter.');
  if (!nonce) return res.status(400).send('Missing `nonce` body parameter.');
  
  if (!layerProviderID) return res.status(500).send('Couldn\'t find LAYER_PROVIDER_ID');
  if (!layerKeyID) return res.status(500).send('Couldn\'t find LAYER_KEY_ID');
  if (!privateKey) return res.status(500).send('Couldn\'t find Private Key');

  var header = JSON.stringify({
    typ: 'JWT',           // Expresses a MIMEType of application/JWT
    alg: 'RS256',         // Expresses the type of algorithm used to sign the token, must be RS256
    cty: 'layer-eit;v=1', // Express a Content Type of application/layer-eit;v=1
    kid: layerKeyID
  });

  var currentTimeInSeconds = Math.round(new Date() / 1000);
  var expirationTime = currentTimeInSeconds + 10000;

  var claim = JSON.stringify({
    iss: layerProviderID,       // The Layer Provider ID
    prn: userId,                // User Identifier
    iat: currentTimeInSeconds,  // Integer Time of Token Issuance 
    exp: expirationTime,        // Integer Arbitrary time of Token Expiration
    nce: nonce                  // Nonce obtained from the Layer Client SDK
  });

  var jws = null;
  try {
    jws = jsrsasign.jws.JWS.sign('RS256', header, claim, privateKey);
  } catch(e) {
    return res.status(500).send('Could not create signature. Invalid Private Key: ' + e);
  }
  
  res.json({
    identity_token: jws
  });
});

var port = process.env.PORT || 3000;
app.listen(port, function() {
  console.log('Express server running on localhost:%d', port);
});
