package com.jingdroid.cook.view;

import com.jingdroid.cook.presentation.model.AuthorEntityModel;

import java.util.List;

/**
 * Created by Jing on 2017/7/24.
 */

public interface IAuthorView {
    void loadAuthor(List<AuthorEntityModel> authors);
}
