package com.aienuo.tea.model.converter;

import com.aienuo.tea.model.dto.JobAddDTO;
import com.aienuo.tea.model.dto.JobUpdateDTO;
import com.aienuo.tea.model.po.Job;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * 定时任务 转换类
 */
@Mapper
public interface JobConverter {

    /**
     * 定时任务 实例
     */
    JobConverter INSTANCE = Mappers.getMapper(JobConverter.class);

    /**
     * DTO 转 PO
     * 将添加对象生成新的数据库对象
     *
     * @param create - 添加对象
     * @return PO - 数据库对象
     */
    @Mappings({
            @Mapping(target = "id", expression = "java(com.baomidou.mybatisplus.core.toolkit.IdWorker.getIdStr())"),
            @Mapping(target = "creatorBy", ignore = true),
            @Mapping(target = "createTime", ignore = true),
            @Mapping(target = "updaterBy", ignore = true),
            @Mapping(target = "updateTime", ignore = true),
            @Mapping(target = "status", expression = "java(com.aienuo.tea.common.enums.JobStatusEnum.NORMAL)"),
            @Mapping(target = "retryCount", defaultValue = "0"),
            @Mapping(target = "retryInterval", defaultValue = "0"),
            @Mapping(target = "monitorTimeout", defaultValue = "-1"),
    })
    Job getAddEntity(final JobAddDTO create);

    /**
     * DTO 合并到 PO
     * 将更新对象与数据库对象合并成新的数据库更新对象
     *
     * @param update - 更新对象
     * @param po     - 数据库对象
     */
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "creatorBy", ignore = true),
            @Mapping(target = "createTime", ignore = true),
            @Mapping(target = "updaterBy", ignore = true),
            @Mapping(target = "updateTime", ignore = true),
            @Mapping(target = "status", ignore = true),
            @Mapping(target = "retryCount", defaultValue = "0"),
            @Mapping(target = "retryInterval", defaultValue = "0"),
            @Mapping(target = "monitorTimeout", defaultValue = "-1"),
    })
    void getUpdateEntity(@MappingTarget final Job po, final JobUpdateDTO update);

}
