package com.aienuo.tea;

import com.aienuo.tea.model.po.Log;
import com.aienuo.tea.utils.LatchUtils;
import com.aienuo.tea.utils.TokenUtils;
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
import java.util.Base64;
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
    public void contextLoads() throws Exception {

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

        String url_resource_guarantee = "jdbc:dm://120.130.140.203:6325?schema=TYPICAL_APPLICATION_RESOURCE_GUARANTEE";
        url_resource_guarantee = AES.encrypt(url_resource_guarantee, key);
        log.info("URL 加密后： mpw:{}", url_resource_guarantee);

        String url_training_assessment = "jdbc:dm://120.130.140.203:6325?schema=TYPICAL_APPLICATION_TRAINING_ASSESSMENT";
        url_training_assessment = AES.encrypt(url_training_assessment, key);
        log.info("URL 加密后： mpw:{}", url_training_assessment);

        String username = "SYSDBA";
        username = AES.encrypt(username, key);
        log.info("username 加密后： mpw:{}", username);
        String password = "Dameng08";
        password = AES.encrypt(password, key);
        log.info("password 加密后： mpw:{}", password);
    }

    /**
     * 数据库存储用户密码
     */
    private void passwordTest() {

        String base64 = "cGFzc3dvcmQwMiE=";

        String input = new String(Base64.getDecoder().decode(base64), StandardCharsets.UTF_8);

        String out = Base64.getEncoder().encodeToString(input.getBytes(StandardCharsets.UTF_8));

        log.info("base64 解码后： {}", input);

        log.info("base64 编码后： {}", out);

        String password = TokenUtils.encodePassword("password02!");
        log.info("password 加密后： {}", password);
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
     * 数据库配置 加密
     */
    public static void main(String[] args) {
        new TeaApplicationTests().tokenUtilTest();
        new TeaApplicationTests().databasePropertiesEncrypt();
        // rXEKCz8LyZaeRar4RDG64QJn576Jyc98Y5wrpptbvp8=
        new TeaApplicationTests().passwordTest();
        new TeaApplicationTests().testLatchUtils();
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
