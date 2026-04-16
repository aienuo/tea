package com.aienuo.tea;

import com.aienuo.tea.model.po.Log;
import com.aienuo.tea.model.vo.OrganizationCoordinateVO;
import com.aienuo.tea.utils.BuildingCoordinateRangeData;
import com.aienuo.tea.utils.LatchUtils;
import com.aienuo.tea.utils.TokenUtils;
import com.aienuo.tea.utils.smutils.SM2Utils;
import com.aienuo.tea.utils.smutils.SM3Utils;
import com.aienuo.tea.utils.smutils.SM4Utils;
import com.baomidou.mybatisplus.core.toolkit.AES;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.charset.StandardCharsets;
import java.security.Provider;
import java.security.Security;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@RunWith(SpringRunner.class)
// 设置随机端口启动服务器（有助于避免测试环境中的冲突）
@SpringBootTest(classes = TeaApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TeaApplicationTests {

    /**
     * LocalServerPort 提供了 @Value("${local.server.port}") 的代替
     */
    @LocalServerPort
    private Integer port;

    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * 向 "/index" 地址发送请求，并打印返回结果
     */
    @Test
    public void contextLoads() {

        ResponseEntity<String> response = this.restTemplate.getForEntity("http://localhost:" + port + "/index", String.class, "");

        log.info("测试结果为：{}", response.getBody());
    }

    /**
     * Token 生成 和 username 解析
     */
    private void tokenUtilTest() {
        String username = "sysadmin";

        String token = TokenUtils.getTokenByUsername("domain", "P", username);

        log.info("token : {}", token);

        log.info("username : {}", TokenUtils.getUsernameByToken(token));
    }

    /**
     * 数据库配置 加密
     */
    private void databasePropertiesEncrypt() {
        // 全局 key 配置到 启动命令里 --mpw.key=9jubPvDU84wkzOm4
        String key = "9jubPvDU84wkzOm4";
        log.info("key： {}", key);

        String url = "jdbc:dm://100.110.111.102:6325?schema=TYPICAL_APPLICATION_RESOURCE_GUARANTEE";
        url = AES.encrypt(url, key);
        log.info("DM URL 加密后： mpw:{}", url);
        String username = "SYSDBA";
        username = AES.encrypt(username, key);
        log.info("username 加密后： mpw:{}", username);
        String password = "Dameng08";
        password = AES.encrypt(password, key);
        log.info("password 加密后： mpw:{}", password);


        url = "jdbc:postgresql://100.110.111.106:2345/TYPICAL_APPLICATION_RESOURCE_GUARANTEE";
        url = AES.encrypt(url, key);
        log.info("PG URL 加密后： mpw:{}", url);
        username = "postgres";
        username = AES.encrypt(username, key);
        log.info("username 加密后： mpw:{}", username);
        password = "root";
        password = AES.encrypt(password, key);
        log.info("password 加密后： mpw:{}", password);
    }

    /**
     * 数据库存储用户密码
     */
    private void passwordTest() {

        String input = "admin";
        log.info("输入内容： {}", input);

        String base64 = Base64.getEncoder().encodeToString(input.getBytes(StandardCharsets.UTF_8));
        log.info("传递给后端前 编码后内容： {}", base64);

        String decoding = new String(Base64.getDecoder().decode(base64), StandardCharsets.UTF_8);
        log.info("获取前端内容 解码后内容： {}", decoding);

        String password = TokenUtils.encodePassword(decoding);
        log.info("输入内容 加密后（数据库实际存储内容）： {}", password);
    }

    /**
     * 异步并行处理 工具类
     */
    private void testLatchUtils() {
        // 1. 准备一个线程池
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        log.info("主流程开始，准备分发异步任务...");

        // 2. 提交多个异步任务
        AtomicReference<Log> log1 = new AtomicReference<>(new Log());
        // 任务一：获取用户信息
        LatchUtils.submitTask(executorService, () -> {
            try {
                log.info("开始获取用户信息...");
                Thread.sleep(1000); // 模拟耗时
                log1.set(new Log().setCode(0).setMessage("用户信息"));
                log.info("获取用户信息成功！");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        AtomicReference<Log> log2 = new AtomicReference<>(new Log());
        // 任务二：获取订单信息
        LatchUtils.submitTask(executorService, () -> {
            try {
                log.info("开始获取订单信息...");
                Thread.sleep(1500); // 模拟耗时
                log2.set(new Log().setCode(1).setMessage("订单信息"));
                log.info("获取订单信息成功！");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        AtomicReference<Log> log3 = new AtomicReference<>(new Log());
        // 任务三：获取商品信息
        LatchUtils.submitTask(executorService, () -> {
            try {
                log.info("开始获取商品信息...");
                Thread.sleep(500); // 模拟耗时
                log3.set(new Log().setCode(1).setMessage("商品信息"));
                log.info("获取商品信息成功！");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        log.info("所有异步任务已提交，主线程开始等待...");

        // 3. 等待所有任务完成，最长等待5秒
        boolean allTasksCompleted = LatchUtils.waitFor(5L, TimeUnit.SECONDS);

        // 4. 根据等待结果继续主流程
        if (allTasksCompleted) {
            log.info("所有异步任务执行成功，主流程继续...");

            log.info("用户：{}", log1.get());

            log.info("订单：{}", log2.get());

            log.info("商品：{}", log3.get());

        } else {
            System.err.println("有任务执行超时，主流程中断！");
        }

        // 5. 关闭线程池
        executorService.shutdown();
    }

    /**
     * 坐标范围测试
     */
    private void coordinateTest() {

        OrganizationCoordinateVO center = new OrganizationCoordinateVO();
        // 纬度
        center.setLatitude(30.0d);
        // 经度
        center.setLongitude(120.0d);
        // 海拔
        center.setAltitude(0.0d);
        // 半径（千米）
        double radius = 100d;
        log.info("经度：{}° E，纬度：{}° N， 范围：{}（千米）", center.getLongitude(), center.getLatitude(), radius);
        BuildingCoordinateRangeData.CoordinateExtremum coordinateExtremum = new BuildingCoordinateRangeData<OrganizationCoordinateVO>().getCoordinateExtremum(center, radius);
        log.info("十进制坐标指定范围的极值： {}", coordinateExtremum);
        // 待处理数据
        List<OrganizationCoordinateVO> list = new ArrayList<>();
        int k = 1;
        for (double i = 3.85; i < 53.56; i += 0.5) {
            for (double j = 73.38; j < 135.04; j += 0.5) {
                k++;
                /**
                 * 最北端 53.56° N 最南端 3.85° N
                 * 最西端 73.38° E 最东端 135.04° E
                 */
                OrganizationCoordinateVO item = new OrganizationCoordinateVO();
                item.setId(String.valueOf(k));
                item.setParentId(String.valueOf(k % 10));
                // 纬度
                item.setLatitude(i);
                // 经度
                item.setLongitude(j);
                // 海拔
                item.setAltitude(0.0d);
                list.add(item);
            }
        }
        log.info("十进制坐标数据集合（{}个）}", list.size());
        // 处理数据
        List<OrganizationCoordinateVO> data = new BuildingCoordinateRangeData<OrganizationCoordinateVO>().buildingCoordinateRangeData(center, radius, list);
        log.info("处理后的数据集合（{}个）", data.size());
        for (OrganizationCoordinateVO item : data) {
            log.info("id： {}，parent：{}，经度：{}，纬度：{}", item.getId(), item.getParentId(), item.getLongitude(), item.getLatitude());
        }
    }

    private void smTest() throws Exception {

        for (Provider provider : Security.getProviders()) {
            System.out.println(provider.getName());
        }

        // ========== SM3 示例 ==========
        String data = "Hello, 国密算法!";
        String sm3Hash = SM3Utils.hash(data);
        System.out.println("SM3哈希: " + sm3Hash);
        System.out.println("SM3验证: " + SM3Utils.verify(data, sm3Hash));

        // ========== SM4 示例 ==========
        String sm4Key = SM4Utils.generateKey();
        String sm4Iv = SM4Utils.generateIV();
        System.out.println("SM4密钥: " + sm4Key);
        System.out.println("SM4 IV: " + sm4Iv);

        String plaintext = "这是一段需要加密的敏感数据";
        String encrypted = SM4Utils.encryptCBC(plaintext, sm4Key, sm4Iv);
        String decrypted = SM4Utils.decryptCBC(encrypted, sm4Key, sm4Iv);
        System.out.println("SM4加密结果: " + encrypted);
        System.out.println("SM4解密结果: " + decrypted);

        // ========== SM2 示例 ==========
        // 生成密钥对
        String[] keyPair = SM2Utils.generateKeyPairHex();
        String privateKey = keyPair[0];
        String publicKey = keyPair[1];
        System.out.println("SM2私钥: " + privateKey);
        System.out.println("SM2公钥: " + publicKey);

        // SM2加密解密
        String sm2Plaintext = "SM2加密测试数据";
        String sm2Ciphertext = SM2Utils.encrypt(sm2Plaintext, publicKey);
        String sm2Decrypted = SM2Utils.decrypt(sm2Ciphertext, privateKey);
        System.out.println("SM2加密结果: " + sm2Ciphertext);
        System.out.println("SM2解密结果: " + sm2Decrypted);

        // SM2签名验签
        String signature = SM2Utils.sign(data, privateKey);
        boolean verified = SM2Utils.verify(data, signature, publicKey);
        System.out.println("SM2签名: " + signature);
        System.out.println("SM2验签结果: " + verified);

    }

    /**
     * 数据库配置 加密
     */
    public static void main(String[] args) {
        new TeaApplicationTests().tokenUtilTest();
        new TeaApplicationTests().databasePropertiesEncrypt();
        new TeaApplicationTests().passwordTest();
        new TeaApplicationTests().testLatchUtils();
        new TeaApplicationTests().coordinateTest();
        try {
            new TeaApplicationTests().smTest();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

	/*@RestController
	@RequestMapping("body")
	@Tag(name = "body参数")
	public class BodyController {

		@Operation(summary = "普通body请求")
		@PostMapping("/body")
		public ResponseEntity<FileResp> body(@RequestBody FileResp fileResp){
			return ResponseEntity.ok(fileResp);
		}

		@Operation(summary = "普通body请求+Param+Header+Path")
		@Parameters({
				@Parameter(name = "id",description = "文件id",in = ParameterIn.PATH),
				@Parameter(name = "token",description = "请求token",required = true,in = ParameterIn.HEADER),
				@Parameter(name = "name",description = "文件名称",required = true,in=ParameterIn.QUERY)
		})
		@PostMapping("/bodyParamHeaderPath/{id}")
		public ResponseEntity<FileResp> bodyParamHeaderPath(@PathVariable("id") String id,@RequestHeader("token") String token, @RequestParam("name")String name,@RequestBody FileResp fileResp){
			fileResp.setName(fileResp.getName()+",receiveName:"+name+",token:"+token+",pathID:"+id);
			return ResponseEntity.ok(fileResp);
		}
	}*/


}
