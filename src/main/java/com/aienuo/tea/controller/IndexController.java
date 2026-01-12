package com.aienuo.tea.controller;

import com.aienuo.tea.business.SecurityServiceImpl;
import com.aienuo.tea.common.response.CommonResponse;
import com.aienuo.tea.model.dto.DictQueryDTO;
import com.aienuo.tea.model.dto.LoginDTO;
import com.aienuo.tea.model.dto.OptionDTO;
import com.aienuo.tea.model.dto.PagingQueryLogDTO;
import com.aienuo.tea.model.po.Dict;
import com.aienuo.tea.model.po.Unit;
import com.aienuo.tea.model.po.User;
import com.aienuo.tea.model.vo.LogVO;
import com.aienuo.tea.model.vo.OptionVO;
import com.aienuo.tea.model.vo.RedisMonitorVO;
import com.aienuo.tea.service.IDictService;
import com.aienuo.tea.service.ILogService;
import com.aienuo.tea.service.IUnitService;
import com.aienuo.tea.utils.SecurityFrameworkUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 典型应用
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/index")
@Tag(name = "典型应用", description = "典型应用", extensions = {
        @Extension(properties = {@ExtensionProperty(name = "x-order", value = "1", parseValue = true)})
})

public class IndexController {

    /**
     * 中台鉴权实现类
     */
    private final SecurityServiceImpl securityService;

    /**
     * 组织机构 服务类
     */
    private final IUnitService unitService;

    /**
     * 数据字典 服务类
     */
    private final IDictService dictService;

    /**
     * 系统日志 服务类
     */
    private final ILogService logService;

    /**
     * Redis
     */
    private final StringRedisTemplate redisTemplate;

    @GetMapping()
    @ApiOperationSupport(order = 1, author = "SanJin")
    @Operation(summary = "测试接口", description = "测试接口", security = {@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)})
    public CommonResponse<User> index() {
        User user = SecurityFrameworkUtils.getLoginUser();
        return new CommonResponse<>(user);
    }

    @PostMapping("/login")
    @ApiOperationSupport(order = 2, author = "SanJin")
    @Operation(summary = "获取 Token", description = "用户登录 获取 Token")
    public CommonResponse<String> login(@RequestBody @Valid LoginDTO login) {
        return new CommonResponse<>(this.securityService.login(login));
    }

    @PostMapping("/logout")
    @ApiOperationSupport(order = 3, author = "SanJin")
    @Operation(summary = "退出登录", description = "退出登录", security = {@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)})
    public CommonResponse<Boolean> logout(@RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String token) {
        this.securityService.logout(token);
        return new CommonResponse<>(Boolean.TRUE);
    }

    @PutMapping("/token")
    @ApiOperationSupport(order = 4, author = "SanJin")
    @Operation(summary = "Token 验证", description = "Token 验证", security = {@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)})
    public CommonResponse<Boolean> checkToken(@RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String token) {
        return new CommonResponse<>(this.securityService.checkToken(token));
    }

    @PostMapping("/current")
    @ApiOperationSupport(order = 5, author = "SanJin")
    @Operation(summary = "获取当前登录用户信息", description = "获取当前登录用户信息", security = {@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)})
    public CommonResponse<User> currentUser() {
        User user = SecurityFrameworkUtils.getLoginUser();
        return new CommonResponse<>(user);
    }

    @GetMapping("/unit/tree")
    @ApiOperationSupport(order = 6, author = "SanJin")
    @Operation(summary = "获取组织机构树", description = "获取组织机构树", security = {@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)})
    @Parameters({
            @Parameter(name = "unitId", description = "单位标识"),
    })
    public CommonResponse<List<Unit>> unitTree(@RequestParam("unitId") String unitId) {
        return new CommonResponse<>(this.unitService.unitTree(unitId));
    }

    @GetMapping("/dic/type")
    @ApiOperationSupport(order = 7, author = "SanJin")
    @Operation(
            summary = "获取字典类型", description = "获取字典类型"
            , security = {@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)}
    )
    public CommonResponse<List<OptionVO>> queryDic(OptionDTO query) {
        return new CommonResponse<>(this.dictService.queryTableItemList(query));
    }

    @GetMapping("/dic/item")
    @ApiOperationSupport(order = 8, author = "SanJin")
    @Operation(summary = "数据字典查询", description = "数据字典查询", security = {@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)})
    public CommonResponse<List<Dict>> queryDic(@Valid DictQueryDTO query) {
        return new CommonResponse<>(this.dictService.queryDictList(query));
    }

    @GetMapping("/log/page")
    @ApiOperationSupport(order = 9, author = "SanJin")
    @Operation(summary = "日志分页查询", security = {@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)})
    public CommonResponse<Page<LogVO>> page(@Valid PagingQueryLogDTO query) {
        return new CommonResponse<>(this.logService.pagingQueryListByParameter(query));
    }

    @GetMapping("/redis")
    @ApiOperationSupport(order = 10, author = "SanJin")
    @Operation(summary = "Redis 监控信息", description = "获得 Redis 监控信息", security = {@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)})
    public CommonResponse<RedisMonitorVO> redisMonitorInfo() {
        // 获得 Redis 统计信息
        Properties info = this.redisTemplate.execute((RedisCallback<Properties>) RedisServerCommands::info);
        Long databaseSize = this.redisTemplate.execute(RedisServerCommands::dbSize);
        Properties commandStats = this.redisTemplate.execute((RedisCallback<Properties>) connection -> connection.info("commandstats"));

        RedisMonitorVO redisMonitor = new RedisMonitorVO();
        // Redis info 指令结果
        redisMonitor.setInfo(info);
        // Redis key 数量
        redisMonitor.setDatabaseSize(databaseSize);
        // 构建 Redis 命令统计结果
        List<RedisMonitorVO.CommandStat> commandStatList = new ArrayList<>(Objects.requireNonNull(commandStats).size());
        commandStats.forEach((key, value) -> {
            RedisMonitorVO.CommandStat commandStat = new RedisMonitorVO.CommandStat();
            String command = String.valueOf(key).replace("cmdstat_", StringPool.EMPTY);
            // Redis 命令
            commandStat.setCommand(command);
            Map<String, Object> map = new HashMap<>();
            String[] array = String.valueOf(value).split(StringPool.COMMA);
            for (String string : array) {
                String[] stringArray = string.split(StringPool.EQUALS);
                map.put(stringArray[0], stringArray[1]);
            }
            // 命令参数
            commandStat.setParam(map);
            commandStatList.add(commandStat);
        });
        // CommandStat 列表
        redisMonitor.setCommandStatList(commandStatList);

        return new CommonResponse<>(redisMonitor);
    }

}
