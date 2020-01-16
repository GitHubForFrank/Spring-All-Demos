package com.zmz.core.infrastructure.translator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ASNPHDG
 * @create 2019-08-03 15:13
 */
public interface Translator<T, V> {
    /**
     * Translate entity bean to value object
     * */
    V E2VO(T entity, V vo);
    /**
     * Translate value object to entity
     * */
    T VO2E(T entity, V vo);

    default List<V> E2VOs(List<T> entities, List<V> vos){
        if (entities == null || entities.size() == 0) {
            return null;
        }
        List<V> result = new ArrayList<>();
        for (int iLoop = 0; iLoop < entities.size(); iLoop++) {
            V vo = null;
            if (vos != null && vos.size() >= iLoop) {
                vo = vos.get(iLoop);
            }
            T entity = entities.get(iLoop);
            result.add(E2VO(entity, vo));
        }
        return result;
    }

    default List<T> VO2Es(List<T> entities, List<V> vos){
        if(vos == null || vos.size()==0){
            return null;
        }
        List<T> result = new ArrayList<>();
        for(int iLoop=0; iLoop<vos.size(); iLoop++){
            T entity = null;
            if(entities != null && entities.size()>=iLoop){
                entity = entities.get(iLoop);
            }
            V vo = vos.get(iLoop);
            result.add(VO2E(entity, vo));
        }
        return result;
    }
}
