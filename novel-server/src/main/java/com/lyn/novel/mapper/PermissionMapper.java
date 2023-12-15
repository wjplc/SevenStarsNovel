package com.lyn.novel.mapper;

import com.lyn.novel.entity.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wjp
 * @since 2023/12/08
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    /**
     * 根据userid查询用户权限
     * @param userId
     * @return
     */
    List<String> getPermissionsByUseId(Long userId);
}
