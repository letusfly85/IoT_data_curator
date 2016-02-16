package models

import java.io.InputStream

import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient
import com.amazonaws.services.dynamodbv2.document.{Item, DynamoDB}

import org.apache.commons.configuration.{PropertiesConfiguration}

//case class IotData(id: Int, detail: String)

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

    val item = new Item().withPrimaryKey("id", 1).withString("detail", "sample data")
    List(1,2,3,4,5,6).foreach {id =>
      val outcome = table.putItem(new Item().withInt("id", id).withString("detail", s"sample data ${id}"))

      println(outcome)
    }

  }
}