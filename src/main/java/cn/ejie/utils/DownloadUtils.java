package cn.ejie.utils;

import cn.ejie.exception.SimpleException;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.File;

public class DownloadUtils {
    public static ResponseEntity<byte[]> getResponseEntity(String fileName, File file) throws Exception{
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", new String(fileName.getBytes("UTF-8"),"iso-8859-1"));
        ResponseEntity<byte[]> responseEntity = null;
        try {
            responseEntity = new ResponseEntity(FileUtils.readFileToByteArray(file),
                    headers, HttpStatus.CREATED);
        }catch (Exception e){
            throw new SimpleException("errorType","系统发生错误：要下载的文件不存在！");
        }
        return responseEntity;
    }
}
