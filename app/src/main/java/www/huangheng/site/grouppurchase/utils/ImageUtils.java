package www.huangheng.site.grouppurchase.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import www.huangheng.site.grouppurchase.entity.ConstantPool;
import www.huangheng.site.grouppurchase.nohttp.NoHttpListener;
import www.huangheng.site.grouppurchase.nohttp.NoHttpServer;

/**
 * 图片上传与下载
 */

public class ImageUtils {

    private static final ImageUtils sImageUtils = new ImageUtils();

    public synchronized static ImageUtils getInstance() {
        return sImageUtils;
    }

    private ImageUtils() {
    }


    /**
     * * 保存图片至服务器
     *
     * @param context  上下文
     * @param fileName 服务器上的文件名称
     * @param resource 图片id
     * @param listener 监听器
     */
    public void saveImageByResource(Context context, String fileName, int resource, NoHttpListener<byte[]> listener) {

        Request<byte[]> request = NoHttp.createByteArrayRequest("https://api.bmob.cn/2/files/" + fileName, RequestMethod.POST);
        request.addHeader("X-Bmob-Application-Id", "9fc41959205e1bbfaf50ad619eb45d0d");
        request.addHeader("X-Bmob-REST-API-Key", "30ea7937c419a9f7e3f779b3f705b636");

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resource);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 设置位图的压缩格式，质量为100%，并放入字节数组输出流中
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);

        InputStream inputStream = new ByteArrayInputStream(baos.toByteArray());

        bitmap.recycle();

        request.setDefineRequestBody(inputStream, "text/plain");

        NoHttpServer.getInstance().add(ConstantPool.SAVEIMAGE_WHAT, request, listener);

    }


}
