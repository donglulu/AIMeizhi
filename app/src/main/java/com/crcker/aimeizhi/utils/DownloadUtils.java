package com.crcker.aimeizhi.utils;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import java.io.File;

import static android.content.Context.DOWNLOAD_SERVICE;

/**
 * Created by Crcker on 16/02/2017.
 * 邮箱：635281462@qq.com
 */

public class DownloadUtils {
    private String dir;
    private String fileName;

    public DownloadUtils(String path,String name) {
        this.dir = path;
        this.fileName = name;

    }

    public void downloadImg(Context context, String url) {

        DownloadManager downloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);


        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        File saveFile = new File(dir+"/",fileName);
        request.setDestinationUri(Uri.fromFile(saveFile));
        //设置允许使用的网络类型，这里是移动网络和wifi都可以
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        long id = downloadManager.enqueue(request);

    }

    public boolean isSDcard() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }


}
