package com.asanam.udacityrecipebook.repository;

import android.database.Cursor;

import com.asanam.udacityrecipebook.network.NetworkApi;

import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class NetworkRecipeRepositoryTest {

    @Test
    public void shouldCallNetworkApiServiceWhenGetRecipesIsCalled() {
        NetworkApi api = Mockito.mock(NetworkApi.class);
        NetworkRecipeRepository repository = new NetworkRecipeRepository(api);

        RecipeRepository.Callback callback = new SpyCallback();
        repository.getRecipes(callback);

        Mockito.verify(api).get(Matchers.any(String.class));
    }

    private class SpyCallback implements RecipeRepository.Callback {
        @Override
        public void onSuccess(Cursor domainList) {

        }
    }
}