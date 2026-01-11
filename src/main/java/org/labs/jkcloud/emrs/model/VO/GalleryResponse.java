package org.labs.jkcloud.emrs.model.VO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GalleryResponse {

    private Long id;

    private String title;

    private String coverImage;

    private String[] images;
}
