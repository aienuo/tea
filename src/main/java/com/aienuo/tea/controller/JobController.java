package com.aienuo.tea.controller;

import com.aienuo.tea.business.JobBusiness;
import com.aienuo.tea.common.base.BaseController;
import com.aienuo.tea.common.response.CommonResponse;
import com.aienuo.tea.model.dto.JobAddDTO;
import com.aienuo.tea.model.dto.JobUpdateDTO;
import com.aienuo.tea.model.dto.PagingQueryJobDTO;
import com.aienuo.tea.model.dto.PagingQueryJobLogDTO;
import com.aienuo.tea.model.vo.JobLogVO;
import com.aienuo.tea.model.vo.JobVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.quartz.SchedulerException;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 定时任务
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/job")
@Tag(name = "定时任务", description = "定时任务", extensions = {
        @Extension(properties = {@ExtensionProperty(name = "x-order", value = "3", parseValue = true)})
})
public class JobController extends BaseController<JobBusiness> {

    @GetMapping("/page")
    @ApiOperationSupport(order = 1, author = "SanJin")
    @Operation(summary = "定时任务分页查询", security = {@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)})
    public CommonResponse<Page<JobVO>> page(@Valid PagingQueryJobDTO query) {
        return new CommonResponse<>(this.service.pagingQueryListByParameter(query));
    }

    @PostMapping(path = "/create")
    @ApiOperationSupport(order = 2, author = "SanJin")
    @Operation(summary = "定时任务添加", security = {@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)})
    public CommonResponse<Boolean> create(@RequestBody @Valid JobAddDTO create) {
        try {
            return new CommonResponse<>(this.service.create(create));
        } catch (SchedulerException e) {
            return new CommonResponse<>(Boolean.FALSE);
        }
    }

    @PutMapping(path = "/update")
    @Operation(summary = "定时任务更新", security = {@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)})
    @ApiOperationSupport(order = 3, author = "SanJin")
    public CommonResponse<Boolean> update(@RequestBody @Valid JobUpdateDTO update) {
        try {
            return new CommonResponse<>(this.service.update(update));
        } catch (SchedulerException e) {
            return new CommonResponse<>(Boolean.FALSE);
        }
    }

    @DeleteMapping(value = "/delete")
    @Operation(summary = "定时任务删除", security = {@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)})
    @ApiOperationSupport(order = 4, author = "SanJin")
    @Parameters({
            @Parameter(name = "id", description = "标识", in = ParameterIn.QUERY),
    })
    public CommonResponse<Boolean> delete(@RequestParam(name = "id") String id) {
        try {
            return new CommonResponse<>(this.service.delete(id));
        } catch (SchedulerException e) {
            return new CommonResponse<>(Boolean.FALSE);
        }
    }

    @PutMapping(path = "/update/{id}")
    @Operation(summary = "定时任务状态更新", security = {@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)})
    @ApiOperationSupport(order = 5, author = "SanJin")
    @Parameters({
            @Parameter(name = "id", description = "定时任务ID", in = ParameterIn.PATH),
            @Parameter(name = "status", description = "定时任务状态", in = ParameterIn.QUERY),
    })
    public CommonResponse<Boolean> updateJobStatus(@PathVariable("id") String id, @RequestParam(name = "status") Integer status) {
        try {
            return new CommonResponse<>(this.service.updateJobStatus(id, status));
        } catch (SchedulerException e) {
            return new CommonResponse<>(Boolean.FALSE);
        }
    }

    @PutMapping(path = "/trigger/{id}")
    @Operation(summary = "定时任务执行", security = {@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)})
    @ApiOperationSupport(order = 6, author = "SanJin")
    @Parameters({
            @Parameter(name = "id", description = "定时任务ID", in = ParameterIn.PATH),
    })
    public CommonResponse<Boolean> trigger(@PathVariable("id") String id) {
        try {
            this.service.trigger(id);
            return new CommonResponse<>(Boolean.TRUE);
        } catch (SchedulerException e) {
            return new CommonResponse<>(Boolean.FALSE);
        }
    }

    @GetMapping("/log/page")
    @ApiOperationSupport(order = 7, author = "SanJin")
    @Operation(summary = "定时任务执行日志分页查询", security = {@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)})
    public CommonResponse<Page<JobLogVO>> page(@Valid PagingQueryJobLogDTO query) {
        return new CommonResponse<>(this.service.pagingQueryListByParameter(query));
    }

}
