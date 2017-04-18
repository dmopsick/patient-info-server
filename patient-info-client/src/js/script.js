$(document).ready(function(){
    renderNavBar();
    renderFooter();
    // Creates a return to home button wherever a div with returnButton is created
    $("#returnButton").append("<form action = 'index.html'> <input class = 'submitButton' type = 'submit' value = 'Return Home'> </form>");

});

// Function usrd to render the navbar. Right now only creating it for desktop viewing
function renderNavBar(){
    // apply style to the nav bar
    $("#navBar").addClass("navbar navbar-default");

    // add the html into the <nav> tag to create the navbar
    document.getElementById("navBar").innerHTML = '<div class = "container-fluid"><div class = "navbar-header">'
    + '<a class = "navbar-brand" href = "index.html">Mopsick Patient Info System</a></div>'
    + '<ul class = "nav navbar-nav"><li><a href = "post_form.php">Add a Patient</a></li>'
    + '<li><a href = "get_one_byId.php">Get Patient by Id</a></li>'
    + '<li><a href = "get_one_byFamily.php">Get Patient by Family Name</a></li>'
    + '<li><a href = "get_all.php">Get all Patients</a></li></ul></div>';
}

// Function used to render the footer.
function renderFooter(){
    // Apply style to the footer
    $("#footer").addClass("footer")

    // Add the html into the <nav> tag to create the footer
    document.getElementById("footer").innerHTML = "<p class = 'myHeader'>Created by Dan Mopsick 2017</p>"
}
