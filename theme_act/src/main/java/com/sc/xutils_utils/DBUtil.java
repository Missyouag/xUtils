package com.sc.xutils_utils;

import android.content.Context;
import android.os.Environment;


import org.xutils.DbManager;
import org.xutils.common.util.FileUtil;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by Administrator on 2016/3/8.
 */
public class DBUtil {

    private Context myContext;
    private DbManager bDb;
    private DbManager pDb;
    private DbManager sDb;

    public DBUtil(Context context) {
        this.myContext = context;
    }

    /**
     * 配置数据库
     *
     * @param dbname  数据库名字
     * @param bool    是否开启事务
     * @param pathDir 文件夹存放位置
     * @param version 数据库版本
     * @return 数据对象
     */
    public DbManager db_confing(String dbname, boolean bool, String pathDir, int version) {
        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
                .setDbName(dbname)
                .setAllowTransaction(bool)
                .setDbDir(FileUtil.getCacheDir(pathDir))
                .setDbVersion(version)
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {

                    }
                });
        DbManager db = x.getDb(daoConfig);
        return db;
    }

    /**
     * 从assess 中取文件
     *
     * @param dbname
     * @param bool
     * @param pathDir
     * @param version
     * @param name
     * @return
     */
    public DbManager db_confing(String dbname, boolean bool, String pathDir, int version, String name) throws IOException {
        if (!checkDataBase(pathDir, name)) {
            copyBigDataBase(pathDir, name);
        }
        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
                .setDbName(dbname)
                .setAllowTransaction(bool)
                .setDbDir(FileUtil.getCacheDir(pathDir))
                .setDbVersion(version)
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {

                    }
                });
        DbManager db = x.getDb(daoConfig);

        return db;
    }

    // 复制assets下的数据库文件时用这个
    @SuppressWarnings("unused")
    private void copyBigDataBase(String pathDir, String name) throws IOException {
        InputStream myInput;
        String outFileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MINGCONGKEJI/" + "ZJiaTao/" + pathDir + "/" + name;
        OutputStream myOutput = new FileOutputStream(outFileName);
        myInput = myContext.getAssets().open(name);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myInput.close();
        myOutput.close();
    }

    // 检查数据库是否有效
    private boolean checkDataBase(String pathDir, String name) {
        File dbf = new File(FileUtil.getCacheDir(pathDir), name);
        if (dbf.exists()) {
            return true;
        } else {
            return false;
        }
    }

    public void browse() {
        bDb = db_confing("browse.db", true, "Browse", 1);
    }

    public void product() {
        pDb = db_confing("product.db", true, "Product", 1);
    }

//    /**
//     * 插入 浏览记录
//     *
//     * @param name//   商品名称
//     * @param imgUrl// 商品图片
//     * @param pID//    店铺Id
//     * @param cID      // cp 分类Id 1美食 2酒店 3休闲 4丽人 5 身边外卖
//     * @param price    // 价格
//     * @param flag//   什么分类
//     *                 0=》生活
//     *                 1=>实体商家
//     */
//    public void setBrowse(String name, String imgUrl, String pID, String cID, String price, String flag) {
//        if (flag == null) {
//            flag = "0";
//        }
//        try {
//            List<Browse> bList = bDb.selector(Browse.class).where("pID", "=", pID).and("fleg", "=", flag).and("cID", "=", cID).findAll();
//            if (bList != null && bList.size() > 0) {
//                bList.get(0).setDate(System.currentTimeMillis());
//                bDb.update(bList.get(0), "date");
//            } else {
//                List<Browse> browses = getBrowse(Integer.parseInt(flag));
//                if (browses != null && browses.size() >= 100)
//                    delBrowse(browses.get(browses.size() - 1).getId());
//                bDb.save(new Browse(name, System.currentTimeMillis(), imgUrl, pID, cID, price, flag));
//            }
//
//        } catch (DbException e) {
//            //e.printStackTrace();
//        }
//    }
//
//    public void delBrowse(int id) {
//        try {
//            bDb.deleteById(Browse.class, id);
//        } catch (DbException e) {
//            //e.printStackTrace();
//        }
//    }
//
//    public void delBrowse() {
//        try {
//            bDb.delete(Browse.class);
//        } catch (DbException e) {
//            //e.printStackTrace();
//        }
//    }
//
//    /**
//     * 获取浏览记录
//     *
//     * @return
//     */
//    public List<Browse> getBrowse(int fleg) {
//        try {
//            List<Browse> browses = bDb.selector(Browse.class).where("fleg", "=", fleg).orderBy("date", true).findAll();
//            return browses;
//        } catch (DbException e) {
//            //e.printStackTrace();
//        }
//        return null;
//    }
//
//    /**
//     * 获取 店铺 购物车
//     */
//    public List<Product> getProduct(String sid) {
////        if(null==pDb)product();
//        try {
//            List<Product> browses = pDb.selector(Product.class).where("sID", "=", sid).findAll();
//            return browses;
//        } catch (DbException e) {
//            //e.printStackTrace();
//        }
//        return null;
//    }
//
//    /**
//     * 获取 店铺 购物车
//     */
//    public List<Product> getProduct(String sid, int type) {
//        try {
//            List<Product> browses = pDb.selector(Product.class).where("sID", "=", sid).and("type", "=", type).findAll();
//            return browses;
//        } catch (DbException e) {
//            //e.printStackTrace();
//        }
//        return null;
//    }
//
//    /**
//     * @param sid// 店铺id
//     * @param pid// 产品Id
//     * @param cid// 购物车id
//     * @param type  0 =>生活服务 1=>实体商家
//     * @param num
//     */
//    public int addProduct(String sid, String pid, String cid, int type, int num, float price, String name) {
//        try {
//            pDb.save(new Product(sid, pid, cid, type, System.currentTimeMillis(), num, price, name));
//            List<Product> products = getProduct(sid);
//            return products.get(products.size() - 1).getId();
//        } catch (DbException e) {
//            //e.printStackTrace();
//        }
//        return 0;
//    }
//
//    public void saveProduct(String id, int num) {
//        try {
//            Product product = pDb.findById(Product.class, id);
//            product.setNum(num);
//            pDb.update(product, "num");
//        } catch (DbException e) {
//            //e.printStackTrace();
//        }
//    }
//
//    public void delProduct(String sid) {
//        try {
//            pDb.delete(Product.class, WhereBuilder.b("sid", "=", sid));
//        } catch (DbException e) {
//            //e.printStackTrace();
//        }
//    }
//
//    public void delProduct(String sid, int type) {
//        try {
//            pDb.delete(Product.class, WhereBuilder.b("sid", "=", sid).and("type", "=", type));
//        } catch (DbException e) {
//            //e.printStackTrace();
//        }
//    }
//
//    public void SearchCity() {
//        sDb = db_confing("searchcity.db", true, "SearchCity", 1);
//    }
//
//    public void AddCity(String province, String city, String district, String town, String name) {
//        try {
//            List<SearchCity> citys = getSearchCity();
//            if (citys != null && citys.size() > 100) {
//                sDb.deleteById(SearchCity.class, citys.get(0).getId());
//            }
//            sDb.save(new SearchCity(province, city, district, town, name, System.currentTimeMillis()));
//        } catch (DbException e) {
//            //e.printStackTrace();
//        }
//    }
//
//    public List<SearchCity> getSearchCity() {
//        try {
//            return sDb.selector(SearchCity.class).findAll();
//        } catch (DbException e) {
//            //e.printStackTrace();
//        }
//        return null;
//    }
//
//    public SearchCity getItemSearchCity() {
//        List<SearchCity> citys = getSearchCity();
//        return citys.get(citys.size() - 1);
//    }
}
