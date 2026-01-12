package com.aienuo.tea.mapper;

import com.aienuo.tea.model.dto.PagingQueryUserDTO;
import com.aienuo.tea.model.po.User;
import com.aienuo.tea.model.vo.UserPageVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * 系统用户 Mapper 接口
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 分页查询
     *
     * @param pagingQuery - 分页查询对象
     * @return Page<UserPageVO> - 用户信息
     */
    Page<UserPageVO> pagingQueryListByParameter(@Param("pg") final Page<UserPageVO> pagingQueryList,  @Param("param") final PagingQueryUserDTO pagingQuery);

}
