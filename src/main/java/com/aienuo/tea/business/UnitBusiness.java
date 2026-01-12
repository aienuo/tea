package com.aienuo.tea.business;

import com.aienuo.tea.common.base.BaseBusiness;
import com.aienuo.tea.common.enums.ArgumentResponseEnum;
import com.aienuo.tea.model.converter.UnitConverter;
import com.aienuo.tea.model.dto.UnitAddDTO;
import com.aienuo.tea.model.dto.UnitUpdateDTO;
import com.aienuo.tea.model.po.Unit;
import com.aienuo.tea.model.po.User;
import com.aienuo.tea.model.vo.UnitTreeVO;
import com.aienuo.tea.service.IUnitService;
import com.aienuo.tea.service.IUserService;
import com.aienuo.tea.utils.BuildingTreeData;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 组织机构
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UnitBusiness extends BaseBusiness {

    /**
     * 组织机构
     */
    private final IUnitService unitService;

    /**
     * 系统用户
     */
    private final IUserService userService;

    /**
     * 树查询
     *
     * @return List<UnitTreeVO> - 组织机构
     */
    public List<UnitTreeVO> treeList() {
        return this.unitService.queryUnitTreeList();
    }

    /**
     * 添加
     *
     * @param add - 添加对象
     * @return Boolean - 成功标识
     */
    public Boolean create(final UnitAddDTO add) {
        if (StringUtils.isNotEmpty(add.getParentId())) {
            // 1、验证父级组织机构是否存在
            Unit parent = this.unitService.getById(add.getParentId());
            ArgumentResponseEnum.INSERT_PARAMETERS_VALID_ERROR.assertNotNull(parent, "组织机构", "父级组织机构信息不存在");
        }
        // 2、验证 组织机构名称 是否存在重复
        Unit unit = this.unitService.getOne(Wrappers.<Unit>lambdaQuery()
                .eq(Unit::getName, add.getName())
                .eq(StringUtils.isNotEmpty(add.getParentId()), Unit::getParentId, add.getParentId()), Boolean.FALSE
        );
        ArgumentResponseEnum.INSERT_PARAMETERS_VALID_ERROR.assertIsNull(unit, "组织机构", "组织机构名称存在重复");
        // 3、 将添加对象生成新的数据库对象
        unit = UnitConverter.INSTANCE.getAddEntity(add);
        // 4、创建新组织机构
        boolean save = this.unitService.save(unit);
        ArgumentResponseEnum.INSERT_PARAMETERS_VALID_ERROR.assertIsTrue(save, "组织机构", "请确认信息准确无误后重新添加");
        return save;
    }

    /**
     * 更新
     *
     * @param update - 更新参数
     * @return Boolean - 成功标识
     */
    public Boolean update(final UnitUpdateDTO update) {
        // 1、更新校验
        if (StringUtils.isNotBlank(update.getParentId())) {
            ArgumentResponseEnum.INSERT_PARAMETERS_VALID_ERROR.assertIsFalse(Objects.equals(update.getParentId(), update.getId()), "组织机构", "请确认信息准确无误后重新更新");
            // 2、校验是否把自己的父节点改成了 自己 的子节点
            List<Unit> list = this.unitService.list();
            if (CollectionUtils.isNotEmpty(list) && StringUtils.isNotBlank(update.getId())) {
                List<Unit> dataList = new ArrayList<>();
                new BuildingTreeData<Unit>().findAllData(Boolean.TRUE, dataList, update.getId(), list);
                List<String> chilidIdList = dataList.stream().map(Unit::getId).filter(StringUtils::isNotBlank).distinct().toList();
                ArgumentResponseEnum.INSERT_PARAMETERS_VALID_ERROR.assertIsFalse(chilidIdList.contains(update.getParentId()), "组织机构", "请确认信息准确无误后重新更新");
            }
        }
        // 3、查询数据库数据
        Unit unit = this.unitService.getById(update.getId());
        ArgumentResponseEnum.UPDATE_PARAMETERS_VALID_ERROR.assertNotNull(unit, "组织机构", "组织机构信息不存在");
        if (StringUtils.isNotEmpty(update.getParentId()) && !update.getParentId().equals(unit.getParentId())) {
            // 4、验证父级组织机构是否存在
            Unit parent = this.unitService.getById(update.getParentId());
            ArgumentResponseEnum.UPDATE_PARAMETERS_VALID_ERROR.assertNotNull(parent, "组织机构", "父级组织机构信息不存在");
        }
        if (StringUtils.isNotEmpty(update.getName()) && !unit.getName().equals(update.getName())) {
            // 5、验证 组织机构名称 是否存在重复
            Unit unitByName = this.unitService.getOne(Wrappers.<Unit>lambdaQuery()
                            .eq(Unit::getName, update.getName())
                            .eq(StringUtils.isNotEmpty(update.getParentId()), Unit::getParentId, update.getParentId())
                    , Boolean.FALSE
            );
            ArgumentResponseEnum.UPDATE_PARAMETERS_VALID_ERROR.assertIsNull(unitByName, "组织机构", "组织机构名称存在重复");
        }
        // 6、将更新对象赋值到数据库对象
        UnitConverter.INSTANCE.getUpdateEntity(unit, update);
        // 7、更新组织机构
        boolean save = this.unitService.updateById(unit);
        ArgumentResponseEnum.UPDATE_PARAMETERS_VALID_ERROR.assertIsTrue(save, "组织机构", "请确认信息准确无误后重新更新");
        return save;
    }

    /**
     * 移除
     *
     * @param idList - 组织机构标识
     * @return Boolean - 成功标识
     */
    public Boolean delete(final List<String> idList) {
        long count = this.unitService.count(Wrappers.<Unit>lambdaQuery().in(Unit::getParentId, idList));
        ArgumentResponseEnum.DELETE_PARAMETERS_VALID_ERROR.assertIsTrue(count == 0, "组织机构", "部分组织机构下存在子节点");
        long roleUnitCount = this.userService.count(Wrappers.<User>lambdaQuery().in(User::getUnitId, idList));
        ArgumentResponseEnum.DELETE_PARAMETERS_VALID_ERROR.assertIsTrue(roleUnitCount == 0, "组织机构", "部分组织机构存在用户");
        boolean delete = this.unitService.removeByIds(idList);
        ArgumentResponseEnum.DELETE_PARAMETERS_VALID_ERROR.assertIsTrue(delete, "组织机构", "请确认信息准确无误后重新删除");
        return delete;
    }

}

