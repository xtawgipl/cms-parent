package cn.edu.uestc.cms.impl;

import cn.edu.uestc.cms.MouldService;
import cn.edu.uestc.cms.entity.MouldBean;
import cn.edu.uestc.cms.mapper.MouldMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("mouldService")
public class MouldServiceImpl implements MouldService{

    @Resource
    private MouldMapper mouldMapper;

    public int deleteByPrimaryKey(Integer id){
        return mouldMapper.deleteByPrimaryKey(id);
    }

    public int insert(MouldBean record){
        return mouldMapper.insert(record);
    }

    public int insertSelective(MouldBean record){
        return mouldMapper.insertSelective(record);
    }

    public MouldBean selectByPrimaryKey(Integer id){
        return mouldMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(MouldBean record){
        return mouldMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(MouldBean record){
        return mouldMapper.updateByPrimaryKey(record);
    }
}