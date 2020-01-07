package com.ams.common.exception.file;

import com.ams.common.exception.base.BaseException;

/**
 * 文件信息异常类
 * 
 * @author zengchao
 */
public class FileException extends BaseException
{
    private static final long serialVersionUID = 1L;

    public FileException(String code, Object[] args)
    {
        super("file", code, args, null);
    }

}
