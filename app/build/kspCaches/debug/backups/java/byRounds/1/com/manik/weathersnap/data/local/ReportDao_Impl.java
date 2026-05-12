package com.manik.weathersnap.data.local;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
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

  public ReportDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfReportEntity = new EntityInsertionAdapter<ReportEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `reports` (`id`,`cityName`,`temperature`,`humidity`,`windSpeed`,`pressure`,`condition`,`notes`,`imagePath`,`originalImageSize`,`compressedImageSize`,`timestamp`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?)";
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
  public Flow<List<ReportEntity>> getAllReports() {
    final String _sql = "SELECT * FROM reports ORDER BY timestamp DESC";
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
            _item = new ReportEntity(_tmpId,_tmpCityName,_tmpTemperature,_tmpHumidity,_tmpWindSpeed,_tmpPressure,_tmpCondition,_tmpNotes,_tmpImagePath,_tmpOriginalImageSize,_tmpCompressedImageSize,_tmpTimestamp);
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
            _result = new ReportEntity(_tmpId,_tmpCityName,_tmpTemperature,_tmpHumidity,_tmpWindSpeed,_tmpPressure,_tmpCondition,_tmpNotes,_tmpImagePath,_tmpOriginalImageSize,_tmpCompressedImageSize,_tmpTimestamp);
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
