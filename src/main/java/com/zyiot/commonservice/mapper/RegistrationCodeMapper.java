package com.zyiot.commonservice.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.zyiot.commonservice.entity.RegistrationCode;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${authot}
 * @since 2018-03-29 14:33:27
 */
public interface RegistrationCodeMapper extends BaseMapper<RegistrationCode> {
	public List<RegistrationCode> selectRegistrationCodeByPage(Pagination page,@Param("registrationCode")RegistrationCode registrationCode);
	public List<RegistrationCode> selectOrRegistrationCodeByPage(Pagination page,@Param("key")String key);
	public List<RegistrationCode> selectRegistrationCodeAll(@Param("registrationCode")RegistrationCode registrationCode);
	@Update("update t_registration_code set status=#{status} where id=#{id} and status='未使用'")
	public Integer changeStatus(@Param("status")String status,@Param("id")Long id);
}
