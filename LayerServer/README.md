
The code in this folder represent a simple backend to generate identity tokens using Node.js.  

# Deploying Layer Identity Server Sample Backend to Heroku

You can easily deploy this node js code to Heroku.

Install the [Heroku Toolbelt](https://toolbelt.heroku.com/) to start, then follow their 'Getting Started' instructions, including logging in the first time:

    % heroku login
    Enter your Heroku credentials.
    Email: youremail@example.com
    Password:
    Could not find an existing public key.
    Would you like to generate one? [Yn]
    Generating new SSH public key.
    Uploading ssh public key /Users/you/.ssh/id_rsa.pub

Inside your new directory, make sure you've created a git repository, and that your work is committed:

    % git init
    % git add .
    % git commit -m "Initial commit"

Then create a Heroku application:

    % heroku create
    Creating young-waterfall-2641... done, stack is cedar
    http://young-waterfall-2641.herokuapp.com/ | git@heroku.com:young-waterfall-2641.git
    Git remote heroku added

Before you deploy the application, you'll need to configure some environment
variables for the server to use. You can get these values from the Layer Dashboard under Authentication:

    % heroku config:set LAYER_PROVIDER_ID=yourlayerproviderid
    % heroku config:set LAYER_KEY_ID=yourlayerkeyid

This code will look for the private key inside a file layer-key.pem.  You will get the private key when you generate a Authentication key in the dashboard.  You can optionally add the private key as a environment variable:

```
% heroku config:set PRIVATE_KEY="-----BEGIN RSA PRIVATE KEY-----
...
-----END RSA PRIVATE KEY-----"
```

At this point, you are ready to deploy and start chatting. With Heroku, that's a
git push away:

    % git push heroku master

You'll see some text flying, and eventually some success. You should be able to
see your bot in your configured chat rooms at this point. If not, you can peek
at the logs to try to debug:

    % heroku logs

If you make any changes to your layer, just commit and push them as
before:

    % git commit -am "Updating server code"
    % git push heroku master

## Example Usage:

###Parameters:

* user_id:  The user ID of the person you want to authenticate
* nonce: The nonce you receive from Layer. See [docs](https://developer.layer.com/docs/guide#authentication) for more info

###Output:
A JSON object containing the identity_token.

### Example curl:
```console
curl -H "Content-Type: application/json"\
     -X POST -d '{"user_id":"USER_ID","nonce":"NONCE"}'\
     https://HEROKU_URL/authenticate
```

Example Result:

```json
{
    "identity_token": "eyJ0eXAiOiJKV1MiLCJhbGciOiJSUzI1NiIsImN0eSI6ImxheWVyLWVpdDt2PTEiLCJraWQiOiI2OWZkZDVhYS02NDc4LTExZTQtOTdmMS0xZGVkMDAwMDAwZTYifQ.eyJpc3MiOiI1YTczMWE0Yy02M2JlLTExZTQtOTEyNC1hYWE1MDIwMDc1ZjgiLCJpYXQiOjE0MTUxNTExMTcsImV4cCI6MTQxNTE2MTExNywibmNlIjoibkcrWHZFb0c3dDBXSEZrZjN0QmlWdEdjekFsTXArUmwydXVqWkN0TVJsSEcxb1FVU05BSXZnM0ZTbnFzTDhiNlFFK2pIZU8vZHJsZ2FJNXJnRXVIR2c9PSJ9.SPVPzzN7S09OafQZV7E_LvHF1mmvj5VU0Kn780ef4tegwLUS_pYj7ODfgSZPS2-MNRFhYb5ACZqtoxNkv32CJBzJzFuwBkZ3CsuX8xdpeXWEqvYtK2OV73x1TNA8RGmWyVjVKq7xjpGUORkLk7KQW3QRrQGSVx_jeOiUxb9HvZI"
}
```

Instructions inspired by [Deploying Hubot onto Heroku](https://github.com/github/hubot/blob/master/docs/README.md)
