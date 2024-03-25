package com.jetchiu.supercanteen.scheduled;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jetchiu.supercanteen.entity.Evaluate;
import com.jetchiu.supercanteen.entity.Store;
import com.jetchiu.supercanteen.mapper.EvaMapper;
import com.jetchiu.supercanteen.mapper.StoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.List;

@Component
public class ScheduledTask {
    @Autowired
    private StoreMapper storeMapper;
    @Autowired
    private EvaMapper evaMapper;
@Scheduled(fixedRate = 60000)
    public void updateStoreScore(){
    System.out.println("定时任务已启动");
//    List<Evaluate> evaluateList=evaMapper.selectList(new QueryWrapper<Evaluate>());
    List<Store> storeList=storeMapper.selectList(new QueryWrapper<Store>());
            for(Store s:storeList){
                 QueryWrapper<Evaluate>queryWrapper=new QueryWrapper<>();
                 queryWrapper.eq("store_id",s.getId());
                List<Evaluate> evaluateList= evaMapper.selectList(queryWrapper);
                if(evaluateList.isEmpty())
                    continue;
                int env=0;
                int ser=0;
                int food=0;
                int hyg=0;
                for(Evaluate e:evaluateList){
                    env+=e.getEnvScore();
                    ser+=e.getSerScore();
                    food+=e.getFooScore();
                    hyg+=e.getHygScore();
                }
                int _env=env/evaluateList.size();
                int _ser=ser/evaluateList.size();
                int _food=food/evaluateList.size();
                int _hyg=hyg/evaluateList.size();
                  QueryWrapper<Store>queryWrapper1=new QueryWrapper<>();
                  s.setEnvScore(_env);
                  s.setFoodScore(_food);
                  s.setHygScore(_hyg);
                  s.setSerScore(_ser);
                DecimalFormat df = new DecimalFormat("#.#");
                  s.setOverallScore(Double.parseDouble(df.format((_env+_food+_hyg+_ser)/4)));
                storeMapper.updateById(s);
            }
    }
}
