package models

import java.io.InputStream
import java.util

import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient
import com.amazonaws.services.dynamodbv2.document.{Item, DynamoDB}
import com.amazonaws.services.dynamodbv2.model.{ComparisonOperator, AttributeValue, Condition, QueryRequest}

import org.apache.commons.configuration.{PropertiesConfiguration}

import scala.collection.mutable
import scala.collection.mutable.Map
import scala.collection.JavaConverters._

case class IoTData(id: Int, detail: String)

/**
  *
  *
  *
  */
object IoTData {

  var endpoint = ""
  if (System.getProperty("dev.env") != null) {
    endpoint = System.getProperty("dev.env")
  } else {
    val inputStream: InputStream =
      getClass.getResourceAsStream("/endpoint.properties")

    val configuration: PropertiesConfiguration =
      new PropertiesConfiguration()
    configuration.load(inputStream, "UTF8")

    endpoint = configuration.getString("dynalite.endpoint")
  }

  val client = new AmazonDynamoDBClient(new ProfileCredentialsProvider())
  client.setEndpoint(endpoint)
  val dynamoDB = new DynamoDB(client)
  val tableName = "IotData"
  val table = dynamoDB.getTable("IotData")

  /**
    *
    * @param id
    * @return
    */
  def item(id: Int): Item = new Item().withPrimaryKey("id", id).withString("detail", "sample data")

  /**
    *
    * @param id
    */
  def create(id: Int): Unit = {
    table.putItem(item(id))
  }

  /**
    *
    * @param id
    * @return
    */
  def findBy(id: Int): List[mutable.Map[String, String]] = {
    val condition: Condition = new Condition()
      .withComparisonOperator(ComparisonOperator.EQ)
      .withAttributeValueList(new AttributeValue().withN(id.toString))
    val keyConditions: Seq[(String, Condition)] = Seq(("id", condition))

    val queryRequest: QueryRequest = new QueryRequest().withTableName(tableName)
      .withKeyConditions(keyConditions.toMap.asJava)

    val outcome = client.query(queryRequest)
    outcome.getItems.asScala.map(convert).toList
  }

  /**
    * @todo データ構造ごとに下記のような処理を実施するなら良いライブラリを探す。なければ自動生成。
    *
    * @param map
    * @return
    */
  def convert(map: util.Map[String, AttributeValue]): Map[String, String] = {
    val _map: Map[String, String] = Map()
    map.asScala.foreach{case (key: String, value: AttributeValue) =>
      key match {
        case "id" => _map.put(key, value.getN)
        case _ => _map.put(key, value.getS)
      }
    }
    _map
  }
}
