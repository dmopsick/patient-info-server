<?php
    // Start the session in order to use ssion variables
    session_start();
    echo "<!DOCTYPE html>
    <html>
        <head>
            <title>Mopsick Patient Info System: Get All Patients</title>
            <link rel='stylesheet' href='../css/style_guide.css'/>
            <link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css'>
            <script src='https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js'></script>
            <script src = '../js/script.js'></script>
        </head>
        <body>
            <nav id = 'navBar'></nav>
            <div class = 'container div-container-head'>
                <div class = 'col-md-12'>
                    <h2 class = 'myHeader'>Patient Info</h2>
                </div>
                <div class = 'col-md-4'></div>
                <div class = 'col-md-5'>";

                if(!isset($_SESSION['patientIndex'])){
                    /* Declare the url that I will be connecting to */
                    $url = "http://localhost:8080/patient/";

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
                    $_SESSION['jsonArray'] = json_decode($result, true);
                }

                if(isset($_SESSION['jsonArray'])){
                    $jsonArray = $_SESSION['jsonArray'];

                    if(!isset($_POST['patientIndex'])){
                        // Want the first patients displayed ot be the first 10 patients if not index is specified
                        $patientIndex = 0;
                    }
                    else{
                        // Specify as an index to start the display of patients
                        $patientIndex = $_POST['patientIndex'];
                    }
                    // Set the ending index of the patients to display as 9 after the beginning index
                    if(($patientIndex + 9) > count($jsonArray)){
                        $endIndex = count($jsonArray) - 1;
                    }
                    else{
                        $endIndex = $patientIndex + 9;
                    }
                    for($i = $patientIndex; $i <= $endIndex; $i++){
                        $patient = $jsonArray[$i];
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
                    // Display a next button and if applicable a previous button

                }
                else if($info['http_code'] === 301){
                    echo "<h3>Error: There appears to be an issue with the backend server.</h3>";
                }

                // Check to see if a next button should be displayed
                if(count($jsonArray) > $endIndex){
                    echo"<form action  = 'get_all.php' method = 'post'>
                        <input type = 'hidden' name = 'patientIndex' value = '" . ($endIndex + 1) . "'>
                        <input type = 'submit' value = 'Next'>
                    </form>";
                }

                // Check to see if a previous button should be displayed
                if($patientIndex != 0){
                    echo"<form action  = 'get_all.php' method = 'post'>
                        <input type = 'hidden' name = 'patientIndex' value = '" . ($patientIndex - 10) . "'>
                        <input type = 'submit' value = 'Previous'>
                    </form>";
                }
                echo "<br>
                <div id = 'returnButton'></div
                </div>
                <div class = 'col-md-3'></div>
                </div>
              </div>
              <br><br><br><br>
        </body>
        <footer id = 'footer'></footer>
    </html>"
 ?>
