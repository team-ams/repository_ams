package com.ams.system.mapper;

import com.ams.system.domain.AssetsCheckItem;
import com.ams.system.domain.AssetsCheckTask;

import java.util.List;

public interface AssetsCheckItemMapper {


    /**
     * 根据盘点单号获取应盘数量
     *
     * @param checkNumber
     * @return
     */
    int getCheckTargetNumByCheckNumber(String checkNumber);

    /**
     * 新增盘点项
     *
     * @param assetsCheckItem
     * @return
     */
    int insertCheckItem(AssetsCheckItem assetsCheckItem);


    /**
     * 根据盘点号删除盘点项
     *
     * @param checkNumber
     * @return
     */
    int deleteCheckTaskByCheckNumber(String checkNumber);
}
