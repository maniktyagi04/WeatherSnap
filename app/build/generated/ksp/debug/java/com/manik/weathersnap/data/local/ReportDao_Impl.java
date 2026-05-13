package com.manik.weathersnap.data.local;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class ReportDao_Impl implements ReportDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ReportEntity> __insertionAdapterOfReportEntity;

  private final EntityDeletionOrUpdateAdapter<ReportEntity> __deletionAdapterOfReportEntity;

  private final EntityDeletionOrUpdateAdapter<ReportEntity> __updateAdapterOfReportEntity;

  private final SharedSQLiteStatement __preparedStmtOfSoftDeleteReport;

  private final SharedSQLiteStatement __preparedStmtOfRestoreReport;

  public ReportDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfReportEntity = new EntityInsertionAdapter<ReportEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `reports` (`id`,`cityName`,`temperature`,`humidity`,`windSpeed`,`pressure`,`condition`,`notes`,`imagePath`,`originalImageSize`,`compressedImageSize`,`timestamp`,`isDeleted`,`deletedAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ReportEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getCityName());
        statement.bindString(3, entity.getTemperature());
        statement.bindString(4, entity.getHumidity());
        statement.bindString(5, entity.getWindSpeed());
        statement.bindString(6, entity.getPressure());
        statement.bindString(7, entity.getCondition());
        statement.bindString(8, entity.getNotes());
        statement.bindString(9, entity.getImagePath());
        statement.bindString(10, entity.getOriginalImageSize());
        statement.bindString(11, entity.getCompressedImageSize());
        statement.bindLong(12, entity.getTimestamp());
        final int _tmp = entity.isDeleted() ? 1 : 0;
        statement.bindLong(13, _tmp);
        if (entity.getDeletedAt() == null) {
          statement.bindNull(14);
        } else {
          statement.bindLong(14, entity.getDeletedAt());
        }
      }
    };
    this.__deletionAdapterOfReportEntity = new EntityDeletionOrUpdateAdapter<ReportEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `reports` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ReportEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfReportEntity = new EntityDeletionOrUpdateAdapter<ReportEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `reports` SET `id` = ?,`cityName` = ?,`temperature` = ?,`humidity` = ?,`windSpeed` = ?,`pressure` = ?,`condition` = ?,`notes` = ?,`imagePath` = ?,`originalImageSize` = ?,`compressedImageSize` = ?,`timestamp` = ?,`isDeleted` = ?,`deletedAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ReportEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getCityName());
        statement.bindString(3, entity.getTemperature());
        statement.bindString(4, entity.getHumidity());
        statement.bindString(5, entity.getWindSpeed());
        statement.bindString(6, entity.getPressure());
        statement.bindString(7, entity.getCondition());
        statement.bindString(8, entity.getNotes());
        statement.bindString(9, entity.getImagePath());
        statement.bindString(10, entity.getOriginalImageSize());
        statement.bindString(11, entity.getCompressedImageSize());
        statement.bindLong(12, entity.getTimestamp());
        final int _tmp = entity.isDeleted() ? 1 : 0;
        statement.bindLong(13, _tmp);
        if (entity.getDeletedAt() == null) {
          statement.bindNull(14);
        } else {
          statement.bindLong(14, entity.getDeletedAt());
        }
        statement.bindLong(15, entity.getId());
      }
    };
    this.__preparedStmtOfSoftDeleteReport = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE reports SET isDeleted = 1, deletedAt = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfRestoreReport = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE reports SET isDeleted = 0, deletedAt = null WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertReport(final ReportEntity report,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfReportEntity.insert(report);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object permanentlyDeleteReport(final ReportEntity report,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfReportEntity.handle(report);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateReport(final ReportEntity report,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfReportEntity.handle(report);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object softDeleteReport(final int id, final long timestamp,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfSoftDeleteReport.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, timestamp);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfSoftDeleteReport.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object restoreReport(final int id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfRestoreReport.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfRestoreReport.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<ReportEntity>> getActiveReports() {
    final String _sql = "SELECT * FROM reports WHERE isDeleted = 0 ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"reports"}, new Callable<List<ReportEntity>>() {
      @Override
      @NonNull
      public List<ReportEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCityName = CursorUtil.getColumnIndexOrThrow(_cursor, "cityName");
          final int _cursorIndexOfTemperature = CursorUtil.getColumnIndexOrThrow(_cursor, "temperature");
          final int _cursorIndexOfHumidity = CursorUtil.getColumnIndexOrThrow(_cursor, "humidity");
          final int _cursorIndexOfWindSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "windSpeed");
          final int _cursorIndexOfPressure = CursorUtil.getColumnIndexOrThrow(_cursor, "pressure");
          final int _cursorIndexOfCondition = CursorUtil.getColumnIndexOrThrow(_cursor, "condition");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfImagePath = CursorUtil.getColumnIndexOrThrow(_cursor, "imagePath");
          final int _cursorIndexOfOriginalImageSize = CursorUtil.getColumnIndexOrThrow(_cursor, "originalImageSize");
          final int _cursorIndexOfCompressedImageSize = CursorUtil.getColumnIndexOrThrow(_cursor, "compressedImageSize");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isDeleted");
          final int _cursorIndexOfDeletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "deletedAt");
          final List<ReportEntity> _result = new ArrayList<ReportEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ReportEntity _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpCityName;
            _tmpCityName = _cursor.getString(_cursorIndexOfCityName);
            final String _tmpTemperature;
            _tmpTemperature = _cursor.getString(_cursorIndexOfTemperature);
            final String _tmpHumidity;
            _tmpHumidity = _cursor.getString(_cursorIndexOfHumidity);
            final String _tmpWindSpeed;
            _tmpWindSpeed = _cursor.getString(_cursorIndexOfWindSpeed);
            final String _tmpPressure;
            _tmpPressure = _cursor.getString(_cursorIndexOfPressure);
            final String _tmpCondition;
            _tmpCondition = _cursor.getString(_cursorIndexOfCondition);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final String _tmpImagePath;
            _tmpImagePath = _cursor.getString(_cursorIndexOfImagePath);
            final String _tmpOriginalImageSize;
            _tmpOriginalImageSize = _cursor.getString(_cursorIndexOfOriginalImageSize);
            final String _tmpCompressedImageSize;
            _tmpCompressedImageSize = _cursor.getString(_cursorIndexOfCompressedImageSize);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final boolean _tmpIsDeleted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsDeleted);
            _tmpIsDeleted = _tmp != 0;
            final Long _tmpDeletedAt;
            if (_cursor.isNull(_cursorIndexOfDeletedAt)) {
              _tmpDeletedAt = null;
            } else {
              _tmpDeletedAt = _cursor.getLong(_cursorIndexOfDeletedAt);
            }
            _item = new ReportEntity(_tmpId,_tmpCityName,_tmpTemperature,_tmpHumidity,_tmpWindSpeed,_tmpPressure,_tmpCondition,_tmpNotes,_tmpImagePath,_tmpOriginalImageSize,_tmpCompressedImageSize,_tmpTimestamp,_tmpIsDeleted,_tmpDeletedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<ReportEntity>> getTrashReports() {
    final String _sql = "SELECT * FROM reports WHERE isDeleted = 1 ORDER BY deletedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"reports"}, new Callable<List<ReportEntity>>() {
      @Override
      @NonNull
      public List<ReportEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCityName = CursorUtil.getColumnIndexOrThrow(_cursor, "cityName");
          final int _cursorIndexOfTemperature = CursorUtil.getColumnIndexOrThrow(_cursor, "temperature");
          final int _cursorIndexOfHumidity = CursorUtil.getColumnIndexOrThrow(_cursor, "humidity");
          final int _cursorIndexOfWindSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "windSpeed");
          final int _cursorIndexOfPressure = CursorUtil.getColumnIndexOrThrow(_cursor, "pressure");
          final int _cursorIndexOfCondition = CursorUtil.getColumnIndexOrThrow(_cursor, "condition");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfImagePath = CursorUtil.getColumnIndexOrThrow(_cursor, "imagePath");
          final int _cursorIndexOfOriginalImageSize = CursorUtil.getColumnIndexOrThrow(_cursor, "originalImageSize");
          final int _cursorIndexOfCompressedImageSize = CursorUtil.getColumnIndexOrThrow(_cursor, "compressedImageSize");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isDeleted");
          final int _cursorIndexOfDeletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "deletedAt");
          final List<ReportEntity> _result = new ArrayList<ReportEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ReportEntity _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpCityName;
            _tmpCityName = _cursor.getString(_cursorIndexOfCityName);
            final String _tmpTemperature;
            _tmpTemperature = _cursor.getString(_cursorIndexOfTemperature);
            final String _tmpHumidity;
            _tmpHumidity = _cursor.getString(_cursorIndexOfHumidity);
            final String _tmpWindSpeed;
            _tmpWindSpeed = _cursor.getString(_cursorIndexOfWindSpeed);
            final String _tmpPressure;
            _tmpPressure = _cursor.getString(_cursorIndexOfPressure);
            final String _tmpCondition;
            _tmpCondition = _cursor.getString(_cursorIndexOfCondition);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final String _tmpImagePath;
            _tmpImagePath = _cursor.getString(_cursorIndexOfImagePath);
            final String _tmpOriginalImageSize;
            _tmpOriginalImageSize = _cursor.getString(_cursorIndexOfOriginalImageSize);
            final String _tmpCompressedImageSize;
            _tmpCompressedImageSize = _cursor.getString(_cursorIndexOfCompressedImageSize);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final boolean _tmpIsDeleted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsDeleted);
            _tmpIsDeleted = _tmp != 0;
            final Long _tmpDeletedAt;
            if (_cursor.isNull(_cursorIndexOfDeletedAt)) {
              _tmpDeletedAt = null;
            } else {
              _tmpDeletedAt = _cursor.getLong(_cursorIndexOfDeletedAt);
            }
            _item = new ReportEntity(_tmpId,_tmpCityName,_tmpTemperature,_tmpHumidity,_tmpWindSpeed,_tmpPressure,_tmpCondition,_tmpNotes,_tmpImagePath,_tmpOriginalImageSize,_tmpCompressedImageSize,_tmpTimestamp,_tmpIsDeleted,_tmpDeletedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getReportById(final int id, final Continuation<? super ReportEntity> $completion) {
    final String _sql = "SELECT * FROM reports WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<ReportEntity>() {
      @Override
      @Nullable
      public ReportEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCityName = CursorUtil.getColumnIndexOrThrow(_cursor, "cityName");
          final int _cursorIndexOfTemperature = CursorUtil.getColumnIndexOrThrow(_cursor, "temperature");
          final int _cursorIndexOfHumidity = CursorUtil.getColumnIndexOrThrow(_cursor, "humidity");
          final int _cursorIndexOfWindSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "windSpeed");
          final int _cursorIndexOfPressure = CursorUtil.getColumnIndexOrThrow(_cursor, "pressure");
          final int _cursorIndexOfCondition = CursorUtil.getColumnIndexOrThrow(_cursor, "condition");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfImagePath = CursorUtil.getColumnIndexOrThrow(_cursor, "imagePath");
          final int _cursorIndexOfOriginalImageSize = CursorUtil.getColumnIndexOrThrow(_cursor, "originalImageSize");
          final int _cursorIndexOfCompressedImageSize = CursorUtil.getColumnIndexOrThrow(_cursor, "compressedImageSize");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isDeleted");
          final int _cursorIndexOfDeletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "deletedAt");
          final ReportEntity _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpCityName;
            _tmpCityName = _cursor.getString(_cursorIndexOfCityName);
            final String _tmpTemperature;
            _tmpTemperature = _cursor.getString(_cursorIndexOfTemperature);
            final String _tmpHumidity;
            _tmpHumidity = _cursor.getString(_cursorIndexOfHumidity);
            final String _tmpWindSpeed;
            _tmpWindSpeed = _cursor.getString(_cursorIndexOfWindSpeed);
            final String _tmpPressure;
            _tmpPressure = _cursor.getString(_cursorIndexOfPressure);
            final String _tmpCondition;
            _tmpCondition = _cursor.getString(_cursorIndexOfCondition);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final String _tmpImagePath;
            _tmpImagePath = _cursor.getString(_cursorIndexOfImagePath);
            final String _tmpOriginalImageSize;
            _tmpOriginalImageSize = _cursor.getString(_cursorIndexOfOriginalImageSize);
            final String _tmpCompressedImageSize;
            _tmpCompressedImageSize = _cursor.getString(_cursorIndexOfCompressedImageSize);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final boolean _tmpIsDeleted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsDeleted);
            _tmpIsDeleted = _tmp != 0;
            final Long _tmpDeletedAt;
            if (_cursor.isNull(_cursorIndexOfDeletedAt)) {
              _tmpDeletedAt = null;
            } else {
              _tmpDeletedAt = _cursor.getLong(_cursorIndexOfDeletedAt);
            }
            _result = new ReportEntity(_tmpId,_tmpCityName,_tmpTemperature,_tmpHumidity,_tmpWindSpeed,_tmpPressure,_tmpCondition,_tmpNotes,_tmpImagePath,_tmpOriginalImageSize,_tmpCompressedImageSize,_tmpTimestamp,_tmpIsDeleted,_tmpDeletedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
