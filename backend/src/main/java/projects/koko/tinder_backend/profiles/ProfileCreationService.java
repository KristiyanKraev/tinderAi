package projects.koko.tinder_backend.profiles;

import static projects.koko.tinder_backend.Utils.generateMyersBriggsTypes;
import static projects.koko.tinder_backend.Utils.selfieTypes;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.micrometer.common.util.StringUtils;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

@Service
public class ProfileCreationService {

    private static final String STABLE_DIFFUSION_URL = "https://fe97a6a77c5448ab52.gradio.live/sdapi/v1/txt2img";

    private ChatClient chatClient;

    private HttpClient httpClient;

    private HttpRequest.Builder stableDiffusionRequestBuilder;

    private List<Profile> generatedProfiles = new ArrayList<>();

    private static final String PROFILES_PER_PATH = "profiles.json";

    @Value("${startup-actions.initializeProfiles}")
    private Boolean initializeProfiles;

    @Value("${tinderai.lookingForGender}")
    private String lookingForGender;

    @Value("#{${tinderai.character.user}}")
    private Map<String, String> userProfileProperties;

    private ProfileRepository profileRepository;


    public ProfileCreationService(ChatClient.Builder builder, ProfileRepository profileRepository) {
        this.chatClient = builder.build();
        this.profileRepository = profileRepository;
        this.httpClient = HttpClient.newHttpClient();
        this.stableDiffusionRequestBuilder = HttpRequest.newBuilder()
                .setHeader("Content-Type", "application/json")
                .uri(URI.create(STABLE_DIFFUSION_URL));
    }

    private static <T> T getRandomElement(List<T> list) {
        return list.get(ThreadLocalRandom.current().nextInt(list.size()));
    }

//    public void createProfiles(int numberOfProfiles) {
//        if (!this.initializeProfiles) {
//            return;
//        }
//
//        //Identify the age range, genders and ethnicities for generating profiles
//        List<Integer> ages = new ArrayList<>();
//        for (int i = 20; i <= 35; i++) {
//            ages.add(i);
//        }
//
//        List<String> ethnicities = new ArrayList<>(List.of("White", "Black", "Asian", "Indian", "Hispanic"));
//        List<String> myersBriggsPersonalityTypes = generateMyersBriggsTypes();
//        String gender = this.lookingForGender;
//
//        while (this.generatedProfiles.size() < numberOfProfiles) {
//
//            int age = getRandomElement(ages);
//            String ethnicity = getRandomElement(ethnicities);
//            String personalityType = getRandomElement(myersBriggsPersonalityTypes);
//
//            String prompt = """
//                    You can call the following tool:
//
//                    Tool name: saveProfile
//                    Arguments:
//                    - firstName (string)
//                    - lastName (string)
//                    - age (number)
//                    - ethnicity (string)
//                    - gender (string)
//                    - bio (string)
//                    - myersBriggsPersonalityType (string)
//
//                    Create a Tinder profile persona of a %s %d year old %s %s.
//
//                    RETURN ONLY VALID JSON.
//                    NO TEXT. NO MARKDOWN.
//
//                    Format:
//                    {
//                      "tool": "saveProfile",
//                      "arguments": {
//                        "firstName": "",
//                        "lastName": "",
//                        "age": 0,
//                        "ethnicity": "",
//                        "gender": "",
//                        "bio": "",
//                        "myersBriggsPersonalityType": ""
//                      }
//                    }
//                    """.formatted(personalityType, age, ethnicity, gender);
//
//            // Call Ollama
//            String output = chatClient
//                    .prompt(prompt)
//                    .call()
//                    .content();
//
//            // Parse JSON
//            ObjectMapper mapper = new ObjectMapper();
//            JsonNode root;
//
//            try {
//                root = mapper.readTree(output);
//            } catch (Exception e) {
//                System.out.println("Invalid JSON from model, retrying...");
//                continue;
//            }
//
//            //  Manual function calling
//            if ("saveProfile".equals(root.path("tool").asText())) {
//
//                JsonNode args = root.get("arguments");
//
//                Profile profile = new Profile(
//                        UUID.randomUUID().toString(),
//                        args.get("firstName").asText(),
//                        args.get("lastName").asText(),
//                        args.get("age").asInt(),
//                        args.get("ethnicity").asText(),
//                        Gender.valueOf(args.get("gender").asText().toUpperCase()),
//                        args.get("bio").asText(),
//                        null,
//                        args.get("myersBriggsPersonalityType").asText()
//                );
//
//                new ProfileCreationUtils().saveProfile().apply(profile);
//            }
//        }
//
//
//        //Save the values in a JSON file
//        saveProfilesToJson(this.generatedProfiles);
//
//
//    }
//
//    private void saveProfilesToJson(List<Profile> profiles) {
//        try {
//            Gson gson = new Gson();
//            List<Profile> existingProfiles = gson.fromJson(
//                    new FileReader(PROFILES_PER_PATH),
//                    new TypeToken<ArrayList<Profile>>() {
//                    }.getType());
//            generatedProfiles.addAll(existingProfiles);
//            List<Profile> profilesWithImages = new ArrayList<>();
//            for (Profile profile : profiles) {
//                if (profile.getImageUrl() == null || "".equals(profile.getImageUrl())) {
//                    profile = generateProfileImage(profile);
//                }
//                profilesWithImages.add(profile);
//            }
//            String jsonString = gson.toJson(profilesWithImages);
//            FileWriter writer = new FileWriter(PROFILES_PER_PATH);
//            writer.write(jsonString);
//            writer.close();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    private Profile generateProfileImage(Profile profile) {
//        String uuid = StringUtils.isBlank(profile.getId()) ? UUID.randomUUID().toString() : profile.getId();
//        profile = new Profile(
//                uuid,
//                profile.getFirstName(),
//                profile.getLastName(),
//                profile.getAge(),
//                profile.getEthnicity(),
//                profile.getGender(),
//                profile.getBio(),
//                uuid + ".jpg",
//                profile.getMyersBriggsPersonalityType()
//        );
//        String randomSelfieType = getRandomElement(selfieTypes());
//
//        String prompt = """
//                Selfie of a %d year old %s %s %s, %s,
//                photorealistic skin texture and details,
//                individual hairs and pores visible,
//                highly detailed, photorealistic, hyperrealistic,
//                subsurface scattering, 4k DSLR,
//                ultrarealistic, best quality, masterpiece.
//                Bio - %s
//                """.formatted(
//                profile.getAge(),
//                profile.getMyersBriggsPersonalityType(),
//                profile.getEthnicity(),
//                profile.getGender(),
//                randomSelfieType,
//                profile.getBio()
//        );
//
//        String negativePrompt = "multiple faces, lowres, text, error, cropped, worst quality, " +
//                "low quality, jpeg artifacts, ugly, duplicate, morbid, mutilated, out of frame, " +
//                "extra fingers, mutated hands, poorly drawn hands, poorly drawn face, mutation, " +
//                "deformed, blurry, dehydrated, bad anatomy, bad proportions, extra limbs, cloned face, " +
//                "disfigured, gross proportions, malformed limbs, missing arms, missing legs, extra arms, " +
//                "extra legs, fused fingers, too many fingers, long neck, username, watermark, signature";
//
//        String jsonString = """
//                { "prompt": %s, "negative_prompt": %s, "steps":40 }
//                """.formatted(
//                prompt,
//                negativePrompt
//        );
//
//        System.out.println("""
//                Creating image for %s\\{profile.firstName()} %s\\{profile.lastName()}(\\ %s{profile.ethnicity()
//                """.formatted(
//                profile.getFirstName(),
//                profile.getLastName(),
//                profile.getEthnicity()
//
//        ));
//
//        //Make a POST request to the Stable diffusion URL
//        HttpRequest httpRequest = this.stableDiffusionRequestBuilder.POST(HttpRequest.BodyPublishers.ofString(jsonString))
//                .build();
//        HttpResponse<String> response;
//        try {
//            response = this.httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
//        } catch (IOException | InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//
//        //Save the generated images in the resources folder
//        record ImageResponse(List<String> images){}
//
//        Gson gson = new Gson();
//        ImageResponse imageResponse = gson.fromJson(response.body(), ImageResponse.class);
//        if(imageResponse.images() != null && !imageResponse.images().isEmpty()){
//            String base64Image = imageResponse.images().getFirst();
//
//            //Decode Base64 to binary
//            byte[] imageBytes = Base64.getDecoder().decode(base64Image);
//            String directoryPath = "src/main/resources/images/";
//            String filePath = directoryPath + profile.getImageUrl();
//            Path directory = Paths.get(directoryPath);
//            if(!Files.exists(directory)){
//                try{
//                    Files.createDirectories(directory);
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//            //Save the image to the file
//            try(FileOutputStream imageOutFile = new FileOutputStream(filePath)){
//                imageOutFile.write(imageBytes);
//            } catch (IOException e) {
//                return null;
//            }
//        }
//        return profile;
//
//        //Link the image name to the profile's image URL field
//    }
//
//
//
//    public void saveProfilesToDB(){
//        Gson gson = new Gson();
//        try{
//            List<Profile> existingProfiles = gson.fromJson(
//                    new FileReader(PROFILES_PER_PATH),
//                    new TypeToken<ArrayList<Profile>>() {}.getType()
//            );
//            profileRepository.deleteAll();
//            profileRepository.saveAll(existingProfiles);
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//        Profile profile = new Profile(
//                userProfileProperties.get("id"),
//                userProfileProperties.get("firstName"),
//                userProfileProperties.get("lastName"),
//                Integer.parseInt(userProfileProperties.get("age")),
//                userProfileProperties.get("ethnicity"),
//                Gender.valueOf(userProfileProperties.get("gender")),
//                userProfileProperties.get("bio"),
//                userProfileProperties.get("imageUrl"),
//                userProfileProperties.get("myersBriggsPersonalityType")
//        );
//        System.out.println(userProfileProperties);
//        profileRepository.save(profile);
//
//    }

    class ProfileCreationUtils{
        @Tool(description = "Save the Tinder Profile information")
        public Function<Profile,Boolean> saveProfile(){
            return ( profile) -> {
                ProfileCreationService.this.generatedProfiles.add(profile);
                return true;
            };
        }
    }
}
