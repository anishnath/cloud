<!DOCTYPE html>
<html lang="en">

<head>

  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="Build and manage your Docker environments online">
  <meta name="google-site-verification" content="bGqQHUWnhNwz3WOXE9hH6cprbhHfSI4q3mdWzLNHQSg" />
  <meta name="author" content="Anish Nath">
  
  <%@ include file="analytics.jsp"%>

  <title>Online Terminal Mysql,PHP,Python</title>

  <!-- Bootstrap core CSS -->
  <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

  <!-- Custom styles for this template -->
  <link href="css/simple-sidebar.css" rel="stylesheet">
  
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  
  	<script type="text/javascript">
		$(document).ready(function() {



			$('#form').submit(function(event) {
				//
				$('#msg').html('<p>Hang On tight We are building This will take approx 10-20 seconds')
				$('#output').html('<img src="images/712.GIF"> loading...');
				$('#python3').prop('disabled', true);
				$('#mariadb10').prop('disabled', true);
				event.preventDefault();
				$.ajax({
					type : "POST",
					url : "playground", //this is my servlet

					data : $("#form").serialize(),
					success : function(msg) {
						$('#msg').empty();
						$('#output').empty();
						$('#python3').prop('disabled', false);
						$('#mariadb10').prop('disabled', false);
						$('#output').append(msg);

					}
				});
			});
			
			$('#form1').submit(function(event) {
				//
				$('#msg').html('<p>Hang On tight We are building This will take approx 10-20 seconds')
				$('#output').html('<img src="images/712.GIF"> loading...');
				$('#python3').prop('disabled', true);
				$('#mariadb10').prop('disabled', true);
				event.preventDefault();
				$.ajax({
					type : "POST",
					url : "playground", //this is my servlet

					data : $("#form1").serialize(),
					success : function(msg) {
						$('#msg').empty();
						$('#output').empty();
						$('#python3').prop('disabled', false);
						$('#mariadb10').prop('disabled', false);
						$('#output').append(msg);

					}
				});
			});
			
		});
	</script>
  

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
        
        <div id="loading" style="display: none;">
			<img src="images/712.GIF" alt="" />Loading!
		</div>
        
        <%
        
        String sessionId = request.getSession().getId();
        System.out.println(sessionId);
        
        %>
        
        <h2 class="mt-4">Launch your preferred Terminal</h3>
        
        <table class="table table-striped">
  <thead>
    <tr>
      <th scope="col">
      
      <form action="" class="form-horizontal-row" id="form" method="POST">
	      <input type="hidden" name="csrf_token" id="csrf_token" value="<%=sessionId%>">
	      <input type="hidden" name="action" id="action" value="python3">
	      <input type="image" class="img-fluid img-thumbnail"  size="4" name="submit_blue" id="python3" value="python3" alt="python3" src="images/playground/python3.png">
	      </form>
      
      </th>
      <th scope="col">
      
      <form action="" class="form-horizontal-row" id="form1" method="POST">
	      <input type="hidden" name="csrf_token" id="csrf_token" value="<%=sessionId%>">
	      <input type="hidden" name="action" id="action" value="mariadb10">
	      <input type="image" class="img-fluid img-thumbnail"  size="4" name="submit_blue" id="mariadb10" value="mariadb10" alt="mariadb10" src="images/playground/mariadb.png">
	      </form>
      
      </th>
      
      
      
      
    </tr>
  </thead>
</table>
        
        
	      
	      
	      
	      
	      
	      <div id="msg"></div>
	      <div id="output"></div>
	      

	      
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