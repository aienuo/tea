package com.aienuo.tea.controller;

import com.aienuo.tea.business.RoleBusiness;
import com.aienuo.tea.common.base.BaseController;
import com.aienuo.tea.common.response.CommonResponse;
import com.aienuo.tea.model.dto.PagingQueryRoleDTO;
import com.aienuo.tea.model.dto.RoleAddDTO;
import com.aienuo.tea.model.dto.RoleUpdateDTO;
import com.aienuo.tea.model.vo.OptionVO;
import com.aienuo.tea.model.vo.RolePageVO;
import com.aienuo.tea.model.vo.RoleVO;
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
 * 角色信息
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/role")
@Tag(name = "角色信息", description = "角色信息", extensions = {
        @Extension(properties = {@ExtensionProperty(name = "x-order", value = "2.2", parseValue = true)})
})
public class RoleController extends BaseController<RoleBusiness> {

    @GetMapping("/page")
    @ApiOperationSupport(order = 1, author = "SanJin")
    @Operation(summary = "角色分页查询", security = {@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)})
    public CommonResponse<Page<RolePageVO>> pagingQueryListByParameter(PagingQueryRoleDTO pagingQuery) {
        return new CommonResponse<>(this.service.pagingQueryListByParameter(pagingQuery));
    }

    @PostMapping(path = "/create")
    @ApiOperationSupport(order = 2, author = "SanJin")
    @Operation(summary = "角色添加", security = {@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)})
    public CommonResponse<Boolean> create(@RequestBody @Valid RoleAddDTO create) {
        return new CommonResponse<>(this.service.create(create));
    }

    @GetMapping(path = "query/{id}")
    @ApiOperationSupport(order = 3, author = "SanJin")
    @Operation(summary = "角色查看", security = {@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)})
    @Parameter(name = "id", description = "角色标识", required = true)
    public CommonResponse<RoleVO> queryById(@PathVariable(name = "id", required = true) String id) {
        return new CommonResponse<>(service.queryById(id));
    }

    @PutMapping(path = "/update")
    @Operation(summary = "角色更新", security = {@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)})
    @ApiOperationSupport(order = 4, author = "SanJin")
    public CommonResponse<Boolean> update(@RequestBody @Valid RoleUpdateDTO update) {
        return new CommonResponse<>(this.service.update(update));
    }

    @DeleteMapping(value = "/delete")
    @Operation(summary = "角色移除", security = {@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)})
    @ApiOperationSupport(order = 5, author = "SanJin")
    @Parameters({
            @Parameter(name = "idList", description = "标识", in = ParameterIn.QUERY),
    })
    public CommonResponse<Boolean> delete(@RequestParam(name = "idList", required = true) List<String> idList) {
        return new CommonResponse<>(this.service.delete(idList));
    }

    @GetMapping("/list")
    @ApiOperationSupport(order = 6, author = "SanJin")
    @Operation(summary = "角色下拉", security = {@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)})
    public CommonResponse<List<OptionVO>> list() {
        return new CommonResponse<>(this.service.list());
    }

}
