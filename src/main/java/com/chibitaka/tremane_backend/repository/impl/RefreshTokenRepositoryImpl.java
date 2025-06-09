package com.chibitaka.tremane_backend.repository.impl;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.chibitaka.tremane_backend.entity.RefreshTokenEntity;
import com.chibitaka.tremane_backend.repository.RefreshTokenRepository;

@Mapper
public interface RefreshTokenRepositoryImpl extends RefreshTokenRepository {

    @Override
    @Insert({
            "INSERT INTO refresh_tokens(user_id, token, expiry_date)",
            "VALUES(#{userId}, #{token}, #{expiryDate})"
    })
    int insert(RefreshTokenEntity entity);
}
