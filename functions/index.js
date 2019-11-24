const functions = require('firebase-functions');

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });
const json = [
{
    "name": "PsOC - 1",
    "year": " 2001",
    "purpose": "hunidity sensor",
    "manufacturer": "Cypress",
    "price": "25",
    "photoUrl": "https://5.imimg.com/data5/YU/RX/MY-4167793/dht-11-humidity-sensor-500x500.jpg"

},
{
    "name": "i7HQ12",
    "year": "2015",
    "purpose": "heat sensor",
    "manufacturer": "ChinaRer",
    "price": "34",
    "photoUrl": "https://4.imimg.com/data4/XE/YV/MY-6126061/gst-heat-sensor-500x500.jpg"
},
{
    "name": "WhoIsHere?",
    "year": "2019",
    "purpose": "walking sensor",
    "manufacturer": "SBU",
    "price": "190",
    "photoUrl": "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcShzY8rTKUxEB4zCTSA5jxeoZiHkKPSuWeeIDYAty2j_LMiCTIKYA&s"
},
{
    "name": "PuIk-20",
    "year": "2015",
    "purpose": "pulse sensor",
    "manufacturer": "IBM",
    "price": "400",
    "photoUrl": "https://images-na.ssl-images-amazon.com/images/I/61Z6jT2SSZL._SX425_.jpg"
},
{
    "name": "LiP-4912",
    "year": "2010",
    "purpose": "Lighting sensor",
    "manufacturer": "Cypress",
    "price": "45",
    "photoUrl": "http://www.ilumtech.eu/wp-content/uploads/freshizer/90fb7577d83e75cbfe5c96d442bd3d0c_new-sensors-360-240-c.jpg"
}
]
exports.sensors = functions.https.onRequest((request, response) => {response.send(json);
});