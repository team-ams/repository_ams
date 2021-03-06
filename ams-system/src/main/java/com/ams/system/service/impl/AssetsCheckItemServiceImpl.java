package com.ams.system.service.impl;

import com.ams.common.core.text.Convert;
import com.ams.system.domain.AssetsCheckItem;
import com.ams.system.domain.AssetsCheckTask;
import com.ams.system.mapper.AssetsCheckItemMapper;
import com.ams.system.mapper.AssetsCheckTaskMapper;
import com.ams.system.service.IAssetsCheckItemService;
import com.ams.system.service.IAssetsCheckTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetsCheckItemServiceImpl implements IAssetsCheckItemService {

    @Autowired
    private AssetsCheckItemMapper checkItemMapper;


    @Override
    public int getCheckTargetNumByCheckNumber(String checkNumber) {
        return checkItemMapper.getCheckTargetNumByCheckNumber(checkNumber);
    }

    @Override
    public int insertCheckItem(AssetsCheckItem assetsCheckItemList) {
        return checkItemMapper.insertCheckItem(assetsCheckItemList);
    }


    @Override
    public int deleteCheckItemByCheckNumber(String checkNumber) {
        return checkItemMapper.deleteCheckItemByCheckNumber(checkNumber);
    }

    @Override
    public int deleteCheckProfitItemByCheckNumber(String checkNumber) {
        return checkItemMapper.deleteCheckProfitItemByCheckNumber(checkNumber);
    }

    @Override
    public List<AssetsCheckItem> getCheckItemByCheckNumber(String checkNumber) {
        return checkItemMapper.getCheckItemByCheckNumber(checkNumber);
    }

    @Override
    public int updateCheckItemStatus(AssetsCheckItem checkItem) {
        return checkItemMapper.updateCheckItemStatus(checkItem);
    }
}
