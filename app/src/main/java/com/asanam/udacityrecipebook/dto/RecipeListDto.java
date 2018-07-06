package com.asanam.udacityrecipebook.dto;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class RecipeListDto {
    private List<RecipeDto> recipeDtoList;

    public RecipeListDto(List<RecipeDto> recipeDtos) {
        recipeDtoList = new ArrayList<>();
        if(recipeDtos != null) {
            recipeDtoList.addAll(recipeDtos);
        }
    }

    public List<RecipeDto> getRecipeDtoList() {
        return recipeDtoList;
    }

    public void setRecipeDtoList(List<RecipeDto> recipeDtoList) {
        this.recipeDtoList = recipeDtoList;
    }
}
