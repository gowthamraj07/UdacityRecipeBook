package com.asanam.udacityrecipebook.presenter;

import android.database.Cursor;

import com.asanam.udacityrecipebook.repository.DBRepository;
import com.asanam.udacityrecipebook.view.StepDetailsView;

import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StepDetailsPresenterTest {

    @Test
    public void shouldMakeRepositoryCall() {
        StepDetailsView view = Mockito.mock(StepDetailsView.class);
        DBRepository repository = Mockito.mock(DBRepository.class);
        StepDetailsPresenter presenter = new StepDetailsPresenter(view, repository);

        Long stepId = new Long(0);
        Long recipeId = new Long(0);
        Cursor cursor = mock(Cursor.class);
        when(repository.getStepDetails(stepId, recipeId)).thenReturn(cursor);
        presenter.showStepDetailsScreen(recipeId, stepId);

        Mockito.verify(repository).getStepDetails(stepId, recipeId);
    }
}