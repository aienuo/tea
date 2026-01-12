package com.aienuo.tea.controller;

import com.aienuo.tea.business.UnitBusiness;
import com.aienuo.tea.common.base.BaseController;
import com.aienuo.tea.common.response.CommonResponse;
import com.aienuo.tea.model.dto.UnitAddDTO;
import com.aienuo.tea.model.dto.UnitUpdateDTO;
import com.aienuo.tea.model.vo.UnitTreeVO;
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
 * 组织机构
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/unit")
@Tag(name = "组织机构", description = "组织机构", extensions = {
        @Extension(properties = {@ExtensionProperty(name = "x-order", value = "2.1", parseValue = true)})
})
public class UnitController extends BaseController<UnitBusiness> {

    @GetMapping("/tree")
    @ApiOperationSupport(order = 1, author = "SanJin")
    @Operation(summary = "组织机构树查询", security = {@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)})
    public CommonResponse<List<UnitTreeVO>> treeList() {
        return new CommonResponse<>(this.service.treeList());
    }

    @PostMapping(path = "/create")
    @ApiOperationSupport(order = 2, author = "SanJin")
    @Operation(summary = "组织机构添加", security = {@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)})
    public CommonResponse<Boolean> create(@RequestBody @Valid UnitAddDTO create) {
        return new CommonResponse<>(this.service.create(create));
    }

    @PutMapping(path = "/update")
    @Operation(summary = "组织机构更新", security = {@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)})
    @ApiOperationSupport(order = 3, author = "SanJin")
    public CommonResponse<Boolean> update(@RequestBody @Valid UnitUpdateDTO update) {
        return new CommonResponse<>(this.service.update(update));
    }

    @DeleteMapping(value = "/delete")
    @Operation(summary = "组织机构移除", security = {@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)})
    @ApiOperationSupport(order = 4, author = "SanJin")
    @Parameters({
            @Parameter(name = "idList", description = "标识", in = ParameterIn.QUERY),
    })
    public CommonResponse<Boolean> delete(@RequestParam(name = "idList", required = true) List<String> idList) {
        return new CommonResponse<>(this.service.delete(idList));
    }

}
