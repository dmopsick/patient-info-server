<?php
    /* Parse the passed patient info into variables to prepare for conversion to JSON objects.*/
    $givenName = $_POST['givenName'];
    $familyName = $_POST['familyName'];
    $birthDate = $_POST['birthDate'];
    // echo "FLAG: birthdate is  " . $birthDate . "<br><br>";
    $address = $_POST['streetAddress'] . ", " . $_POST['city'] . ", ". $_POST['state'] . ", " . $_POST['postalCode'] . ", " . $_POST['country'];
    $diagnosis = $_POST['diagnosis'];
    // Format the phone number to be ###-###-####
    $phoneNumber = substr($_POST['phoneNumber'], 0, 3) . "-" . substr($_POST['phoneNumber'], 3, 3) . "-" . substr($_POST['phoneNumber'], 6, 4);
    $insuranceProvider = $_POST['insuranceProvider'];
    $insuranceId = $_POST['insuranceId'];

    /* Put all the values to be passed via JSON in an array */
    $jsonArray = array(
        'givenName' => $givenName,
        'familyName' => $familyName,
        'birthDate' => $birthDate,
        'address' => $address,
        'diagnosis' => $diagnosis,
        'phoneNumber' => $phoneNumber,
        'insuranceProvider' => $insuranceProvider,
        'insuranceId' => $insuranceId
    );

    //echo "FLAG jsonArray is: " . print_r($jsonArray) . "<br><br><br>";

    /* Encode the array of values into a JSON object */
    $data = json_encode($jsonArray);

    /* Testing to see if json is properly formatted */
    // echo "FLAG jsonString is: " . $data . "<br><br>";

    /* Declare the url for sending the PHP via json, for now I will be doing it
    locally to my Java/Spring backend*/
    $url = "http://localhost:8080/patient/";

    // echo "FLAG the url being reached is: " . $url . "<br><br>";

    /* Initialize the connection */
    $ch = curl_init($url);

    /* Build the request itself */
    curl_setopt($ch, CURLOPT_CUSTOMREQUEST, "POST");
    curl_setopt($ch, CURLOPT_POSTFIELDS, $data);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($ch, CURLOPT_HTTPHEADER, array(
        'Content-Type: application/json',
        'Content-Length: ' . strlen($data)));

    $result = curl_exec($ch);
    $info = curl_getinfo($ch);
    // echo "result dump: " . var_dump($result) . "<br><br><br>";
    // echo "FLAG: " . print_r($info);

    echo "<!DOCTYPE html>
        <html>
            <head>
                <title>Mopsick Patient Info System</title>
                <link rel='stylesheet' href='../css/style_guide.css'/>
                <link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css'>
                <script src='https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js'></script>
                <script src = '../js/script.js'></script>
            </head>
            <body>
              <div id = 'page-wrapper'>
                  <nav id = 'navBar'></nav>
                  <div class = 'container div-container-head'>
                      <div class = 'col-md-12'>";
    if($info['http_code'] == 201){
    /* Display something to the user, not sure what yet */
    echo "              <h1 class = 'myHeader'>Patient succesfully added into the database.</h1>
                      </div>
                      <div class = 'col-md-4'></div>
                      <div class = 'col-md-5'>
                          <br>
                          <h3 class = 'myHeader'>Recorded Information</h3>
                          <p class = 'formItem'>Given Name: $givenName</p>
                          <p class = 'formItem'>Family Name: $familyName</p>
                          <p class = 'formItem'>Birth date: $birthDate</p>
                          <p class = 'formItem'>Address: $address</p>
                          <p class = 'formItem'>Diagnosis: $diagnosis</p>
                          <p class = 'formItem'>Phone number: $phoneNumber</p>
                          <p class = 'formItem'>Insurance Provider: $insuranceProvider</p>
                          <p class = 'formItem'>Insurance Id: $insuranceId</p>
                          <form action = 'form.php'>
                            <button class = 'submitButton' type = 'submit'>Submit another patient</button>
                          </form>
                      </div>
                      <div class = 'col-md-3'></div>
                  </div>
              </div>
          </body>
            </body>
        </html>";
    }
    else if($info['http_code'] === 0){
        echo "              <h1 class = 'myHeader'>Error: It looks like the backend server is down.</h1>
                        </div>
                        <div class = 'col-md-4'></div>
                        <div class = 'col-md-5'>
                            <br>
                            <form action = 'index.html'>
                                <input class = 'submitButton' type = 'submit' value = 'Return Home'>
                            </form>
                        </div>
                        <div class = 'col-md-3'></div>
                    </div>
                </div>
            </body>
        </html>";
    }

?>
