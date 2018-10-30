package com.dida.loginmodule.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.dida.loginmodule.model.Recipe;

import java.util.List;

@Dao
public interface RecipeDaoAccess {

    @Insert
    void insertOnlySingleRecipe(Recipe recipe);

    @Insert
    void insertMultipleRecipe(List<Recipe> recipeList);

    @Query("SELECT*FROM Recipe WHERE Id =:recipeId")
    Recipe fetchOneRecipebyRecipeId(int recipeId);

    @Update
    void updateRecipe(Recipe recipe);

    @Delete
    void deleteRecipe(Recipe recipe);
}