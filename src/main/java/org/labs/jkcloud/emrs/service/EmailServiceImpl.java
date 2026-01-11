package org.labs.jkcloud.emrs.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.*;

import java.time.Year;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
@Slf4j
@Qualifier("emailServiceAWS")
public class EmailServiceImpl implements EmailService {

    @Value("${aws.ses.sender}")
    private String sender;

    @Value("${aws.ses.recipient}")
    private String recipient; // comma-separated emails

    @Value("${aws.ses.emailTemplateName}")
    private String emailTemplateName;

    private final SesClient sesClient = SesClient.builder()
            .region(Region.AP_SOUTH_1)
            .build();

    @Override
    public boolean sendOtpEmail(String otp) {
        String[] recipientList = recipient.split(",");

        try {
            // Prepare template data
            Map<String, String> templateDataMap = new HashMap<>();
            templateDataMap.put("otp", otp);
            templateDataMap.put("year", String.valueOf(Year.now()));

            String templateDataJson = new ObjectMapper().writeValueAsString(templateDataMap);

            // Create request
            SendTemplatedEmailRequest templatedEmailRequest = SendTemplatedEmailRequest.builder()
                    .source(sender)
                    .destination(Destination.builder().toAddresses(recipientList).build())
                    .template(emailTemplateName)
                    .templateData(templateDataJson)
                    .build();

            sesClient.sendTemplatedEmail(templatedEmailRequest);
            log.info("✅ Templated OTP email sent to: {}", String.join(", ", recipientList));
            return true;

        } catch (SesException e) {
            log.error("❌ SES error while sending OTP email: {}", e.awsErrorDetails().errorMessage());
        } catch (Exception e) {
            log.error("❌ Unexpected error while sending OTP email", e);
        }

        return false; // indicate failure to caller
    }
}
