package org.labs.jkcloud.emrs.model.VO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UploadResponse {
    private boolean success;
    private String message;
    private String fileName;
    private String url;
    private String error;
}
