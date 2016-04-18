/**
  * Created by admin-k on 2016/4/18.
  */

import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.services.s3._
import scala.collection.JavaConversions._
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import com.amazonaws.services.s3.model.{ListObjectsRequest, PutObjectRequest};

object s3operation extends App {
  val credentials = new ProfileCredentialsProvider().getCredentials()
  val s3Client = new AmazonS3Client(credentials)
  println("===========================================")
  println("Getting Started with Amazon S3");
  println("===========================================\n")

  val listBucket = s3Client.listBuckets()
  listBucket map (bucket => println("bucket name: " + bucket.getName))

  println("Uploading a new object to S3 from a file\n")
  val bucketName = "fffffe-ffff"
  val key = "d/test.txt"
  s3Client.putObject(new PutObjectRequest(bucketName, key, createSampleFile()))

  println("Listing objects")
  val listobject = s3Client.listObjects(new ListObjectsRequest()
    .withBucketName(bucketName)
    .withPrefix("d"));
   listobject.getObjectSummaries map
     (object_name => println("object name: " +
       object_name.getKey + "\n" +
     "object size: " + object_name.getSize))

  println("Deleting an object\n");
  s3Client.deleteObject(bucketName, key);



  def createSampleFile() : File = {
    val file = File.createTempFile("aws-java-sdk-", ".txt");
    file.deleteOnExit();
    val writer = new OutputStreamWriter(new FileOutputStream(file));
    writer.write("abcdefghijklmnopqrstuvwxyz\n");
    writer.write("01234567890112345678901234\n");
    writer.write("!@#$%^&*()-=[]{};':',.<>/?\n");
    writer.write("01234567890112345678901234\n");
    writer.write("abcdefghijklmnopqrstuvwxyz\n");
    writer.close();
    return file;
  }
}
