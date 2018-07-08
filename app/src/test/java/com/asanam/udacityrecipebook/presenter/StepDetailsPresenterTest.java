package com.asanam.udacityrecipebook.presenter;

import android.database.Cursor;
import android.support.annotation.NonNull;

import com.asanam.udacityrecipebook.db.DBContract;
import com.asanam.udacityrecipebook.repository.DBRepository;
import com.asanam.udacityrecipebook.view.StepDetailsView;

import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StepDetailsPresenterTest {

    private static final Long RECIPE_ID = 0L;
    private static final Long STEP_ID = 0L;
    private static final String VIDEO_URL = "http://";
    private static final int VIDEO_URL_INDEX = 1;
    private static final int DESCRIPTION_INDEX = 2;
    private static final String ANY_DESCRIPTION = "any description";
    private static final int IMAGE_URL_INDEX = 3;
    public static final String IMAGE_MP4_URL = "http://hi.mp4";
    public static final String IMAGE_URL = "http://";

    @Test
    public void shouldMakeRepositoryCall() {
        StepDetailsView view = Mockito.mock(StepDetailsView.class);
        DBRepository repository = Mockito.mock(DBRepository.class);
        StepDetailsPresenter presenter = new StepDetailsPresenter(view, repository);

        Cursor cursor = mock(Cursor.class);
        when(repository.getStepDetails(STEP_ID, RECIPE_ID)).thenReturn(cursor);
        Cursor previousCursor = mock(Cursor.class);
        when(previousCursor.getCount()).thenReturn(0);
        when(repository.getPreviousStepDetails(RECIPE_ID, STEP_ID)).thenReturn(previousCursor);
        presenter.showStepDetailsScreen(RECIPE_ID, STEP_ID);

        Mockito.verify(repository).getStepDetails(STEP_ID, RECIPE_ID);
    }

    @Test
    public void shouldShowVideoWhenUrlIsNotNull() {
        StepDetailsView view = Mockito.mock(StepDetailsView.class);
        DBRepository repository = Mockito.mock(DBRepository.class);
        StepDetailsPresenter presenter = new StepDetailsPresenter(view, repository);

        Cursor cursor = mock(Cursor.class);
        when(cursor.getColumnIndex(DBContract.StepTable.COLUMN_VIDEO_URL)).thenReturn(VIDEO_URL_INDEX);
        when(cursor.getColumnIndex(DBContract.StepTable.COLUMN_DESCRIPTION)).thenReturn(DESCRIPTION_INDEX);
        when(cursor.getString(VIDEO_URL_INDEX)).thenReturn(VIDEO_URL);
        when(cursor.getString(DESCRIPTION_INDEX)).thenReturn(ANY_DESCRIPTION);
        when(repository.getStepDetails(STEP_ID, RECIPE_ID)).thenReturn(cursor);
        Cursor previousCursor = mock(Cursor.class);
        when(previousCursor.getCount()).thenReturn(0);
        when(repository.getPreviousStepDetails(RECIPE_ID, STEP_ID)).thenReturn(previousCursor);
        presenter.showStepDetailsScreen(RECIPE_ID, STEP_ID);

        verify(view).showVideo(VIDEO_URL, ANY_DESCRIPTION);
        verify(view).hideImage();
    }

    @Test
    public void shouldShowVideoWhenThumbnailUrlEndsWithMp4() {
        StepDetailsView view = Mockito.mock(StepDetailsView.class);
        DBRepository repository = Mockito.mock(DBRepository.class);
        StepDetailsPresenter presenter = new StepDetailsPresenter(view, repository);

        Cursor cursor = getMockCursor(IMAGE_MP4_URL);
        when(repository.getStepDetails(STEP_ID, RECIPE_ID)).thenReturn(cursor);
        Cursor previousCursor = mock(Cursor.class);
        when(previousCursor.getCount()).thenReturn(0);
        when(repository.getPreviousStepDetails(RECIPE_ID, STEP_ID)).thenReturn(previousCursor);
        presenter.showStepDetailsScreen(RECIPE_ID, STEP_ID);

        verify(view).showVideo(IMAGE_MP4_URL, ANY_DESCRIPTION);
        verify(view).hideImage();
    }

    @Test
    public void shouldShowImageWhenVideoIsNotAvailableButImageUrlIsAvalable() {
        StepDetailsView view = Mockito.mock(StepDetailsView.class);
        DBRepository repository = Mockito.mock(DBRepository.class);
        StepDetailsPresenter presenter = new StepDetailsPresenter(view, repository);

        Cursor cursor = getMockCursor(IMAGE_URL);
        when(repository.getStepDetails(STEP_ID, RECIPE_ID)).thenReturn(cursor);
        Cursor previousCursor = mock(Cursor.class);
        when(previousCursor.getCount()).thenReturn(0);
        when(repository.getPreviousStepDetails(RECIPE_ID, STEP_ID)).thenReturn(previousCursor);
        presenter.showStepDetailsScreen(RECIPE_ID, STEP_ID);

        verify(view).showImage(IMAGE_URL, ANY_DESCRIPTION);
        verify(view).hideVideo();
    }

    @Test
    public void shouldHideVideAndImageWhenBothAreNotAvailable() {
        StepDetailsView view = Mockito.mock(StepDetailsView.class);
        DBRepository repository = Mockito.mock(DBRepository.class);
        StepDetailsPresenter presenter = new StepDetailsPresenter(view, repository);

        Cursor cursor = getMockCursor(null);
        when(repository.getStepDetails(STEP_ID, RECIPE_ID)).thenReturn(cursor);
        Cursor previousCursor = mock(Cursor.class);
        when(previousCursor.getCount()).thenReturn(0);
        when(repository.getPreviousStepDetails(RECIPE_ID, STEP_ID)).thenReturn(previousCursor);
        presenter.showStepDetailsScreen(RECIPE_ID, STEP_ID);

        verify(view).hideImage();
        verify(view).hideVideo();
        verify(view).showDescription(ANY_DESCRIPTION);
    }

    @NonNull
    private Cursor getMockCursor(String videoUrl) {
        Cursor cursor = mock(Cursor.class);
        when(cursor.getColumnIndex(DBContract.StepTable.COLUMN_VIDEO_URL)).thenReturn(VIDEO_URL_INDEX);
        when(cursor.getColumnIndex(DBContract.StepTable.COLUMN_DESCRIPTION)).thenReturn(DESCRIPTION_INDEX);
        when(cursor.getColumnIndex(DBContract.StepTable.COLUMN_THUMBNAIL_URL)).thenReturn(IMAGE_URL_INDEX);
        when(cursor.getString(VIDEO_URL_INDEX)).thenReturn(null);
        when(cursor.getString(DESCRIPTION_INDEX)).thenReturn(ANY_DESCRIPTION);
        when(cursor.getString(IMAGE_URL_INDEX)).thenReturn(videoUrl);
        return cursor;
    }

    @Test
    public void shouldMakeRepositoryCallForCheckingPreviousStep() {
        StepDetailsView view = Mockito.mock(StepDetailsView.class);
        DBRepository repository = Mockito.mock(DBRepository.class);
        StepDetailsPresenter presenter = new StepDetailsPresenter(view, repository);

        Cursor cursor = mock(Cursor.class);
        when(cursor.getColumnIndex(DBContract.StepTable.COLUMN_VIDEO_URL)).thenReturn(VIDEO_URL_INDEX);
        when(cursor.getColumnIndex(DBContract.StepTable.COLUMN_DESCRIPTION)).thenReturn(DESCRIPTION_INDEX);
        when(cursor.getString(VIDEO_URL_INDEX)).thenReturn(VIDEO_URL);
        when(cursor.getString(DESCRIPTION_INDEX)).thenReturn(ANY_DESCRIPTION);
        when(repository.getStepDetails(STEP_ID, RECIPE_ID)).thenReturn(cursor);
        Cursor previousCursor = mock(Cursor.class);
        when(previousCursor.getCount()).thenReturn(0);
        when(repository.getPreviousStepDetails(RECIPE_ID, STEP_ID)).thenReturn(previousCursor);
        presenter.showStepDetailsScreen(RECIPE_ID, STEP_ID);

        verify(repository).getPreviousStepDetails(RECIPE_ID, STEP_ID);
    }

    @Test
    public void shouldHidePreviousButtonWhenThereIsNoPreviousStep() {
        StepDetailsView view = Mockito.mock(StepDetailsView.class);
        DBRepository repository = Mockito.mock(DBRepository.class);
        StepDetailsPresenter presenter = new StepDetailsPresenter(view, repository);

        Cursor cursor = mock(Cursor.class);
        when(cursor.getColumnIndex(DBContract.StepTable.COLUMN_VIDEO_URL)).thenReturn(VIDEO_URL_INDEX);
        when(cursor.getColumnIndex(DBContract.StepTable.COLUMN_DESCRIPTION)).thenReturn(DESCRIPTION_INDEX);
        when(cursor.getString(VIDEO_URL_INDEX)).thenReturn(VIDEO_URL);
        when(cursor.getString(DESCRIPTION_INDEX)).thenReturn(ANY_DESCRIPTION);
        when(repository.getStepDetails(STEP_ID, RECIPE_ID)).thenReturn(cursor);
        Cursor previousCursor = mock(Cursor.class);
        when(previousCursor.getCount()).thenReturn(0);
        when(repository.getPreviousStepDetails(RECIPE_ID, STEP_ID)).thenReturn(previousCursor);
        presenter.showStepDetailsScreen(RECIPE_ID, STEP_ID);

        verify(view).hidePrevious();
    }
}