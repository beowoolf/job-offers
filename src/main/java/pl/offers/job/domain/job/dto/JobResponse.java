package pl.offers.job.domain.job.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class JobResponse {

    @NotNull(message = "{title.not.null}")
    @NotEmpty(message = "{title.not.empty}")
    private String title;
    @NotNull(message = "{street.not.null}")
    @NotEmpty(message = "{street.not.empty}")
    private String street;
    @NotNull(message = "{city.not.null}")
    @NotEmpty(message = "{city.not.empty}")
    private String city;
    @NotNull(message = "{workplaceType.not.null}")
    @NotEmpty(message = "{workplaceType.not.empty}")
    private String workplaceType;
    @NotNull(message = "{workingTime.not.null}")
    @NotEmpty(message = "{workingTime.not.empty}")
    private String workingTime;
    @NotNull(message = "{companyName.not.null}")
    @NotEmpty(message = "{companyName.not.empty}")
    private String companyName;
    @NotNull(message = "{experienceLevel.not.null}")
    @NotEmpty(message = "{experienceLevel.not.empty}")
    private String experienceLevel;
    @NotNull(message = "{latitude.not.null}")
    @NotEmpty(message = "{latitude.not.empty}")
    private String latitude;
    @NotNull(message = "{longitude.not.null}")
    @NotEmpty(message = "{longitude.not.empty}")
    private String longitude;
    @NotNull(message = "{publishedAt.not.null}")
    @NotEmpty(message = "{publishedAt.not.empty}")
    private String publishedAt;
    @NotNull(message = "{remoteInterview.not.null}")
    private Boolean remoteInterview;
    @NotNull(message = "{openToHireUkrainians.not.null}")
    private Boolean openToHireUkrainians;
    @NotNull(message = "{id.not.null}")
    @NotEmpty(message = "{id.not.empty}")
    private String slug;
    @NotNull(message = "{employmentTypes.not.null}")
    private List<EmploymentType> employmentTypes = new ArrayList<EmploymentType>();
    @NotNull(message = "{companyLogoThumbUrl.not.null}")
    @NotEmpty(message = "{companyLogoThumbUrl.not.empty}")
    private String companyLogoThumbUrl;
    @NotNull(message = "{skills.not.null}")
    private List<Skill> skills = new ArrayList<Skill>();
    @NotNull(message = "{multilocation.not.null}")
    private List<Multilocation> multilocation = new ArrayList<Multilocation>();
    @NotNull(message = "{requiredSkills.not.null}")
    private List<String> requiredSkills;
    private Object niceToHaveSkills;
    @JsonProperty("categoryId")
    private int categoryId;

}
