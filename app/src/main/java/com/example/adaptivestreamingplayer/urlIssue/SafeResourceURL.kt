package com.infinitylearn.learn.utils

import java.net.URL

object SafeResourceURL {
    // private const val S3_BASE_URL = "https://your-s3-bucket.s3.amazonaws.com/"
    private const val CLOUDFRONT_BASE_URL = "https://il-cms.infinitylearn.com/"

//     fun getS3Url(objectKey: String): String {
//         // Construct the S3 URL using the base URL and the object key
//         return S3_BASE_URL + objectKey
//     }

    fun getCloudFrontUrl(resourcePath: String): String {
        // Construct the CloudFront URL using the base URL and the resource path
        return CLOUDFRONT_BASE_URL + resourcePath
    }
}

// Usage example:
//val s3Url = SafeResourceURL.getS3Url("media/1645178767835.png")
