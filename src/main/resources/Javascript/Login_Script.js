function myFunction() {
    $.ajax({
        type: "POST",
        url: "demo4806.herokuapp.com/rest/api/user/get/login",
        contentType : "application/json",
        dataType : 'json',
        data:JSON.stringify({"username": document.getElementById("username").value,"password" :document.getElementById("password").value}),
        success: function (data) {
            document.getElementById("custId").value = data.id;
            if(data.role === "ADMIN"){
                document.getElementById("adminHidden").value = "admin";
            }
            else{
                document.getElementById("adminHidden").value = "not";
            }
            document.getElementById("hello").submit();
        },
        error: function (data) {
            alert("Wrong Username and Password");
        }
    });
}