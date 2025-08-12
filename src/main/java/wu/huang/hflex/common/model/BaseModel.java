package wu.huang.hflex.common.model;

import java.util.Map;

public abstract class BaseModel {
    abstract <T extends BaseModel> T transformFromMap(Map<String, Object> map);
}
