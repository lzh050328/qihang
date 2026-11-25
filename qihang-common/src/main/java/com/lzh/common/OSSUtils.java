package com.lzh.common;

import com.aliyun.oss.*;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.common.comm.SignVersion;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.UUID;

import static java.lang.System.in;
@Component
public class OSSUtils {
    @Autowired
    private Oss oss;
//    @Value("${ali.oss.endpoint}")
//    private String endpoint;
//    @Value("${ali.oss.bucketName}")
//    private String bucketName;
//    @Value("${ali.oss.region}")
//    private String region;
    public  String upload(InputStream in,String fileName) throws Exception {
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
//        String endpoint = "https://oss-cn-beijing.aliyuncs.com";
        // 从环境变量中获取访问凭证。运行本代码示例之前，请确保已设置环境变量OSS_ACCESS_KEY_ID和OSS_ACCESS_KEY_SECRET。
        EnvironmentVariableCredentialsProvider credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();
        // 填写Bucket名称，例如examplebucket。
//        String bucketName = "qkprojectlzh";
        // 保留原文件后缀，OSS 会按后缀判断 Content-Type（浏览器才能当图片打开）
        String suffix = "";
        if (fileName != null && fileName.lastIndexOf(".") > -1) {
            suffix = fileName.substring(fileName.lastIndexOf("."));
        }
        // 填写PutObjectRequest的key，即Object完整名称。完整名称不能包含Bucket名称，例如exampledir/exampleobject.txt。
        String objectName = UUID.randomUUID().toString() + suffix;
        // 填写Bucket所在地域。以华东1（杭州）为例，Region填写为cn-hangzhou。
//        String region = "cn-beijing";

        // 创建OSSClient实例。
        // 当OSSClient实例不再使用时，调用shutdown方法以释放资源。
        ClientBuilderConfiguration clientBuilderConfiguration = new ClientBuilderConfiguration();
        clientBuilderConfiguration.setSignatureVersion(SignVersion.V4);
        OSS ossClient = OSSClientBuilder.create()
                .endpoint(oss.getEndpoint())
                .credentialsProvider(credentialsProvider)
                .clientConfiguration(clientBuilderConfiguration)
                .region(oss.getRegion())
                .build();

        try {
            // 创建PutObjectRequest对象，上传调用方传进来的文件流
            // （原来这里传的是硬编码字符串 "Hello OSS，你好世界"，所以不管传什么图片，存进去的都是那段文本）
            PutObjectRequest putObjectRequest = new PutObjectRequest(oss.getBucketName(), objectName, in);
            // 如果需要上传时设置存储类型和访问权限，请参考以下示例代码。
            // ObjectMetadata metadata = new ObjectMetadata();
            // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
            // metadata.setObjectAcl(CannedAccessControlList.Private);
            // putObjectRequest.setMetadata(metadata);

            // 上传文件。
            PutObjectResult result = ossClient.putObject(putObjectRequest);
            String url = "https://" + oss.getBucketName() + "." + oss.getEndpoint().substring("https://".length()) + "/" + objectName;
            System.out.println(url);
            return url;
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return null;

    }

    public  String upload(File file,String fileName) throws Exception {
        return upload(new FileInputStream(file), fileName);
    }
    public  String upload(MultipartFile file) throws Exception {
        return upload(file.getInputStream(),file.getOriginalFilename());
    }

}
