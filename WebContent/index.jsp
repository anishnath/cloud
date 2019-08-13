<!DOCTYPE html>
<html lang="en">

<head>

  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="Build and manage your Docker environments online">
  <meta name="google-site-verification" content="bGqQHUWnhNwz3WOXE9hH6cprbhHfSI4q3mdWzLNHQSg" />
  <meta name="author" content="Anish Nath">
  
  <%@ include file="analytics.jsp"%>

  <title>Build and manage your Docker environments online</title>

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
    <div class="bg-light border-right" id="sidebar-wrapper">
             <div class="sidebar-heading"></div>
      
      <div class="list-group list-group-flush">
         <a href="index.jsp" class="list-group-item list-group-item-action bg-light">Home</a>
      </div>
      
       <div class="list-group list-group-flush">
        <a href="login.jsp" class="list-group-item list-group-item-action bg-light">Login</a>
      </div>
      

      
      <div class="list-group list-group-flush">
        
      </div>
      
      <div class="list-group list-group-flush">
        
      </div>

     <div class="list-group list-group-flush">
        <a href="add.jsp" class="list-group-item list-group-item-action bg-light">Container Service</a>
      </div>
      
      <div class="list-group list-group-flush">
        <a href="sql.jsp" class="list-group-item list-group-item-action bg-light">MySQL Service</a>
      </div>
      
	<div class="list-group list-group-flush">
        <a href="contactus.jsp" class="list-group-item list-group-item-action bg-light">Contact Me</a>
      </div>
      
      <div class="list-group list-group-flush">
        <a href="https://www.linkedin.com/in/anishnath" class="list-group-item list-group-item-action bg-light">Hire Me!!</a>
      </div>
       <div class="list-group list-group-flush">
        <a href="https://8gwifi.org" class="list-group-item list-group-item-action bg-light">Cryptography Playground</a>
      </div>
    </div>
    <!-- /#sidebar-wrapper -->

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
        <p>This is the container solution to host your docker images online</p>
        <p>You can use this container service to host your docker image online even you can customize the DNS </p>
        <p>Right now the Hosting is only for HTTP based docker image, you can deploy other NON HTTP images also though the provisioning of service will not happen, though you can conitnue with other operation of your docker image</p>
      </div>
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