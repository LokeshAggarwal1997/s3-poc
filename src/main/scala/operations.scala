import java.io.{File, FileOutputStream}
import java.util

import Application.client
import org.slf4j.{Logger, LoggerFactory}
import software.amazon.awssdk.awscore.exception.AwsServiceException
import software.amazon.awssdk.core.ResponseInputStream
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model._

object operations {

  val logger: Logger = LoggerFactory.getLogger("operations")

  def deleteBucket(client: S3Client, bucket: List[String]): Unit = {
    bucket.foreach(eachBucket => deleteBucket(client, eachBucket))
  }

  def deleteBucket(client: S3Client, bucket: String): DeleteBucketResponse = {
    val deleteBucket: DeleteBucketRequest = DeleteBucketRequest
      .builder()
      .bucket(bucket)
      .build()

    client.deleteBucket(deleteBucket)
  }

  def createBucket(client: S3Client, bucket: String, region: String): CreateBucketResponse = {
    val config: CreateBucketConfiguration = CreateBucketConfiguration
      .builder()
      .locationConstraint(region)
      .build()


    val request: CreateBucketRequest = CreateBucketRequest
      .builder()
      .bucket(bucket)
      .createBucketConfiguration(config)
      .build()

    client.createBucket(request)
  }


  def listAllBucket(client: S3Client): Unit = {
    val list: ListBucketsRequest = ListBucketsRequest
      .builder()
      .build()
    val listResponse: ListBucketsResponse = client.listBuckets(list)
    val p: util.List[Bucket] = listResponse.buckets()
    p.forEach(bucket => println(bucket.toString))
  }


  def deleteBucketObject(client: S3Client, bucket: String, key: String): DeleteObjectResponse = {
    val deleteObjectRequest: DeleteObjectRequest = DeleteObjectRequest
      .builder()
      .bucket(bucket)
      .key(key)
      .build()

    client.deleteObject(deleteObjectRequest)
  }

  def getAllObjectsListOfBucket(client: S3Client, bucket: String): util.List[S3Object] = {
    val request = ListObjectsRequest
      .builder()
      .bucket(bucket)
      .build()
    val k: ListObjectsResponse = client.listObjects(request)
    val c: util.List[S3Object] = k.contents()
    c
  }


  def putObjectInBucket(client: S3Client, bucket: String, key: String, path: String): Unit = {
    val p: PutObjectRequest = PutObjectRequest
      .builder()
      .bucket(bucket)
      .key(key)
      .build()
    val data: RequestBody = RequestBody.fromFile(new File(path))
    client.putObject(p, data)
  }

  def getDataFromBucket(client: S3Client, bucket: String, key: String, path: String):Unit= {
      val objectRequest: GetObjectRequest = GetObjectRequest.builder().bucket(bucket).key(key).build()
      val inputStream: ResponseInputStream[GetObjectResponse] = client.getObject(objectRequest)

      val file = new File(path)
      val f = new FileOutputStream(file)

      while (inputStream.available() != 1) {
        f.write(inputStream.read())
      }

      f.close()
      inputStream.close()
  }

  def getAllObjectsListOfVersionBucket(client: S3Client,bucket:String): util.List[ObjectVersion] ={
    val p: ListObjectVersionsRequest = ListObjectVersionsRequest.builder().bucket(bucket).build()
    val kk: ListObjectVersionsResponse = client.listObjectVersions(p)
    kk.versions()
  }

}
