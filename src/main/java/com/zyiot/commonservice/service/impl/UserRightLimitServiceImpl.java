package com.zyiot.commonservice.service.impl;

import com.zyiot.commonservice.entity.UserRightLimit;
import com.zyiot.commonservice.mapper.UserRightLimitMapper;
import com.zyiot.commonservice.service.IUserRightLimitService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 该表存储用户命令被禁止/允许的权限。 服务实现类
 * </p>
 *
 * @author wyf
 * @since 2017-12-08
 */
@Service
public class UserRightLimitServiceImpl extends ServiceImpl<UserRightLimitMapper, UserRightLimit> implements IUserRightLimitService {

}
