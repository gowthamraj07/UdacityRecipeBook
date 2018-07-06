package com.asanam.udacityrecipebook.presenter;

import com.asanam.udacityrecipebook.domain.Recipe;
import com.asanam.udacityrecipebook.repository.RecipeRepository;
import com.asanam.udacityrecipebook.view.RecipeCardsView;

import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class RecipeCardsPresenterTest {

    private List<Recipe> spyDomainList;

    @Test
    public void shouldMakeRepositoryCallWhenShowCardsMethodIsCalled() {
        RecipeRepository repository = mock(RecipeRepository.class);
        RecipeCardsPresenter presenter = new RecipeCardsPresenter(null, repository);

        presenter.showCards();

        verify(repository).getRecipies(any(RecipeRepository.Callback.class));
    }

    @Test
    public void shouldShowCardsWhenRepositoryReturnsRecipeList() {
        RecipeCardsView view = mock(RecipeCardsView.class);
        RecipeRepository repository = new FakeSuccessRepository();
        RecipeCardsPresenter presenter = new RecipeCardsPresenter(view, repository);

        presenter.showCards();

        verify(view).showCardsView(spyDomainList);
    }

    @Test
    public void shouldShowErrorMessageWhenRepositoryReturnsEmptyList() {
        RecipeCardsView view = mock(RecipeCardsView.class);
        RecipeRepository repository = new FakeFailureRepository();
        RecipeCardsPresenter presenter = new RecipeCardsPresenter(view, repository);

        presenter.showCards();

        verify(view).showErrorMessage();
    }

    private class FakeSuccessRepository implements RecipeRepository {
        @Override
        public void getRecipies(Callback callback) {
            spyDomainList = singletonList(new Recipe());
            callback.onSuccess(spyDomainList);
        }
    }

    private class FakeFailureRepository implements RecipeRepository {
        @Override
        public void getRecipies(Callback callback) {
            callback.onSuccess(Collections.EMPTY_LIST);
        }
    }
}