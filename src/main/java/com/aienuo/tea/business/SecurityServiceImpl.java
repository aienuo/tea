package com.aienuo.tea.business;

import com.aienuo.tea.common.enums.ArgumentResponseEnum;
import com.aienuo.tea.common.enums.CommonResponseEnum;
import com.aienuo.tea.model.dto.LoginDTO;
import com.aienuo.tea.model.dto.SsoUserDTO;
import com.aienuo.tea.model.po.Permission;
import com.aienuo.tea.model.po.RolePermission;
import com.aienuo.tea.model.po.User;
import com.aienuo.tea.model.po.UserRole;
import com.aienuo.tea.service.IPermissionService;
import com.aienuo.tea.service.IRolePermissionService;
import com.aienuo.tea.service.IUserRoleService;
import com.aienuo.tea.service.IUserService;
import com.aienuo.tea.utils.IPV4Utils;
import com.aienuo.tea.utils.SecurityFrameworkUtils;
import com.aienuo.tea.utils.TokenUtils;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import dm.jdbc.filter.stat.json.JSONObject;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 中台鉴权实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Lazy})// 解决Bean循环依赖
public class SecurityServiceImpl {

    /**
     * Redis
     */
    private final StringRedisTemplate redisTemplate;

    /**
     * HTTP 请求
     */
    private final RestTemplate restTemplate;

    /**
     * 系统用户 服务类
     */
    private final IUserService userService;

    /**
     * 菜单权限
     */
    private final IPermissionService permissionService;

    /**
     * 用户角色关系
     */
    private final IUserRoleService userRoleService;

    /**
     * 角色权限关系
     */
    private final IRolePermissionService rolePermissionService;

    /**
     * 添加 SSO 启用开关，默认禁用
     */
    @Value("${sso.enabled:false}")
    private Boolean enabled;

    /**
     * 应用 标识
     */
    @Value("${sso.application_id:cpk.cetccloud.bzzy}")
    private String applicationId;

    /**
     * 应用 密钥
     */
    @Value("${sso.application_secret:20a12f771b7748988c4b37f2983c3cd6}")
    private String applicationSecret;

    /**
     * 获取用户信息
     */
    @Value("${sso.url.user:http://127.0.0.1:80/mock/sso/user}")
    private String userDataUrl;

    /**
     * 退出登录
     */
    @Value("${sso.url.logout:http://127.0.0.1:80/mock/sso/logout}")
    private String logoutUrl;

    /**
     * token 校验
     */
    @Value("${sso.url.token.check:http://127.0.0.1:80/mock/sso/valtoken/%s}")
    private String tokenCheckUrl;

    /**
     * 获取 HttpServletRequest
     */
    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

    /**
     * 获取用户信息
     *
     * @param token - 前端 Token
     * @return User - 用户信息
     */
    public User getUserDataFromToken(final String token) {
        // 系统用户信息
        User user = new User();
        // 依据 token 去缓存内获取用户名
        String username = this.redisTemplate.opsForValue().get(token);
        if (StringUtils.isBlank(username)) {
            // SSO 启用开关
            if (enabled) {
                // 中台用户信息
                SsoUserDTO userDataFromToken = getUserDataFromSso(token);
                if (userDataFromToken != null) {
                    // 查询 判断 系统中是否存在 用户信息
                    User query = this.userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, userDataFromToken.getAccount()), Boolean.FALSE);
                    if (query != null) {
                        user = query;
                    }
                    // 转为系统用户字段
                    user.setUsername(userDataFromToken.getAccount()).setRealName(userDataFromToken.getFullname()).setIdentityNumber(userDataFromToken.getIdString()).setDelFlag(userDataFromToken.getIsDelete() == null || userDataFromToken.getIsDelete() ? 1 : 0).setId(userDataFromToken.getUserId());
                    // 密码
                    user.setPassword(userDataFromToken.getPassword());
                    // 获取 用户单位（只获取主部门）
                    if (CollectionUtils.isNotEmpty(userDataFromToken.getRelation())) {
                        // 主副部门（1：主部门；0：副部门）
                        SsoUserDTO.Relation relation = userDataFromToken.getRelation().stream().filter(item -> item.getPriOrgan() != null && item.getPriOrgan().equals(1)).findFirst().orElse(null);
                        if (relation != null && StringUtils.isNotBlank(relation.getOrganId())) {
                            // 单位信息
                            user.setUnitId(relation.getOrganId());
                        }
                    }
                    // 添加或者更新 用户信息
                    this.userService.saveOrUpdate(user);
                }
            } else {
                // 解析 username 适配集成平台
                username = TokenUtils.getUsernameByToken(token);
                // 禁用 SSO 登录，解析 username 数据库中查询
                user = this.userService.queryUserByUserName(username);
            }
        } else {
            // 缓存中存在，直接数据库中查询
            user = this.userService.queryUserByUserName(username);
        }
        return user;
    }

    /**
     * 获取用户信息 </br>
     * <p>
     * 请求方式：POST </br>
     * 请求地址：http://ip:port/api/sso/user </br>
     * 请求头部： </br>
     * Content-Type: application/x-www-form-urlencoded </br>
     * 请求参数： </br>
     * access_token： token </br>
     * client_ip： {桌面客户端 IP 地址}（可通过 HTTP 请求中获取客户端 IP） </br>
     *
     * @param token - 前端 Token
     * @return SsoUserDTO - 用户信息
     */
    private SsoUserDTO getUserDataFromSso(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        // 获取请求（客户）端地址
        String clientIp = IPV4Utils.getClientIpAddress(getHttpServletRequest());
        headers.set("client_ip", clientIp);
        MultiValueMap<String, String> valueMap = new LinkedMultiValueMap<>(2);
        valueMap.add("access_token", token);
        valueMap.add("client_ip", clientIp);
        HttpEntity<JSONObject> httpEntity = new HttpEntity(valueMap, headers);
        // 忽略SSL验证
        ignoreSSL();
        // 调用接口查询
        SsoUserDTO user = this.restTemplate.postForObject(this.userDataUrl, httpEntity, SsoUserDTO.class);
        log.info("从中台获取到的用户信息：{}", user);
        return user;
    }

    /**
     * 校验 URL权限 是否合法
     *
     * @param request - HttpServletRequest
     * @param user    - User
     */
    public void verificationPermissions(HttpServletRequest request, final User user) {
        String uri = request.getRequestURI();
        Permission permission = this.permissionService.getOne(Wrappers.<Permission>lambdaQuery().eq(Permission::getUri, uri), Boolean.FALSE);
        if (permission != null) {
            // 用户角色关联
            List<UserRole> userRoleList = this.userRoleService.list(Wrappers.<UserRole>lambdaQuery().eq(UserRole::getUserId, user.getId()));
            CommonResponseEnum.PERMISSIONS_500.assertNotEmpty(userRoleList);
            // 角色编号
            List<String> roleIdList = userRoleList.stream().map(UserRole::getRoleId).filter(StringUtils::isNotEmpty).distinct().collect(Collectors.toList());
            CommonResponseEnum.PERMISSIONS_500.assertNotEmpty(roleIdList);
            // 角色菜单权限关联
            List<RolePermission> roleMenuList = this.rolePermissionService.list(Wrappers.<RolePermission>lambdaQuery().in(RolePermission::getRole, roleIdList).eq(RolePermission::getPermission, permission.getId()));
            CommonResponseEnum.PERMISSIONS_500.assertNotEmpty(roleMenuList);
        }
    }

    /**
     * 退出登录
     *
     * @param token - 前端 Token
     */
    public void logout(String token) {
        token = token.replaceAll(SecurityFrameworkUtils.AUTHORIZATION_BEARER, "");
        if (enabled) {
            this.logoutForSso(token);
        }
        // 清除缓存
        this.deleteToken(token);
    }

    /**
     * 退出登录</br>
     * <p>
     * 请求方式：POST </br>
     * 请求地址：http://ip:port/api/sso/logout </br>
     * 请求参数： </br>
     * access_token： token </br>
     *
     * @param token - 前端 Token
     */
    private void logoutForSso(final String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        // 获取请求（客户）端地址
        String clientIp = IPV4Utils.getClientIpAddress(getHttpServletRequest());
        headers.set("client_ip", clientIp);
        MultiValueMap<String, String> valueMap = new LinkedMultiValueMap<>();
        HttpEntity<JSONObject> httpEntity = new HttpEntity(valueMap, headers);
        // 忽略SSL验证
        ignoreSSL();
        // 调用接口查询
        this.restTemplate.postForObject(this.logoutUrl + StringPool.SLASH + token, httpEntity, JSONObject.class);
    }

    /**
     * Token 校验
     *
     * @param token - 前端 Token
     * @return Boolean 验证是否失败
     */
    public Boolean checkToken(String token) {
        // 去除 Token 中带的 Bearer
        token = token.replaceAll(SecurityFrameworkUtils.AUTHORIZATION_BEARER, "");
        boolean check = Boolean.FALSE;
        if (enabled) {
            check = this.checkTokenForSso(token);
        } else {
            // 解析 username 适配集成平台
            String username = TokenUtils.getUsernameByToken(token);
            // 本地验证
            check = this.redisTemplate.hasKey(token) && this.redisTemplate.hasKey(username);
        }
        if (!check) {
            // 清除缓存
            this.deleteToken(token);
        } else {
            // 刷新过期时间
            this.setToken(token);
        }
        return check;
    }

    /**
     * SSO Token 校验 </br>
     * <p>
     * 请求方式：POST </br>
     * 请求地址：http://ip:port/api/sso/valtoken/{token} </br>
     * 请求头部：client_ip={桌面客户端 IP 地址}（可通过 HTTP 请求中获取客户端IP） </br>
     * 参数说明：token=调用接口凭证 </br>
     *
     * @param token - 前端 Token
     * @return Boolean 验证是否失败
     */
    private Boolean checkTokenForSso(final String token) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            // 获取请求（客户）端地址
            String clientIp = IPV4Utils.getClientIpAddress(getHttpServletRequest());
            headers.set("client_ip", clientIp);
            MultiValueMap<String, String> valueMap = new LinkedMultiValueMap<>(2);
            valueMap.add("access_token", token);
            valueMap.add("client_ip", clientIp);
            HttpEntity<JSONObject> httpEntity = new HttpEntity(valueMap, headers);
            // 忽略SSL验证
            ignoreSSL();
            // 调用接口查询
            JSONObject data = this.restTemplate.postForObject(String.format(this.tokenCheckUrl, token), httpEntity, JSONObject.class);
            log.info("从中台获取到的信息：{}", data);
            // {"result": "success", "rsltcode": "0", "rsltmsg": "操作成功"}
            if (data != null) {
                return data.has("result") && "success".equals(data.getString("result")) && data.has("rsltcode") && "0".equals(data.getString("rsltcode"));
            }
        } catch (Exception exception) {
            log.error("SSO Token 校验 失败： {}", exception.getMessage(), exception);
            return Boolean.FALSE;
        }
        return Boolean.FALSE;
    }

    /**
     * 忽略SSL验证
     */
    private void ignoreSSL() {
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManager[] trustManagers = new TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] x509Certificates, String message) {

                }

                @Override
                public void checkServerTrusted(X509Certificate[] x509Certificates, String message) {

                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }};
            sslContext.init(null, trustManagers, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            log.error("忽略SSL验证 失败: {}", e.getMessage(), e);
        }
    }

    /**
     * 清除 Token 缓存
     *
     * @param token - key
     */
    public void deleteToken(final String token) {
        // 解析 username 适配集成平台
        String username = TokenUtils.getUsernameByToken(token);
        // 清除缓存
        this.redisTemplate.delete(token);
        this.redisTemplate.delete(username);
    }

    /**
     * 设置 Token 缓存
     *
     * @param token - key
     */
    public void setToken(final String token) {
        // 解析 username 适配集成平台
        String username = TokenUtils.getUsernameByToken(token);
        // 设置一小时不过期
        this.redisTemplate.opsForValue().set(token, username, 1, TimeUnit.HOURS);
        this.redisTemplate.opsForValue().set(username, token, 1, TimeUnit.HOURS);
    }

    /**
     * 登录获取 Token
     *
     * @param login 登录信息
     * @return String - Token
     */
    public String login(final LoginDTO login) {
        // 获取用户信息
        User user = this.userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, login.getUsername()).eq(User::getPassword, login.getPassword()), Boolean.FALSE);
        ArgumentResponseEnum.SELECT_PARAMETERS_VALID_ERROR.assertNotNull(user, "用户信息", "用户名或者密码不正确");
        String token = "";
        if (this.redisTemplate.hasKey(user.getUsername())) {
            token = this.redisTemplate.opsForValue().get(user.getUsername());
        } else {
            // 生成 Token 适配集成平台
            token = TokenUtils.getTokenByUsername("domain", "clientType", user.getUsername());
        }
        // 设置 Token 到缓存 过期时间默认一小时
        this.setToken(token);
        return token;
    }

}
