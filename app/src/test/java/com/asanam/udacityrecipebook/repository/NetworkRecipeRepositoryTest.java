package com.asanam.udacityrecipebook.repository;

import android.database.Cursor;

import com.asanam.udacityrecipebook.dto.RecipeListDto;
import com.asanam.udacityrecipebook.network.NetworkApi;

import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

public class NetworkRecipeRepositoryTest {

    @Test
    public void shouldCallNetworkApiServiceWhenGetRecipesIsCalled() {
        NetworkApi api = Mockito.mock(NetworkApi.class);
        DBRepository dbRepository = Mockito.mock(DBRepository.class);
        NetworkRecipeRepository repository = new NetworkRecipeRepository(api, dbRepository);

        RecipeRepository.Callback callback = new SpyCallback();
        repository.getRecipes(callback);

        Mockito.verify(api).get(Matchers.any(String.class));
    }

    @Test
    public void shouldCallSaveRecipesWhenApiReturnsValidResponse() {
        NetworkApi api = new FakeSuccessApi();
        DBRepository dbRepository = Mockito.mock(DBRepository.class);
        NetworkRecipeRepository repository = new NetworkRecipeRepository(api, dbRepository);

        RecipeRepository.Callback callback = Mockito.mock(RecipeRepository.Callback.class);
        repository.getRecipes(callback);

        Mockito.verify(dbRepository).saveRecipe(Matchers.any(RecipeListDto.class));
    }

    @Test
    public void shouldNotCallSaveRecipesWhenApiReturnsInvalidResponse() {
        NetworkApi api = new FakeFailureApi();
        DBRepository dbRepository = Mockito.mock(DBRepository.class);
        NetworkRecipeRepository repository = new NetworkRecipeRepository(api, dbRepository);

        RecipeRepository.Callback callback = Mockito.mock(RecipeRepository.Callback.class);
        repository.getRecipes(callback);

        Mockito.verify(dbRepository, Mockito.times(0)).saveRecipe(Matchers.any(RecipeListDto.class));
    }

    private class SpyCallback implements RecipeRepository.Callback {
        @Override
        public void onSuccess(Cursor domainList) {

        }
    }

    private class FakeSuccessApi implements NetworkApi {
        @Override
        public String get(String url) {
            return "{}";
        }
    }

    private class FakeFailureApi implements NetworkApi {
        @Override
        public String get(String url) {
            return null;
        }
    }
}