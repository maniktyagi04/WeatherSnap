package com.manik.weathersnap.data.local;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class WeatherDatabase_Impl extends WeatherDatabase {
  private volatile WeatherDao _weatherDao;

  private volatile ReportDao _reportDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(3) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `weather_table` (`id` INTEGER, `cityName` TEXT NOT NULL, `temperature` REAL NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `reports` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `cityName` TEXT NOT NULL, `temperature` TEXT NOT NULL, `humidity` TEXT NOT NULL, `windSpeed` TEXT NOT NULL, `pressure` TEXT NOT NULL, `condition` TEXT NOT NULL, `notes` TEXT NOT NULL, `imagePath` TEXT NOT NULL, `originalImageSize` TEXT NOT NULL, `compressedImageSize` TEXT NOT NULL, `timestamp` INTEGER NOT NULL, `isDeleted` INTEGER NOT NULL, `deletedAt` INTEGER)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'b16d176e9a970ee145a905ea533b3d33')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `weather_table`");
        db.execSQL("DROP TABLE IF EXISTS `reports`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsWeatherTable = new HashMap<String, TableInfo.Column>(3);
        _columnsWeatherTable.put("id", new TableInfo.Column("id", "INTEGER", false, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeatherTable.put("cityName", new TableInfo.Column("cityName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeatherTable.put("temperature", new TableInfo.Column("temperature", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysWeatherTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesWeatherTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoWeatherTable = new TableInfo("weather_table", _columnsWeatherTable, _foreignKeysWeatherTable, _indicesWeatherTable);
        final TableInfo _existingWeatherTable = TableInfo.read(db, "weather_table");
        if (!_infoWeatherTable.equals(_existingWeatherTable)) {
          return new RoomOpenHelper.ValidationResult(false, "weather_table(com.manik.weathersnap.data.local.WeatherEntity).\n"
                  + " Expected:\n" + _infoWeatherTable + "\n"
                  + " Found:\n" + _existingWeatherTable);
        }
        final HashMap<String, TableInfo.Column> _columnsReports = new HashMap<String, TableInfo.Column>(14);
        _columnsReports.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReports.put("cityName", new TableInfo.Column("cityName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReports.put("temperature", new TableInfo.Column("temperature", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReports.put("humidity", new TableInfo.Column("humidity", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReports.put("windSpeed", new TableInfo.Column("windSpeed", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReports.put("pressure", new TableInfo.Column("pressure", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReports.put("condition", new TableInfo.Column("condition", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReports.put("notes", new TableInfo.Column("notes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReports.put("imagePath", new TableInfo.Column("imagePath", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReports.put("originalImageSize", new TableInfo.Column("originalImageSize", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReports.put("compressedImageSize", new TableInfo.Column("compressedImageSize", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReports.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReports.put("isDeleted", new TableInfo.Column("isDeleted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReports.put("deletedAt", new TableInfo.Column("deletedAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysReports = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesReports = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoReports = new TableInfo("reports", _columnsReports, _foreignKeysReports, _indicesReports);
        final TableInfo _existingReports = TableInfo.read(db, "reports");
        if (!_infoReports.equals(_existingReports)) {
          return new RoomOpenHelper.ValidationResult(false, "reports(com.manik.weathersnap.data.local.ReportEntity).\n"
                  + " Expected:\n" + _infoReports + "\n"
                  + " Found:\n" + _existingReports);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "b16d176e9a970ee145a905ea533b3d33", "2b4cbaa8c8afba8091a837e70bded9bf");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "weather_table","reports");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `weather_table`");
      _db.execSQL("DELETE FROM `reports`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(WeatherDao.class, WeatherDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ReportDao.class, ReportDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public WeatherDao getDao() {
    if (_weatherDao != null) {
      return _weatherDao;
    } else {
      synchronized(this) {
        if(_weatherDao == null) {
          _weatherDao = new WeatherDao_Impl(this);
        }
        return _weatherDao;
      }
    }
  }

  @Override
  public ReportDao getReportDao() {
    if (_reportDao != null) {
      return _reportDao;
    } else {
      synchronized(this) {
        if(_reportDao == null) {
          _reportDao = new ReportDao_Impl(this);
        }
        return _reportDao;
      }
    }
  }
}
