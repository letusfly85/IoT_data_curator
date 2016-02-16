var AWS = require('aws-sdk')
AWS.config.loadFromPath('node/config.json');

var dynamo = new AWS.DynamoDB({region: 'us-east-1', endpoint: 'http://127.0.0.1'})
dynamo.listTables(console.log.bind(console))

var params = {
    TableName : "IotData",
    KeySchema: [       
        { AttributeName: "id",     KeyType: "HASH"},  //Partition key
        { AttributeName: "detail", KeyType: "RANGE" }  //Sort key
    ],
    AttributeDefinitions: [       
        { AttributeName: "id", AttributeType: "N" },
        { AttributeName: "detail", AttributeType: "S" }
    ],
    ProvisionedThroughput: {       
        ReadCapacityUnits: 1, 
        WriteCapacityUnits: 1
    }
};

dynamo.createTable(params, function(err, data) {
    if (err) {
        console.error("Unable to create table. Error JSON:", JSON.stringify(err, null, 2));
    } else {
        console.log("Created table. Table description JSON:", JSON.stringify(data, null, 2));
    }
});

dynamo.describeTable(params, function(err, data) {
    if (err) {
        console.log(err, err.stack);
    } else {
        console.log(data);
    }
});

dynamo.listTables(console.log.bind(console))
