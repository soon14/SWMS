package com.swms.search.parameter;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class SearchParam {

    // identify search: must start with "com.swms.search.parameter"
    @NotEmpty
    private String searchIdentity;

    @NotEmpty
    private List<Column> showColumns;

    private SearchObject searchObject;

    @Data
    public static class Column {

        private String dbField;
        @NotEmpty
        private String objectField;
        @NotEmpty
        private String javaType;
    }

    @Data
    public static class SearchObject {
        private String tables;
        private String where;
        private String groupBy;
        private String orderBy;
    }

}
