package com.jingdroid.cook.presentation.mapper;

import com.jingdroid.cook.data.entity.AuthorEntity;
import com.jingdroid.cook.presentation.model.AuthorEntityModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jing on 2017/7/24.
 */

public class ModelDataMapper {
    public static ModelDataMapper instance;

    public static ModelDataMapper getInstance() {
        if (instance == null) {
            instance = new ModelDataMapper();
        }
        return instance;
    }
    private ModelDataMapper() {

    }

    public AuthorEntityModel transform_author(AuthorEntity entity) {
        AuthorEntityModel model = new AuthorEntityModel();
        model.setAuthor_id(entity.getAuthor_id());
        model.setAuthor_name(entity.getAuthor_name());
        model.setAuthor_head(entity.getAuthor_head());
        model.setAuthor_sign(entity.getAuthor_sign());
        model.setTypeid(entity.getTypeid());
        return model;
    }

    public List<AuthorEntityModel> transform_authorlist(List<AuthorEntity> entities) {
        List<AuthorEntityModel> list = new ArrayList<>();
        if (entities != null && !entities.isEmpty()) {
            for (AuthorEntity entity : entities) {
                list.add(transform_author(entity));
            }
        }
        return list;
    }
}
