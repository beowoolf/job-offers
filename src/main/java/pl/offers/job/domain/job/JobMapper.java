package pl.offers.job.domain.job;

import pl.offers.job.domain.job.dto.JobRequestDto;
import pl.offers.job.domain.job.dto.JobResponse;
import pl.offers.job.domain.job.dto.JobResponseDto;

public class JobMapper {

    public static JobResponseDto mapFromJobToJobResponseDto(Job job) {
        return JobResponseDto.builder()
                .companyLogoUrl(job.getCompanyLogoUrl())
                .addressText(job.getAddressText())
                .city(job.getCity())
                .companySize(job.getCompanySize())
                .companyName(job.getCompanyName())
                .countryCode(job.getCountryCode())
                .displayOffer(job.getDisplayOffer())
                .employmentTypes(job.getEmploymentTypes())
                .experienceLevel(job.getExperienceLevel())
                .companyUrl(job.getCompanyUrl())
                .markerIcon(job.getMarkerIcon())
                .latitude(job.getLatitude())
                .title(job.getTitle())
                .url(job.getUrl())
                .longitude(job.getLongitude())
                .multilocation(job.getMultilocation())
                .openToHireUkrainians(job.getOpenToHireUkrainians())
                .publishedAt(job.getPublishedAt())
                .street(job.getStreet())
                .skills(job.getSkills())
                .wayOfApply(job.getWayOfApply())
                .workplaceType(job.getWorkplaceType())
                .remote(job.getRemote())
                .remoteInterview(job.getRemoteInterview())
                .id(job.getId())
                .build();
    }

    public static Job mapFromJobDtoToJob(JobRequestDto jobRequestDto) {
        return Job.builder()
                .companyLogoUrl(jobRequestDto.getCompanyLogoUrl())
                .addressText(jobRequestDto.getAddressText())
                .city(jobRequestDto.getCity())
                .companySize(jobRequestDto.getCompanySize())
                .companyName(jobRequestDto.getCompanyName())
                .countryCode(jobRequestDto.getCountryCode())
                .displayOffer(jobRequestDto.getDisplayOffer())
                .employmentTypes(jobRequestDto.getEmploymentTypes())
                .experienceLevel(jobRequestDto.getExperienceLevel())
                .companyUrl(jobRequestDto.getCompanyUrl())
                .markerIcon(jobRequestDto.getMarkerIcon())
                .latitude(jobRequestDto.getLatitude())
                .title(jobRequestDto.getTitle())
                .url(jobRequestDto.getId())
                .longitude(jobRequestDto.getLongitude())
                .multilocation(jobRequestDto.getMultilocation())
                .openToHireUkrainians(jobRequestDto.getOpenToHireUkrainians())
                .publishedAt(jobRequestDto.getPublishedAt())
                .street(jobRequestDto.getStreet())
                .skills(jobRequestDto.getSkills())
                .wayOfApply(jobRequestDto.getWayOfApply())
                .workplaceType(jobRequestDto.getWorkplaceType())
                .remote(jobRequestDto.getRemote())
                .remoteInterview(jobRequestDto.getRemoteInterview())
                .build();
    }

    public static Job mapFromJobResponseToJob(JobResponse jobResponse) {
        return Job.builder()
                .companyLogoUrl(jobResponse.getCompanyLogoUrl())
                .addressText(jobResponse.getAddressText())
                .city(jobResponse.getCity())
                .companySize(jobResponse.getCompanySize())
                .companyName(jobResponse.getCompanyName())
                .countryCode(jobResponse.getCountryCode())
                .displayOffer(jobResponse.getDisplayOffer())
                .employmentTypes(jobResponse.getEmploymentTypes())
                .experienceLevel(jobResponse.getExperienceLevel())
                .companyUrl(jobResponse.getCompanyUrl())
                .markerIcon(jobResponse.getMarkerIcon())
                .latitude(jobResponse.getLatitude())
                .title(jobResponse.getTitle())
                .url("https://justjoin.it/offers/" + jobResponse.getId())
                .longitude(jobResponse.getLongitude())
                .multilocation(jobResponse.getMultilocation())
                .openToHireUkrainians(jobResponse.getOpenToHireUkrainians())
                .publishedAt(jobResponse.getPublishedAt())
                .street(jobResponse.getStreet())
                .skills(jobResponse.getSkills())
                .wayOfApply(jobResponse.getWayOfApply())
                .workplaceType(jobResponse.getWorkplaceType())
                .remote(jobResponse.getRemote())
                .remoteInterview(jobResponse.getRemoteInterview())
                .build();
    }

}
