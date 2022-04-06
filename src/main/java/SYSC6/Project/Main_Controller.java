package SYSC6.Project;

import SYSC6.Project.user.User;
import SYSC6.Project.user.UserUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@Controller
public class Main_Controller {
<<<<<<< master

    /**
     * Id of the current logged in User
     * TestFlag is a flag to determine if the initial admin set up was done or not
     */
=======
>>>>>>> Completed user delete method functionality
    private Long id = 0L;
    private boolean testFlag = true;

<<<<<<< master
    /**
     * Sets a basic user and admin within the system sur-conventing the need for required password format
     */
    private void testInit(){
        if(testFlag){
            createUser("admin", "admin");
            createUser("user", "user");
            testFlag = false;
        }
    }

    /**
     * Start the intialization of the admin and user then redirects to the login page
     * @return the Login html
     */
    @GetMapping("/")
    public String login(){
        testInit();
=======
    @GetMapping("/")
    public String login(){
>>>>>>> Completed user delete method functionality
        return "login_form";
    }

    /**
     * Javascript returns the id of the active user and if that user is an admin or not
     * this allows to redirect the user to the appropriate page
     * @param UserId id of the current user
     * @param admin value indicating if that user is an admin or not
     * @return user_portal page or admin_portal page
     */
    @PostMapping("/login_form")
    public String login_process(@RequestParam(value="id",required=true) String UserId, @RequestParam(value="admin", required = true) String admin){
<<<<<<< master
        id = (long) Integer.parseInt(UserId);
=======
        id = Integer.parseInt(UserId) * 1L;
>>>>>>> Completed user delete method functionality
        if(admin.equals("admin")){
            return "redirect:/admin_portal";
        }
        return "redirect:/user_portal";
    }

    /**
     * Bring the user to the registration form
     * @return Registration page
     */
    @PostMapping("/Register")
    public String Register(){
        return "redirect:/Registration";
    }

<<<<<<< master
    /**
     * Displays the html for the registration page
     * @return
     */
=======
>>>>>>> Completed user delete method functionality
    @GetMapping("/Registration")
    public String Reg(){
        return "Registration";
    }

<<<<<<< master
    /**
     * Used to create a new user sent over from the registration page
     * User is tagged with an id and an admin value to redirect to its appropriate page
     * @param admin if the user is an admin or not
     * @param TempId the user id
     * @return user_portal page or admin_portal page
     */
    @PostMapping("/TempCreate")
    public String TempCreate(@RequestParam(value="admin", required = true) String admin, @RequestParam(value="id", required = true) String TempId){
        id = Integer.parseInt(TempId) * 1L;
        if(admin.equals("admin")){
            return "redirect:/admin_portal";
        }
        else{
            return "redirect:/user_portal";
=======
    /*
    @PostMapping("/Create")
    public String create(@RequestParam(value="user",required=true) String user, @RequestParam(value="pass",required=true) String pass){
        id = createUser(user,pass);
        User check_user = getUser(id);
        if(check_user.getUsername().equals("admin")){
            if(check_user.getPassword().equals("admin")){
                return "redirect:/admin_portal";
            }
>>>>>>> Completed user delete method functionality
        }
    }
    */

    @PostMapping("/TempCreate")
    public String TempCreate(@RequestParam(value="admin", required = true) String admin, @RequestParam(value="id", required = true) String TempId){
        id = Integer.parseInt(TempId) * 1L;
        if(admin.equals("admin")){
            return "redirect:/admin_portal";
        }
        else{
            return "redirect:/user_portal";
        }
    }

    /**
     * Logs the user out and returns the user to the login screen
     * @return to the login page
     */
    @PostMapping("/LogOut")
    public String logout(){
        id = 0L;
        return "redirect:/";
    }

    /**
     * Brings the logged in user to the user portal page
     * @param model
     * @return returns the html for the user portal
     */
    @GetMapping("/user_portal")
    public String greeting(Model model) {
        if(id==0){
            return "login_form";
        }
        User user = getUser(id);
<<<<<<< master
        model.addAttribute("name", user.getUsername());
=======
        name_place = user.getUsername();
        model.addAttribute("name", name_place);
>>>>>>> Completed user delete method functionality
        model.addAttribute("role", user.getRole().toString());
        return "user_portal";
    }

<<<<<<< master
    @PutMapping("/Upgrade")
    private String upgradeUser(){
        return "redirect:/user_portal";
    }

=======
    @PostMapping("/delete_user")
    public String deleteUsers() {
        deleteUser(this.id);

        return "redirect:/";
    }

    @PostMapping("/noDelete")
    public String back() {
        return "redirect:/user_portal";
    }

    @GetMapping("/delete_user")
    public String delete_page() {
//        return "confirm_delete";

        return "";
    }

    // GetMapping for HTML page
    // Yes: calls the delete_user command
    // No returns to the user portal page

>>>>>>> Completed user delete method functionality
    @GetMapping("/admin_portal")
    public String greeting_admin(@RequestParam(name="name", required=false, defaultValue="World") String name_place, Model model) {
        if(id==0){
            return "login_form";
        }
        User user = getUser(id);
        if(UserUtil.hasAdmin(user)){
            model.addAttribute("name", "Admin");
            model.addAttribute("role", user.getRole().toString());
            return "admin_portal";
        }
        return "login_form";
    }

    @GetMapping("/view_users")
    public String getUsers(){
        if(id==0){
            return "login_form";
        }
        User user = getUser(id);
        if(UserUtil.hasAdmin(user)){
            return "view_users";
        }
        return logout();
    }

<<<<<<< master
    //---

=======
>>>>>>> Completed user delete method functionality
    public Long createUser(String Username, String Password){
        JSONParser jsonParser = new JSONParser();
        Long x = 0L;
        try {
            URL url = new URL ("http://localhost:8080/rest/api/user/add");
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
            String jsonInputString = "{" + '"' + "username" + '"' + ":" + '"' + Username + '"' + "," + '"' + "password" + '"' + ":" + '"' + Password + '"'+"}";
            System.out.println(jsonInputString);
            try(OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println(response);
                JSONObject temp = (JSONObject) jsonParser.parse(response.toString());
                x = (Long) temp.get("id");
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        catch (IOException e){
            System.out.println("Error");
        }
        return x;
    }

    /**
     * Used by other Java endpoints to fetch a User
     * @param id, id
     * @return User
     */
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

    /**
     * Gets all users?
     * @return
     */
    public ArrayList<User> checkUser(){
        JSONParser jsonParser = new JSONParser();
        ArrayList<User> users = new ArrayList<>();
        try {
            URL url = new URL ("http://localhost:8080/rest/api/user");
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
                JSONArray temp = (JSONArray) jsonParser.parse(response.toString());
                for(Object o : temp){
                    JSONObject user = (JSONObject) o;
                    User userTemp = new User(user.get("username").toString(), user.get("password").toString());
                    userTemp.setId((Long) user.get("id"));
                    users.add(userTemp);
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        catch (IOException e){
            System.out.println("Error");
        }
        return users;
    }

<<<<<<< master

    public User DelUser(Long id){
=======
    public User deleteUser(Long id){
>>>>>>> Completed user delete method functionality
        JSONParser jsonParser = new JSONParser();
        User user = new User();
        System.out.println(id);
        try {
            URL url = new URL ("http://localhost:8080/rest/api/user/Del/"+id.toString());
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("DELETE");
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
                user = new User(temp.get("username").toString(), temp.get("password").toString());

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        catch (IOException e){
            System.out.println("Error");
        }
        return user;

        // Button to Delete user
        // redirects to the DeletePortal page
        // add controller and HTML
        // Delete account portal
        // Redirects to a method in my controller
        // In the method, it will delete the user
        // DeletePortal.html
        // Test endpoint: going to that page, Delete portal
        // GET and POST request that works
        // Delete button, and then popup. Yes Delete, NO go back to user portal
    }

    /**
     * Use to change a users role
     * @param id, id
     * @param role, in the payload, String version of the RoleType
     * @return
     */
    public User changeUserRole(Long id, RoleType role){
        JSONParser jsonParser = new JSONParser();
        User user = new User();
        System.out.println(id);
        try {
            URL url = new URL ("http://localhost:8080/rest/api/user/upgrade/"+id.toString());
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
            String jsonInputString = "{" + '"' + "role" + '"' + ":" + '"' + role + '"'+"}";
            //System.out.println(jsonInputString);
            try(OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println("response: "+response);
                JSONObject temp = (JSONObject) jsonParser.parse(response.toString());
                //System.out.println(temp.get("username").toString());
                user = new User(temp.get("username").toString(), temp.get("password").toString(), RoleType.getRoleByString(temp.get("role").toString()));
                user.setId(Long.valueOf(temp.get("id").toString()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        catch (IOException e){
            System.out.println("Error changeUserRole");
        }
        return user;
    }
}
