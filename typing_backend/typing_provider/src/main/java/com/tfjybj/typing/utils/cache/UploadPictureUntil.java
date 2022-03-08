package com.tfjybj.typing.utils.cache;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.github.tobato.fastdfs.service.TrackerClient;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;

@Slf4j
@Component
public class UploadPictureUntil {
    @Resource
    private FastFileStorageClient fastFileStorageClient;
    @Resource
    private TrackerClient trackerClient;
    // 获取配置文件中的配置IP地址
    @Value("${fdfs.realIp}")
    private String realIp;
    // 所有音频格式
    public static String AUDIO_FREQUENCY_FORMAT = "CD、WAVE、AIFF、MPEG、MP3、MPEG-4、MIDI、WMA、RealAudio、VQF、OggVorbis、AMR、APE、FLAC、AAC";
    // 所有文件格式
    public static String FILE_FORMAT = "WEBP、BMP、PCX、TIF、GIF、JPEG、TGA、EXIF、FPX、SVG、PSD、CDR、PCD、DXF、UFO、EPS、AI、PNG、HDRI、RAW、WMF、FLIC、EMF、ICO、JPG、JPEG、PNG、GIF";

    public static String PICTURE_FORMAT = "JPG、JPEG、PNG";
    // 目前视频播放支持的格式为wev
    public static String VIDEO_FORMAT = "WEBM";

    public String uploadPicture(File file) {
        boolean flag = false;
        try {
            // 判断是否为图片
            if (PICTURE_FORMAT.contains(file.getName().substring(file.getName().indexOf('.') + 1).toUpperCase())) {
                flag = true;
                // 把图片进行压缩,大小为300×300的图片并且命名为rose.jpg存放到项目的根目录下面
                Thumbnails.of(file).size(300, 300).toFile("../rose.jpg");
                file = new File("../rose.jpg");
            }
            // 通过路径转换成文件流picture
            FileInputStream wordStrm = new FileInputStream(file);
            MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(), "text/plain", wordStrm);
            // 图片上传到fastdfs
            StorePath storePath = fastFileStorageClient.uploadFile(multipartFile.getInputStream(), multipartFile.getSize(), FilenameUtils.getExtension(multipartFile.getOriginalFilename()), null);
            // 获取图片的完整地址
            String imagePath = "http://" + realIp + "/" + storePath.getFullPath();
            if (flag) {
                // 删除根目录下面的图片
                file.delete();
            }
            return imagePath;
        } catch (Exception e) {
            log.error("图片插入错误,fileOnly=" + file.getAbsolutePath(), e);
            return null;
        }
    }


    /**
     * 图片上传备份,和上面的道理一样
     */
//    public String uploadPicture(File file) {
//        try {
//            // 通过路径转换成文件流picture
//            FileInputStream wordStrm = new FileInputStream(file);
//            MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(), "text/plain", wordStrm);
//            StorePath storePath = fastFileStorageClient.uploadFile(multipartFile.getInputStream(), multipartFile.getSize(), FilenameUtils.getExtension(multipartFile.getOriginalFilename()), null);
//            String serverPath = trackerClient.getStoreStorage().getIp();
//            String imagePath = "http://" + serverPath + "/" + storePath.getFullPath();
//            log.info("图片上传成功，地址：" + imagePath);
//            return imagePath;
//        } catch (Exception e) {
//            log.error("图片插入错误,file="+file.getAbsolutePath() ,e);
//            return null;
//        }
//    }
}

