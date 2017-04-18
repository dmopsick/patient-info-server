<?php
    require "../php/form_helper.php";
    echo "<!DOCTYPE html>
        <html>
        <head>
            <title>Mopsick Patient Info System: Submit</title>
            <link rel='stylesheet' href='../css/style_guide.css'/>
            <link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css'>
            <script src='https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js'></script>
            <script src = '../js/script.js'></script>
        </head>
        <body>
            <div id = 'page-wrapper'>
                <nav id = 'navBar'></nav>
                <div class = 'container div-container-head'>
                    <div class = 'col-md-12'>
                        <h2 class = 'myHeader'> Enter Patient Info</h2>
                    </div>
                    <div class = 'col-md-4'></div>
                    <div class = 'col-md-5'>
                        <br>
                        <form action = 'post_submission.php' method = 'post'>
                            <p class = 'formItem'>Given Name: <input  type = 'text' name ='givenName' maxlength = '35' required autofocus/></p>
                            <p class = 'formItem'>Family Name: <input type = 'text' name = 'familyName' maxlength = '35' required></p>

                            <p class = 'formItem'>Birthdate (mm-dd-yyyy): <input type = 'text' name = 'birthDate' maxlength = '10' size = '12' required></p>

                            <p class = 'formItem'>Street Address: <input type = 'text' name ='streetAddress' maxlength = '50' required></p>

                            <p class = 'formItem'>City: <input type ='text' name = 'city' maxlength = '35' required></p>

                            <p class = 'formItem'>State: " . insertStateList() .

                            "<p class = 'formItem'>Zipcode: <input type ='text' name = 'postalCode' maxlength = '5' required></p>

                            <input type = 'hidden' name = 'country' value = 'US'/>

                            <p class = 'formItem'>Diagnosis: <input type = 'text' name = 'diagnosis' maxlength = '9' required></p>

                            <p class = 'formItem'>Phone number (without dashes): <input type = 'text' name = 'phoneNumber' maxlength = '10' size = '10' required></p>

                            <p class = 'formItem'>Insurance provider: <input type = 'text' name = 'insuranceProvider' maxlength ='35' size = '10' required></p>

                            <p class = 'formItem'>Insurance Id: <input type = 'text' name = 'insuranceId' maxlength = '9' size = '10' required></p>
                            <br>
                            <input class = 'submitButton' type = 'submit'>
                        </form>
                        <br>
                        <form action = 'index.html'>
                            <input class = 'submitButton' type = 'submit' value = 'Return Home'>
                        </form>
                    </div>
                    <div class = 'col-md-3'></div>
                </div>
            </div>
        </body>
        <footer id = 'footer'></footer>
    </html>";
?>
