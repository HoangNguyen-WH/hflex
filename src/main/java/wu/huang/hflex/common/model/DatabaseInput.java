package wu.huang.hflex.common.model;

import lombok.Data;
import wu.huang.hflex.common.utils.JSONHelper;

import java.util.Map;

@Data
public class DatabaseInput {
    private String errCode;
    private String errMsg;
    private String jsonData;

    public DatabaseInput(Map<String, Object> params) {
        this.jsonData = JSONHelper.obj2JsonStr(params);
    }
}
