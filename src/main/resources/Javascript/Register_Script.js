function myFunction() {
    console.log(document.getElementById("username").value);
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/rest/api/user/get/" + document.getElementById("username").value,
        contentType : "application/json",
        success: function (data) {
            document.getElementById("userHidden").value = document.getElementById("username").value
            document.getElementById("passHidden").value = document.getElementById("password").value
            createFunction()
        },
        error: function (data) {
            alert("Username already in use");
        }
    });
}

function createFunction(){
    $.ajax({
        type: "POST",
        url: "http://localhost:8080/rest/api/user/add",
        contentType : "application/json",
        data:JSON.stringify({"username": document.getElementById("userHidden").value,"password" : document.getElementById("passHidden").value}),
        success: function (data) {
            document.getElementById("idHidden").value = data.id;
            if(data.role === "ADMIN"){
                document.getElementById("adminHidden").value = "admin";
            }
            else{
                document.getElementById("adminHidden").value = "not";
            }
            document.getElementById("hello").submit();
        },
        error: function (data) {
            alert("Error Creating new user");
        }
    });
}