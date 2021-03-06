package com.cygnet.model.dao;

import android.arch.paging.LivePagedListProvider;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.cygnet.model.db.models.AlbumModel;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by cmpatel on 11/7/2017.
 */

/**
 * Name : AlbumDao
 *<br> Created by 1730 on 11/08/2017
 *<br> Modified by 1730 on 11/08/2017
 *<br> Purpose : Dao interface will contains db operation methods with appropriate annotations for AlbumModel.
 */

@Dao
public interface AlbumDao {

    //To get single AlbumModel from AlbumId
    @Query("Select * from albumsTbl where id = :albumId")
    AlbumModel getAlbum(int albumId);

    //LivePagedListProvider used for observe LiveData<List<AlbumModel>> with Paging.
    @Query("Select * from albumsTbl ORDER BY id ASC")
    LivePagedListProvider<Integer, AlbumModel> getPagedAlbumList();

    //Insert AlbumModel Data
    @Insert(onConflict = REPLACE)
    void insertAlbum(AlbumModel albumModel);

    //Insert list of AlbumModel Data
    @Insert(onConflict = REPLACE)
    void insertAlbumList(List<AlbumModel> albumModelList);

    //Update AlbumModel Data
    @Update
    void updateAlbum(AlbumModel userModel);

    //Delete AlbumModel data
    @Delete
    void deleteAlbum(AlbumModel userModel);


}
