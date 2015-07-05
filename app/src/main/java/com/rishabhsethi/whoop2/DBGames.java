package com.rishabhsethi.whoop2;

/**
 * Created by rishabh on 17/6/15.
 */
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Windows on 25-02-2015.
 */
public class DBGames {
    public static final int GAME = 0;
    public static final int UPCOMING = 1;
    private GamesHelper mHelper;
    private SQLiteDatabase mDatabase;

    public DBGames(Context context) {
        mHelper = new GamesHelper(context);
        mDatabase = mHelper.getWritableDatabase();
    }

    public void insertMovies(int table, ArrayList<Games> listGames, boolean clearPrevious) {
        if (clearPrevious) {
            deleteMovies(table);
        }


        //create a sql prepared statement
        String sql = "INSERT INTO " + (table == GAME ? GamesHelper.TABLE_GAME : GamesHelper.TABLE_UPCOMING) + " VALUES (?,?,?,?,?,?,?,?,?,?);";
        //compile the statement and start a transaction
        SQLiteStatement statement = mDatabase.compileStatement(sql);
        mDatabase.beginTransaction();
        for (int i = 0; i < listGames.size(); i++) {
            Games currentGame = listGames.get(i);
            statement.clearBindings();
            //for a given column index, simply bind the data to be put inside that index
            statement.bindString(2, currentGame.getGame_name());
            statement.bindString(3, currentGame.getGame_type());
            statement.bindString(4, currentGame.getGame_description());
            statement.bindString(5, currentGame.getGame_url());

            statement.execute();
        }
        //set the transaction as successful and end the transaction
        L.m("inserting entries " + listGames.size() + new Date(System.currentTimeMillis()));
        mDatabase.setTransactionSuccessful();
        mDatabase.endTransaction();
    }

    public ArrayList<Games> readMovies(int table) {
        ArrayList<Games> listGames = new ArrayList<>();

        //get a list of columns to be retrieved, we need all of them
        String[] columns = {GamesHelper.COLUMN_UID,
                GamesHelper.GAME_NAME,
                GamesHelper.GAME_TYPE,
                GamesHelper.GAME_DESCRIPTION,
                GamesHelper.GAME_URL,
                /*GamesHelper.COLUMN_URL_THUMBNAIL,
                GamesHelper.COLUMN_URL_SELF,
                GamesHelper.COLUMN_URL_CAST,
                GamesHelper.COLUMN_URL_REVIEWS,
                GamesHelper.COLUMN_URL_SIMILAR*/
        };
        Cursor cursor = mDatabase.query((table == GAME ? GamesHelper.TABLE_GAME : GamesHelper.TABLE_UPCOMING), columns, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            L.m("loading entries " + cursor.getCount() + new Date(System.currentTimeMillis()));
            do {

                //create a new movie object and retrieve the data from the cursor to be stored in this movie object
                Games game = new Games();
                //each step is a 2 part process, find the index of the column first, find the data of that column using
                //that index and finally set our blank movie object to contain our data
                game.setGame_name(cursor.getString(cursor.getColumnIndex(GamesHelper.GAME_NAME)));
                //long releaseDateMilliseconds = cursor.getLong(cursor.getColumnIndex(GamesHelper.COLUMN_RELEASE_DATE));
                //game.setReleaseDateTheater(releaseDateMilliseconds != -1 ? new Date(releaseDateMilliseconds) : null);
                //game.setAudienceScore(cursor.getInt(cursor.getColumnIndex(GamesHelper.COLUMN_AUDIENCE_SCORE)));
                game.setGame_type(cursor.getString(cursor.getColumnIndex(GamesHelper.GAME_TYPE)));
                game.setGame_description(cursor.getString(cursor.getColumnIndex(GamesHelper.GAME_DESCRIPTION)));
                game.setGame_url(cursor.getString(cursor.getColumnIndex(GamesHelper.GAME_URL)));
                game.setGame_image_url(cursor.getString(cursor.getColumnIndex(GamesHelper.GAME_IMAGE_URL)));
                /*game.setUrlCast(cursor.getString(cursor.getColumnIndex(GamesHelper.COLUMN_URL_CAST)));
                game.setUrlReviews(cursor.getString(cursor.getColumnIndex(GamesHelper.COLUMN_URL_REVIEWS)));
                game.setUrlSimilar(cursor.getString(cursor.getColumnIndex(GamesHelper.COLUMN_URL_SIMILAR)));*/
                //add the movie to the list of movie objects which we plan to return
                listGames.add(game);
            }
            while (cursor.moveToNext());
        }
        return listGames;
    }

    public void deleteMovies(int table) {
        mDatabase.delete((table == GAME ? GamesHelper.TABLE_GAME : GamesHelper.TABLE_UPCOMING), null, null);
    }

    private static class GamesHelper extends SQLiteOpenHelper {
        public static final String COLUMN_UID = "id";
        public static final String TABLE_GAME = "game_current";
        public static final String TABLE_UPCOMING = "game_upcoming";
        public static final String GAME_NAME = "GAME NAME";
        public static final String GAME_TYPE = "GAME TYPE";
        public static final String GAME_DESCRIPTION = "GAME DESCTIPTION";
        public static final String GAME_URL = "GAME URL";
        public static final String GAME_IMAGE_URL = "GAME IMAGE URL";
        private static final String CREATE_TABLE_GAME = "CREATE TABLE " + TABLE_GAME + " (" +
                COLUMN_UID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                GAME_NAME + " TEXT," +
                GAME_TYPE + " TEXT," +
                GAME_DESCRIPTION + " TEXT," +
                GAME_URL + " TEXT," +
                GAME_IMAGE_URL + " TEXT," +
                ");";
        private static final String CREATE_TABLE_UPCOMING = "CREATE TABLE " + TABLE_UPCOMING + " (" +
                COLUMN_UID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                GAME_NAME + " TEXT," +
                GAME_TYPE + " TEXT," +
                GAME_DESCRIPTION + " TEXT," +
                GAME_URL + " TEXT," +
                GAME_IMAGE_URL + " TEXT," +
                ");";
        private static final String DB_NAME = "movies_db";
        private static final int DB_VERSION = 1;
        private Context mContext;

        public GamesHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
            mContext = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CREATE_TABLE_GAME);
                db.execSQL(CREATE_TABLE_UPCOMING);
                L.m("create table box office executed");
            } catch (SQLiteException exception) {
                L.t(mContext, exception + "");
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                L.m("upgrade table box office executed");
                db.execSQL(" DROP TABLE " + TABLE_GAME + " IF EXISTS;");
                db.execSQL(" DROP TABLE " + TABLE_UPCOMING + " IF EXISTS;");
                onCreate(db);
            } catch (SQLiteException exception) {
                L.t(mContext, exception + "");
            }
        }
    }
}