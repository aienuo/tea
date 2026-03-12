package com.aienuo.tea.common.base;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 *
 * Coordinate 坐标 对象
 *
 * <br>
 * <p>
 * Java 中 推荐使用 Double 类型
 * <li>精度足够：double 提供 15～16 位十进制有效数字，对于经纬度（-180～180）可达到亚米级甚至毫米级精度，远高于常见的测量误差；对于海拔（可能覆盖较大范围）也能精确表示。</li>
 * <li>范围适配：double 的取值范围（约 ±1.8×10³⁰⁸）完全覆盖经度、纬度、海拔的数值范围。</li>
 * <li>性能高效：double 是 CPU 原生支持的浮点类型，计算开销小，适合大量坐标运算。</li>
 * <li>标准实践：几乎所有地理空间相关的 Java 库（如 GeoTools、JTS、Proj4j）都使用 double 表示坐标。</li>
 *
 * <br>
 * <p>
 * 数据库设计建议使用 DECIMAL 类型
 * <li>纬度：范围 -90 到 90，建议使用 DECIMAL(10, 8)，即总共10位数字，其中小数部分占8位。这提供了足够的精度（约厘米级）。</li>
 * <li>经度：范围 -180 到 180，建议使用 DECIMAL(11, 8)，即总共11位数字，小数部分占8位。</li>
 * <li>海拔：根据你的数据范围，可以使用 DECIMAL(7, 2)，即总共7位数字，小数部分占2位（厘米级）。</li>
 */
@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class BaseCoordinate<T extends BaseEntity<T>> extends BaseEntity<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 经度（十进制度数）(经度：-180～180，东经为正、西经为负)
     */
    @Schema(description = "经度（十进制度数）")
    @TableField(value = "ZBJD")
    private Double longitude;

    /**
     * 纬度（十进制度数）(纬度：-90～90，北纬为正、南纬为负)
     */
    @Schema(description = "纬度（十进制度数）")
    @TableField(value = "ZBWD")
    private Double latitude;

    /**
     * 海拔（十进制度数）
     */
    @Setter
    @Schema(description = "海拔（十进制度数）")
    @TableField(value = "ZBHB")
    private Double altitude;

    /**
     * 距离（千米）（非数据库字段）
     */
    @Setter
    @Schema(description = "距离（千米）")
    @TableField(exist = false)
    private Double distance;

    /**
     * 设置经度
     *
     * @param longitude - 经度
     */
    public void setLongitude(final Double longitude) {
        this.longitude = isLongitudeValid(longitude) ? longitude : 0d;
    }

    /**
     * 设置经度
     *
     * @param degrees   - 度（TINYINT (纬度：-90～90，经度-180～180)）
     * @param minutes   - 分（TINYINT (0～59)）
     * @param seconds   - 秒（DECIMAL(5,2) (例如 0～59.99，根据精度决定小数位数)）
     * @param direction - 方向（WSAD）
     */
    public void setLongitude(final Integer degrees, final Integer minutes, final Double seconds, final String direction) {
        this.longitude = dmsToDecimal(degrees, minutes, seconds, direction);
    }

    /**
     * 设置纬度
     *
     * @param latitude - 纬度
     */
    public void setLatitude(final Double latitude) {
        this.latitude = isLatitudeValid(latitude) ? latitude : 0d;
    }

    /**
     * 设置纬度
     *
     * @param degrees   - 度（TINYINT (纬度：-90～90，经度-180～180)）
     * @param minutes   - 分（TINYINT (0～59)）
     * @param seconds   - 秒（DECIMAL(5,2) (例如 0～59.99，根据精度决定小数位数)）
     * @param direction - 方向（WSAD）
     */
    public void setLatitude(final Integer degrees, final Integer minutes, final Double seconds, final String direction) {
        this.latitude = dmsToDecimal(degrees, minutes, seconds, direction);
    }

    /**
     * 获取DMS经度
     *
     * @return String - DMS经度
     */
    public String getLongitudeString() {
        return decimalToDms(longitude, Boolean.FALSE);
    }

    /**
     * 获取DMS纬度
     *
     * @return String - DMS纬度
     */
    public String getLatitudeString() {
        return decimalToDms(latitude, Boolean.TRUE);
    }

    /**
     * 校验纬度
     *
     * @param latitude - 纬度
     * @return Boolean - 纬度是否合法
     */
    private static Boolean isLatitudeValid(final Double latitude) {
        if (latitude == null) {
            return false;
        }
        // 检查是否为 NaN 或无穷大
        if (latitude.isNaN() || latitude.isInfinite()) {
            return false;
        }
        // 闭区间检查：是否在 [-90, 90] 内
        return latitude >= -90.0 && latitude <= 90.0;
    }

    /**
     * 校验经度
     *
     * @param longitude - 经度
     * @return Boolean - 经度是否合法
     */
    private static Boolean isLongitudeValid(final Double longitude) {
        if (longitude == null) {
            return false;
        }
        // 检查是否为 NaN 或无穷大
        if (longitude.isNaN() || longitude.isInfinite()) {
            return false;
        }
        // 闭区间检查：是否在 [-180, 180] 内
        return longitude >= -180.0 && longitude <= 180.0;
    }

    /**
     * DMS 坐标转 十进制经纬度
     *
     * @param degrees   - 度（TINYINT (纬度：-90～90，经度-180～180)）
     * @param minutes   - 分（TINYINT (0～59)）
     * @param seconds   - 秒（DECIMAL(5,2) (例如 0～59.99，根据精度决定小数位数)）
     * @param direction - 方向（WSAD）
     * @return Double - 十进制经纬度
     */
    private static Double dmsToDecimal(final Integer degrees, final Integer minutes, final Double seconds, final String direction) {
        double decimal = Math.abs(degrees) + minutes / 60.0 + seconds / 3600.0;
        if ("S".equals(direction) || "W".equals(direction) || degrees < 0) {
            // 北纬、东经为正；南纬、西经为负
            decimal = -decimal;
        }
        return decimal;
    }

    /**
     * 十进制经纬度转 DMS
     *
     * @param decimal    - 十进制经纬度
     * @param isLatitude - 是否是纬度
     * @return String - DMS
     */
    private static String decimalToDms(Double decimal, Boolean isLatitude) {
        // 获取经纬度的方向
        char direction = isLatitude ? (decimal >= 0 ? 'N' : 'S') : (decimal >= 0 ? 'E' : 'W');
        // 获取经纬度的度、分、秒
        decimal = Math.abs(decimal);
        // 获取经纬度的 度
        int degrees = decimal.intValue();
        double minutesDouble = (decimal - degrees) * 60;
        // 获取经纬度的 分
        int minutes = (int) minutesDouble;
        // 获取经纬度的 秒
        double seconds = (minutesDouble - minutes) * 60;
        return String.format("%d°%d′%.3f″%c", degrees, minutes, seconds, direction);
    }

}
