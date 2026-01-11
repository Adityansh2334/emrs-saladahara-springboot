package org.labs.jkcloud.emrs.model.VO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.labs.jkcloud.emrs.model.Contact;
import org.labs.jkcloud.emrs.model.ImportantDate;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdmissionRequestResponse {
    private Long id;
    private String applicationFileUrl;

    private Contact contact;

    private List<String> eligibility;
    private List<String> documentsRequired;
    private List<ImportantDate> importantDates; // format: "Event: Date"
}
