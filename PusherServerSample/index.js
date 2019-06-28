const express = require("express");
const bodyParser = require("body-parser");
const Chatkit = require("@pusher/chatkit-server");
const app = express();

const chatkit = new Chatkit.default({
  instanceLocator: "v1:us1:ffa1e6ad-5dba-40a8-9d2a-a2868a8879bb",
  key:
    "59e3d93b-0421-4d03-b3ef-c6178ad92168:GY1rDDFRCfYTmAXlFI+ZFuju3uGgmKNujQHt10vC/S4="
});

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));


app.post("/users", (req, res) => {
  const userId = req.body.userId;
  chatkit
    .createUser({
      id: userId,
      name: userId
    })
    .then(() => {
      res.sendStatus(200);
    })
    .catch(err => {
      console.log(err);
      if (err.error === "services/chatkit/user_already_exists") {
        console.log(`User already exists: ${userId}`);
        res.sendStatus(200);
      } else {
        res.status(err.status).json(err);
      }
    });
});

app.post("/token", (req, res) => {
  const result = chatkit.authenticate({
    userId: req.query.user_id
  });
  res.status(result.status).send(result.body);
});

const server = app.listen(3000, () => {
  console.log(`Express server running on port ${server.address().port}`);
});
