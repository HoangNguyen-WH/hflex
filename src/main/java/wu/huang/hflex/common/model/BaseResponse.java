package wu.huang.hflex.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T extends BaseModel> {
    private commonsResponse commons;
    private PagingResponse paging;
    private T dataModel;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class commonsResponse {
        private String code;
        private String type;
        private String message;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PagingResponse {
        private long totalRows;
        private long totalPages;
        private int currentPage;
        private int pageSize;
    }
}
