package com.asanam.udacityrecipebook.presenter;

import com.asanam.udacityrecipebook.repository.RecipeRepository;

import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class RecipeCardsPresenterTest {

    @Test
    public void shouldMakeRepositoryCallWhenShowCardsMethodIsCalled() {
        RecipeRepository repository = Mockito.mock(RecipeRepository.class);
        RecipeCardsPresenter presenter = new RecipeCardsPresenter(repository);

        presenter.showCards();

        Mockito.verify(repository).getRecipies(Matchers.any(RecipeRepository.Callback.class));
    }

}