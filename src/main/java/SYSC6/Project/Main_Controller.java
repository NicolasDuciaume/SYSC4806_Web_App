package SYSC6.Project;

import SYSC6.Project.user.User;
import SYSC6.Project.user.UserRepository;
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

    /**
     * Id of the current logged in User
     * TestFlag is a flag to determine if the initial admin set up was done or not
     */
    private Long id = 0L;
    private boolean testFlag = true;

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
        id = (long) Integer.parseInt(UserId);
        if(admin.equals("admin")){
            return "redirect:/admin_portal";
        }
        return "redirect:/user/"+id;
    }

    /**
     * Bring the user to the registration form
     * @return Registration page
     */
    @PostMapping("/Register")
    public String Register(){
        return "redirect:/Registration";
    }

    /**
     * Displays the html for the registration page
     * @return
     */
    @GetMapping("/Registration")
    public String Reg(){
        return "Registration";
    }

    @PostMapping("/noDelete")
    public String back() {
        return "redirect:/user_portal";
    }

    @PostMapping("/yes_delete")
    public String deleteUsers() {
        deleteUser(this.id);

        return "redirect:/";
    }

    @GetMapping("/confirm_delete")
    public String delete_page() {
        return "redirect:/confirm_delete";
    }

    @PostMapping("/confirm_delete")
    public String delete() {
        return "confirm_delete";
    }

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
            return "redirect:/user/"+id;
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


    @GetMapping("/admin_portal")
    public String greeting_admin(@RequestParam(name="name", required=false, defaultValue="World") String name_place, Model model) {
        if(id==0){
            return "login_form";
        }
        Admin user = getAdmin(id);
        if(UserUtil.hasAdmin(user)){
            model.addAttribute("name", "Admin");
            model.addAttribute("role", user.getRole().toString());
            ArrayList<User> users = getAllUsers();
            model.addAttribute("total_users",Integer.toString(users.size()));
            model.addAttribute("free_users",getFreeUsers(users));
            model.addAttribute("paid_users",getPaidUsers(users));
            model.addAttribute("admin_users",getAdmins(users));
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

    public ArrayList<User> getAllUsers(){
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
                JSONArray temp = (JSONArray) jsonParser.parse(response.toString());
                for(Object o : temp){
                    JSONObject user = (JSONObject) o;
                    User userTemp = new User(user.get("username").toString(), user.get("password").toString(),RoleType.getRoleByString(user.get("role").toString()));
                    userTemp.setId((Long) user.get("id"));
                    users.add(userTemp);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        catch (IOException e){
            System.out.println("getAllUsers() Error");
        }
        return users;
    }

    public String getFreeUsers(ArrayList<User> users){
        int counter = 0;
        for(User user : users){
            if(user.getRole() == RoleType.FREE_USER){
                counter++;
            }
        }
        return Integer.toString(counter);
    }
    public String getPaidUsers(ArrayList<User> users){
        int counter = 0;
        for(User user : users){
            if(user.getRole() == RoleType.PAID_USER){
                counter++;
            }
        }
        return Integer.toString(counter);
    }

    public String getAdmins(ArrayList<User> users){
        int counter = 0;
        for(User user : users){
            if(user.getRole() == RoleType.ADMIN){
                counter++;
            }
        }
        return Integer.toString(counter);
    }

    /**
     * Used by other Java endpoints to fetch a User
     * @param id, id
     * @return User
     */
    public User getUser(Long id){
        JSONParser jsonParser = new JSONParser();
        User user = new User();
//        System.out.println(id);
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
//                System.out.println(response);
                JSONObject temp = (JSONObject) jsonParser.parse(response.toString());
                //System.out.println(temp.get("username").toString());
                user = new User(temp.get("username").toString(), temp.get("password").toString(), RoleType.getRoleByString(temp.get("role").toString()));
                user.setId(Long.valueOf(temp.get("id").toString()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        catch (IOException e){
            System.out.println("getUser() Error");
        }
        return user;
    }

    public Admin getAdmin(Long id){
        JSONParser jsonParser = new JSONParser();
        Admin user = new Admin();
//        System.out.println(id);
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
                JSONObject temp = (JSONObject) jsonParser.parse(response.toString());
                user = new Admin(temp.get("username").toString(), temp.get("password").toString(), RoleType.getRoleByString(temp.get("role").toString()));
                user.setId(Long.valueOf(temp.get("id").toString()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        catch (IOException e){
            System.out.println("getUser() Error");
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
//                System.out.println(response);
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

    public User deleteUser(Long id){
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