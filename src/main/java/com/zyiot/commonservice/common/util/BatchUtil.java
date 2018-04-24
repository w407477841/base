package com.zyiot.commonservice.common.util;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.enums.SqlMethod;
import com.baomidou.mybatisplus.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.baomidou.mybatisplus.toolkit.CollectionUtils;

public class BatchUtil<T> {
@Transactional
	public  void  batchInsert(Class<T> clazz,List<T> entities){
		if (CollectionUtils.isEmpty(entities)) {
            throw new IllegalArgumentException("Error: entityList must not be empty");
        }
        try (SqlSession batchSqlSession = SqlHelper.sqlSessionBatch(clazz)) {
            int size = entities.size();
          //  String sqlStatement = sqlStatement(SqlMethod.INSERT_ONE);
            String sqlStatement= SqlHelper.table(clazz).getSqlStatement(SqlMethod.INSERT_ONE.getMethod());
            for (int i = 0; i < size; i++) {
                batchSqlSession.insert(sqlStatement, entities.get(i));
                if (i >= 1 && i % 30 == 0) {
                    batchSqlSession.flushStatements();
                }
            }
            batchSqlSession.flushStatements();
        } catch (Throwable e) {
            throw new MybatisPlusException("Error: Cannot execute insertBatch Method. Cause", e);
        }
	}
	
public static void main(String[] args) {
	
	PasswordEncoder encoder = new StandardPasswordEncoder("abc");
	System.out.println( encoder.encode("k"));
	// encoder = new StandardPasswordEncoder("abc");
		System.out.println( encoder.encode("k"));
		 encoder = new StandardPasswordEncoder("abc");
	//		System.out.println( encoder.encode("k"));
}

}
