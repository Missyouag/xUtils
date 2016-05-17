package sc.xutils_utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.widget.ImageView;


import org.xutils.ImageManager;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * final bitmap 以及改变bitmap大小的工具类
 * <p/>
 * <p/>
 * "http" 网站类<p/>
 * "assets://" assets文件<p/>
 * "file:"   本地文件"/"
 *
 * @author sc
 *         <p/>
 *         imageOptions = new ImageOptions.Builder()
 *         .setSize(DensityUtil.dip2px(120), DensityUtil.dip2px(120))
 *         .setRadius(DensityUtil.dip2px(5))
 *         .setCrop(true)
 *         // 加载中或错误图片的ScaleType
 *         //.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
 *         .setImageScaleType(ImageView.ScaleType.CENTER_CROP).build()圆角
 */
public class XBitmap {

    public final static String AssetsURL = "assets://";


    private static void ClearCache() {
        if (getBitmapUtil() != null) {
            getBitmapUtil().clearCacheFiles();
        }
    }

    private static ImageManager getBitmapUtil() {
        return x.image();
    }


    /**
     * 应用内图片
     *
     * @param imgView
     * @param img
     */
    public static void ShowImg(ImageView imgView, String img) {
        ShowImgUrl(imgView, AppPort.getPicture(img));
    }

    /**
     * 应用内图片
     *
     * @param imgView
     * @param img
     */
    public static void ShowImg(ImageView imgView, String img, int width) {
        ShowImgUrl(imgView, AppPort.getPicture(img, width));
    }

    /**
     * 显示为全屏宽图片
     *
     * @param imgView
     * @param img
     */
    public static void ShowImgBig(ImageView imgView, String img) {
        ShowImgUrl(imgView, AppPort.getPicture(img, 0));
    }



    /**
     * 商品图片
     *
     * @param imgView
     * @param img
     */
    public static void ShowImgCenter(ImageView imgView, String img, int width) {
        ImageOptions options = new ImageOptions.Builder()
//                .setLoadingDrawableId(R.mipmap.img_good_loading)
//                .setFailureDrawableId(R.mipmap.img_good_loading)
                // 加载中或错误图片的ScaleType
                //.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
                // 默认自动适应大小
                // .setSize(...)
                .setIgnoreGif(true)
                .setImageScaleType(ImageView.ScaleType.CENTER_INSIDE).build();
        ShowImgUrl(imgView, AppPort.getPicture(img, width), options);
    }


    /**
     * @param imgView
     * @param img
     */
    public static void ShowImgFit_Null(ImageView imgView, String img) {
        if (null != imgView) ShowImgFitWithWitdh(imgView, img);
    }

    /**
     * @param imgView
     * @param img
     */
    public static void ShowImgFitWithWitdh(ImageView imgView, String img) {
        ImageOptions options = new ImageOptions.Builder()
                // 加载中或错误图片的ScaleType
                //.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
                // 默认自动适应大小
                // .setSize(...)
//                .setIgnoreGif(true)
                .setImageScaleType(ImageView.ScaleType.FIT_XY).build();
        ShowImgUrl(imgView, AppPort.getPicture(img, imgView.getMeasuredWidth()), options);
    }

    /**
     * 应用内图片
     *
     * @param imgView
     * @param img
     */
    public static void ShowImg(ImageView imgView, String img, ImageOptions options) {
        ShowImgUrl(imgView, AppPort.getPicture(img), options);
    }



  
    /**
     * 常规<b/>
     * 慎用<b/>
     * 直接地址...
     *
     * @param imgView
     * @param url
     */
    public static void ShowImgUrl(ImageView imgView, String url) {
        ShowImgUrl(imgView, url, null);
    }

    public static void ShowImgUrl(ImageView imgView, String url, ImageOptions options) {
//        LogUtil.LogIMG(imgView + ":" + url);
        getBitmapUtil().bind(imgView, url, options);
    }

    /**
     * 头像
     *
     * @param head
     * @param img
     */
    public static void ShowIcon(ImageView head, String img) {
        ImageOptions options = new ImageOptions.Builder()
//                .setFailureDrawableId(R.mipmap.ic_my_head)
                // 加载中或错误图片的ScaleType
                //.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
                // 默认自动适应大小
                // .setSize(...)
                .setIgnoreGif(false)
//                .setRadius(20)
//                .setImageScaleType(ImageView.ScaleType.CENTER)
                .build();
        ShowImg(head, img, options);


//        ShowImgUrl(head, TextUtils.isEmpty(img) ? AssetsURL + "myahead.png" : AppPort.getPictureX10(img));//  ic_my_head.jpg
    }

    public static Bitmap small(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postScale(0.2f, 0.2f); // 长和宽放大缩小的比例
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
    }

    public static Bitmap Getsmall(Resources res, int id) {
        return small(BitmapFactory.decodeResource(res, id));
    }

    /**
     * 得到网页中图片的地址
     */
    public static ArrayList<String> getImgSrcList(String htmlStr) {
        ArrayList<String> pics = new ArrayList<String>();

        String regEx_img = "<img.*?src=\"http://(.*?).jpg\""; // 图片链接地址
        Pattern p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
        Matcher m_image = p_image.matcher(htmlStr);
        while (m_image.find()) {
            String src = m_image.group(1);
            if (src.length() < 100) {
                pics.add("http://" + src + ".jpg");
            }
        }
        return pics;
    }

    /**
     * 得到网页中图片的地址
     */
    public static ArrayList<String> getImgSrcListForAPP(String htmlStr) {
        ArrayList<String> pics = new ArrayList<String>();
        String regEx_img = "<img.*?src=\"(http://.*?.[p|j][n|p]g)"; // 图片链接地址
        Pattern p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
        Matcher m_image = p_image.matcher(htmlStr);
        while (m_image.find()) {
            String src = m_image.group(1);
            if (src.length() < 100) {
                pics.add(src);
            }
        }
//        LogUtil.d(htmlStr + "\n" + pics.toString());
        return pics;
    }

    public static void ShowPic(ImageView img, String url) {
        ImageOptions options = new ImageOptions.Builder()
//                .setFailureDrawableId(R.mipmap.ic_my_head)
                // 加载中或错误图片的ScaleType
                //.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
                // 默认自动适应大小
                // .setSize(...)
                .setIgnoreGif(false)
                .setImageScaleType(ImageView.ScaleType.FIT_CENTER).build();
        ShowImgUrl(img, AppPort.getPicture(url), options);
    }
}
