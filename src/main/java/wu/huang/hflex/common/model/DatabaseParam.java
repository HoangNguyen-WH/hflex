package wu.huang.hflex.common.model;

import lombok.Data;
import wu.huang.hflex.common.utils.JSONHelper;

import java.util.List;
import java.util.Map;

@Data
public class DatabaseParam {
    private String key;
    private String errCode;
    private String sql;
    private String errMsg;
    private String jsonData;
    private List<Map<String, Object>> cursor;

    public DatabaseParam(Map<String, Object> params, String key) {
        this.key = key;
        this.jsonData = JSONHelper.obj2JsonStr(params);
    }
}
