package com.ams.system.service;

import com.ams.system.domain.AssetsCheckItem;
import com.ams.system.domain.AssetsCheckTask;

import java.util.List;

public interface IAssetsCheckItemService {

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
     * @param assetsCheckItemList
     * @return
     */
    int insertCheckItem(AssetsCheckItem assetsCheckItemList);


    /**
     * 删除所有的符合该盘点单号的盘点项
     *
     * @param checkNumber
     * @return
     */
    int deleteCheckItemByCheckNumber(String checkNumber);

    /**
     * 删除所有符合盘点单号且为盘盈项的盘点项
     *
     * @param checkNumber
     * @return
     */
    int deleteCheckProfitItemByCheckNumber(String checkNumber);

    /**
     * 根据checkNumber查询得到checkItem
     *
     * @param checkNumber
     * @return
     */
    List<AssetsCheckItem> getCheckItemByCheckNumber(String checkNumber);

    /**
     * 更新盘点状态
     *
     * @param checkItem
     * @return
     */
    int updateCheckItemStatus(AssetsCheckItem checkItem);
}
