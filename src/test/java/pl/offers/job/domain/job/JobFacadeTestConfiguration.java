package pl.offers.job.domain.job;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.offers.job.domain.job.dto.JobResponse;
import pl.offers.job.domain.job.dto.JobsResponse;

import java.util.List;

public class JobFacadeTestConfiguration {
    private final InMemoryFetcherTestImpl inMemoryFetcherTest;
    private final InMemoryJobRepository jobRepository;
    public ObjectMapper objectMapper = new ObjectMapper();

    JobFacadeTestConfiguration() throws JsonProcessingException {
        String json = """
                {
                 	"success": true,
                 	"list": [
                 		{
                 			"slug": "leverx-integration-architect-cig",
                 			"title": "Integration Architect (CIG)",
                 			"requiredSkills": [
                 				"SAP"
                 			],
                 			"niceToHaveSkills": null,
                 			"workplaceType": "hybrid",
                 			"workingTime": "full_time",
                 			"experienceLevel": "mid",
                 			"employmentTypes": [
                 				{
                 					"to": null,
                 					"from": null,
                 					"type": "permanent",
                 					"to_chf": null,
                 					"to_eur": null,
                 					"to_gbp": null,
                 					"to_pln": null,
                 					"to_usd": null,
                 					"currency": "usd",
                 					"from_chf": null,
                 					"from_eur": null,
                 					"from_gbp": null,
                 					"from_pln": null,
                 					"from_usd": null
                 				}
                 			],
                 			"categoryId": 23,
                 			"multilocation": [
                 				{
                 					"city": "Wroclaw",
                 					"slug": "leverx-integration-architect-cig",
                 					"street": "Pilsudskiego 69, 50-019",
                 					"latitude": 51.100532600000001,
                 					"longitude": 17.0311415
                 				}
                 			],
                 			"city": "Wroclaw",
                 			"street": "Pilsudskiego 69, 50-019",
                 			"latitude": "51.1005326",
                 			"longitude": "17.0311415",
                 			"remoteInterview": true,
                 			"companyName": "LeverX",
                 			"companyLogoThumbUrl": "https://public.justjoin.it/offers/company_logos/thumb_x2/d7395b4e4eb5edd530f1137435bb0a91a63f9117.jpg?1700137000",
                 			"publishedAt": "2024-01-31T09:00:00.000Z",
                 			"openToHireUkrainians": true
                 		},
                 		{
                 			"slug": "fujitsu-linux-engineer",
                 			"title": "Linux Engineer",
                 			"requiredSkills": [
                 				"Red Hat",
                 				"Linux",
                 				"Ansible"
                 			],
                 			"niceToHaveSkills": null,
                 			"workplaceType": "remote",
                 			"workingTime": "full_time",
                 			"experienceLevel": "mid",
                 			"employmentTypes": [
                 				{
                 					"to": null,
                 					"from": null,
                 					"type": "permanent",
                 					"to_chf": null,
                 					"to_eur": null,
                 					"to_gbp": null,
                 					"to_pln": null,
                 					"to_usd": null,
                 					"currency": "pln",
                 					"from_chf": null,
                 					"from_eur": null,
                 					"from_gbp": null,
                 					"from_pln": null,
                 					"from_usd": null
                 				}
                 			],
                 			"categoryId": 13,
                 			"multilocation": [
                 				{
                 					"city": "Łódź",
                 					"slug": "fujitsu-linux-engineer",
                 					"street": "Milionowa",
                 					"latitude": 51.752020100000003,
                 					"longitude": 19.492133299999999
                 				},
                 				{
                 					"city": "Warszawa",
                 					"slug": "fujitsu-linux-engineer-warszawa",
                 					"street": "Centrum",
                 					"latitude": 52.230209100000003,
                 					"longitude": 21.0113922
                 				},
                 				{
                 					"city": "Katowice",
                 					"slug": "fujitsu-linux-engineer-katowice",
                 					"street": "Centrum",
                 					"latitude": 51.752020100000003,
                 					"longitude": 19.492133299999999
                 				},
                 				{
                 					"city": "Kraków",
                 					"slug": "fujitsu-linux-engineer-krakow",
                 					"street": "Centrum",
                 					"latitude": 50.0591121,
                 					"longitude": 19.9378922
                 				},
                 				{
                 					"city": "Gdańsk",
                 					"slug": "fujitsu-linux-engineer-gdansk",
                 					"street": "Centrum",
                 					"latitude": 54.3520252,
                 					"longitude": 18.646638400000001
                 				},
                 				{
                 					"city": "Poznań",
                 					"slug": "fujitsu-linux-engineer-poznan",
                 					"street": "Centrum",
                 					"latitude": 52.406374,
                 					"longitude": 16.9251681
                 				},
                 				{
                 					"city": "Wroclaw",
                 					"slug": "fujitsu-linux-engineer-wroclaw",
                 					"street": "Centrum",
                 					"latitude": 51.107885199999998,
                 					"longitude": 17.038537600000002
                 				},
                 				{
                 					"city": "Zielona Góra",
                 					"slug": "fujitsu-linux-engineer-zielona-gora",
                 					"street": "Centrum",
                 					"latitude": 51.935621400000002,
                 					"longitude": 15.5061862
                 				},
                 				{
                 					"city": "Bydgoszcz",
                 					"slug": "fujitsu-linux-engineer-bydgoszcz",
                 					"street": "Centrum",
                 					"latitude": 53.123480399999998,
                 					"longitude": 18.008437799999999
                 				},
                 				{
                 					"city": "Rzeszów",
                 					"slug": "fujitsu-linux-engineer-rzeszow",
                 					"street": "Centrum",
                 					"latitude": 50.041186699999997,
                 					"longitude": 21.9991196
                 				},
                 				{
                 					"city": "Toruń",
                 					"slug": "fujitsu-linux-engineer-torun",
                 					"street": "Centrum",
                 					"latitude": 53.013790200000003,
                 					"longitude": 18.598443700000001
                 				}
                 			],
                 			"city": "Łódź",
                 			"street": "Milionowa",
                 			"latitude": "51.7520201",
                 			"longitude": "19.4921333",
                 			"remoteInterview": true,
                 			"companyName": "Fujitsu",
                 			"companyLogoThumbUrl": "https://public.justjoin.it/offers/company_logos/thumb_x2/1c4a3f3ef88249e8709fbd8d4323287228c0aa70.jpg?1706020703",
                 			"publishedAt": "2024-01-30T15:58:00.000Z",
                 			"openToHireUkrainians": false
                 		},
                 		{
                 			"slug": "prime-engineering-technical-analyst-with-sap-krakow",
                 			"title": "Technical Analyst with SAP",
                 			"requiredSkills": [
                 				"SAP TR",
                 				"SAP",
                 				"SAP Cash Management",
                 				"SAP FI",
                 				"Java",
                 				"SAP IHC / SAP APM"
                 			],
                 			"niceToHaveSkills": null,
                 			"workplaceType": "remote",
                 			"workingTime": "full_time",
                 			"experienceLevel": "mid",
                 			"employmentTypes": [
                 				{
                 					"to": 20000,
                 					"from": 16000,
                 					"type": "b2b",
                 					"to_chf": 4275.46,
                 					"to_eur": 4579.8400000000001,
                 					"to_gbp": 3903.98,
                 					"to_pln": "20000",
                 					"to_usd": 4965.1400000000003,
                 					"currency": "pln",
                 					"from_chf": 3420.3679999999999,
                 					"from_eur": 3663.8719999999998,
                 					"from_gbp": 3123.1840000000002,
                 					"from_pln": "16000",
                 					"from_usd": 3972.1120000000001
                 				}
                 			],
                 			"categoryId": 22,
                 			"multilocation": [
                 				{
                 					"city": "Kraków",
                 					"slug": "prime-engineering-technical-analyst-with-sap-krakow",
                 					"street": "Centrum",
                 					"latitude": 50.0591121,
                 					"longitude": 19.9378922
                 				},
                 				{
                 					"city": "Wroclaw",
                 					"slug": "prime-engineering-technical-analyst-with-sap-wroclaw",
                 					"street": "Centrum",
                 					"latitude": 51.107885199999998,
                 					"longitude": 17.038537600000002
                 				},
                 				{
                 					"city": "Gdańsk",
                 					"slug": "prime-engineering-technical-analyst-with-sap-gdansk",
                 					"street": "Centrum",
                 					"latitude": 54.3520252,
                 					"longitude": 18.646638400000001
                 				},
                 				{
                 					"city": "Poznań",
                 					"slug": "prime-engineering-technical-analyst-with-sap-poznan",
                 					"street": "Centrum",
                 					"latitude": 52.409363399999997,
                 					"longitude": 16.931799099999999
                 				},
                 				{
                 					"city": "Warszawa",
                 					"slug": "prime-engineering-technical-analyst-with-sap",
                 					"street": "Centrum",
                 					"latitude": 52.230209100000003,
                 					"longitude": 21.0113922
                 				}
                 			],
                 			"city": "Kraków",
                 			"street": "Centrum",
                 			"latitude": "50.0591121",
                 			"longitude": "19.9378922",
                 			"remoteInterview": true,
                 			"companyName": "Prime Engineering",
                 			"companyLogoThumbUrl": "https://public.justjoin.it/offers/company_logos/thumb_x2/d82b9c0187e10636eb9f44e48c8db70feab6368e.png?1706627503",
                 			"publishedAt": "2024-01-30T15:13:56.445Z",
                 			"openToHireUkrainians": false
                 		},
                 		{
                 			"slug": "prime-engineering-business-analyst-with-sap-krakow",
                 			"title": "Business Analyst with SAP",
                 			"requiredSkills": [
                 				"SAP-IHC",
                 				"SAP-FI",
                 				"SAP-APM",
                 				"procesy płatności",
                 				"SAP Finance/Treasury",
                 				"Java"
                 			],
                 			"niceToHaveSkills": null,
                 			"workplaceType": "remote",
                 			"workingTime": "full_time",
                 			"experienceLevel": "mid",
                 			"employmentTypes": [
                 				{
                 					"to": 20000,
                 					"from": 16000,
                 					"type": "b2b",
                 					"to_chf": 4275.46,
                 					"to_eur": 4579.8400000000001,
                 					"to_gbp": 3903.98,
                 					"to_pln": "20000",
                 					"to_usd": 4965.1400000000003,
                 					"currency": "pln",
                 					"from_chf": 3420.3679999999999,
                 					"from_eur": 3663.8719999999998,
                 					"from_gbp": 3123.1840000000002,
                 					"from_pln": "16000",
                 					"from_usd": 3972.1120000000001
                 				}
                 			],
                 			"categoryId": 22,
                 			"multilocation": [
                 				{
                 					"city": "Kraków",
                 					"slug": "prime-engineering-business-analyst-with-sap-krakow",
                 					"street": "Centrum",
                 					"latitude": 50.0591121,
                 					"longitude": 19.9378922
                 				},
                 				{
                 					"city": "Wroclaw",
                 					"slug": "prime-engineering-business-analyst-with-sap-wroclaw",
                 					"street": "Centrum",
                 					"latitude": 51.107885199999998,
                 					"longitude": 17.038537600000002
                 				},
                 				{
                 					"city": "Gdańsk",
                 					"slug": "prime-engineering-business-analyst-with-sap-gdansk",
                 					"street": "Centrum",
                 					"latitude": 54.3520252,
                 					"longitude": 18.646638400000001
                 				},
                 				{
                 					"city": "Poznań",
                 					"slug": "prime-engineering-business-analyst-with-sap-poznan",
                 					"street": "Centrum",
                 					"latitude": 52.409363399999997,
                 					"longitude": 16.931799099999999
                 				},
                 				{
                 					"city": "Warszawa",
                 					"slug": "prime-engineering-business-analyst-with-sap",
                 					"street": "Centrum",
                 					"latitude": 52.230209100000003,
                 					"longitude": 21.0113922
                 				}
                 			],
                 			"city": "Kraków",
                 			"street": "Centrum",
                 			"latitude": "50.0591121",
                 			"longitude": "19.9378922",
                 			"remoteInterview": true,
                 			"companyName": "Prime Engineering",
                 			"companyLogoThumbUrl": "https://public.justjoin.it/offers/company_logos/thumb_x2/2aa9eedaff2834aec65eec4e552c296e16fd2d9f.png?1706627530",
                 			"publishedAt": "2024-01-30T15:13:49.295Z",
                 			"openToHireUkrainians": false
                 		}
                    ]
                }""";
        JobsResponse parsedJson = objectMapper.readValue(json, new TypeReference<>() {
        });
        this.inMemoryFetcherTest = new InMemoryFetcherTestImpl(parsedJson);
        this.jobRepository = new InMemoryJobRepository();
    }

    JobFacadeTestConfiguration(JobsResponse remoteClientJobs) {
        this.inMemoryFetcherTest = new InMemoryFetcherTestImpl(remoteClientJobs);
        this.jobRepository = new InMemoryJobRepository();
    }

    JobFacade jobFacadeForTests() {
        return new JobFacade(jobRepository, new JobService(inMemoryFetcherTest, jobRepository));
    }

}
