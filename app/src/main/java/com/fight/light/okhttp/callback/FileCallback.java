package com.fight.light.okhttp.callback;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import okhttp3.Response;

public abstract class FileCallback extends Callback<File> {

    /**
     * 目标文件存储的文件夹路径
     */
    private String destFileDir;
    /**
     * 目标文件存储的文件名
     */
    private String destFileName;

    public FileCallback(String destFileDir, String destFileName)
    {
        this.destFileDir = destFileDir;
        this.destFileName = destFileName;
    }
    @Override
    public File parseNetworkResponse(Response response) throws Exception {
        return downFile(response);
    }

  public File downFile(Response response) {
      InputStream is = null;
      byte[] buf = new byte[2048];
      int len;
      FileOutputStream fos = null;
      try {
          is = response.body().byteStream();
          long total = response.body().contentLength();
          long sum = 0;
          File dir = new File(destFileDir);
          if (!dir.exists()) {
              dir.mkdirs();
          }
          File file = new File(dir, destFileName);
          fos = new FileOutputStream(file);
          while ((len = is.read(buf)) != -1) {
              sum += len;
              fos.write(buf, 0, len);
              final long finalSum = sum;

              inProgress(finalSum * 1.0f / total, total);
          }
          fos.flush();
          return file;

      } catch (FileNotFoundException e) {
          e.printStackTrace();
      } catch (IOException e) {
          e.printStackTrace();
      } finally {
          try {
              if (is != null)
                  is.close();
              if (fos != null)
                  fos.close();
          } catch (IOException e) {
          }
      }
      return null;
  }

    public interface OnDownloadListener {
        /**
         * 下载成功
         */
        void onDownloadSuccess(File file);

        /**
         * @param progress 下载进度
         */
        void onDownloading(int progress);

        /**
         * 下载失败
         */
        void onDownloadFailed();
    }

}
