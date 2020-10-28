package com.elkdeals.mobile.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.elkdeals.mobile.api.models.TimeLineModel;

import java.util.ArrayList;

/**
 * User_Cart_DB creates the table User_Cart and handles all CRUD operations relevant to User_Cart
 **/
public class User_Auctions_DB {

    SQLiteDatabase db;

    // Table Name
    public static final String TABLE_Auctions = "TimeLineModel";
    // Table Columns
    public static final String id = "id";
    public static final String name = "name";
    public static final String desc = "desc";
    public static final String current = "current";
    public static final String entering = "entering";
    public static final String image = "image";
    public static final String started = "started";
    public static final String finished = "finished";
    public static final String way = "way";
    public static final String video = "video";
    public static final String startprice = "startprice";
    public static final String endprice = "endprice";
    public static final String registered = "registered";
    public static final String stepprice = "stepprice";
    public static final String auctionprice = "auctionprice";
    public static final String percentage = "percentage";
    public static final String cname = "cname";
    public static final String idnumber = "idnumber";
    public static final String datetype = "datetype";
    public static final String enddate = "enddate";
    public static final String reg = "reg";
    public static final String phone = "phone";
    public static final String lat = "lat";
    public static final String lang = "lang";



    //*********** Returns the Query to Create TABLE_Auctions ********//

    public static String createTableCart() {

        return "CREATE TABLE IF NOT EXISTS `TimeLineModel` (`id` INTEGER PRIMARY KEY  NOT NULL, `name` TEXT, `desc` TEXT, `current` INTEGER NOT NULL, `entering` INTEGER NOT NULL, `image` TEXT, `started` INTEGER, `finished` INTEGER, `way` INTEGER NOT NULL, `video` TEXT, `startprice` INTEGER NOT NULL, `registered` INTEGER, `endprice` INTEGER NOT NULL, `stepprice` INTEGER NOT NULL, `auctionprice` INTEGER NOT NULL, `percentage` INTEGER NOT NULL, `cname` TEXT, `idnumber` TEXT, `datetype` TEXT, `enddate` INTEGER NOT NULL, `reg` INTEGER NOT NULL, `phone` TEXT, `lat` TEXT, `lang` TEXT)";
    }


    //*********** Insert New Cart Item ********//

    public void addAuction(TimeLineModel auction) {
        // get and open SQLiteDatabase Instance from static method of DB_Manager class
        db = DB_Manager.getInstance().openDatabase();

        ContentValues productValues = new ContentValues();

        productValues.put(id, auction.getId());
        productValues.put(name, auction.getName());
        productValues.put(desc, auction.getDesc());
        productValues.put(current, auction.getCurrent());
        productValues.put(entering, auction.getEntering());
        productValues.put(image, auction.getImage());
        productValues.put(started, auction.getStarted());
        productValues.put(finished, auction.getFinished());
        productValues.put(way, auction.getWay());
        productValues.put(video, auction.getVideo());
        productValues.put(startprice, auction.getStartprice());
        productValues.put(endprice, auction.getEndprice());
        productValues.put(registered, auction.getRegistered());
        productValues.put(stepprice, auction.getStepprice());
        productValues.put(auctionprice, auction.getAuctionprice());
        productValues.put(percentage, auction.getPercentage());
        productValues.put(cname, auction.getCname());
        productValues.put(idnumber, auction.getIdnumber());
        productValues.put(datetype, auction.getDatetype());
        productValues.put(enddate, auction.getEnddate());
        productValues.put(reg, auction.getRegistered());
        productValues.put(phone, auction.getPhone());
        productValues.put(lat, auction.getLat());
        productValues.put(lang, auction.getLang());


        db.insertWithOnConflict(TABLE_Auctions, null, productValues, SQLiteDatabase.CONFLICT_REPLACE);

        // close the Database
        DB_Manager.getInstance().closeDatabase();
    }

    //*********** Delete specific Item from Cart ********//

    public void deleteAuction(int auctionID) {
        // get and open SQLiteDatabase Instance from static method of DB_Manager class
        db = DB_Manager.getInstance().openDatabase();

        db.delete(TABLE_Auctions, id + " = ?", new String[]{String.valueOf(auctionID)});
        // close the Database
        DB_Manager.getInstance().closeDatabase();
    }
    public static int getColumnIndexOrThrow(@NonNull Cursor c, @NonNull String name) {
        final int index = c.getColumnIndex(name);
        if (index >= 0) {
            return index;
        }
        return c.getColumnIndexOrThrow("`" + name + "`");
    }
    //*********** Get all Cart Items ********//

    public ArrayList<TimeLineModel> getAuctions() {
        // get and open SQLiteDatabase Instance from static method of DB_Manager class
        db = DB_Manager.getInstance().openDatabase();

        Cursor _cursor = db.rawQuery("SELECT * FROM " + TABLE_Auctions, null);
        ArrayList<TimeLineModel> _result = new ArrayList<TimeLineModel>(_cursor.getCount());
        try {
            final int _cursorIndexOfId = getColumnIndexOrThrow(_cursor, "id");
            final int _cursorIndexOfName = getColumnIndexOrThrow(_cursor, "name");
            final int _cursorIndexOfDesc = getColumnIndexOrThrow(_cursor, "desc");
            final int _cursorIndexOfCurrent = getColumnIndexOrThrow(_cursor, "current");
            final int _cursorIndexOfEntering = getColumnIndexOrThrow(_cursor, "entering");
            final int _cursorIndexOfImage = getColumnIndexOrThrow(_cursor, "image");
            final int _cursorIndexOfStarted = getColumnIndexOrThrow(_cursor, "started");
            final int _cursorIndexOfFinished = getColumnIndexOrThrow(_cursor, "finished");
            final int _cursorIndexOfWay = getColumnIndexOrThrow(_cursor, "way");
            final int _cursorIndexOfVideo = getColumnIndexOrThrow(_cursor, "video");
            final int _cursorIndexOfStartprice = getColumnIndexOrThrow(_cursor, "startprice");
            final int _cursorIndexOfRegistered = getColumnIndexOrThrow(_cursor, "registered");
            final int _cursorIndexOfEndprice = getColumnIndexOrThrow(_cursor, "endprice");
            final int _cursorIndexOfStepprice = getColumnIndexOrThrow(_cursor, "stepprice");
            final int _cursorIndexOfAuctionprice = getColumnIndexOrThrow(_cursor, "auctionprice");
            final int _cursorIndexOfPercentage = getColumnIndexOrThrow(_cursor, "percentage");
            final int _cursorIndexOfCname = getColumnIndexOrThrow(_cursor, "cname");
            final int _cursorIndexOfIdnumber = getColumnIndexOrThrow(_cursor, "idnumber");
            final int _cursorIndexOfDatetype = getColumnIndexOrThrow(_cursor, "datetype");
            final int _cursorIndexOfEnddate = getColumnIndexOrThrow(_cursor, "enddate");
            final int _cursorIndexOfReg = getColumnIndexOrThrow(_cursor, "reg");
            final int _cursorIndexOfPhone = getColumnIndexOrThrow(_cursor, "phone");
            final int _cursorIndexOfLat = getColumnIndexOrThrow(_cursor, "lat");
            final int _cursorIndexOfLang = getColumnIndexOrThrow(_cursor, "lang");
            while(_cursor.moveToNext()) {
                final TimeLineModel _item;
                _item = new TimeLineModel();
                final int _tmpId;
                _tmpId = _cursor.getInt(_cursorIndexOfId);
                _item.setId(_tmpId);
                final String _tmpName;
                _tmpName = _cursor.getString(_cursorIndexOfName);
                _item.setName(_tmpName);
                final String _tmpDesc;
                _tmpDesc = _cursor.getString(_cursorIndexOfDesc);
                _item.setDesc(_tmpDesc);
                final int _tmpCurrent;
                _tmpCurrent = _cursor.getInt(_cursorIndexOfCurrent);
                _item.setCurrent(_tmpCurrent);
                final int _tmpEntering;
                _tmpEntering = _cursor.getInt(_cursorIndexOfEntering);
                _item.setEntering(_tmpEntering);
                final String _tmpImage;
                _tmpImage = _cursor.getString(_cursorIndexOfImage);
                _item.setImage(_tmpImage);
                final Boolean _tmpStarted;
                final Integer _tmp;
                if (_cursor.isNull(_cursorIndexOfStarted)) {
                    _tmp = null;
                } else {
                    _tmp = _cursor.getInt(_cursorIndexOfStarted);
                }
                _tmpStarted = _tmp == null ? null : _tmp != 0;
                _item.setStarted(_tmpStarted);
                final Boolean _tmpFinished;
                final Integer _tmp_1;
                if (_cursor.isNull(_cursorIndexOfFinished)) {
                    _tmp_1 = null;
                } else {
                    _tmp_1 = _cursor.getInt(_cursorIndexOfFinished);
                }
                _tmpFinished = _tmp_1 == null ? null : _tmp_1 != 0;
                _item.setFinished(_tmpFinished);
                final int _tmpWay;
                _tmpWay = _cursor.getInt(_cursorIndexOfWay);
                _item.setWay(_tmpWay);
                final String _tmpVideo;
                _tmpVideo = _cursor.getString(_cursorIndexOfVideo);
                _item.setVideo(_tmpVideo);
                final int _tmpStartprice;
                _tmpStartprice = _cursor.getInt(_cursorIndexOfStartprice);
                _item.setStartprice(_tmpStartprice);
                final Boolean _tmpRegistered;
                final Integer _tmp_2;
                if (_cursor.isNull(_cursorIndexOfRegistered)) {
                    _tmp_2 = null;
                } else {
                    _tmp_2 = _cursor.getInt(_cursorIndexOfRegistered);
                }
                _tmpRegistered = _tmp_2 == null ? null : _tmp_2 != 0;
                _item.setRegistered(_tmpRegistered);
                final int _tmpEndprice;
                _tmpEndprice = _cursor.getInt(_cursorIndexOfEndprice);
                _item.setEndprice(_tmpEndprice);
                final int _tmpStepprice;
                _tmpStepprice = _cursor.getInt(_cursorIndexOfStepprice);
                _item.setStepprice(_tmpStepprice);
                final int _tmpAuctionprice;
                _tmpAuctionprice = _cursor.getInt(_cursorIndexOfAuctionprice);
                _item.setAuctionprice(_tmpAuctionprice);
                final int _tmpPercentage;
                _tmpPercentage = _cursor.getInt(_cursorIndexOfPercentage);
                _item.setPercentage(_tmpPercentage);
                final String _tmpCname;
                _tmpCname = _cursor.getString(_cursorIndexOfCname);
                _item.setCname(_tmpCname);
                final String _tmpIdnumber;
                _tmpIdnumber = _cursor.getString(_cursorIndexOfIdnumber);
                _item.setIdnumber(_tmpIdnumber);
                final String _tmpDatetype;
                _tmpDatetype = _cursor.getString(_cursorIndexOfDatetype);
                _item.setDatetype(_tmpDatetype);
                final int _tmpEnddate;
                _tmpEnddate = _cursor.getInt(_cursorIndexOfEnddate);
                _item.setEnddate(_tmpEnddate);
                final boolean _tmpReg;
                final int _tmp_3;
                _tmp_3 = _cursor.getInt(_cursorIndexOfReg);
                _tmpReg = _tmp_3 != 0;
                _item.setReg(_tmpReg);
                final String _tmpPhone;
                _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
                _item.setPhone(_tmpPhone);
                final String _tmpLat;
                _tmpLat = _cursor.getString(_cursorIndexOfLat);
                _item.setLat(_tmpLat);
                final String _tmpLang;
                _tmpLang = _cursor.getString(_cursorIndexOfLang);
                _item.setLang(_tmpLang);
                _result.add(_item);
            }
        } finally {
            _cursor.close();
        }
        DB_Manager.getInstance().closeDatabase();

        return _result;
    }

    public void clearAuctions(){

        // get and open SQLiteDatabase Instance from static method of DB_Manager class
        db = DB_Manager.getInstance().openDatabase();

        db.delete(TABLE_Auctions, null, null);

        // close the Database
        DB_Manager.getInstance().closeDatabase();
    }
}

