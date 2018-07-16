package com.asanam.udacityrecipebook.view;

public interface StepDetailsView {
    void showVideo(String url, String description);
    void showImage(String imageUrl, String anyDescription);
    void showDescription(String anyDescription);

    void hideImage();
    void hideVideo();


    void hidePrevious();

    void showPrevious();

    void showNext();

    void hideNext();

    void reset();

    void hideImageAndVideo();
}
