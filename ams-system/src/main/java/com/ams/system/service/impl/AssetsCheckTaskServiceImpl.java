package com.ams.system.service.impl;

import com.ams.common.core.text.Convert;
import com.ams.system.domain.AssetsCheckTask;
import com.ams.system.domain.AssetsTransfer;
import com.ams.system.mapper.AssetsCheckItemMapper;
import com.ams.system.mapper.AssetsCheckTaskMapper;
import com.ams.system.mapper.AssetsTransferMapper;
import com.ams.system.service.IAssetsCheckTaskService;
import com.ams.system.service.IAssetsTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetsCheckTaskServiceImpl implements IAssetsCheckTaskService {

    @Autowired
    private AssetsCheckTaskMapper checkTaskMapper;
    @Autowired
    private AssetsCheckItemMapper checkItemMapper;


    @Override
    public List<AssetsCheckTask> getCheckTaskAll() {
        return checkTaskMapper.getCheckTaskAll();
    }

    @Override
    public List<AssetsCheckTask> staticCheckTaskByDate(String startTime, String endTime) {
        return checkTaskMapper.staticCheckTaskByDate(startTime,endTime);
    }

    @Override
    public List<AssetsCheckTask> getCheckedAll() {
        return checkTaskMapper.getCheckedAll();
    }

    @Override
    public List<AssetsCheckTask> getCheckTaskByUserId(int checkUserId) {
        return checkTaskMapper.getCheckTaskByUserId(checkUserId);
    }

    @Override
    public List<AssetsCheckTask> getCheckRecordByUserId(int checkUserId) {
        return checkTaskMapper.getCheckRecordByUserId(checkUserId);
    }

    @Override
    public AssetsCheckTask getCheckTaskByTaskId(int taskId) {
        return checkTaskMapper.getCheckTaskByTaskId(taskId);
    }

    @Override
    public int getCheckTargetNumByUserId(String checkUserId) {
        return checkTaskMapper.getCheckTargetNumByUserId(checkUserId);
    }

    @Override
    public int insertCheckTask(AssetsCheckTask assetsCheckTask) {
        return checkTaskMapper.insertCheckTask(assetsCheckTask);
    }

    @Override
    public int updateCheckTask(AssetsCheckTask assetsCheckTask) {
        return checkTaskMapper.updateCheckTask(assetsCheckTask);
    }

    @Override
    public int deleteCheckTaskByTaskId(String taskId) {
        Integer[] taskIds = Convert.toIntArray(taskId);
        for (int taskid :taskIds) {

            AssetsCheckTask task = checkTaskMapper.getCheckTaskByTaskId(taskid);
            if(task == null){
                return 0;
            }
            String checkNumber = task.getCheckNumber();
            int deleteRows = checkItemMapper.deleteCheckItemByCheckNumber(checkNumber);
            if(deleteRows == 0){
                return 0;
            }
        }
        return checkTaskMapper.deleteCheckTaskByTaskId(taskIds);
    }
}
