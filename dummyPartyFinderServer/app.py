from flask import Flask, request
import json

app = Flask(__name__)

currentPartiesJSONString = '[{"note": "&62l+ vang","members": [{"username": "PotatoSlayer56","displayName": "[MVP+] PotatoSlayer56"},{"username": "ImCitrus","displayName": "[VIP] ImCitrus"}]},{"note": "duis fugiat cillum","members": [{"username": "Jewel","displayName": "Gaines"}]},{"note": "esse esse consequat","members": [{"username": "Pruitt","displayName": "Nona"}]},{"note": "et eu culpa","members": [{"username": "Shannon","displayName": "Doyle"},{"username": "Mcintyre","displayName": "Mamie"},{"username": "Brandie","displayName": "Cathryn"}]},{"note": "pariatur occaecat ea","members": [{"username": "Gloria","displayName": "Patrica"}]},{"note": "labore est quis","members": [{"username": "Clara","displayName": "Rollins"},{"username": "Iva","displayName": "Holcomb"},{"username": "Welch","displayName": "Norris"}]},{"note": "ex sunt dolor","members": [{"username": "Robinson","displayName": "Agnes"}]}]'
currentPartiesJSON = json.loads(currentPartiesJSONString)

@app.route("/")
def hello_world():
    return "<p>le server</p>"

@app.route('/api/data/unregister', methods=['POST'])
def removePartyData():
    global currentPartiesJSONString
    global currentPartiesJSON
    id = request.get_data()
    for i in range (len(currentPartiesJSON)):
        if(currentPartiesJSON[i]["id"] == id):
            currentPartiesJSON.pop(i)
    currentPartiesJSONString = json.dumps(currentPartiesJSON)

@app.route('/api/data', methods=['POST'])
def postPartyData():
    global currentPartiesJSONString
    global currentPartiesJSON
    data = request.get_json()
    note = data["note"][0]
    members = []
    for member in data["members"][0]:
        members.append({"displayName": member["displayName"][0], "username": member["username"][0]})
    newParty = {"note": note, "members": members}
    currentPartiesJSON.append(newParty)
    currentPartiesJSONString = json.dumps(currentPartiesJSON)
    return "Party Registered", 201

@app.route('/api/data', methods=['GET'])
def getPartyData():
    return currentPartiesJSONString

if __name__ == "__main__":
    app.run(debug=True, port=5678)