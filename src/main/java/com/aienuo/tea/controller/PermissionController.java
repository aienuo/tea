package com.aienuo.tea.controller;

import com.aienuo.tea.business.PermissionBusiness;
import com.aienuo.tea.common.base.BaseController;
import com.aienuo.tea.common.response.CommonResponse;
import com.aienuo.tea.model.dto.PermissionAddDTO;
import com.aienuo.tea.model.dto.PermissionUpdateDTO;
import com.aienuo.tea.model.vo.ButtonVO;
import com.aienuo.tea.model.vo.MenuTreeVO;
import com.aienuo.tea.model.vo.PermissionTreeVO;
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
 * 菜单权限
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/permission")
@Tag(name = "菜单权限", description = "菜单权限", extensions = {
        @Extension(properties = {@ExtensionProperty(name = "x-order", value = "2.1", parseValue = true)})
})
public class PermissionController extends BaseController<PermissionBusiness> {

    @GetMapping("/tree")
    @ApiOperationSupport(order = 1, author = "SanJin")
    @Operation(summary = "菜单权限树查询", security = {@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)})
    public CommonResponse<List<PermissionTreeVO>> treeList() {
        return new CommonResponse<>(this.service.treeList());
    }

    @PostMapping(path = "/create")
    @ApiOperationSupport(order = 2, author = "SanJin")
    @Operation(summary = "菜单权限添加", security = {@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)})
    public CommonResponse<Boolean> create(@RequestBody @Valid PermissionAddDTO create) {
        return new CommonResponse<>(this.service.create(create));
    }

    @PutMapping(path = "/update")
    @Operation(summary = "菜单权限更新", security = {@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)})
    @ApiOperationSupport(order = 3, author = "SanJin")
    public CommonResponse<Boolean> update(@RequestBody @Valid PermissionUpdateDTO update) {
        return new CommonResponse<>(this.service.update(update));
    }

    @DeleteMapping(value = "/delete")
    @Operation(summary = "菜单权限移除", security = {@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)})
    @ApiOperationSupport(order = 4, author = "SanJin")
    @Parameters({
            @Parameter(name = "idList", description = "标识", in = ParameterIn.QUERY),
    })
    public CommonResponse<Boolean> delete(@RequestParam(name = "idList", required = true) List<String> idList) {
        return new CommonResponse<>(this.service.delete(idList));
    }

    @GetMapping("/menu")
    @ApiOperationSupport(order = 5, author = "SanJin")
    @Operation(summary = "菜单树查询", security = {@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)})
    public CommonResponse<List<MenuTreeVO>> menu() {
        return new CommonResponse<>(this.service.queryMenuTreeListByUserId());
    }

    @GetMapping("/button")
    @ApiOperationSupport(order = 6, author = "SanJin")
    @Operation(summary = "按钮查询", security = {@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)})
    public CommonResponse<List<ButtonVO>> button() {
        return new CommonResponse<>(this.service.queryButtonListByUserId());
    }

}
