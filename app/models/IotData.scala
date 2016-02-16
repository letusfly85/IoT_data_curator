package models

import java.io.InputStream

import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient
import com.amazonaws.services.dynamodbv2.document.{Item, DynamoDB}
import com.amazonaws.services.dynamodbv2.model.{ComparisonOperator, AttributeValue, Condition, QueryRequest}

import org.apache.commons.configuration.{PropertiesConfiguration}

import scala.collection.mutable.Map
import scala.collection.JavaConverters._


object IoTData {

  //def create(entities: List[IotData]): Unit = {
  def create(): Unit = {
    val inputStream: InputStream =
      getClass.getResourceAsStream("/endpoint.properties")

    val configuration: PropertiesConfiguration =
      new PropertiesConfiguration()

    configuration.load(inputStream, "UTF8")

    val endpoint = configuration.getString("dynalite.endpoint")

    val client = new AmazonDynamoDBClient(new ProfileCredentialsProvider())
    client.setEndpoint(endpoint)

    val dynamoDB = new DynamoDB(client)
    val table = dynamoDB.getTable("IotData")

    println(table.describe())

    def item(id: Int): Item = new Item().withPrimaryKey("id", id).withString("detail", "sample data")
    List(1,2,3,4,5,6).foreach {id =>
      val outcome = table.putItem(item(id))
    }

    val condition: Condition = new Condition()
        .withComparisonOperator(ComparisonOperator.EQ)
        .withAttributeValueList(new AttributeValue().withN("20"))

    val keyConditions: Seq[(String, Condition)] = Seq(("id", condition))

    val queryRequest: QueryRequest = new QueryRequest().withTableName("IotData")
        .withKeyConditions(keyConditions.toMap.asJava)

    val outcome = client.query(queryRequest)
    println(outcome.getItems)
  }
}