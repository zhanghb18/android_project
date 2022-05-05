package com.tsinghua.course.Frame.Util;

import io.netty.handler.codec.http.multipart.MixedFileUpload;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class FileUtil {
    private static String prefix_url = "http://42.193.117.251:80/";
    //private static String prefix_url = "http://127.0.0.1:80/";
    private static String RESOURCE_PATH = "/static/";
    //private static String RESOURCE_PATH = "/Users/ivan/SeiNo/";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public String saveFile(MixedFileUpload file) throws Exception {
        String timeStamp = simpleDateFormat.format(new Date()) + '/';
        String filename = file.getFilename();
        File new_file = new File(RESOURCE_PATH + timeStamp);
        if ( !new_file.exists() ) {
            new_file.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(RESOURCE_PATH + timeStamp + filename, false);
        out.write(file.get());
        out.flush();
        out.close();
        return prefix_url + timeStamp + filename;
    }

    public String file(byte[] file) throws Exception {
        String timeStamp = simpleDateFormat.format(new Date()) + '/';
        File new_file = new File(RESOURCE_PATH + timeStamp);
        if ( !new_file.exists() ) {
            new_file.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(RESOURCE_PATH + timeStamp + ".jpeg", false);
        out.write(file);
        out.flush();
        out.close();
        return prefix_url + timeStamp + ".jpeg";
    }

}
