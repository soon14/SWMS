package com.swms.station.view.model;

import com.swms.common.constants.WorkStationOperationTypeEnum;
import com.swms.common.constants.WorkStationStatusEnum;
import com.swms.station.business.model.OperateTask;
import com.swms.station.business.model.PutWall;
import com.swms.station.business.model.WorkLocation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkStationVO {

    private String stationCode;
    private WorkStationStatusEnum workStationStatus;
    private WorkStationOperationTypeEnum operationType;
    private String chooseArea;

    private List<Tip> tips;

    private WorkLocationArea workLocationArea;
    private SkuArea skuArea;
    private PutWallArea putWallArea;
    private OrderArea orderArea;
    private Toolbar toolbar;

    @Getter
    @AllArgsConstructor
    public enum ChooseAreaEnum {
        SKU_AREA("skuArea"),
        CONTAINER_AREA("containerArea"),
        PUT_WALL_AREA("putWallArea"),
        SCAN_AREA("scanArea"), // 主要用于非缓存货架的空箱出库
        ORDER_AREA("orderArea"),
        TIPS("tips");
        private final String value;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Tip {
        private String tipType;
        private String type;
        private Object data;
        private Long duration;
        private String tipCode;

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Tip tip = (Tip) o;
            return Objects.equals(tipType, tip.tipType) && Objects.equals(tipCode, tip.tipCode);
        }

        @Override
        public int hashCode() {
            return Objects.hash(tipType + tipCode);
        }

        @Getter
        @AllArgsConstructor
        public enum TipShowTypeEnum {
            TIP("tip", "提示框"),
            CONFIRM("confirm", "弹框"),
            VOICE("voice", "语音播报"),
            ;

            private final String value;
            private final String name;
        }

    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Toolbar {
        private boolean enableReportAbnormal;
        private boolean enableSplitContainer;
        private boolean enableReleaseSlot;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class WorkLocationArea {
        private List<WorkLocation> workLocationViews;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SkuArea {
        private String pickType;
        private String totalToBePickedQty;
        private List<OperateTask> pickingViews;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PutWallArea {
        private String putWallDisplayStyle;
        private List<PutWall> putWallViews;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderArea {

    }
}
