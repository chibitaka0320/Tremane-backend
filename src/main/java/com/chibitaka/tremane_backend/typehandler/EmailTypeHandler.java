package com.chibitaka.tremane_backend.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import com.chibitaka.tremane_backend.vo.EmailVo;

@MappedTypes(EmailVo.class)
public class EmailTypeHandler extends BaseTypeHandler<EmailVo> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, EmailVo parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setString(i, parameter.getValue());
    }

    @Override
    public EmailVo getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        return value != null ? new EmailVo(value) : null;
    }

    @Override
    public EmailVo getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        return value != null ? new EmailVo(value) : null;
    }

    @Override
    public EmailVo getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        return value != null ? new EmailVo(value) : null;
    }

}
