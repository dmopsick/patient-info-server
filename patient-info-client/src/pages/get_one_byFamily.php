<?php
    echo "<!DOCTYPE html>
    <html>
        <head>
            <title>Mopsick Patient Info System: Get One By Family</title>
            <link rel='stylesheet' href='../css/style_guide.css'/>
            <link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css'>
            <script src='https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js'></script>
            <script src = '../js/script.js'></script>
        </head>
        <body>
            <nav id = 'navBar'></nav>
            <div class = 'container div-container-head'>
                <div class = 'col-md-12'>
                    <h2 class = 'myHeader'>";
                    if(!isset($_POST['familyName'])){
                        echo "Enter a Family Name";
                    }
                    else{
                        echo "Requested Patient information";
                    }
                    echo "</h2>
                </div>
                <div class = 'col-md-4'></div>
                <div class = 'col-md-5'>";
                if(!isset($_POST['familyName'])){
                    echo "<br><form action = 'get_one_byFamily.php' method = 'post'>
                        <p class = 'searchLabel'> Family Name: <input type = 'input' name = 'familyName' maxLength = '35' size = '20' required></p>
                        <input class = 'submitButton' type = 'submit' value ='Search'>
                    </form>";
                }
                else{
                    /* Declare the url that I will be connecting to */
                    $url = "http://localhost:8080/patient/searchFamily/" . $_POST['familyName'];

                    // Initialize the connection
                    $ch = curl_init($url);

                    /* Build the request to the server */
                    curl_setopt($ch, CURLOPT_CUSTOMREQUEST, "GET");
                    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);

                    // Execute the request
                    $result = curl_exec($ch);
                    // echo print_r($result);

                    // Get info from the request
                    $info = curl_getinfo($ch);
                    // echo print_r($info);

                    // Close the connection to the backend server
                    curl_close($ch);

                    // Handle the response from the backend server
                    if($info['http_code'] == 404){
                        echo "<p>There are no patients in the system with the family name: " . $_POST['familyName'] . "<p>";
                    }
                    else if($info['http_code'] == 200){
                        // Decode the JSON returned from the request into an Array
                        $jsonArray = json_decode($result, true);

                        foreach($jsonArray as $patient){
                            echo "<h3 class = 'myHeader'>Info for Patient Id:" . $patient['id'] . "</h3>
                            <p class = 'formItem'>Given Name: " .$patient['givenName'] . "</p>
                            <p class = 'formItem'>Family Name: " . $patient['familyName'] . "</p>
                            <p class = 'formItem'>Birth date: " . $patient['birthDate'] . "</p>
                            <p class = 'formItem'>Address: " . $patient['address'] . "</p>
                            <p class = 'formItem'>Diagnosis: " . $patient['diagnosis'] . "</p>
                            <p class = 'formItem'>Phone number: ". $patient['phoneNumber'] . "</p>
                            <p class = 'formItem'>Insurance Provider: " . $patient['insuranceProvider'] . "</p>
                            <p class = 'formItem'>Insurance Id: " . $patient['insuranceId'] . "</p>
                            <br>";
                        }
                    }
                    else if($info['http_code'] === 301){
                        echo "<h3>Error: There appears to be an issue with the backend server.</h3>";
                    }
                }
                echo "<br>
                <div id = 'returnButton'></div
                </div>
                <div class = 'col-md-3'></div>
                </div>
              </div>
        </body>
        <footer id = 'footer'></footer>
    </html>"
 ?>
