
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import scala.collection.JavaConverters._
import com.atilika.kuromoji.ipadic.Tokenizer
import com.atilika.kuromoji.ipadic.Token

object WordCount {

  def main(args: Array[String]) {

    val spark = SparkSession.builder.appName("Word Count Application").getOrCreate()
    import spark.implicits._

    val textFile = spark.read.textFile("text.txt")
    val counts = textFile.flatMap(line => new Tokenizer.Builder().build().tokenize(line).asScala.map(_.getSurface).distinct)
      .groupBy("value")
      .agg(count("*") as "DF")

    counts.collect().foreach(println)
      
    spark.stop()
  }
}
