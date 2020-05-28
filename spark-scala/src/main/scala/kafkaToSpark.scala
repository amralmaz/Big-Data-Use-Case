package com.sparkbyexamples.spark.streaming.kafka.avro
import org.apache.spark.sql.functions.{col, from_json}
import org.apache.spark.sql._
import org.apache.spark.sql.types._


object kafkaToSpark {
  def main(args: Array[String]): Unit = {

    val spark: SparkSession = SparkSession.builder()
      .master("local[4]")
      .appName("kafkaToSpark")
      .getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")

    val df = spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "http://quickstart.cloudera:9092")
      .option("subscribe", "Tweets")
      .option("startingOffsets", "latest") // From starting
      .load()

    println("start")
    df.printSchema()

    val schema = StructType(Seq(
      StructField("id", LongType, true),
      StructField("text", StringType, true),
      StructField("lang", StringType, true),
      StructField("user_id", LongType, true),
      StructField("user_name", StringType, true),
      StructField("user_screenName", StringType, true),
      StructField("user_location", StringType, true),
      StructField("user_followersCount", IntegerType, true),
      StructField("verified", BooleanType, true),
      StructField("possibly_sensitive", BooleanType, true),
      StructField("timestamp_ms", LongType, true)
    ))

    val df_tweet = df.selectExpr("CAST(value AS STRING)") // First convert binary to string
      .select(from_json(col("value"), schema).as("data")).select("data.id", "data.text","data.lang"
      ,"data.user_id","data.user_name","data.user_screenName"
      ,"data.user_location","data.user_followersCount"
      ,"data.verified","data.possibly_sensitive","data.timestamp_ms")
      .filter(col("data.id").isNotNull)
      .filter(col("data.user_location").isNotNull)

      df_tweet.printSchema()

      df_tweet.drop("value")
      .writeStream
      .foreachBatch((batchDs: Dataset[_], batchId: Long) => {
        batchDs
        .write
        .mode(SaveMode.Append).parquet("hdfs://quickstart.cloudera:8020/user/hive/warehouse/Tweets/")
      }).start().awaitTermination()

  }
}