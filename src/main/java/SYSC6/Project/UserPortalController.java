package SYSC6.Project;

import SYSC6.Project.user.User;
import SYSC6.Project.user.UserRepository;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;


@Controller
public class UserPortalController {
    private static int LIMIT = 1000;
    private boolean limitExists = false;
    private Long id = 0L;
    private HashMap<Long, String> users = new HashMap<>(); // TODO: store users internally on portal and acces their info according to id

    @Autowired
    UserRepository userRepository;

    @GetMapping("/user/{id}")
    public String getId(@PathVariable("id") String userID){
        id = Integer.parseInt(userID) * 1L;
        return "redirect:/user_portal";
    }

    /*
    @PostMapping("/user_portal")
    public String enterPortal(@RequestParam(value="id",required=true) String UserId, @RequestParam(value="role",required=true) String role, Model model){
        id = Integer.parseInt(UserId) * 1L;
        checkRole(role);
        model.addAttribute("limitExists", limitExists);

        System.out.println("Entered user portal");

        return "redirect:/user_portal";
    }*/

    @GetMapping("/user_portal")
    public String greeting(Model model) {
        if(id==0){
            return "login_form";
        }
        User user = getUser(id);

        // Store user info within the portal if it's not already saved
        if (!users.containsKey(id)){
            users.put(id, user.toString());
        }
        checkRole(user.getRole().toString());
        model.addAttribute("name", user.getUsername());
        model.addAttribute("role", user.getRole().toString());

        return "user_portal";
    }

    @PostMapping("/GoBack")
    public String goBack(){

        System.out.println("Back from app_proxy");

        return "redirect:/user_portal";
    }

    /**
     * Checks the user's role
     * If they are a paid user, disable the app limit
     * Otherwise, enable limit
     */
    private void checkRole(String role){
        // User's role is PAID_USER
        if (role == RoleType.PAID_USER.toString()){
            // don't enable limit
            limitExists = false;
        }
        else{
            // By default, we will apply a limit i.e. assume FREE_USER
            limitExists = true;
        }
    }

    /**
     * @return redirects page to the app proxy
     */
    @PostMapping("/Click")
    public String incrementClicks(Model model){
        User user = getUser(id);
        System.out.printf("User Info with ID=%d [BEFORE]: \n%s\n", id, users.get(id));
//        System.out.printf("User Info with ID=%d: \n%s\n", id, user.toString());
//        int numClicks = user.getClicks();
        int numClicks = Integer.parseInt(users.get(id).split("=")[5].replace("]", ""));

        // Check if a limit has been reached only if it is applied (free user only)
        if (limitExists) {
            if (numClicks == LIMIT) {
                // free user has reached their 1000 click limit - disable "enter app" button
                System.out.println("Limit reached");

                // stay on current page
                return "redirect:/user_portal";
            }
        }

        int oldCount = numClicks;
        numClicks++;

        String updatedUserData = users.get(id).replace(String.format("Clicks=%d",oldCount), String.format("Clicks=%d",numClicks));
        users.replace(id, updatedUserData); // Update locally stored user data

        user.setClicks(numClicks); // Update user
        userRepository.save(user); // update clicks in database

        model.addAttribute("limitExists", limitExists);
        System.out.println("Your ID: " + id);
//        System.out.println("User Info \n: " + user.toString());
        System.out.printf("User Info with ID=%d [AFTER]: \n%s\n", id, users.get(id));
        // enter app
        return "redirect:/app_proxy";
    }

    /**
     * Actions when user enters the app via user portal
     * Here we will check if an UNPAID user has reached their limit and display it
     * A PAID user will not have a limit applied to them
     * @param model
     * @return
     */
    @GetMapping("/app_proxy")
    public String enterApp(Model model){
        User user = getUser(id);
        model.addAttribute("name", user.getUsername());
        model.addAttribute("role", user.getRole().toString());
        return "app_proxy";
    }

    public User getUser(Long id){
        JSONParser jsonParser = new JSONParser();
        User user = new User();
        System.out.println(id);
        try {
            URL url = new URL ("http://localhost:8080/rest/api/user/"+id.toString());
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println(response);
                JSONObject temp = (JSONObject) jsonParser.parse(response.toString());
                //System.out.println(temp.get("username").toString());
                user = new User(temp.get("username").toString(), temp.get("password").toString(), RoleType.getRoleByString(temp.get("role").toString()));
                user.setId(Long.valueOf(temp.get("id").toString()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        catch (IOException e){
            System.out.println("Error");
        }
        return user;
    }
}