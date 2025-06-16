package com.chibitaka.tremane_backend.repository.impl;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import com.chibitaka.tremane_backend.entity.RefreshTokenEntity;
import com.chibitaka.tremane_backend.repository.RefreshTokenRepository;

@Mapper
public interface RefreshTokenRepositoryImpl extends RefreshTokenRepository {

        @Override
        @Insert({
                        "INSERT INTO refresh_tokens(user_id, token, device_info, expiry_date)",
                        "VALUES(#{userId}, #{token}, #{deviceInfo}, #{expiryDate})"
        })
        int insert(RefreshTokenEntity entity);

        @Override
        @Update({
                        "UPDATE refresh_tokens SET token = #{token}, expiry_date = #{expiryDate}",
                        "WHERE user_id = #{userId} AND device_info =#{deviceInfo}"
        })
        int update(RefreshTokenEntity entity);
}
