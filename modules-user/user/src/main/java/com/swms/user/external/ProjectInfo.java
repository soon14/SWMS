package com.swms.user.external;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author sws
 * @date 2021/10/8 17:33
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectInfo {

    private String project;

    private String redirectUrl;

    private Map<String, String> imageUrls;

    private String countries;

    private String language;
}
