package com.ams.system.service;

import com.ams.system.domain.AssetsCheckTask;
import com.ams.system.domain.AssetsTransfer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IAssetsCheckTaskService {

    /**
     * 获取全部未盘点的盘点任务
     *
     * @return
     */
    List<AssetsCheckTask> getCheckTaskAll(AssetsCheckTask checkTask);
    /**
     * 统计盘点详情
     *
     * @param startTime
     * @param endTime
     * @return
     */
    List<AssetsCheckTask> staticCheckTaskByDate(@Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 获取已盘点的待审核列表
     *
     * @return
     */
    List<AssetsCheckTask> getCheckedAll();


    /**
     * 根据负责人id获取盘点任务
     *
     * @param checkUserId
     * @return
     */
    List<AssetsCheckTask> getCheckTaskByUserId(int checkUserId);

    /**
     * 根据负责人用户id查找已盘点记录
     *
     * @param checkUserId
     * @return
     */
    List<AssetsCheckTask> getCheckRecordByUserId(int checkUserId);

    /**
     * 根据盘点id查找盘点任务
     *
     * @param taskId
     * @return
     */
    AssetsCheckTask getCheckTaskByTaskId(int taskId);

    /**
     * 根据负责人id获取应盘数量
     *
     * @param checkUserId
     * @return
     */
    int getCheckTargetNumByUserId(String checkUserId);

    /**
     * 新增盘点任务
     *
     * @param assetsCheckTask
     * @return
     */
    int insertCheckTask(AssetsCheckTask assetsCheckTask);

    /**
     * 更新盘点任务
     *
     * @param assetsCheckTask
     * @return
     */
    int updateCheckTask(AssetsCheckTask assetsCheckTask);

    /**
     * 根据盘点id删除盘点任务
     *
     * @param taskId
     * @return
     */
    int deleteCheckTaskByTaskId(String taskId);
}
