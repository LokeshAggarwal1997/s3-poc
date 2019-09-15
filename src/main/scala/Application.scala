import software.amazon.awssdk.auth.credentials.{AwsCredentials, AwsCredentialsProvider}
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest


object Application extends App {

  val region = Region.AP_SOUTH_1
  val bucket: String = "hello" + System.currentTimeMillis()

  val credentials = new AwsCredentialsProvider {
    override def resolveCredentials(): AwsCredentials = new AwsCredentials {
      override def accessKeyId(): String = ""
      override def secretAccessKey = ""
    }
  }

  val client: S3Client = S3Client.builder().credentialsProvider(credentials).region(region).build()


//  val v = DeleteObjectRequest.builder()
//    .bucket("hellobottle")
//    .key("tom_clancys_ghost_recon_wildlands_season_pass-wallpaper-1366x768.jpg")
//    .versionId("mUCkKUF5p7yK7gnSTRzmtlPgno3FfNBo")
//    .build()

 // client.deleteObject(v)

  println("------")

//  val m = operations.getAllObjectsListOfVersionBucket(client, "hellobottle")

  val p: Unit = operations.listAllBucket(client)
}
