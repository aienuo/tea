package com.aienuo.tea.controller;

import com.aienuo.tea.business.UserBusiness;
import com.aienuo.tea.common.base.BaseController;
import com.aienuo.tea.common.response.CommonResponse;
import com.aienuo.tea.model.dto.PagingQueryUserDTO;
import com.aienuo.tea.model.dto.ResetPasswordDTO;
import com.aienuo.tea.model.dto.UserAddDTO;
import com.aienuo.tea.model.dto.UserUpdateDTO;
import com.aienuo.tea.model.vo.UserPageVO;
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
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统用户
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/user")
@Tag(name = "系统用户", description = "系统用户", extensions = {
        @Extension(properties = {@ExtensionProperty(name = "x-order", value = "2.3", parseValue = true)})
})
public class UserController extends BaseController<UserBusiness> {

    @GetMapping("/page")
    @ApiOperationSupport(order = 1, author = "SanJin")
    @Operation(summary = "系统用户分页查询", security = {@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)})
    public CommonResponse<Page<UserPageVO>> page(@Valid PagingQueryUserDTO query) {
        return new CommonResponse<>(this.service.pagingQueryListByParameter(query));
    }

    @PostMapping(path = "/create")
    @ApiOperationSupport(order = 2, author = "SanJin")
    @Operation(summary = "系统用户添加", security = {@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)})
    public CommonResponse<Boolean> create(@RequestBody @Valid UserAddDTO create) {
        return new CommonResponse<>(this.service.create(create));
    }

    @PutMapping(path = "/update")
    @Operation(summary = "系统用户更新", security = {@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)})
    @ApiOperationSupport(order = 3, author = "SanJin")
    public CommonResponse<Boolean> update(@RequestBody @Valid UserUpdateDTO update) {
        return new CommonResponse<>(this.service.update(update));
    }

    @PutMapping(path = "reset")
    @Operation(summary = "重置密码", security = {@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)})
    @ApiOperationSupport(order = 4, author = "SanJin")
    public CommonResponse<Boolean> resetPassword(@RequestBody @Valid ResetPasswordDTO resetPassword) {
        return new CommonResponse<>(service.resetPassword(resetPassword));
    }

    @DeleteMapping(value = "/delete")
    @Operation(summary = "系统用户删除", security = {@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)})
    @ApiOperationSupport(order = 5, author = "SanJin")
    @Parameters({
            @Parameter(name = "idList", description = "标识", in = ParameterIn.QUERY),
    })
    public CommonResponse<Boolean> delete(@RequestParam(name = "idList", required = true) List<String> idList) {
        return new CommonResponse<>(this.service.delete(idList));
    }

    @PutMapping(value = "/update/status")
    @Operation(summary = "冻结/解冻接口", description = "多条冻结/解冻")
    @ApiOperationSupport(order = 6, author = "XinLau")
    public CommonResponse<Boolean> update(@RequestBody List<String> idList) {
        return new CommonResponse<>(service.update(idList));
    }

}
