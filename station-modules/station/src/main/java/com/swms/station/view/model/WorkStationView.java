package com.swms.station.view.model;

import com.swms.common.constants.WorkStationOperationTypeEnum;
import com.swms.common.constants.WorkStationStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkStationView {

    private String stationCode;
    private WorkStationStatusEnum stationStatus;
    private WorkStationOperationTypeEnum stationOperationType;
    private String chooseArea;

    private List<Tip> tips;

    private ContainerArea containerArea;
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
    public static class ContainerArea {
        private List<ContainerView> containerViews;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SkuArea {
        private String pickType;
        private String totalToBePickedQty;
        private List<PickingView> pickingViews;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PutWallArea {
        private String putWallDisplayStyle;
        private List<PutWallView> putWallViews;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ContainerView {
        private String containerCarrier;
        private String machineType;
        private String groupCode;
        private boolean active;
        private CarrierDesc carrierDesc;
    }

    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PickingView {
        private Long skuBatchId;
        private String skuCode;
        private String skuName;
        //        private List<SnInfo> snInfos;
        private Map<String, Object> batchAttributeJson;
        //        private List<PageConfigDetail> skuDesc;
        private Integer requiredQty;
        private Integer pickedQty;
        private Integer shortAmount;
        private Integer toBePickedQty;
        //        private AmountDisplayRule amountDisplayRule;
        private String imageUrl;
        private boolean isScanned;
        private String subContainerCode;
        private String subContainerName;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PutWallView {
        private String putWallCode;
        private int level;
        private int bay;
        private String location;
        private boolean active;
        private boolean showHasTask;
        private List<PutWallSlotView> putWallSlotView;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    @SuperBuilder(toBuilder = true)
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PutWallSlotView extends SubContainerVO {
        private String transferContainerCode;
        private Integer dispatchQty;
        //        private List<PageConfigDetail> putWallSlotDesc;
        private String status;
        private boolean allowSplit;
        //        private AmountDisplayRule amountDisplayRule;
        private List<Long> orderIds;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CarrierDesc {
        private Integer level;
        private Integer bay;
        private List<CarrierSlotView> carrierSlots;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    @SuperBuilder(toBuilder = true)
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CarrierSlotView extends SubContainerVO {
        private String subContainerCode;
        private ContainerDesc containerDesc;
        private String abnormalFlag;
        private String abnormalDesc;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ContainerDesc {
        private String containerCode;
        private String containerType;
        private Integer level;
        private Integer bay;
        private List<SubContainerVO> subContainers;
        private String face;
        private Integer rotationAngle;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderArea {

    }
}
