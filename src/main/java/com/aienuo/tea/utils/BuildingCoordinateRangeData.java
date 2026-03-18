package com.aienuo.tea.utils;

import com.aienuo.tea.common.base.BaseCoordinate;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import lombok.Data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 依据 十进制坐标（经度-longitude、纬度-latitude）和半径（radius，千米） 获取 符合条件的 十进制坐标
 * <br>
 *
 * <br>
 * 使用说明：
 * <li>1、先输入 当前十进制坐标（经度-longitude、纬度-latitude）和半径（radius，千米）推算出  坐标（经度、纬度）的极值（CoordinateExtremum）</li>
 * <li>2、根据 十进制坐标（经度-longitude、纬度-latitude）极值 作为查询条件去数据库内检索 符合条件的 十进制坐标对象（伪集合，此为正方形范围，实际应该为 圆形范围）</li>
 * <li>3、调用 buildingCoordinateRangeData 方法 将 中心点、半径（千米）、符合条件的 十进制坐标集合 放入，即可获取 最终符合条件的 十进制坐标集合，同时也计算出距离（距离 中心点（center） 直线距离（distance））</li>
 * </p>
 */
public class BuildingCoordinateRangeData<T extends BaseCoordinate<T>> {

    /**
     * 地球平均半径（千米）
     */
    private static final double RADIUS = 6371.393;

    /**
     * 计算弧度
     *
     * @param angle - 坐标值（角度）
     * @return - 弧度
     */
    private Double getRadian(final Double angle) {
        // 角度转弧度计算公式： 弧度 = 角度数 * (π / 180)
        return angle * (Math.PI / 180);
    }

    /**
     * 获取 指定距离之后的 经度偏差值
     *
     * @param center - 中心点
     * @param radius - 半径（千米）
     * @return Double - 指定距离之后的 经度偏差值
     */
    private Double getLongitudeDeviation(final T center, final Double radius) {
        // 指定距离之后的 经度偏差值
        double longitudeDeviation = 2 * Math.asin(Math.sin(radius / (2 * RADIUS)) / Math.cos(this.getRadian(center.getLongitude())));
        // 弧度转角度（角度数 = 弧度 * (180 / π)）
        return longitudeDeviation * (180 / Math.PI);
    }

    /**
     * 获取 指定距离之后的 纬度偏差值
     *
     * @param radius - 半径（千米）
     * @return Double - 指定距离之后的 纬度偏差值
     */
    private Double getLatitudeDeviation(final Double radius) {
        // 指定距离之后的 纬度偏差值（弧度 = 弧长 / 半径）
        double latitudeDeviation = radius / RADIUS;
        // 弧度转角度（角度数 = 弧度 * (180 / π)）
        return latitudeDeviation * (180 / Math.PI);
    }

    /**
     * 计算 基准点 与 目标点 之间的距离（千米）
     *
     * @param datum  - 基准点
     * @param target - 目标点
     * @return Double - 距离：单位 千米
     */
    private Double computeDistance(final T datum, final T target) {
        // 基准点 纬度坐标 的 弧度
        double datumLatitudeRad = this.getRadian(datum.getLatitude());
        // 目标点 纬度坐标 的 弧度
        double targetLatitudeRad = this.getRadian(target.getLatitude());
        // 基准点 纬度坐标 与 目标点 纬度坐标 的 弧度差
        double latitudeRadDifference = datumLatitudeRad - targetLatitudeRad;
        // 基准点 经度坐标 与 目标点 经度坐标 的 弧度差
        double longitudeRadDifference = this.getRadian(datum.getLongitude()) - this.getRadian(target.getLongitude());
        // 基准点 与 目标点 之间的 弧度差
        double radDifference = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(latitudeRadDifference / 2), 2) + Math.cos(datumLatitudeRad) * Math.cos(targetLatitudeRad) * Math.pow(Math.sin(longitudeRadDifference / 2), 2)));
        // 基准点 与 目标点 之间的 直线距离（千米）  弧长计算公式： 弧长 = 弧度 * 半径
        double distance = radDifference * RADIUS;
        target.setDistance(distance);
        return distance;
    }

    /**
     * 获取范围内的数据
     *
     * @param center - 中心点
     * @param radius - 半径（千米）
     * @param list   - 数据列表
     * @return List<T> - 范围内的数据
     */
    public List<T> buildingCoordinateRangeData(final T center, final Double radius, final List<T> list) {
        List<T> dataList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(list)) {
            if (radius <= 0) {
                return dataList;
            }
            if (list.size() > 10000) {
                // 如果数据量过大，使用并行流 (Parallel Stream，对于包含 IO、阻塞操作或小数据量的任务请谨慎使用)
                dataList = list.parallelStream().filter(target -> this.computeDistance(center, target) <= radius)
                        // 依据距离远近 排序
                        .sorted(Comparator.comparingDouble(T::getDistance))
                        .toList();
            } else {
                // 过滤出范围内的数据
                dataList = list.stream().filter(target -> this.computeDistance(center, target) <= radius)
                        // 依据距离远近 排序
                        .sorted(Comparator.comparingDouble(T::getDistance))
                        .toList();
            }
        }
        return dataList;
    }

    /**
     * 获取 十进制坐标极值
     *
     * @param center - 中心点
     * @param radius - 半径（千米）
     * @return CoordinateExtremum - 十进制坐标极值
     */
    public CoordinateExtremum getCoordinateExtremum(final T center, final Double radius) {
        // 获取 经度偏差值
        double longitudeDeviation = this.getLongitudeDeviation(center, radius);
        // 获取 纬度偏差值
        double latitudeDeviation = this.getLatitudeDeviation(radius);
        // 获取 十进制坐标极值
        CoordinateExtremum coordinateExtremum = new CoordinateExtremum();
        // 最小经度
        coordinateExtremum.setMinLongitude(center.getLongitude() - longitudeDeviation);
        // 最大经度
        coordinateExtremum.setMaxLongitude(center.getLongitude() + longitudeDeviation);
        // 最小纬度
        coordinateExtremum.setMinLatitude(center.getLatitude() - latitudeDeviation);
        // 最大纬度
        coordinateExtremum.setMaxLatitude(center.getLatitude() + latitudeDeviation);
        return coordinateExtremum;
    }

    /**
     * 十进制坐标极值
     */
    @Data
    public static class CoordinateExtremum {

        /**
         * 最小经度
         */
        private Double minLongitude;

        /**
         * 最大经度
         */
        private Double maxLongitude;

        /**
         * 最小纬度
         */
        private Double minLatitude;

        /**
         * 最大纬度
         */
        private Double maxLatitude;

    }

}
