package com.asanam.udacityrecipebook.presenter;

import android.database.Cursor;

import com.asanam.udacityrecipebook.repository.DBRepository;
import com.asanam.udacityrecipebook.view.DetailsView;

import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DetailsPresenterTest {

    @Test
    public void shouldMakeRepositoryCall() {
        DetailsView view = mock(DetailsView.class);
        DBRepository repository = mock(DBRepository.class);
        DetailsPresenter presenter = new DetailsPresenter(view, repository);

        Long recipeId = new Long(1);
        presenter.getSteps(recipeId);

        Mockito.verify(repository).getSteps(recipeId);
    }

    @Test
    public void shouldMakeViewCallWhenRepositoryReturnsValidResponse() {
        DetailsView view = mock(DetailsView.class);
        DBRepository repository = mock(DBRepository.class);
        DetailsPresenter presenter = new DetailsPresenter(view, repository);

        Cursor cursor = mock(Cursor.class);
        when(cursor.getCount()).thenReturn(1);
        when(repository.getSteps(any(Long.class))).thenReturn(cursor);
        Long recipeId = new Long(1);
        presenter.getSteps(recipeId);

        verify(view).showDetails(cursor);
    }

    @Test
    public void shouldNotMakeViewCallWhenRepositoryReturnsInValidResponse() {
        DetailsView view = mock(DetailsView.class);
        DBRepository repository = mock(DBRepository.class);
        DetailsPresenter presenter = new DetailsPresenter(view, repository);

        Cursor cursor = mock(Cursor.class);
        when(cursor.getCount()).thenReturn(0);
        when(repository.getSteps(any(Long.class))).thenReturn(cursor);
        Long recipeId = new Long(1);
        presenter.getSteps(recipeId);

        verify(view, times(0)).showDetails(cursor);
    }
}