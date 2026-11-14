from flask import Flask, request
import json

app = Flask(__name__)

currentPartiesJSONString = '[]'
currentPartiesJSON = json.loads(currentPartiesJSONString)

@app.route("/")
def hello_world():
    return "you found me!"

@app.route('/api/data/unregister', methods=['POST'])
def removePartyData():
    global currentPartiesJSONString
    global currentPartiesJSON
    data = request.get_json()
    id = data["id"][0]
    for i in range (len(currentPartiesJSON)):
        if(currentPartiesJSON[i]["id"] == id):
            currentPartiesJSON.pop(i)
    currentPartiesJSONString = json.dumps(currentPartiesJSON)
    print(f"Unregistered party with id: {id}")
    return "Party unregistered", 201

@app.route('/api/data', methods=['POST'])
def postPartyData():
    global currentPartiesJSONString
    global currentPartiesJSON
    data = request.get_json()
    id = data["id"][0]
    note = data["note"][0]
    size = data["size"][0]
    owner = data["owner"][0]
    newParty = {"id": id, "note": note, "owner": owner, "size": size}
    currentPartiesJSON.append(newParty)
    currentPartiesJSONString = json.dumps(currentPartiesJSON)
    print(f"Registered party with id: {id}")
    return "Party Registered", 201

@app.route('/api/data', methods=['GET'])
def getPartyData():
    return currentPartiesJSONString

if __name__ == "__main__":
    app.run(debug=True, port=5678)