package pl.offers.job.domain.job;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.offers.job.domain.job.dto.EmploymentType;
import pl.offers.job.domain.job.dto.Multilocation;
import pl.offers.job.domain.job.dto.Skill;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Document("jobs")
class Job {

    @Id
    private String id;
    @Indexed(unique = true)
    private String url;
    private String title;
    private String street;
    private String city;
    private String countryCode;
    private String addressText;
    private String markerIcon;
    private String workplaceType;
    private String companyName;
    private String companyUrl;
    private String companySize;
    private String experienceLevel;
    private String latitude;
    private String longitude;
    private String publishedAt;
    private Boolean remoteInterview;
    private Boolean openToHireUkrainians;
    private Boolean displayOffer;
    private List<EmploymentType> employmentTypes = new ArrayList<EmploymentType>();
    private String companyLogoUrl;
    private List<Skill> skills = new ArrayList<Skill>();
    private Boolean remote;
    private List<Multilocation> multilocation = new ArrayList<Multilocation>();
    private String wayOfApply;

}
