<!DOCTYPE html>
<html lang="en">

<head>

  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="Host your Docker image online nginx,wordpress,jenkins,drupal,haproxy and many otehrs">
  <meta name="google-site-verification" content="bGqQHUWnhNwz3WOXE9hH6cprbhHfSI4q3mdWzLNHQSg" />
  <meta name="author" content="Anish Nath">
  
  <%@ include file="analytics.jsp"%>

  <title>Host your Docker image online </title>

  <!-- Bootstrap core CSS -->
  <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

  <!-- Custom styles for this template -->
  <link href="css/simple-sidebar.css" rel="stylesheet">

</head>

<body>

<%@ include file="navigation.jsp"%>



<div></div>
<hr>
  <div class="d-flex" id="wrapper">


<hr>
       <!-- Sidebar -->

<%@ include file="sidebar.jsp"%>

<!-- Sidebar Ends -->

    <!-- Page Content -->
    <div id="page-content-wrapper">



      <nav class="navbar navbar-expand-lg navbar-light bg-light border-bottom">
        <button class="btn btn-primary" id="menu-toggle">Menu</button>

        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>

        
      </nav>

      <div class="container-fluid">
      
       <a href="https://twitter.com/anish2good?ref_src=twsrc%5Etfw" class="twitter-follow-button" data-show-count="false">Follow Me for Updates</a><script async src="https://platform.twitter.com/widgets.js" charset="utf-8"></script></a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        
        
        <h1 class="mt-4">Welcome to 0cloud0.com</h1>
        <p>This is the container solution to host your docker images online. You can use this container service to host your docker image online. Right now the Hosting is only for HTTP based docker image</p>
      </div>
      
      <hr>
      
      <h3 class="mt-4">Docker Image deployments</h3>
      <p>Deploy you prefer stack or your custom http based docker image for example nginx,wordpress,phpldap,ansible tower, joomla etc....</p>
      <a href="add.jsp">
     					    <img class="img-fluid" src="images/playground/dockercustom.png"  alt="custom socker image">  
							<img class="img-fluid rounded" src="images/playground/nginx.png"  alt="nginx deploy online"> 
							<img class="img-fluid rounded" src="images/playground/tomcat.png"  alt="tomcat deploy online"> 
							<img class="img-fluid rounded" src="images/playground/haproxy.png"  alt="haproxy deploy online"> 
							<img class="img-fluid rounded" src="images/playground/wordpress.png"  alt="haproxy deploy online">
							<img class="img-fluid rounded" src="images/playground/phpldap.png"  alt="haproxy deploy online"> 
							<img class="img-fluid rounded" src="images/playground/jenkins.png"  alt="jenkins deploy online">
							<img class="img-fluid rounded" src="images/playground/drupal.png"  alt="drupal deploy online"> 
							<img class="img-fluid rounded" src="images/playground/joomla.png"  alt="joomla deploy online"> 
							
   </a>
  <h3 class="mt-4">Terminal Service</h3>
  <p>Launch you prefer terminal </p>
      
      <a href="playground">
      
      <img class="img-fluid rounded" src="images/playground/python3.png"  alt="python3">
      <img class="img-fluid rounded" src="images/playground/mariadb.png"  alt="mariadb10">
      
      <img class="img-fluid rounded" src="images/playground/centos.png"  alt="centos7">
      <img class="img-fluid rounded" src="images/playground/archlinux.png"  alt="archlinux">
      <img class="img-fluid rounded" src="images/playground/rhel7.png"  alt="rhel7">
      <img class="img-fluid rounded" src="images/playground/rhel8.png"  alt="rhel8">
      <img class="img-fluid rounded" src="images/playground/ubuntu.png"  alt="ubuntu">
      </a>
      
    <h3 class="mt-4">MySQL Service</h3>
   <p> <a href="sql.jsp">Get a Working copy of mysql Database online </a></p>
  
     
       <h3 class="mt-4">Other Feature </h3>
       <p> All you deployment will be served over https </p>
       <p> If you need custom DNS thats also supported </p>
       <p> Give a Try its free </p>
       
        <%@ include file="addcomments.jsp"%>
      
      <div class="sharethis-inline-share-buttons"></div>
      
    </div>
    <!-- /#page-content-wrapper -->

  </div>
  <!-- /#wrapper -->

  <!-- Bootstrap core JavaScript -->
  <script src="vendor/jquery/jquery.min.js"></script>
  <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

  <!-- Menu Toggle Script -->
  <script>
    $("#menu-toggle").click(function(e) {
      e.preventDefault();
      $("#wrapper").toggleClass("toggled");
    });
  </script>

</body>

</html>