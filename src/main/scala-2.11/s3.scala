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
  //IAM 的四種認證模式之一,使用user的accesskey
  //預設放在~/.aws/credentials內
  //可以在命令提示字元或在linux的terminal中打aws configure設定access key
  val credentials = new ProfileCredentialsProvider().getCredentials()
  //連接s3並放入憑證
  val s3Client = new AmazonS3Client(credentials)
  println("===========================================")
  println("Getting Started with Amazon S3");
  println("===========================================\n")
  //列出所能看到的bucket
  val listBucket = s3Client.listBuckets()
  listBucket map (bucket => println("bucket name: " + bucket.getName))
  //上傳文件到s3
  println("Uploading a new object to S3 from a file\n")
  //想要上傳的bucket名稱
  val bucketName = "fffffe-ffff"
  //想要上傳bucket內的prefix跟key都在這邊
  //請回想上課所講的key-value在s3的概念
  val key = "d/test.txt"
  //createSamlpeFile()是一個創造文件的方法在下方
                                        //bucket名稱 //key //放入的文件
  s3Client.putObject(new PutObjectRequest(bucketName, key, createSampleFile()))
  //列出bucket底下的物件及大小
  println("Listing objects")
  val listobject = s3Client.listObjects(new ListObjectsRequest()
    .withBucketName(bucketName)
    .withPrefix("d"));
   listobject.getObjectSummaries map
     (object_name => println("object name: " +
       object_name.getKey + "\n" +
     "object size: " + object_name.getSize))
  //刪除物件
  println("Deleting an object\n");
  s3Client.deleteObject(bucketName, key);


  //創造檔案的方法,使用java.io的OutputStreamWriter來寫入檔案
  //注意這邊是寫入一個暫存檔案再返回檔案供上面的方法調取File類別的這個物件
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
